package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.entity.Product;
import prefix.Prefix;
import scenario.Scenario;

@WebServlet("/menu")
public class MenuServlet extends TemplateServlet {
	private static final long serialVersionUID = 113412341243L;
	
	private Scenario scenario;
       
    public MenuServlet() {
        super();
        scenario = new Scenario();
    }

	@Override
	protected String insertJs() {
		return null;
	}

	@Override
	protected String insertCss() {
		return "styleForMenu";
	}

	@Override
	protected String insertTitle() {
		return "Catalog";
	}

	@Override
	protected void insertLogic(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws IOException {
		 out.println("<div class=\"container\">");
		    out.println(" <h1>Catalog</h1><br/>");
		    out.println(" <div class=\"catalog\">");
		    for(Product product : scenario.getCatalogByCategory(1)) {
		    	out.println("<div class=\"item\">" + product.getName() + "<br/><a href=\"" + Prefix.prefix  + "/item?product=" + product.getProductid() + "\">See Details</a></div>");
		    }
			out.println("</div>");
		
	}

}
