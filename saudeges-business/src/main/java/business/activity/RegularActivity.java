package business.activity;

import static javax.persistence.CascadeType.ALL;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import business.instructor.Instructor;
import business.schedule.Schedule;
import business.schedule.WeekDayTimeSlot;
import business.specialty.Specialty;
import business.utils.UtilDate;
import facade.dtos.BeginEndDTO;

/**
 * Entity implementation class for Entity: RegularActivity Subclass of Activity
 */
@Entity
@DiscriminatorValue(value = "Regular")
public class RegularActivity extends Activity {

	private int numMaxParticipants;

	@OneToMany(cascade = { ALL })
	private List<Schedule> scheduleList = new ArrayList<>();

	/**
	 * Constructor needed by JPA.
	 */
	public RegularActivity() {
	}

	/**
	 * Constructs an RegularActivity given its data
	 * 
	 * @param name               The name of the regular activity
	 * @param specialty          The specialty of the regular activity
	 * @param price              The price of the regular activity
	 * @param weekSessions       The number of sessions of the regular activity
	 * @param sessionDuration    The duration of each session in the regular
	 *                           activity
	 * @param numMaxParticipants The max number of participants per session in the
	 *                           regular activity
	 * @requires price != null && price > 0 && specialty != null &&
	 *           numMaxParticipants > 0;
	 */
	public RegularActivity(int numMaxParticipants, String name, Specialty specialty, long price, int weekSessions,
			int sessionDuration) {
		super(name, specialty, price, weekSessions, sessionDuration);
		this.numMaxParticipants = numMaxParticipants;
	}
	
	/**
	 * Adds a new Schedule to a RegularActivity with the given data
	 * @param monthlyDuration 
	 * @param begin
	 * @param ...
	 * @requires ...
	 */
	public void addSchedule(int monthlyDuration, Date begin, List<WeekDayTimeSlot> weekSessionsDates, Instructor i, int instructorDuration) {
		Schedule schedule = new Schedule(monthlyDuration, begin, weekSessionsDates, i, instructorDuration, numMaxParticipants, sessionDuration);
		scheduleList.add(schedule);
	}

	/**
	 * Gets the list of RegularActivitySchedule
	 * 
	 * @return The list of schedule
	 */
	public List<Schedule> getSchedules() {
		return scheduleList;
	}

	/**
	 * Gets the max number of participants per session of the regular activity
	 * 
	 * @return The max number of participants per session of the regular activity
	 */
	public int getNumMaxParticipants() {
		return numMaxParticipants;
	}

	/**
	 * Gets the times slots required for a schedule of this regular activity 
	 * during a given number of months starting in a given date with a 
	 * given schedule
	 * 
	 * @return The list of time slots 
	 * @requires weekSessionsDates.size() == numSessions
	 */	
	public List<BeginEndDTO> getTimeSlots(int howManyMonths, Date beginDate,
			List<WeekDayTimeSlot> weekSessionsDates) {
		List<BeginEndDTO> result = new ArrayList<>();

		Date endDate = UtilDate.toDate(UtilDate.toLocalDate(beginDate).plusMonths(howManyMonths));
		Calendar calendar = Calendar.getInstance();
		int weekday;

		for (WeekDayTimeSlot wdts : weekSessionsDates) {
			calendar.setTime(beginDate);
			weekday = calendar.get(Calendar.DAY_OF_WEEK);

			while (weekday != dayOfWeekToInt(wdts.getDay())) {
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				weekday = calendar.get(Calendar.DAY_OF_WEEK);
			}
			
			LocalTime ldate = UtilDate.toLocalTime(wdts.getTime());
			calendar.set(Calendar.HOUR_OF_DAY, ldate.getHour());
			calendar.set(Calendar.MINUTE, ldate.getMinute());

			Date date = calendar.getTime();

			while (endDate.after(date)) {
				result.add(new BeginEndDTO(date, UtilDate.addMinutes(date,sessionDuration)));
				calendar.add(Calendar.DATE, 7);
				date = calendar.getTime();
			}
		}	
		return result;
	}
	

	/**
	 * Given a day of week, returns the correspondent Integer
	 *
	 * @return the correspondent int for the day of week, if error return 0
	 */
	//TODO: ordinal nao serve? expressao com relação entre um e outro?
	public static int dayOfWeekToInt(DayOfWeek dow) {
		int d = 0;
		
		if(dow == DayOfWeek.SUNDAY) {
			d = 1;
		}else if(dow == DayOfWeek.MONDAY) {
			d = 2;
		}else if(dow == DayOfWeek.TUESDAY) {
			d = 3;
		}else if(dow == DayOfWeek.WEDNESDAY) {
			d = 4;
		}else if(dow == DayOfWeek.THURSDAY) {
			d = 5;
		}else if(dow == DayOfWeek.FRIDAY) {
			d = 6;
		}else if(dow == DayOfWeek.SATURDAY) {
			d = 7;
		}
		
		return d;
	}


}
