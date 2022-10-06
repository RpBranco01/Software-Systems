package business.handlers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;

import business.activity.Activity;
import business.activity.ActivityCatalog;
import business.activity.RegularActivity;
import business.reservation.Reservation;
import business.reservation.ReservationCatalog;
import business.schedule.Schedule;
import business.schedule.ScheduleCatalog;
import business.session.Session;
import business.utils.MockPayment;
import business.utils.UtilDate;
import facade.dtos.ActivityScheduleDTO;
import facade.dtos.RegularActivityDTO;
import facade.dtos.ReservationDTO;
import facade.dtos.ScheduleDTO;
import facade.exceptions.ActivityNotFoundException;
import facade.exceptions.ApplicationException;
import facade.exceptions.InvalidDurationException;
import facade.exceptions.InvalidInstanceException;
import facade.exceptions.InvalidReservationException;
import facade.exceptions.ScheduleNotFoundException;

/**
 * Handles the buy monthly participation use case: 1) buyParticipation 2)
 * chooseSchedule
 */
@Stateless
public class BuyMonthlyParticipationHandler {

	@EJB
	private ActivityCatalog ac;
	
	@EJB
	private ScheduleCatalog sc;
	
	@EJB
	private ReservationCatalog rc;
	

	/**
	 * Given the activity name, returns a list with info of schedules 
	 * 
	 * @param activityName The name of the activity
	 * @return The pair of the list of schedules with the price of the activity
	 * @throws ApplicationException if an error occurs while attempting to get the data
	 */
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public ActivityScheduleDTO getSchedulesInfoForActivity(String activityName) throws ApplicationException {
		try {
			Activity a = ac.getActivity(activityName);
			

			if (a == null) {
				throw new ActivityNotFoundException("Name does not match any activity");
			}
			if (!(a instanceof RegularActivity)) {
				throw new InvalidInstanceException("Selected activity is not regular");
			}

			RegularActivity ra = (RegularActivity) a;
			List<ScheduleDTO> result = scheduleToDTO(ra.getSchedules());
			RegularActivityDTO regularActivityDTO = new RegularActivityDTO((RegularActivity) a);
			return new ActivityScheduleDTO(result, regularActivityDTO);

		} catch (Exception e) {
			throw new ApplicationException("Error getting shedule list from activity \"" + activityName + "\"", e);
		}
	}

	/**
	 * Given the required data, it creates a reservation for each session during the
	 * given begin and duration
	 * 
	 * @param scheduleID   The schedule ID
	 * @param begin        The date which begins the reservation
	 * @param duration     The duration of the monthly participation
	 * @param email        The email associated to the reservation
	 * @param activityName The name of the activity in which the schedule is
	 *                     associated
	 * @return The string with the data needed to pay the monthly participation
	 * @throws ApplicationException if an error occurs while attempting to do the
	 *                              reservations
	 */
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public ReservationDTO chooseSchedule(int scheduleID, Date begin, int duration, String email, String activityName)
			throws ApplicationException {
		try {
		
			Activity a = ac.getActivity(activityName);
			if (a == null) {
				throw new ActivityNotFoundException("Name does not match any activity");
			}

			if (!(a instanceof RegularActivity)) {
				throw new InvalidInstanceException("Selected activity is not regular");
			}

			Schedule schedule = sc.getRegularActivitySchedule(scheduleID);
			if (schedule == null) {
				throw new ScheduleNotFoundException("Could not find schedule with id = " + scheduleID);
			}

			if (duration > schedule.getDurationInMonths()) {
				throw new InvalidDurationException("The duration of the reservation is invalid");
			}

			Reservation reservation = new Reservation(UtilDate.toDate(UtilDate.getMockCurrentDate()), email,
					MockPayment.generateMockEntity(), MockPayment.generateMockReference(), a.getPrice() * duration);
			
			rc.addReservation(reservation);

			addReservations(schedule, reservation, begin, duration);
			
			return new ReservationDTO(reservation);

		} catch (Exception e) {
			throw new ApplicationException("Error setting reservation", e);
		}
	}

	/**
	 * With the required data, add the reservation res in the list of reservations
	 * in session
	 * 
	 * @param schedule The schedule
	 * @param res      The reservation to add
	 * @param begin    The date which begins the reservation
	 * @param duration The duration of the monthly participation
	 * @throws ApplicationException if an error occurs while attempting to add the
	 *                              reservation
	 */
	private void addReservations(Schedule schedule, Reservation res, Date begin, int duration)
			throws InvalidReservationException {
		for (Session session : schedule.getSessionsBetween(begin, duration)) {
			if (session.getNumPlaces() <= 0) {
				throw new InvalidReservationException("No available slots");
			}
			session.addReservation(res);
		}
	}

	/**
	 * With the given data, creates the list of scheduleDTOs
	 * 
	 * @param schedules The list of schedules
	 * @return The list of scheduleDTOs
	 */
	private List<ScheduleDTO> scheduleToDTO(List<Schedule> schedules) {
		List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
		for (Schedule schedule : schedules) {
			scheduleDTOs.add(new ScheduleDTO(schedule));
		}
		return scheduleDTOs;
	}
}
