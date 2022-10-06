package facade.startup;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import facade.services.BuyMonthlyParticipationService;
import facade.services.CreateActivityService;
import facade.services.ScheduleOccasionaActivityService;
import facade.services.SetNewScheduleService;
//TODO: isto é meio inventado

public class SaudeGesSys {
	
	private CreateActivityService createActivity;
	
	private SetNewScheduleService setSchedule;
	
	private BuyMonthlyParticipationService buyParticipation;
	
	private ScheduleOccasionaActivityService scheduleOccationActivity;
	
	
//  private EntityManagerFactory emf;
//	public void run() {
//		try {
//			emf = Persistence.createEntityManagerFactory("saudeges-business");
//		} catch (Exception e) {
//			System.out.println("Error connecting to database. Error: " + e.getMessage());
//		}
//	}

//	/**
//	 * Closes the database connection
//	 */
//	public void stopRun() {
//		emf.close();
//	}

	/**
	 * Returns the activity service
	 * 
	 * @return The activity service
	 */
	public CreateActivityService getActivityService() {
		return this.createActivity;
	}

	/**
	 * Returns a monthly participation service
	 * 
	 * @return a monthly participation service
	 */
	public BuyMonthlyParticipationService getMensalParticipationService() {
		return this.buyParticipation;
	}

	/**
	 * Returns a set new schedule service
	 * 
	 * @return a set new schedule service
	 */
	public SetNewScheduleService getScheduleService() {
		return this.setSchedule;
	}

	/**
	 * Returns a schedule occasional activity service
	 * 
	 * @return a schedule occasional activity service
	 */
	public ScheduleOccasionaActivityService getScheduleOccasionalActivityHandler() {
		return this.scheduleOccationActivity;
	}
}
