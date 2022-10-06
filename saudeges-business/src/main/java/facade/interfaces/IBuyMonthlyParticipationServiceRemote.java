package facade.interfaces;

import java.util.Date;

import javax.ejb.Remote;

import facade.dtos.ActivityScheduleDTO;
import facade.dtos.ReservationDTO;
import facade.exceptions.ApplicationException;

@Remote
public interface IBuyMonthlyParticipationServiceRemote {

	public ActivityScheduleDTO getSchedulesInfoForActivity(String activityName) throws ApplicationException; 

	public ReservationDTO chooseSchedule(int rasID, Date begin, int duration, String email, String activityName)
			throws ApplicationException;
}
