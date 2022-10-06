package facade.services;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.handlers.SetNewScheduleHandler;
import business.schedule.WeekDayTimeSlot;
import facade.dtos.Pair;
import facade.dtos.RegularActivityDTO;
import facade.exceptions.ApplicationException;
import facade.interfaces.ISetNewScheduleServiceRemote;

/**
 * A service that offers the required operations to set new schedule, hiding its
 * implementation from the client.
 *
 */
@Stateless
public class SetNewScheduleService implements ISetNewScheduleServiceRemote {

	/**
	 * The business object facade that handles the use case "Set new Schedule"
	 */
	@EJB
	private SetNewScheduleHandler scheduleHandler;


	/**
	 * Returns the list of all regular activity names
	 * 
	 * @return list of all regular activity names
	 * @throws ApplicationException 
	 */
	public List<RegularActivityDTO> getRegularActivities() throws ApplicationException {
		return scheduleHandler.getRegularActivities();
	}

	/**
	 * Given the required data sets the schedule for the named activity
	 * 
	 * @param activityName       The activity name
	 * @param weekSessionsDates  The list with the weekSessionsDates
	 * @param begin              The beginning date of the schedule
	 * @param monthlyDuration    The monthly duration of the schedule
	 * @param instructorID       The id of the instructor
	 * @param instructorDuration The duration of the instructor instruction
	 * @throws ApplicationException if an error occurs while attempting to create
	 *                              the schedule
	 */
	public void setSchedule(String name, List<Pair<DayOfWeek, LocalTime>> weekSessionsDates, Date begin,
			int monthlyDuration, int instructorID, int instructorDuration) throws ApplicationException {
		
		List<WeekDayTimeSlot> weekSessionTimeSlots = new ArrayList<>();
		for (Pair<DayOfWeek, LocalTime> wsd : weekSessionsDates)
			weekSessionTimeSlots.add(new WeekDayTimeSlot(wsd.getValue0(),wsd.getValue1()));
		
		scheduleHandler.setSchedule(name, weekSessionTimeSlots, begin, monthlyDuration, instructorID, instructorDuration);
	}
}
