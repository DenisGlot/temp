package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.derby.iapi.util.StringUtil;
import org.apache.log4j.Logger;

import cache.realization.UserCache;
import cache.realization.simple.ProductCache;
import cache.realization.simple.SuplierCache;
import dao.MyDAO;
import dao.entity.Product;
import dao.entity.Suplier;
import dao.entity.User;
import prefix.Prefix;
import scenario.Scenario;
import shopping_card.ShoppingCard;
import shopping_card.ShoppingCarts;

@WebServlet("/item")
public class ItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1234234534L;
	
	private final Logger logger = Logger.getLogger(ItemServlet.class);
	
	private ProductCache productCache;
	
	private SuplierCache suplierCache;
	
	private UserCache userCahce;
       
    public ItemServlet() {
        super();
        productCache = new ProductCache(Product.class);
        suplierCache = new SuplierCache(Suplier.class);
        userCahce = new UserCache(User.class);
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
			HttpSession session = request.getSession();
			String email = (String) session.getAttribute("email");
		    String productidString = request.getParameter("product");
		    String quantity = request.getParameter("quantity");
		    Integer productid;
		    Product product;
		    if(productidString == null) {
		    	logger.warn("Parameter item was null!!!");
		    	response.sendRedirect("/");
			} else {
				
				productid = Integer.parseInt(productidString);
				product = productCache.get(productid);
				if (product == null) {
					logger.warn("In database was not any appropriate id for product");
					return;
				}
				String isPutting = request.getParameter("cart");
				boolean isPut = isPutting !=null && isPutting.equals("yes"); 
				if(isPut) {
					ShoppingCard shoppingCard = new ShoppingCard(userCahce.get(email));
				    shoppingCard.addProduct(product, Integer.parseInt(quantity));
					ShoppingCarts.shoppingCarts.put(email,shoppingCard );
				}
				
				// Logic ends
				// Real html
				out.println(" <div class=\"container\">");
				out.println("<h1 class=\"my-4\">" + product.getName() +"</h1>");
				out.println("<div class=\"row\">");
				out.println("<div class=\"col-md-8\"><img class=\"img-fluid\" src=\"http://placehold.it/750x500\" alt=\"\"></div>");
				out.println("<div class=\"col-md-4\">");
				out.println("<h3 class=\"my-3\">Description</h3>");
				out.println("<p>" + product.getDescription() + "</p>");
				out.println("<h3 class=\"my-3\">Price : " + product.getPrice() + "</h3>");
				out.println("<h3 class=\"my-3\">Left : " + product.getQuantity() + "</h3>");
				out.println("<h3 class=\"my-3\">Made by : " + suplierCache.get(product.getSuplierid()).getName() +" </h3>");
				out.println("</div>");
				out.println("</div>");
				if(isPut) {
					out.println("<a href = \"" + Prefix.prefix + "/shoppingcart?cart=yes\">Put it in cart</a>");
				} else {
					out.println("<a href = \"" + Prefix.prefix + "/item?product=" + productid + "&cart=yes\">Put it in cart</a>");
				}
				out.println("</div");
				out.println("</body>");
				out.println("</html>");
			}
		}
	}

}
