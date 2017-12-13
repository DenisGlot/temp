package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.entity.Product;
import scenario.Scenario;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Scenario scenario;
       
    public MenuServlet() {
        super();
        scenario = new Scenario();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sendHtmlToBrowser(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	

	private void sendHtmlToBrowser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try (PrintWriter out = response.getWriter()) {
			out.println("<!DOCTYPE html>");
			out.println("<html><head>");
			out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
     		out.println("<script type=\"text/javascript\" src=\"jquery-3.2.1.min.js\"></script>");
//			out.println("<script type=\"text/javascript\" src=\"sign.js\"></script>");
			// For style
			out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"styleForMenu.css\">");
			//
			out.println("<title>Catalog</title></head>");
			out.println("<body>");
		    out.println("<div class=\"container\">");
		    out.println(" <h1>Catalog</h1><br/>");
		    out.println(" <div class=\"catalog\">");
		    for(Product product : scenario.getCatalogByCategory(1)) {
		    	out.println("<div class=\"item\">" + product.getName() + "<br/><a href=\"/item?product=" + product.getProductid() + "\">See Details</a></div>");
		    }
		    out.println("");
			out.println("</div>");
			out.println("</body>");
			out.println("</html>");
		}
	}

}
