import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/calc")
public class CalcServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	final Logger logger = Logger.getLogger(CalcServlet.class);

	private String email;

	private Double result;
	private Double first;
	private Double second;
	private String firstString;
	private String secondString;
	private String act;
	private boolean er1, er2;
	private boolean isPOST;

	public CalcServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("I'm in doGet()");
		doHtml(request, response);
	}

	/**
	 * I tried to catch exceptions from parsing JSON but try-catch can't catch
	 * RunTimeException So in doGet method i made a check on right filling first
	 * part and second part
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("I'm in doPost()");
		JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);

		if (data.isJsonNull()) {
			System.out.println("*****JSON is null!!");
		} else {
			System.out.println(data.toString());
		}
		try {
			firstString = data.get("first").getAsString();
		} catch (Exception e) {
			er1 = true;
			e.printStackTrace();
		}
		try {
			secondString = data.get("second").getAsString();
		} catch (Exception e) {
			er2 = true;
			e.printStackTrace();
		}
		try {
			act = data.get("act").getAsString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(logger.isDebugEnabled()) {
			logger.debug("First= " + firstString + ";Second= " + secondString + ";Action= " + act);
		}
		System.out.println("******First= " + firstString + ";Second= " + secondString + ";Action= " + act);

		isPOST = true;
		doHtml(request, response);
	}

	private void doHtml(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("**** Starting doHtml ******");

		// Logic below
		String pattern = "[-+]?\\d+?[.]?[0-9]*?";
		String error = "Please, write correct number. Example -12.34";
		// Set the response message's MIME type
		response.setContentType("text/html;charset=UTF-8");
				
		PrintWriter out = response.getWriter();
		if (request.getParameter("email") != null) {
			email = request.getParameter("email");
			if(logger.isDebugEnabled()) {
				logger.debug(email + " was authenticated");
			}
		}
		er1 = false;
		er2 = false;
		if (!isPOST) {
			if (request.getParameter("first") != null && request.getParameter("first") != ""
					&& request.getParameter("first").matches(pattern)) {
				first = Double.parseDouble(request.getParameter("first"));
			} else {
				er1 = true;
				first = null;
			}
			if (request.getParameter("second") != null && request.getParameter("second") != ""
					&& request.getParameter("first").matches(pattern)) {
				second = Double.parseDouble(request.getParameter("second"));
			} else {
				er2 = true;
				second = null;
			}
			result = null;
			if (request.getParameter("action") == null) {
				act = "+";
			} else {
				act = request.getParameter("action");
			}
		} else {
			if (firstString.matches(pattern)) {
				first = Double.parseDouble(firstString);
			} else {
				first = null;
				er1 = true;
			}
			if (secondString.matches(pattern)) {
				second = Double.parseDouble(secondString);
			} else {
				er2 = true;
				second = null;
			}
			if (act == null) {
				act = "+";
			}
		}

		if (first != null && second != null) {
			switch (act) {
			case "+":
				result = first + second;
				break;
			case "-":
				result = first - second;
				break;
			case "*":
				result = first * second;
				break;
			case ":":
				result = first / second;
				break;
			case "sqrt first":
				result = Math.sqrt(first);
				break;
			case "sqrt second":
				result = Math.sqrt(second);
				break;
			default:
				System.out.println("I've just made a culculation!");
			}
		}

		// Sending html
		try {
			out.println("<!DOCTYPE html>");
			out.println("<html><head>");
			out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
			// // Well, I tried to use ajax with JS.
			out.println("<script type=\"text/javascript\" src=\"jquery-3.2.1.min.js\"></script>");
			out.println("<script type=\"text/javascript\" src=\"calculate.js\"></script>");
			//
			out.println("<title>Calculator</title></head>");
			out.println("<body style=\"width : 500px ; margin : 0 auto ; background-color : #FFFACD;\">");
			out.println("<div id=\"mydiv\">");
			out.println("<h1>Calculator Pro</h1>");
			if (email != null) {
				out.println("<h2>You signed in as " + email + "</h2>");
			}
			out.println("<form id=\"myform\">");
			out.println(
					"<p>First part</p><input id=\"first\" style=\"border-radius: 6px; border: 1px solid #ccc;\" name=\"first\" type=\"text\" value=\""
							+ (er1 ? firstString : first) + "\"><em style=\"color:red;\"> " + (er1 ? error : "")
							+ "</em>");
			System.out.println("The second is = " + second);
			out.println(
					"<p>Second part</p><input id = \"second\" style=\"border-radius: 6px; border: 1px solid #ccc;\" name=\"second\" type=\"text\" value = \" "
							+ (er2 ? secondString : second) + " \"> <em style=\"color:red;\">" + (er2 ? error : "")
							+ "</em>");
			out.println("<p>Action : <strong> " + act
					+ "</strong> </p><select id = \"act\" name=\"action\"><option value=\"+\">Plus</option><option value=\"-\">Minus</option><option value=\"*\">Multiply</option>"
					+ "<option value=\":\">Divide</option><option value=\"sqrt first\">Sqrt of First part</option><option value=\"sqrt second\">Sqrt of Second part</option></select> <br/><br/>");
			out.println(
					"<button id=\"btn\" style=\"width : 170px; height : 35px; border-radius: 15px; border: 3px solid 	#228B22; \" type=\"submit\">Calculate</button><br/>");
			out.println("<div id=\"result\"><strong> Your result is : <h2>" + result + "</h2></strong></div>");
			out.println("</form>");
			out.println("</div>");
			out.println("</body>");
			out.println("</html>");
		} finally {
			out.close(); // Always close the output writer
		}
		isPOST = false;
		System.out.println("*****Done!");

	}
}
