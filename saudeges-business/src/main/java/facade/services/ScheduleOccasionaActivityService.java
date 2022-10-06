package facade.services;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.handlers.ScheduleOccasionalActivityHandler;
import facade.dtos.InstructorDTO;
import facade.dtos.OccasionalActivityDTO;
import facade.dtos.ReservationDTO;
import facade.exceptions.ApplicationException;
import facade.interfaces.IScheduleOccasionalActivityServiceRemote;

/**
 * A service that offers the required operations to schedule an occasional
 * activity, hiding its implementation from the client.
 *
 */
@Stateless
public class ScheduleOccasionaActivityService implements IScheduleOccasionalActivityServiceRemote {

	/**
	 * The business object facade that handles the use case "Schedule Occasional
	 * Activity"
	 */
	@EJB
	private ScheduleOccasionalActivityHandler handler;

	/**
	 * Given the specialty designation, return a list with all occasional activity
	 * names ordered by price
	 * 
	 * @param specialtyDesignation The specialty designation
	 * @return list of all occasional activity names
	 * @throws ApplicationException if an error occurs while attempting to get the
	 *                              list
	 */
	public List<OccasionalActivityDTO> scheduleOccasionalActivity(String specialtyDesignation) throws ApplicationException {
		return handler.scheduleOccasionalActivity(specialtyDesignation);
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
	public List<InstructorDTO> setScheduleOccasionalActivity(String activityName, List<Date> beinDateSessions)
			throws ApplicationException {
		return handler.setScheduleOccasionalActivity(activityName, beinDateSessions);
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
	public ReservationDTO concludeScheduleOccasionalActivityOperation(String activityName, int instructorId, String email)
			throws ApplicationException {
		return handler.concludeScheduleOccasionalActivityOperation(activityName, instructorId, email);
	}
}
