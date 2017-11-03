import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CalcServlet
 */
@WebServlet("/calc")
public class CalcServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private double result;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CalcServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Logig
	      
	      double first;
	      double second;
	      String act;
	      String error ="Please, write correct number";
	      boolean er1=false,er2=false;
	      
	        if(request.getParameter("first")!=null && request.getParameter("first").matches("[-+]?[0-9]*\\\\.?[0-9]+")) {
	        	first = Double.parseDouble(request.getParameter("first"));
	        } else {
	        	er1=true;
	        	first=-1;
	        }
	        if(request.getParameter("second")!=null && request.getParameter("first").matches("[-+]?[0-9]*\\\\.?[0-9]+")) {
	        	second= Double.parseDouble(request.getParameter("second"));
	        } else {
	        	er2=true;
	        	second=-1;
	        }
		    result=0;
		    if(request.getParameter("action")==null) {
		    	act="+";
		    } else {
			act = request.getParameter("action");
		    }
			switch(act)
			{
			case "+":
				result = first+second;
				break;
			case "-":
				result=first-second;
				break;
			case "*":
				result = first * second;
				break;
			case ":":
				result=first/second;
				break;
			}
		
		
		// Set the response message's MIME type
	      response.setContentType("text/html;charset=UTF-8");
	      // Allocate a output writer to write the response message into the network socket
	      PrintWriter out = response.getWriter();
	 
	      // Write the response message, in an HTML page
	      try {
	         out.println("<!DOCTYPE html>");
	         out.println("<html><head>");
	         out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
	         out.println("<title>Calculator</title></head>");
	         out.println("<body>");
	         out.println("<h1>Calculator</h1>");  // says Hello
	         // Echo client's request information
	         out.println("<form>");
	         out.println("<p>First part</p><input name=\"first\" type=\"text\"> " + (er1?error:""));
	         out.println("<p>Second part</p><input name=\"second\" type=\"text\">" + (er2?error:""));
	         out.println("<p>Action : </p><select name=\"action\"><option value=\"+\">+</option><option value=\"-\">-</option><option value=\"*\">*</option><option value=\":\">:</option></select> <br/>");
	         out.println("<button type=\"submit\">Calculate</button><br/>");
	         out.println("<label><strong> Your result is : <h2>" + result + "</h2></strong></label>");
	         out.println("</form>");
	         out.println("</body>");
	         out.println("</html>");
	      } finally {
	         out.close();  // Always close the output writer
	      }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   
		doGet(request, response);
	}

}
