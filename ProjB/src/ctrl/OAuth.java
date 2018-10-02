package ctrl;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OAuth
 */
@WebServlet("/OAuth.do")
public class OAuth extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("calc") == null && request.getParameter("name") == null) {
			this.getServletContext().getRequestDispatcher("/OAuth.html").forward(request, response);
		} else if (request.getParameter("name") == null) {
			response.sendRedirect(
					"https://www.eecs.yorku.ca/~roumani/servers/auth/oauth.cgi?back=http://localhost:4413/ProjB/OAuth.do");
		} else {
			response.setContentType("text/html");
			Writer out = response.getWriter();

			String html = "<!DOCTYPE html><html lang=\"en\"><body>";
			html += "<p><a href='Dash.do'>Back to Dashboard</a></p>";
			html += "<p><strong>Authentication Result:</strong></p><code>" + request.getQueryString();
			html += "</code></body></html>";

			out.write(html);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
