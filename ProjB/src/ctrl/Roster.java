package ctrl;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Brain;

/**
 * Servlet implementation class Roster
 */
@WebServlet("/Roster.do")
public class Roster extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("calc") == null) {
			this.getServletContext().getRequestDispatcher("/Roster.html").forward(request, response);
		} else {
			Brain model = new Brain();
			try {
				String course = request.getParameter("course");
				String xml = model.doRoster(course);

				response.setContentType("text/html");
				Writer out = response.getWriter();

				String html = "<!DOCTYPE html><html lang=\"en\"><body>";
				html += "<p><a href='Dash.do'>Back to Dashboard</a></p>";
				html += "<h1>Course Roster</h1>" + xml;
				html += "</body></html>";

				out.write(html);
			} catch (Exception e) {
				response.setContentType("text/html");
				Writer out = response.getWriter();
				String html = "<!DOCTYPE html><html><body>";
				html += "<p><a href=' Dash.do'>Back to Dashboard</a></p>";
				html += "<p>Error " + e.getMessage() + "</p>";
				out.write(html);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
