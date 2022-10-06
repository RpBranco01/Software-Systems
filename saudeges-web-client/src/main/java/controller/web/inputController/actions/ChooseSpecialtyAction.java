package controller.web.inputController.actions;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.dtos.OccasionalActivityDTO;
import facade.exceptions.ActivityNotFoundException;
import facade.exceptions.ApplicationException;
import facade.exceptions.SpecialtyNotFoundException;
import facade.interfaces.IScheduleOccasionalActivityServiceRemote;
import presentation.web.model.ScheduleOccasionaActivityModel;

@Stateless
public class ChooseSpecialtyAction extends Action{

	@EJB private IScheduleOccasionalActivityServiceRemote service;
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ScheduleOccasionaActivityModel model = createHelper(request);
		request.setAttribute("model", model);
		boolean isCatch = false;
		
		if(validInput(model)) {
			try {
				List<OccasionalActivityDTO> occasionalActivities = service.scheduleOccasionalActivity(model.getSpecialty());
				model.setOccasionalActivities(occasionalActivities);
				
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
			request.getRequestDispatcher("/scheduleOccasionalActivity/chooseSpecialty.jsp").forward(request, response);
		}else {
			request.getRequestDispatcher("/scheduleOccasionalActivity/chooseActivity.jsp").forward(request, response);
		}
		
	}
	
	private boolean validInput(ScheduleOccasionaActivityModel model) {
		//check if activityName is filled
		return isFilled(model, model.getSpecialty(), "A designação da especialidade tem de estar preenchido.");
	}
	
	private ScheduleOccasionaActivityModel createHelper(HttpServletRequest request) {
		//Create the object model
		ScheduleOccasionaActivityModel model = new ScheduleOccasionaActivityModel();
		
		//fill it with data from the request
		model.setSpecialty(request.getParameter("specialty"));
		
		return model;
	}

}
