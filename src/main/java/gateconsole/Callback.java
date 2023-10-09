package gateconsole;

import gate.io.URL;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/callback")
public class Callback extends HttpServlet
{

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.sendRedirect(new URL("/gate/Gate")
			.setModule("gateconsole.modules.admin")
			.setParameter("code", request.getParameter("code"))
			.setParameter("state", request.getParameter("state")).toString());
	}
}
