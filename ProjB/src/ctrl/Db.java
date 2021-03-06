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
 * Servlet implementation class Db
 */
@WebServlet("/Db.do")
public class Db extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("calc") == null) {
			request.getServletContext().getRequestDispatcher("/Db.html").forward(request, response);
		} else {
			Brain model = new Brain();
			try {
				String itemNo = request.getParameter("itemno");
				String dbString = model.doDb(itemNo);

				response.setContentType("text/html");
				Writer out = response.getWriter();
				String html = "<!DOCTYPE html><html lang=\"en\"><body>";
				html += "<p><a href='Dash.do'>Back to Dashboard</a></p>";
				html += "<p>Prime: " + dbString + "</p>";
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
