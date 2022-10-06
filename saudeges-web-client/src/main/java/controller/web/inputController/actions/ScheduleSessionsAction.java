package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.dtos.ReservationDTO;
import facade.exceptions.ActivityNotFoundException;
import facade.exceptions.ApplicationException;
import facade.exceptions.InstructorNotFoundException;
import facade.interfaces.IScheduleOccasionalActivityServiceRemote;
import presentation.web.model.ScheduleOccasionaActivityModel;

@Stateless
public class ScheduleSessionsAction extends Action {

@EJB private IScheduleOccasionalActivityServiceRemote service;
	

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ScheduleOccasionaActivityModel model = createHelper(request);
		request.setAttribute("model", model);
		boolean isCatch = false;
		
		if(validInput(model)) {
			try {
				ReservationDTO reservation = service.concludeScheduleOccasionalActivityOperation(model.getActivity(), 
																			model.getInstructor(), model.getEmail());
				
				StringBuilder sb = new StringBuilder();
				sb.append("Entidade: "+ reservation.getEntity());
				sb.append(" Referência: " + reservation.getReference());
				sb.append(" Pagamento: " + reservation.getPrice());
				model.addMessage(sb.toString());
				
			}
			catch(InstructorNotFoundException e) {
				model.addMessage("Instrutor não encontrado.");
				isCatch = true;
			}
			catch(ActivityNotFoundException e) {
				model.addMessage("Atividade não encontrada.");
				isCatch = true;
			}
			catch(ApplicationException e) {
				model.addMessage("Erro ao agendar sessões.");
				isCatch = true;
			}
		}
		else {
			isCatch = true;
		}
		
		request.setAttribute("model", model);
		
		request.getRequestDispatcher("/scheduleOccasionalActivity/chooseInstructor.jsp").forward(request, response);
		
		
	}
	
	private boolean validInput(ScheduleOccasionaActivityModel model) {
		
		//check if instructor is filled
		return isFilled(model, String.valueOf(model.getInstructor()), "O instrutor tem que estar preenchido.");
	}

	private ScheduleOccasionaActivityModel createHelper(HttpServletRequest request) {
		//Create the object model
		ScheduleOccasionaActivityModel model = new ScheduleOccasionaActivityModel();
		
		//fill it with data from the request
		model.setSpecialty(request.getParameter("specialty"));
		model.setActivity(request.getParameter("activity"));
		model.setEmail(request.getParameter("email"));
		model.setInstructor(Integer.valueOf(request.getParameter("instructor")));
		
		
		return model;
	}
}
