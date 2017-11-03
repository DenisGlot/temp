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
	private double first;
	private double second;
       
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
		
		// Logic below
	      String act;
	      String pattern = "[-+]?\\d+?[.]?[0-9]*?";
	      String error ="Please, write correct number. Example 12.34";
	      boolean er1=false,er2=false;
	      
	        if(request.getParameter("first")!=null && request.getParameter("first")!="" && request.getParameter("first").matches(pattern)) {
	        	first = Double.parseDouble(request.getParameter("first"));
	        } else {
	        	er1=true;
	        	first=-1;
	        }
	        if(request.getParameter("second")!=null && request.getParameter("second")!="" && request.getParameter("first").matches(pattern)) {
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
			case "sqrt first":
				result = Math.sqrt(first);
			    break;
			case "sqrt second":
				result = Math.sqrt(second);
				break;
			}
		
		
		// Set the response message's MIME type
	      response.setContentType("text/html;charset=UTF-8");
	      // Allocate a output writer to write the response message into the network socket
	      PrintWriter out = response.getWriter();
	 
	      // Write the response message, in a HTML page
	      try {
	         out.println("<!DOCTYPE html>");
	         out.println("<html><head>");
	         out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
	         out.println("<title>Calculator</title></head>");
	         out.println("<body>");
	         out.println("<h1>Calculator Pro</h1>");  // says Hello
	         // Echo client's request information
	         out.println("<form>");
	         out.println("<p>First part</p><input style=\"border-radius: 6px; border: 1px solid #ccc;\" name=\"first\" type=\"text\" value=\"" + first + "\"><em style=\"color:red;\"> " + (er1?error:"") + "</em>");
	         out.println("<p>Second part</p><input style=\"border-radius: 6px; border: 1px solid #ccc;\" name=\"second\" type=\"text\" value = \" " +  second + " \"> <em style=\\\"color:red;\\\">" + (er2?error:"") + "</em>");
	         out.println("<p>Action : <strong> " + act + "</strong> </p><select name=\"action\"><option value=\"+\">Plus</option><option value=\"-\">Minus</option><option value=\"*\">Multiply</option>"
	         		+ "<option value=\":\">Divide</option><option value=\"sqrt first\">Sqrt of First part</option><option value=\"sqrt second\">Sqrt of Second part</option></select> <br/><br/>");
	         out.println("<button style=\"width : 170px; height : 35px; border-radius: 15px; border: 3px solid 	#228B22; \" type=\"submit\">Calculate</button><br/>");
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
