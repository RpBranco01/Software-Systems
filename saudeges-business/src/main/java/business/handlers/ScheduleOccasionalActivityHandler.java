package business.handlers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;

import business.activity.Activity;
import business.activity.ActivityCatalog;
import business.activity.OccasionalActivity;
import business.instructor.Instructor;
import business.instructor.InstructorCatalog;
import business.reservation.Reservation;
import business.reservation.ReservationCatalog;
import business.session.Session;
import business.specialty.Specialty;
import business.specialty.SpecialtyCatalog;
import business.utils.MockPayment;
import business.utils.UtilDate;
import facade.dtos.InstructorDTO;
import facade.dtos.OccasionalActivityDTO;
import facade.dtos.ReservationDTO;
import facade.exceptions.ActivityNotFoundException;
import facade.exceptions.ApplicationException;
import facade.exceptions.InstructorNotFoundException;
import facade.exceptions.InvalidDateException;
import facade.exceptions.InvalidNumSessionsException;
import facade.exceptions.SpecialtyNotFoundException;

/**
 * Handles the schedule occasional activity use case: 1)
 * scheduleOccasionalActivity 2) setScheduleOccasionalActivity 3)
 * concludeScheduleOccasionalActivityOperation
 */
@Stateless
public class ScheduleOccasionalActivityHandler {

	@EJB
	private ActivityCatalog ac;
	
	@EJB
	private SpecialtyCatalog sc;
	
	@EJB
	private InstructorCatalog ic;
	
	@EJB
	private ReservationCatalog rc;
	

	/**
	 * Given the specialty designation, return a list with all occasional activity
	 * names ordered by price
	 * 
	 * @param specialtyDesignation The specialty designation
	 * @return list of all occasional activity names
	 * @throws ApplicationException if an error occurs while attempting to get the
	 *                              list
	 */
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public List<OccasionalActivityDTO> scheduleOccasionalActivity(String specialtyDesignation) throws ApplicationException {
		try {
			Specialty specialty = sc.getSpecialty(specialtyDesignation);
			if (specialty == null) {
				throw new SpecialtyNotFoundException("Specialty requested does not exist");
			}

			List<Activity> list = ac.getActivityWithSpecialty(specialty);
			if (list.isEmpty()) {
				throw new ActivityNotFoundException("Activity list with specialty does not have activities");
			}

			List<OccasionalActivity> occasionalActivities = new ArrayList<>();
			for (Activity activity : list) {
				if (activity instanceof OccasionalActivity) {
					occasionalActivities.add((OccasionalActivity) activity);
				}
			}

			Collections.sort(occasionalActivities,
					(OccasionalActivity oa1, OccasionalActivity oa2) -> (int) (oa1.getPrice() - oa2.getPrice()));

			List<OccasionalActivityDTO> occasionalActivitiesDTO = new ArrayList<>();
			for (Activity activity : occasionalActivities) {
				OccasionalActivityDTO occasionalActivityDTO = new OccasionalActivityDTO((OccasionalActivity) activity);
				occasionalActivitiesDTO.add(occasionalActivityDTO);
			}

			return occasionalActivitiesDTO;
		} catch (Exception e) {
			throw new ApplicationException(
					"Error getting list of occasional activities with specialty \"" + specialtyDesignation + "\"", e);
		}
	}

	/**
	 * Given the activity name and a list of dates, checks which instructors are
	 * available
	 * 
	 * @param activityName      The activity name
	 * @param beginDateSessions The list of dates
	 * @return list of pairs with the id and the name of the available instructors
	 * @throws ApplicationException if an error occurs while attempting to get the
	 *                              list
	 */
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public List<InstructorDTO> setScheduleOccasionalActivity(String activityName, List<Date> beginDateSessions)
			throws ApplicationException {
		try {
			Activity activity = ac.getActivity(activityName);
			if (activity == null) {
				throw new ActivityNotFoundException("Activity not found with name " + activityName);
			}
			
			if (!(activity instanceof OccasionalActivity)) {
				throw new ActivityNotFoundException("Ocasional Activity not found with name " + activityName);
			}
			OccasionalActivity ocasionalActivity = (OccasionalActivity) activity;

			if (activity.getNumSessions() != beginDateSessions.size()) {
				throw new InvalidNumSessionsException("Invalid size of dates for sessions");
			}

			checkSessionsDates(beginDateSessions, ocasionalActivity);
			
			ocasionalActivity.addSessions(beginDateSessions);
		
			List<Instructor> instructorAvailable = ic.getAvailableInstructors(beginDateSessions,activity.getDuration());
			List<InstructorDTO> returnList = new ArrayList<>();
			for (Instructor instructor : instructorAvailable) {
				if(instructor.getCertifications().contains(activity.getSpecialty().getCertification())) {
					InstructorDTO instructorDTO = new InstructorDTO(instructor);
					returnList.add(instructorDTO);
				}	
			}
			return returnList;

		} catch (Exception e) {
			throw new ApplicationException(
					"Error getting list of available instructors for activity with name \"" + activityName + "\"", e);
		}
	}

	/**
	 * Given the required data, creates a reservation and sets the instructor for
	 * the each session
	 * 
	 * @param activityName The activity name
	 * @param instructorId The id of the chosen instructor
	 * @param email        The email associated to the reservation
	 * @return The string with the data needed to pay the reservation
	 * @throws ApplicationException if an error occurs while attempting to set the
	 *                              reservations
	 */
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public ReservationDTO concludeScheduleOccasionalActivityOperation(String activityName, int instructorId, String email)
			throws ApplicationException {
		try {
			Instructor instructor = ic.getInstructor(instructorId);
			if (instructor == null) {
				throw new InstructorNotFoundException("Instructor not found with id = " + instructorId);
			}

			Activity activity = ac.getActivity(activityName);
			if (activity == null) {
				throw new ActivityNotFoundException("Activity not found with name = " + activityName);
			}

			OccasionalActivity oa = (OccasionalActivity) activity;

			Reservation res = new Reservation(UtilDate.toDate(UtilDate.getMockCurrentDate()), email,
					MockPayment.generateMockEntity(), MockPayment.generateMockReference(), activity.getPrice());

			rc.addReservation(res);
			addReservations(oa, res, instructor);
			
			return new ReservationDTO(res);
		} catch (Exception e) {
			throw new ApplicationException("Error setting reservation for activity with name  \"" + activityName + "\"",
					e);
		}
	}

	/**
	 * Given the the list of dates and the activity, checks if the dates are not
	 * incompatible
	 * 
	 * @param beginDateSessions The list of dates
	 * @param activity          The activity
	 * @requires activity != null && beginDateSessions != null
	 * @throws InvalidDateException if an error occurs if the dates are incompatible
	 */
	private void checkSessionsDates(List<Date> beginDateSessions, Activity activity) throws InvalidDateException {
		Calendar calendar = Calendar.getInstance();
		for (Date date1 : beginDateSessions) {
			calendar.setTime(date1);
			calendar.add(Calendar.MINUTE, activity.getDuration());
			Date end1 = calendar.getTime();

			for (Date date2 : beginDateSessions) {
				calendar.setTime(date2);
				calendar.add(Calendar.MINUTE, activity.getDuration());
				Date end2 = calendar.getTime();

				final String exceptionName = "Session date intersection";

				if (!date1.equals(date2)) {
					if (date1.after(date2)) {
						if (!date1.after(end2)) {
							throw new InvalidDateException(exceptionName);
						}
					} else if (date2.after(date1)) {
						if (!date2.after(end1)) {
							throw new InvalidDateException(exceptionName);
						}
					} else {
						throw new InvalidDateException(exceptionName);
					}
				}
			}
		}
	}

	/**
	 * Adds the reservations and sets the instructor to the sessions of the
	 * occasional activity
	 * 
	 * @param oa         The occasional activity
	 * @param res        The reservation
	 * @param instructor The instructor
	 * @requires oa != null
	 */
	private void addReservations(OccasionalActivity oa, Reservation res, Instructor instructor) {
		for (Session session : oa.getAllSessions()) {
			session.addReservation(res);
			session.setInstructor(instructor);
		}
	}
}
