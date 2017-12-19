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

public class FeedbackServlet extends TemplateServlet {
	private static final long serialVersionUID = 123423534L;
	
	private final Logger logger = Logger.getLogger(FeedbackServlet.class);
       
	private Scenario scenario;

    public FeedbackServlet() {
        super();
        scenario = new Scenario(); 
    }

	@Override
	public String insertJs() {
		return null;
	}

	@Override
	public String insertCss() {
		return "css/bootstrap.min.css";
	}

	@Override
	public String insertTitle() {
		return "Feedback";
	}

	@Override
	public void insertLogic(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws IOException {
		String message = request.getParameter("message");
		logger.debug(message);
		logger.debug( request.getSession().getAttribute("email"));
		logger.debug( request.getSession().getAttribute("email").toString());
		logger.debug( request.getParameter("subject"));
		if(message != null) {
			scenario.sendFeedBack(message,request.getParameter("subject"), request.getSession().getAttribute("email").toString());
			out.println("<div class=\"contain\">");
			out.println("<strong style = \"color: #00FA9A; font-size: 1.5em;\">Thank you! Your opinion is very important to us.</strong>");
			out.println("<a style =\"font: bold 20px Arial;background-color: #ADFF2F;color: #333333;padding: 2px 6px 2px 6px;border: 2px solid #CCCCCC; border-radius: 6px;\" href = \"" + Prefix.prefix + "/\">Choose something else? </a>");
			out.println("</div>");
		} else {
			// HTML
			out.println("<section id=\"contact\" class=\"content-section text-center\">\r\n" + 
					"        <div class=\"contact-section\">\r\n" + 
					"            <div class=\"container\">\r\n" + 
					"              <h2>Contact Us</h2>\r\n" + 
					"              <p>Feel free to shout us by feeling the contact form or visiting our social network sites like Fackebook,Whatsapp,Twitter.</p>\r\n" + 
					"              <div class=\"row\">\r\n" + 
					"                <div class=\"col-md-8 col-md-offset-2\">\r\n" + 
					"                  <form class=\"form-horizontal\" action =\"\">\r\n" + 
					"                    <div class=\"form-group\">\r\n" + 
					"                      <label for=\"exampleInputName2\">Topic</label>\r\n" + 
					"                      <input name=\"subject\" type=\"text\" class=\"form-control\" id=\"exampleInputName2\" placeholder=\"Subject\">\r\n" + 
					"                    </div>\r\n" + 
					"                    <div class=\"form-group \">\r\n" + 
					"                      <label for=\"exampleInputText\">Your Message</label>\r\n" + 
					"                     <textarea  name = \"message\" class=\"form-control\" rows=\"10\" placeholder=\"Description\"></textarea> \r\n" + 
					"                    </div>\r\n" + 
					"                    <button type=\"submit\" class=\"btn btn-default\">Send Message</button>\r\n" + 
					"                  </form>\r\n" + 
					"                </div>\r\n" + 
					"              </div>\r\n" + 
					"            </div>\r\n" + 
					"        </div>\r\n" + 
					"      </section>");
		
		}
	}
}
