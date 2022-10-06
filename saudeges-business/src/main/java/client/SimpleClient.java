//package client;
//
//import java.time.DayOfWeek;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//import business.utils.UtilDate;
//import facade.dtos.Pair;
//import facade.dtos.ScheduleDTO;
//import facade.exceptions.ApplicationException;
//import facade.services.BuyMonthlyParticipationService;
//import facade.services.CreateActivityService;
//import facade.services.ScheduleOccasionaActivityService;
//import facade.services.SetNewScheduleService;
//import facade.startup.SaudeGesSys;
//
///**
// * Class that runs the main with tests
// * 
// * @author Rodrigo Branco
// * @author Vasco Lopes
// * @author Sérgio Ferreira
// */
//public class SimpleClient {
//
//	static final String cause = "        Cause: ";
//	static final String error = "        Error: ";
//
//	static final String massage = "Massagem Desportiva";
//	static final String fisio = "Fisioterapia";
//	static final String pilates1x = "Pilates 1x";
//	static final String pilates2x = "Pilates 2x";
//
//	public static void main(String[] args) {
//
//		System.setErr(System.out);
//
//		SaudeGesSys app = new SaudeGesSys();
//
//		try {
////			app.run();
//			CreateActivityService as = app.getActivityService();
//
//			// Teste 1
//			System.out.println("Teste 1:");
//			createActivity(as, "Fisioterapia 1", false, fisio, 1, 45, 43);
//			System.out.println();
//
//			// Teste 2
//			System.out.println("Teste 2:");
//			createActivity(as, "Fisioterapia 5", false, fisio, 5, 45, 195);
//			System.out.println();
//
//			// Teste 3
//			System.out.println("Teste 3:");
//			createActivity(as, "Massagem 3x60", false, massage, 3, 60, 200);
//			System.out.println();
//
//			// Teste 4
//			System.out.println("Teste 4:");
//			try {
//				createActivity(as, "Massagem 3x20", false, massage, 3, 20, 100);
//			} catch (ApplicationException e) {
//				printException(e);
//			}
//			System.out.println();
//
//			// Teste 5
//			System.out.println("Teste 5:");
//			createActivity(as, pilates2x, true, "Pilates Clínico", 2, 45, 60, 5);
//			System.out.println();
//
//			// Teste 6
//			System.out.println("Teste 6:");
//			createActivity(as, pilates1x, true, "Pilates Clínico", 1, 45, 40, 2);
//			System.out.println();
//
//			// Teste 7
//			System.out.println("Teste 7:");
//			try {
//				createActivity(as, "Pós Parto 3x", true, "Pós Parto", 3, 30, 40);
//			} catch (ApplicationException e) {
//				printException(e);
//			}
//			System.out.println();
//
//			// Teste 8
//			System.out.println("Teste 8:");
//			SetNewScheduleService ds = app.getScheduleService();
//			List<Pair<DayOfWeek, LocalTime>> weekSessionsDates = new ArrayList<>();
//			weekSessionsDates.add(Pair.with(DayOfWeek.TUESDAY, LocalTime.of(14, 0)));
//			weekSessionsDates.add(Pair.with(DayOfWeek.THURSDAY, LocalTime.of(14, 0)));
//			Date beginDate = UtilDate.toDate(LocalDate.of(2022, 4, 1));
//
//			try {
//				setSchedule(ds, pilates2x, weekSessionsDates, beginDate, 6, 2, 2);
//			} catch (ApplicationException e) {
//				printException(e);
//			}
//			System.out.println();
//
//			// Teste 9
//			System.out.println("Teste 9:");
//			setSchedule(ds, pilates2x, weekSessionsDates, beginDate, 6, 1, 2);
//			System.out.println();
//
//			// Teste 10
//			System.out.println("Teste 10:");
//			weekSessionsDates = new ArrayList<>();
//			weekSessionsDates.add(Pair.with(DayOfWeek.MONDAY, LocalTime.of(14, 30)));
//			weekSessionsDates.add(Pair.with(DayOfWeek.THURSDAY, LocalTime.of(14, 30)));
//			beginDate = UtilDate.toDate(LocalDate.of(2022, 4, 18));
//
//			try {
//				setSchedule(ds, pilates2x, weekSessionsDates, beginDate, 6, 1, 1);
//			} catch (ApplicationException e) {
//				printException(e);
//			}
//			System.out.println();
//
//			// Teste 11
//			System.out.println("Teste 11:");
//			weekSessionsDates = new ArrayList<>();
//			weekSessionsDates.add(Pair.with(DayOfWeek.MONDAY, LocalTime.of(14, 30)));
//			beginDate = UtilDate.toDate(LocalDate.of(2022, 4, 1));
//			setSchedule(ds, pilates1x, weekSessionsDates, beginDate, 3, 1, 3);
//			System.out.println();
//
//			// Teste 12
//			System.out.println("Teste 12:");
//			weekSessionsDates = new ArrayList<>();
//			weekSessionsDates.add(Pair.with(DayOfWeek.FRIDAY, LocalTime.of(14, 30)));
//			beginDate = UtilDate.toDate(LocalDate.of(2022, 4, 18));
//			setSchedule(ds, pilates1x, weekSessionsDates, beginDate, 3, 1, 3);
//
//			BuyMonthlyParticipationService bs = app.getMensalParticipationService();
//			System.out.println();
//
//			// Teste 13
//			System.out.println("Teste 13:");
//			beginDate = UtilDate.toDate(LocalDate.of(2022, 4, 1));
//			buyMonthlyParticipation(bs, pilates1x, beginDate, 3, "u1@gmail.com");
//			System.out.println();
//
//			// Teste 14
//			System.out.println("Teste 14:");
//			beginDate = UtilDate.toDate(LocalDate.of(2022, 4, 11));
//			buyMonthlyParticipation(bs, pilates1x, beginDate, 1, "u2@gmail.com");
//			System.out.println();
//
//			// Teste 15
//			System.out.println("Teste 15:");
//			beginDate = UtilDate.toDate(LocalDate.of(2022, 4, 18));
//			try {
//				buyMonthlyParticipation(bs, pilates1x, beginDate, 1, "u3@gmail.com");
//			} catch (ApplicationException e) {
//				printException(e);
//			}
//			System.out.println();
//
//			// Teste 16
//			System.out.println("Teste 16:");
//			beginDate = UtilDate.toDate(LocalDate.of(2022, 5, 16));
//			buyMonthlyParticipation(bs, pilates1x, beginDate, 1, "u3@gmail.com");
//			System.out.println();
//
//			// Teste 17
//			System.out.println("Teste 17:");
//			ScheduleOccasionaActivityService soas = app.getScheduleOccasionalActivityHandler();
//			Calendar cal = Calendar.getInstance();
//			List<Date> mydates = new ArrayList<>();
//			Date newDate;
//			newDate = UtilDate.toDate(LocalDate.of(2022, 3, 28));
//			cal.setTime(newDate);
//			cal.set(Calendar.HOUR_OF_DAY, 10);
//			cal.set(Calendar.MINUTE, 0);
//			mydates.add(cal.getTime());
//
//			newDate = UtilDate.toDate(LocalDate.of(2022, 4, 4));
//			cal.setTime(newDate);
//			cal.set(Calendar.HOUR_OF_DAY, 14);
//			cal.set(Calendar.MINUTE, 0);
//			mydates.add(cal.getTime());
//
//			newDate = UtilDate.toDate(LocalDate.of(2022, 4, 11));
//			cal.setTime(newDate);
//			cal.set(Calendar.HOUR_OF_DAY, 12);
//			cal.set(Calendar.MINUTE, 0);
//			mydates.add(cal.getTime());
//
//			scheduleOccasionalActivity(soas, massage, mydates, 2, "u4@gmail.com");
//			System.out.println();
//			
//			// Teste EXTRA
//			System.out.println("Teste EXTRA:");
//			beginDate = UtilDate.toDate(LocalDate.of(2022, 5, 1));
//			buyMonthlyParticipation(bs, pilates2x, beginDate, 1, "mal@gmail.com");
//			System.out.println();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static void createActivity(CreateActivityService as, String activityName, boolean isRegular,
//			String specialtyDesignation, int numSessions, int sessionDuration, int price) throws ApplicationException {
//		System.out.print(" - Available specialties: ");
//		System.out.println(as.getSpecialities());
//		System.out.println("Creating new activity named \"" + activityName + "\" with chosen specialty \""
//				+ specialtyDesignation + "\"");
//		as.addActivity(activityName, specialtyDesignation, isRegular, numSessions, sessionDuration, price);
//		System.out.println("Activity sucessfully added!");
//	}
//
//	public static void createActivity(CreateActivityService as, String activityName, boolean isRegular,
//			String specialtyDesignation, int numSessions, int sessionDuration, int price, int numMaxParticipants)
//			throws ApplicationException {
//		System.out.print(" - Specialty names: ");
//		System.out.println(as.getSpecialities());
//		System.out.println("Creating new activity named \"" + activityName + "\" with chosen specialty \""
//				+ specialtyDesignation + "\"");
//		as.addActivity(activityName, specialtyDesignation, isRegular, numSessions, sessionDuration, price,
//				numMaxParticipants);
//		System.out.println("Activity sucessfully added!");
//	}
//
//	public static void setSchedule(SetNewScheduleService ds, String activityName,
//			List<Pair<DayOfWeek, LocalTime>> weekSessionsDates, Date begin, int monthlyDuration, int instructorID,
//			int instructorDuration) throws ApplicationException {
//		System.out.print(" - Current regular activities: ");
//		System.out.println(ds.getRegularActivities());
//		System.out.println("Creating new schedule for activity named \"" + activityName + "\"");
//		ds.setSchedule(activityName, weekSessionsDates, begin, monthlyDuration, instructorID, instructorDuration);
//		System.out.println("Schedule sucessfully added!");
//	}
//
//	public static void buyMonthlyParticipation(BuyMonthlyParticipationService bs, String activityName, Date begin,
//			int monthlyDuration, String email) throws ApplicationException {
//
//		System.out.println(" - Current schedules of regular activity named \"" + activityName + "\"");
//		Pair<List<ScheduleDTO>,Double> infos = bs.getSchedulesInfoForActivity(activityName);
//		List<ScheduleDTO> infoSchedules = infos.getValue0();
//
//		for (ScheduleDTO scheduleDTO : infoSchedules) {
//			System.out.println("   - Schedule that begins in date: " + scheduleDTO.getDate() + 
//					" with price " + infos.getValue1());
//			for (Pair<DayOfWeek,LocalTime> date: scheduleDTO.getWhen()) {
//				System.out.println("          "+ date.getValue0()+" "+ date.getValue1());
//			}			
//		}
//
//		System.out.println("Creating monthly participation for the earliest schedule");
//		String string = bs.chooseSchedule(infoSchedules.get(0).getID(), begin, monthlyDuration, email, activityName);
//
//		System.out.println("Participation sucessfully purchased!");
//		System.out.println("Payment Details:");
//		System.out.println(" - " + string);
//	}
//
//	public static void scheduleOccasionalActivity(ScheduleOccasionaActivityService soas, String specialtyName,
//			List<Date> sessionDates, int instructorID, String email) throws ApplicationException {
//
//		System.out.print("Current occasional activities: ");
//		List<String> activities = soas.scheduleOccasionalActivity(specialtyName);
//		System.out.println(activities);
//
//		System.out.println(
//				"Setting schedule for occasional activity named \"" + activities.get(activities.size() - 1) + "\"");
//		List<Pair<Integer, String>> instructors = soas.setScheduleOccasionalActivity(activities.get(0), sessionDates);
//		for (Pair<Integer, String> pair : instructors) {
//			System.out.println(
//					" - Instructor named \"" + pair.getValue1() + "\" with id=" + pair.getValue0() + " is available");
//		}
//		System.out.println("Selecting instructor with number " + instructorID);
//		String string = soas.concludeScheduleOccasionalActivityOperation(activities.get(activities.size() - 1),
//				instructorID, email);
//
//		System.out.println("Participation sucessfully purchased!");
//		System.out.println("Payment Details:");
//		System.out.println(" - " + string);
//	}
//
//	private static void printException(ApplicationException e) {
//		System.err.println(error + e.getMessage());
//		if (e.getCause() != null) {
//			System.err.println(cause + e.getCause());
//		}
//	}
//}
