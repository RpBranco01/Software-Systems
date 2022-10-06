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
import business.instructor.Instructor;
import business.instructor.InstructorCatalog;
import business.schedule.WeekDayTimeSlot;
import business.session.SessionCatalog;
import business.utils.UtilDate;
import facade.dtos.BeginEndDTO;
import facade.dtos.RegularActivityDTO;
import facade.exceptions.ActivityNotFoundException;
import facade.exceptions.ApplicationException;
import facade.exceptions.InstructorNotFoundException;
import facade.exceptions.InvalidDateException;
import facade.exceptions.InvalidDurationException;
import facade.exceptions.InvalidInstructorException;
import facade.exceptions.InvalidNumSessionsException;
import facade.exceptions.UnavailableInstructorException;

/**
 * Handles the regular activity schedule creation use case: 1) createSchedule 2)
 * setSchedule
 * 
 */
@Stateless
public class SetNewScheduleHandler {
	
	@EJB
	private SessionCatalog sc;
	
	@EJB
	private ActivityCatalog ac;
	
	@EJB
	private InstructorCatalog ic;


	/**
	 * Returns the list of all regular activity names
	 * 
	 * @return list of all occasional activity names
	 * @throws ApplicationException 
	 */
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public List<RegularActivityDTO> getRegularActivities() throws ApplicationException {
		List<RegularActivityDTO> regularActivityDTOs = new ArrayList<>();
		try {
			
			List<Activity> test = ac.getAllActivities();
			
			for (Activity activity : test) {
				if(activity instanceof RegularActivity) {
					RegularActivityDTO regularActivityDTO = new RegularActivityDTO((RegularActivity) activity);
					regularActivityDTOs.add(regularActivityDTO);
				}
			}
		} catch (Exception e) {
			throw new ApplicationException("Error getting regular activities", e);
		}
		return regularActivityDTOs;
	}

	/**
	 * Given the required data sets the schedule for the named activity
	 * 
	 * @param activityName       The activity name
	 * @param weekSessionsDates  The list with the weekSessionsDates
	 * @param beginDate              The beginning date of the schedule
	 * @param monthlyDuration    The monthly duration of the schedule
	 * @param instructorID       The id of the instructor
	 * @param instructorDuration The duration of the instructor instruction
	 * @throws ApplicationException if an error occurs while attempting to create
	 *                              the schedule
	 */
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public void setSchedule(String activityName, List<WeekDayTimeSlot> weekSessionsDates, Date beginDate,
			int monthlyDuration, int instructorID, int instructorDuration) throws ApplicationException {

		try {
			RegularActivity activity = (RegularActivity) ac.getActivity(activityName);
			if (activity == null) 
				throw new ActivityNotFoundException("Name: " + activityName);
			
			if (weekSessionsDates.size() > activity.getNumSessions()) 
				throw new InvalidNumSessionsException(
						String.format("weekSessionsDates's size (%d) greater than activity's week sessions (%d)",
								weekSessionsDates.size(), activity.getNumSessions()));

			if (!beginDate.after(UtilDate.toDate(UtilDate.getMockCurrentDate()))) 
				throw new InvalidDateException("Date of beginning is invalid");

			if (monthlyDuration < 1) 
				throw new InvalidDurationException("Monthly duration invalid");

			Instructor i = ic.getInstructor(instructorID);
			if (i == null) 
				throw new InstructorNotFoundException(
						String.format("Instructor with id \"%d\" not found", instructorID));
			
			if (!i.hasRightCertification(activity.getSpecialty())) 
				throw new InvalidInstructorException(
						"Instructor doesn't have the required certification: " + activity.getSpecialty().getCertification());

			if (monthlyDuration < instructorDuration) {
				throw new InvalidDurationException(
						String.format("Monthly duration (%d) smaller than instructor duration (%d)", monthlyDuration,
								instructorDuration));
			}
			List<BeginEndDTO> timeSlotsInstructor = activity.getTimeSlots(instructorDuration,beginDate,weekSessionsDates);
			if (!sc.isInsctructorAvailable(i,timeSlotsInstructor))
				throw new UnavailableInstructorException("Instructor is unavailable");
			
			activity.addSchedule(monthlyDuration,beginDate,weekSessionsDates, i, instructorDuration);

		} catch (Exception e) {
			throw new ApplicationException("Error setting timetable with name \"" + activityName + "\"", e);
		}
	}

}