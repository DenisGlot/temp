package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import prefix.Prefix;

/**
 * Here is used Template method pattern
 * @author Denis
 *
 */
public interface SendHtml {
	
	final Logger logger = Logger.getLogger(SendHtml.class);

	/**
	 * If it's null then nothing changes
	 * @return name of a file with javascript without extension
	 */
	public String insertJs();

	/**
	 * If it's null then nothing changes
	 * @return name of a file with css without extension
	 */
	public String insertCss();


	public String insertTitle();

	/**
	 * Its place is between tag body. It looks like &ltbody> insertLogic();
	 * &lt/body>
	 * 
	 * @param request
	 * @param response
	 * @param out
	 * @throws IOException
	 */
	public void insertLogic(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws IOException;
	
	default void sendHtmlToBrowser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug("**** Starting sendHtmlToBrowser in " + request.getServletPath() + " ******");
		// Set the response message's MIME type
        response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			out.println("<!DOCTYPE html>");
			out.println("<html><head>");
			out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
			// // I use ajax here.
			out.println("<script type=\"text/javascript\" src=\"jquery-3.2.1.min.js\"></script>");
			if (insertJs() != null) {
				out.println("<script type=\"text/javascript\" src=\"" + insertJs() + ".js\"></script>");
			}
			out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\" crossorigin=\"anonymous\">"); 
	        out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css\" integrity=\"sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp\" crossorigin=\"anonymous\">");
					
			
			
			out.println("<title>" + insertTitle() + "</title></head>");
			out.println("<body>");
			out.print("<!-- Fixed navbar -->\r\n"); 
			out.print("    <nav class=\"navbar navbar-default navbar-fixed-top\">\r\n");
			out.print("      <div class=\"container\">\r\n");
			out.print("        <div class=\"navbar-header\">\r\n");
			out.print("          <button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#navbar\" aria-expanded=\"false\" aria-controls=\"navbar\">\r\n");
			out.print("            <span class=\"sr-only\">Toggle navigation</span>\r\n" );
			out.print("            <span class=\"icon-bar\"></span>\r\n");
			out.print("            <span class=\"icon-bar\"></span>\r\n"); 
			out.print("            <span class=\"icon-bar\"></span>\r\n");
			out.print("          </button>\r\n");
			out.print("          <a class=\"navbar-brand\" href=\"#\">E-shop</a>\r\n");
			out.print("        </div>\r\n");
			out.print("        <div id=\"navbar\" class=\"navbar-collapse collapse\">\r\n");
			out.print("          <ul class=\"nav navbar-nav\">\r\n");
			out.print("            <li " + (request.getServletPath().equals("/menu") ?"class=\"active\"":"") + "><a href=\"" + Prefix.prefix + "/\">Menu</a></li>\r\n");
			out.print("            <li  " + (request.getServletPath().equals("/shoppingcart") ?"class=\"active\"":"") + "><a href=\"" + Prefix.prefix + "/shoppingcart\">Cart</a></li>\r\n");
			out.print("            <li " + (request.getServletPath().equals("/registerDetails") ?"class=\"active\"":"") + " style = \"margin-left : 70px;\"><a href=\"" + Prefix.prefix + "/registerDetails\">Registration</a></li>\r\n");
			out.print("            <li " + (request.getServletPath().equals("/signin") ?"class=\"active\"":"") + "><a href=\"" + Prefix.prefix + "/signin\">Log in</a></li>\r\n");
			out.print("            <li " + (request.getServletPath().equals("/calc") ?"class=\"active\"":"") + "style = \"margin-left : 100px;\"><a  href=\"" + Prefix.prefix + "/calc\">Calculator :)</a></li>\r\n");
			out.print("            <li " + (request.getServletPath().equals("/feedback") ?"class=\"active\"":"") + "style = \"margin-left : 100px;\"><a  href=\"" + Prefix.prefix + "/feedback\">Feedback</a></li>\r\n");
			out.print("          </ul>\r\n");
			out.print("        </div><!--/.nav-collapse -->\r\n");
			out.print("      </div>\r\n");
			out.println("    </nav>");
			if (insertCss() != null) {
				out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + insertCss() + ".css\">");
			}
			
			out.println("<div style = \"margin-top : 60px;\">");
			insertLogic(request, response, out);
			out.println("</div>");
			out.println("</body>");
			out.println("</html>");
			
		}
		logger.debug("*****Done with sendHtmlToBrowser  in " + request.getServletPath() + " ********");
	}
}
