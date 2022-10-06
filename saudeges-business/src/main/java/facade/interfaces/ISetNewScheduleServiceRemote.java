package facade.interfaces;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import facade.dtos.Pair;
import facade.dtos.RegularActivityDTO;
import facade.exceptions.ApplicationException;

@Remote
public interface ISetNewScheduleServiceRemote {

	public List<RegularActivityDTO> getRegularActivities() throws ApplicationException;

	public void setSchedule(String name, List<Pair<DayOfWeek, LocalTime>> weekSessionsDates, Date begin,
			int monthlyDuration, int instructorID, int instructorDuration) throws ApplicationException;
}
