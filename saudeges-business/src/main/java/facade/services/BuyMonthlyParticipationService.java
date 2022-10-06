package facade.services;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.handlers.BuyMonthlyParticipationHandler;
import facade.dtos.ActivityScheduleDTO;
import facade.dtos.ReservationDTO;
import facade.exceptions.ApplicationException;
import facade.interfaces.IBuyMonthlyParticipationServiceRemote;

/**
 * A service that offers the required operations to buy a monthly participation,
 * hiding its implementation from the client.
 *
 */
@Stateless
public class BuyMonthlyParticipationService implements IBuyMonthlyParticipationServiceRemote {

	/**
	 * The business object facade that handles the use case "Buy Monthly
	 * Participation"
	 */
	@EJB
	private BuyMonthlyParticipationHandler mensalParticipationHandler;


	/**
	 * Given the activity name, returns the info for schedules of activity
	 * 
	 * @param activityName The name of the activity
	 * @return The pair of the list of schedules with the price of the activity
	 * @throws ApplicationException if an error occurs while attempting to get the data
	 */
	public ActivityScheduleDTO getSchedulesInfoForActivity(String activityName) throws ApplicationException {
		return mensalParticipationHandler.getSchedulesInfoForActivity(activityName);
	}

	/**
	 * Given the required data, it creates a reservation for each session during the
	 * given begin and duration
	 * 
	 * @param rasID        The schedule ID
	 * @param begin        The date which begins the reservation
	 * @param duration     The duration of the monthly participation
	 * @param email        The email associated to the reservation
	 * @param activityName The name of the activity in which the schedule is
	 *                     associated
	 * @return The string with the data needed to pay the monthly participation
	 * @throws ApplicationException if an error occurs while attempting to do the
	 *                              reservations
	 */
	public ReservationDTO chooseSchedule(int rasID, Date begin, int duration, String email, String activityName)
			throws ApplicationException {
		return mensalParticipationHandler.chooseSchedule(rasID, begin, duration, email, activityName);
	}

}
