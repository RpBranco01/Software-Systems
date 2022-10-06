package controller.web.inputController.actions;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.dtos.InstructorDTO;
import facade.exceptions.ActivityNotFoundException;
import facade.exceptions.ApplicationException;
import facade.exceptions.SpecialtyNotFoundException;
import facade.interfaces.IScheduleOccasionalActivityServiceRemote;
import presentation.web.model.ScheduleOccasionaActivityModel;

@Stateless
public class DefineDatesAction extends Action{

	@EJB private IScheduleOccasionalActivityServiceRemote service;
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ScheduleOccasionaActivityModel model = createHelper(request);
		request.setAttribute("model", model);
		boolean isCatch = false;
		
		if(validInput(model)) {
			try {
				List<String> strings = new ArrayList<>(Arrays.asList(model.getDates()));
				List<Date> dates = new ArrayList<>();
				for (String string : strings) {
					dates.add(new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(string));	
				}
				
				System.out.println(model.getActivity());
				System.out.println(dates);
				List<InstructorDTO> instructors = service.setScheduleOccasionalActivity(model.getActivity(), dates);
				model.setInstructors(instructors);
				
			}
			catch (ParseException e) {
				model.addMessage("Formato da data inválida.");
				isCatch = true;
			}
			catch(ActivityNotFoundException e) {
				model.addMessage("Atividade não encontrada.");
				isCatch = true;
			}
			catch(SpecialtyNotFoundException e) {
				model.addMessage("Especialidade não encontrada.");
				isCatch = true;
			}
			catch(ApplicationException e) {
				model.addMessage("Erro ao procurar atividades.");
				isCatch = true;
			}
		}else {
			isCatch = true;
		}
		
		request.setAttribute("model", model);
		if(isCatch) {
			request.getRequestDispatcher("/scheduleOccasionalActivity/chooseDates.jsp").forward(request, response);	
		}else {
			request.getRequestDispatcher("/scheduleOccasionalActivity/chooseInstructor.jsp").forward(request, response);	
		}
		
	}
	
	private boolean validInput(ScheduleOccasionaActivityModel model) {
		
		boolean result = true;
		
		for (int i = 0; i < model.getNumDates(); i++) {
			result &= isFilled(model, model.getDates()[i], "As datas devem estar preenchidas");
		}
		
		return result;
	}
	
	private ScheduleOccasionaActivityModel createHelper(HttpServletRequest request) {
		//Create the object model
		ScheduleOccasionaActivityModel model = new ScheduleOccasionaActivityModel();
		
		//fill it with data from the request
		model.setSpecialty(request.getParameter("specialty"));
		
		model.setActivity(request.getParameter("activity"));
		
		model.setNumDates(request.getParameter("numDates"));
		
		String[] dates = new String[model.getNumDates()];
		
		for (int i = 1; i <= model.getNumDates(); i++) {
			dates[i-1] = request.getParameter("date"+i);
		}
		
		model.setDates(dates);
		
		return model;
	}

}
