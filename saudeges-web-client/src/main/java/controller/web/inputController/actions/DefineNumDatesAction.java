package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.interfaces.IScheduleOccasionalActivityServiceRemote;
import presentation.web.model.ScheduleOccasionaActivityModel;

@Stateless
public class DefineNumDatesAction extends Action{
	
	@EJB private IScheduleOccasionalActivityServiceRemote service;
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ScheduleOccasionaActivityModel model = createHelper(request);
		request.setAttribute("model", model);
		if(validInput(model)) {
			request.getRequestDispatcher("/scheduleOccasionalActivity/chooseDates.jsp").forward(request, response);
		}else {
			request.setAttribute("model", model);
			request.getRequestDispatcher("/scheduleOccasionalActivity/chooseActivity.jsp").forward(request, response);
		}	
	}
	
	private boolean validInput(ScheduleOccasionaActivityModel model) {
		
		boolean result = isFilled(model, model.getSpecialty(), "A designação da especialidade tem de estar preenchido.");
		
		result &= isFilled(model, model.getActivity(), "A actividade tem que estar preenchida");
		
		result &= isFilled(model, String.valueOf(model.getNumDates()), "O número de sessões deve estar preenchido.");
		
		//check if activityName is filled
		return result;
	}
	
	private ScheduleOccasionaActivityModel createHelper(HttpServletRequest request) {
		//Create the object model
		ScheduleOccasionaActivityModel model = new ScheduleOccasionaActivityModel();
		
		//fill it with data from the request
		model.setSpecialty(request.getParameter("specialty"));
		model.setActivity(request.getParameter("activity"));
		model.setNumDates(request.getParameter("numDates"));
		
		return model;
	}

}
