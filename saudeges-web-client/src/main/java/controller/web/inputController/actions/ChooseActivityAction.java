package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.dtos.ActivityScheduleDTO;
import facade.exceptions.ActivityNotFoundException;
import facade.exceptions.ApplicationException;
import facade.exceptions.InvalidInstanceException;
import facade.interfaces.IBuyMonthlyParticipationServiceRemote;
import presentation.web.model.BuyMonthlyParticipationModel;

@Stateless
public class ChooseActivityAction extends Action {
	
	@EJB private IBuyMonthlyParticipationServiceRemote service;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BuyMonthlyParticipationModel model = createHelper(request);
		request.setAttribute("model", model);
		boolean isCatch = false;
		
		if(validInput(model)) {
			try {
				ActivityScheduleDTO schedules = service.getSchedulesInfoForActivity(model.getActivityName());
				model.setSchedules(schedules.getSchedules());			
			}
			catch(ActivityNotFoundException e) {
				model.addMessage("Atividade não encontrada.");
				isCatch = true;
			}
			catch(InvalidInstanceException e) {
				model.addMessage("Atividade não é regular.");
				isCatch = true;
			}
			catch(ApplicationException e) {
				model.addMessage("Erro ao procurar horários.");
				isCatch = true;
			}
		}
		else {
			isCatch = true;
		}
		request.setAttribute("model", model);
		
		if(isCatch) {
			request.getRequestDispatcher("/buyMonthlyParticipation/chooseActivity.jsp").forward(request, response);
		}else {
			request.getRequestDispatcher("/buyMonthlyParticipation/buyMonthlyParticipation.jsp").forward(request, response);
		}
		
	}
	
	private boolean validInput(BuyMonthlyParticipationModel model) {
		//check if activityName is filled
		return isFilled(model, model.getActivityName(), "O nome da atividade tem de estar preenchido.");
	}
	
	private BuyMonthlyParticipationModel createHelper(HttpServletRequest request) {
		//Create the object model
		BuyMonthlyParticipationModel model = new BuyMonthlyParticipationModel();
		
		//fill it with data from the request
		model.setActivityName(request.getParameter("activityName"));
		
		return model;
	}

}
