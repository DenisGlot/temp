package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import prefix.Prefix;
import scenario.Scenario;

public abstract class TemplateServlet extends HttpServlet implements SendHtml {
	private static final long serialVersionUID = 12534655463L;

	private final Logger logger = Logger.getLogger(TemplateServlet.class);
	
	protected Scenario scenario;

	public TemplateServlet() {
		super();
		scenario = new Scenario();
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
	public abstract String insertJs();

	/**
	 * If it's null then nothing changes
	 * @return name of a file with css without extension
	 */
	public abstract String insertCss();

	protected String addCss() {
		return null;
	}

	public abstract String insertTitle();

	/**
	 * Its place is between tag body. It looks like &ltbody> insertLogic();
	 * &lt/body>
	 * 
	 * @param request
	 * @param response
	 * @param out
	 * @throws IOException
	 */
	public abstract void insertLogic(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws IOException;

	
}
