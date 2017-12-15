package servlets;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/calc")
public class CalcServlet extends TemplateServlet {

	private static final long serialVersionUID = 1L;

	final Logger logger = Logger.getLogger(CalcServlet.class);
	
	// Pattern for number
	public static final	String pattern = "[-+]?\\d+?[.]?[0-9]*?";
	// It shows if string doesn't match pattern
	public static final String error = "Example -12.34 without spaces. ";

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

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			logger.debug("I'm in doGet()");
		sendHtmlToBrowser(request, response);
	}

	@Override
	/**
	 * I tried to catch exceptions from parsing JSON but try-catch can't catch
	 * RunTimeException So in doGet method i made a check on right filling first
	 * part and second part
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			logger.debug("I'm in doPost()");
		JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
		// Getting parameters from JSON 
		firstString = data.get("first").getAsString();
		secondString = data.get("second").getAsString();
		act = data.get("act").getAsString();

		if (logger.isDebugEnabled()) {
			logger.debug("First= " + firstString + ";Second= " + secondString + ";Action= " + act);
		}

		isPOST = true;
		sendHtmlToBrowser(request, response);
	}
	
	@Override
	protected String insertJs() {
		return "calculate";
	}

	@Override
	protected String insertCss() {
		return "style";
	}

	@Override
	protected String insertTitle() {
		return "Calculator";
	}

	@Override
	protected void insertLogic(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		String role = (String) session.getAttribute("role");
		// Set the response message's MIME type
		response.setContentType("text/html;charset=UTF-8");
        // If current http request has email parameter then 
		// page redirects to new one without parameters at all
		//I wrote request.getAttribute("email") again 
		// because email could be something,
		// it would be endless cycle
		if (request.getParameter("email")!= null) {
				logger.debug(email + " was authenticated");
			// It redirects to new page with calculator without parametres email and
			// password which more safety
			response.sendRedirect("calc");
		} else {
			er1 = false;
			er2 = false;
			if (isPOST) {
				fillPartsInPost(request);
			} else {
				fillPartsWithParameters(request);
			}
			
			calculateResult();
			
			out.println("<div id=\"mydiv\" class=\"box\">");
			if(role!=null && role.equals("admin")) {
				out.println("<h2>Welcome, sir!</h2>");
			} 
			else if (email != null) {
				out.println("<h2>You signed in as " + email.substring(0, email.indexOf("@")==-1?email.length():email.indexOf("@")) + "</h2>");
			}
			out.println("<form id=\"myform\">");
			out.println(
					"<strong>First part</strong><input id=\"first\" class=\"email\" name=\"first\" type=\"text\" value=\""
							+ (er1 ? firstString : first) + "\"><em style=\"color:red;\"><br/> "
							+ (er1 ? error : "") + "</em><br/>");
			out.println(
					"<strong>Second part</strong><input id = \"second\" class=\"email\" name=\"second\" type=\"text\" value = \""
							+ (er2 ? secondString : second) + "\"> <em style=\"color:red;\"><br/>"
							+ (er2 ? error : "") + "</em><br/>");
			out.println(selectShowPreviousOption(act));
			out.println("<input type=\"submit\" value=\"Calculate\" id=\"btn2\"/><br/><br/><br/><br/>");
			out.println("<h1>" + result + "</h1>");
			out.println("</form>");
			out.println("</div>");
			isPOST = false;
		}
	}
	
	private void calculateResult() {

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
				logger.warn("There was not proper calculation");
			}
		}

	}
	/**
	 * Filling numbers with parameters from http request
	 * @param request
	 */
	private void fillPartsWithParameters(HttpServletRequest request) {
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
	}
	/**
	 * Filling numbers with values that were gotten from JSON
	 * @param request
	 */
	private void fillPartsInPost(HttpServletRequest request) {
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

	/**
	 * I used ajax request for a whole form in calculator page
	 * It means the select tag will update every time with new ajax request
	 * Therefore select will show the option "Plus" every time after updating
	 * The method "insert"  inserts parameter selected="selected" in tag option
	 * And it inserts in place that depends on action of calculation
	 * 
	 * @param act
	 * @return
	 */
	private String selectShowPreviousOption(String act) {
		switch (act) {
		case "+":
			return insert(54);
		case "-":
			return insert(85);
		case "*":
			return insert(117);
		case ":":
			return insert(152);
		case "sqrt first":
			return insert(185);
		case "sqrt second":
			return insert(239);
		default:
			System.err.println("I haven't made a selection!");
		}
		return "<select class=\"email\" id = \"act\" name=\"action\"><option value=\"+\">Plus</option><option value=\"-\">Minus</option><option value=\"*\">Multiply</option>"
				+ "<option value=\":\">Divide</option><option value=\"sqrt first\">Sqrt of First part</option><option value=\"sqrt second\">Sqrt of Second part</option></select> <br/><br/>";
	}

	/**
	 * Inserting in tag html select of mine
	 * 
	 * @param index
	 * @return
	 */
	private String insert(int index) {
		String selected = " selected=\"selected\"";
		String select = "<select class=\"email\" id = \"act\" name=\"action\"><option value=\"+\">Plus</option><option value=\"-\">Minus</option><option value=\"*\">Multiply</option>"
				+ "<option value=\":\">Divide</option><option value=\"sqrt first\">Sqrt of First part</option><option value=\"sqrt second\">Sqrt of Second part</option></select> <br/><br/>";
		String selectBegin = select.substring(0, index);
		String selectEnd = select.substring(index);
		return selectBegin + selected + selectEnd;
	}

	
}
