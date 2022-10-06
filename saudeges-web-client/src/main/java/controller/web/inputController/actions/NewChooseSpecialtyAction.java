package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import presentation.web.model.ScheduleOccasionaActivityModel;

@Stateless
public class NewChooseSpecialtyAction extends Action{

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ScheduleOccasionaActivityModel model = new ScheduleOccasionaActivityModel();
		request.setAttribute("model", model);
		request.getRequestDispatcher("/scheduleOccasionalActivity/chooseSpecialty.jsp").forward(request, response);
	}

}
