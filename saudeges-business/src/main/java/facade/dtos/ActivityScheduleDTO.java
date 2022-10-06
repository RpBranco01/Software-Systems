package facade.dtos;

import java.io.Serializable;
import java.util.List;

public class ActivityScheduleDTO implements Serializable{
	
	private List<ScheduleDTO> schedules;
	private RegularActivityDTO ra;
	
	public ActivityScheduleDTO(List<ScheduleDTO> schedules, RegularActivityDTO ra) {
		this.schedules = schedules;
		this.ra = ra;
	}
	
	public List<ScheduleDTO> getSchedules() {
		return this.schedules;
	}

	public RegularActivityDTO getRegularActivity() {
		return this.ra;
	}
}
