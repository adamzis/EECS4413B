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
 * Servlet implementation class Http
 */
@WebServlet("/Http.do")
public class Http extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("calc") == null) {
			request.getServletContext().getRequestDispatcher("/Http.html").forward(request, response);
		} else {
			Brain model = new Brain();

			try {
				String query = request.getParameter("query");
				String country = request.getParameter("country");
				String returnedValue = model.doHttp(country, query);

				response.setContentType("text/html");
				Writer out = response.getWriter();
				String html = "<html lang=\"en\"><body>";
				html += "<p><a href='Dash.do'>Back to Dashboard</a></p>";
				html += "<p><strong>Country Data:</strong><br><code>" + returnedValue + "</code></p>";
				html += "</body></html>";

				out.write(html);
			} catch (Exception e) {
				response.setContentType("text/html");
				Writer out = response.getWriter();
				String html = "<html><body>";
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
