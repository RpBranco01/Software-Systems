package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Stateless
public class GoBackToIndexAction extends Action{

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/saudeges-web-client/");
	}
}
