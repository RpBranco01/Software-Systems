package facade.interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import facade.dtos.InstructorDTO;
import facade.dtos.OccasionalActivityDTO;
import facade.dtos.ReservationDTO;
import facade.exceptions.ApplicationException;

@Remote
public interface IScheduleOccasionalActivityServiceRemote {

	public List<OccasionalActivityDTO> scheduleOccasionalActivity(String specialtyDesignation) throws ApplicationException;

	public List<InstructorDTO> setScheduleOccasionalActivity(String activityName, List<Date> beinDateSessions)
			throws ApplicationException;
	
	public ReservationDTO concludeScheduleOccasionalActivityOperation(String activityName, int instructorId, String email)
			throws ApplicationException;
}
