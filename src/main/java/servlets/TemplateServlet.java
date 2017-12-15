package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public abstract class TemplateServlet extends HttpServlet {
	private static final long serialVersionUID = 12534655463L;

	private final Logger logger = Logger.getLogger(TemplateServlet.class);

	public TemplateServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		sendHtmlToBrowser(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * If it's null then nothing changes
	 * @return name of a file with javascript without extension
	 */
	protected abstract String insertJs();

	/**
	 * If it's null then nothing changes
	 * @return name of a file with css without extension
	 */
	protected abstract String insertCss();

	protected String addCss() {
		return null;
	}

	protected abstract String insertTitle();

	/**
	 * Its place is between tag body. It looks like &ltbody> insertLogic();
	 * &lt/body>
	 * 
	 * @param request
	 * @param response
	 * @param out
	 * @throws IOException
	 */
	protected abstract void insertLogic(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws IOException;

	protected void sendHtmlToBrowser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug("**** Starting sendHtmlToBrowser ******");
		try (PrintWriter out = response.getWriter()) {
			out.println("<!DOCTYPE html>");
			out.println("<html><head>");
			out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
			// // I use ajax here.
			out.println("<script type=\"text/javascript\" src=\"jquery-3.2.1.min.js\"></script>");
			if (insertJs() != null) {
				out.println("<script type=\"text/javascript\" src=\"" + insertJs() + ".js\"></script>");
			}
			if (insertCss() != null) {
				out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + insertCss() + ".css\">");
			}
			if (addCss() != null) {
				out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + addCss() + ".css\">");
			}
			out.println("<title>" + insertTitle() + "</title></head>");
			out.println("<body>");
			insertLogic(request, response, out);
			out.println("</body>");
			out.println("</html>");
		}
		logger.debug("*****Done with sendHtmlToBrowser!");
	}
}
