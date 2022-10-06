package controller.web.inputController.actions;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.dtos.ReservationDTO;
import facade.exceptions.ApplicationException;
import facade.exceptions.InvalidDurationException;
import facade.interfaces.IBuyMonthlyParticipationServiceRemote;
import presentation.web.model.BuyMonthlyParticipationModel;

@Stateless
public class BuyMonthlyParticipationAction extends Action {
	
	@EJB private IBuyMonthlyParticipationServiceRemote buyParticipationService;
	

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BuyMonthlyParticipationModel model = createHelper(request);
		request.setAttribute("model", model);
		boolean isCatch = false;
		
		if(validInput(model)) {
			try {
				System.out.println("ANTES DATA");
				Date date = new SimpleDateFormat("dd/MM/yyyy").parse(model.getDate());
				System.out.println("DEPOIS DATA");
				ReservationDTO reservation = buyParticipationService.chooseSchedule(intValue(model.getRASID()), date, 
																intValue(model.getDuration()), model.getEmail(), model.getActivityName());
				
				StringBuilder sb = new StringBuilder();
				sb.append("Entidade: "+ reservation.getEntity());
				sb.append(" Referência: " + reservation.getReference());
				sb.append(" Pagamento: " + reservation.getPrice());
				model.addMessage(sb.toString());
				
			}
			catch (ParseException e) {
				model.addMessage("Formato da data inválida.");
				isCatch = true;
			}
			catch(InvalidDurationException e) {
				model.addMessage("Duração inválida.");
				isCatch = true;
			}
			catch(ApplicationException e) {
				model.addMessage("Erro ao selecionar horário");
				isCatch = true;
			}
		}
		else {
			isCatch = true;
		}
		request.setAttribute("model", model);
		
		request.getRequestDispatcher("/buyMonthlyParticipation/buyMonthlyParticipation.jsp").forward(request, response);

	
		
		
	}
	
	private boolean validInput(BuyMonthlyParticipationModel model) {
		
		//check if date is filled
		boolean result = isFilled(model, model.getDate(), "A data tem de estar preenchida.");
		
		//check if email is filled
		result &= isFilled(model, model.getEmail(), "O email tem de estar preenchido.");
		
		//check if rasID is filled
		result &= isFilled(model, model.getRASID(), "O rasID tem de estar preenchido.")
				&& isInt(model, model.getRASID(), "O rasID contem caracteres inválidos.");
		
		//check if duration is filled
		result &= isFilled(model, model.getDuration(), "A duração tem de estar preenchida.")
				&& isInt(model, model.getDuration(), "A duração contem caracteres inválidos.");
		
		//check if activityName is filled
		result &= isFilled(model, model.getActivityName(), "O nome da atividade tem de estar preenchido.");
				
		return result;
	}

	private BuyMonthlyParticipationModel createHelper(HttpServletRequest request) {
		//Create the object model
		BuyMonthlyParticipationModel model = new BuyMonthlyParticipationModel();
		
		//fill it with data from the request
		model.setDate(request.getParameter("date"));
		model.setEmail(request.getParameter("email"));
		model.setRASID(request.getParameter("schedule"));
		model.setDuration(request.getParameter("duration"));
		model.setActivityName(request.getParameter("activityName"));
		
		return model;
	}

}
