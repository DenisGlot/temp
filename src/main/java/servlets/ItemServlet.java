package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.derby.iapi.util.StringUtil;
import org.apache.log4j.Logger;

import cache.realization.simple.ProductCache;
import cache.realization.simple.SuplierCache;
import dao.MyDAO;
import dao.entity.Product;
import dao.entity.Suplier;
import scenario.Scenario;

public class ItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1234234534L;
	
	private final Logger logger = Logger.getLogger(ItemServlet.class);
	
	private ProductCache productCache;
	
	private SuplierCache suplierCache;
       
    public ItemServlet() {
        super();
        productCache = new ProductCache(Product.class);
        suplierCache = new SuplierCache(Suplier.class);
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
			// For style from bootstrap
			out.println("<link href=\"css/bootstrap.min.css\" rel=\"stylesheet\">");
			out.println("<link href=\"css/portfolio-item.css\" rel=\"stylesheet\">");
			//
			out.println("<title>Details</title></head>");
			out.println("<body>");
		    // Logic begins
		    String productidString = request.getParameter("item"); 
		    if(productidString == null) {
		    	logger.warn("Parameter item was null!!!");
		    	response.sendRedirect("/");
			} else {
				Integer productid = Integer.parseInt(productidString);
				Product product = productCache.get(productid);
				if (product == null) {
					logger.warn("In database was not any appropriate id for product");
				}
				// Logic ends
				// Real html
				out.println(" <div class=\"container\">");
				out.println("h1 class=\"my-4\">" + product.getName() +"</h1>");
				out.println("<div class=\"row\">");
				out.println("<div class=\"col-md-8\"><img class=\"img-fluid\" src=\"http://placehold.it/750x500\" alt=\"\"></div>");
				out.println("<div class=\"col-md-4\">");
				out.println("<h3 class=\"my-3\">Description</h3>");
				out.println("<p>" + product.getDescription() + "</p>");
				out.println("<h3 class=\"my-3\">Price : " + product.getPrice() + "</h3>");
				out.println("<h3 class=\"my-3\">Left : " + product.getQuantity() + "</h3>");
				out.println("<h3 class=\"my-3\">Made in : " + suplierCache.get(product.getSuplierid()).getName() +" </h3>");
				out.println("");
				out.println("");
				out.println("");
				out.println("");
				out.println("");
				out.println("");
				out.println("");
				out.println("<a href = \"/shppingcart\">Put it in cart</a>");
				out.println("</body>");
				out.println("</html>");
			}
		}
	}

}
