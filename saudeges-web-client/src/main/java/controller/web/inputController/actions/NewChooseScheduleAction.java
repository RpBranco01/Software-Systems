package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import presentation.web.model.BuyMonthlyParticipationModel;

@Stateless
public class NewChooseScheduleAction extends Action{

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		BuyMonthlyParticipationModel model = new BuyMonthlyParticipationModel();
		request.setAttribute("model", model);
		request.getRequestDispatcher("/buyMonthlyParticipation/chooseActivity.jsp").forward(request, response);
	}
}
