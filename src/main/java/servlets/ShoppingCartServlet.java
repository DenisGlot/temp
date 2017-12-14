package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import dao.entity.Product;
import scenario.Scenario;
import shopping_card.ShoppingCard;
import shopping_card.ShoppingCarts;

public class ShoppingCartServlet extends HttpServlet {
	private static final long serialVersionUID = 13453441243L;
	
	private final Logger logger = Logger.getLogger(ShoppingCartServlet.class);
	
	private Scenario scenario;
	
    public ShoppingCartServlet() {
        super();
        scenario = new  Scenario();
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
			//For script
     		out.println("<script type=\"text/javascript\" src=\"jquery-3.2.1.min.js\"></script>");
			out.println("<script type=\"text/javascript\" src=\"shopping.js\"></script>");
			// For style from bootstrap
			out.println("<link href=\"styleForCart\" rel=\"stylesheet\">");
			//
			out.println("<title>Details</title></head>");
			out.println("<body>");
			
		    // Logic begins
			HttpSession session = request.getSession();
			String email = (String) session.getAttribute("email");
			String isBuying = request.getParameter("buy");
			ShoppingCard shoppingCard = ShoppingCarts.shoppingCarts.get(email);
		    if(isBuying !=null && isBuying.equals("yes")) {
		    	scenario.buyFromBasket(shoppingCard);
		    	ShoppingCarts.shoppingCarts.remove(email);
		    }
		    //Logic ends
		        out.println("div class=\"shopping-cart\">");
		        out.println("<div class=\"title\">Shopping Bag</div>");
		        //Products 
		        for (Entry<Product, Integer> orderUnit : shoppingCard.getProducts().entrySet()) {
		        	Product product = orderUnit.getKey();
		        	Integer quantity = orderUnit.getValue();
		        	out.println("<div class=\"item\">");
			        out.println(" <div class=\"buttons\"><span class=\"delete-btn\"></span><span class=\"like-btn\"></span></div>");
			        out.println("<div class=\"image\"><img src=\"" + product.getUrlofimg() + "\" alt=\"\" /></div>");
			        out.println("<div class=\"description\"><span>" + product.getName() +"</span><span>" + product.getPrice() + "</span></div>");
			        out.println("<div class=\"quantity\">");
			        out.println("<button class=\"plus-btn\" type=\"button\" name=\"button\">");
			        //Image plus
			        out.println("<img src=\"https://designmodo.com/demo/shopping-cart/plus.svg\" alt=\"\" />");
			        out.println("</button>");
			        out.println(" <input type=\"text\" name=\"name\" value=\"" + quantity + "\">");
			        out.println("<button class=\"minus-btn\" type=\"button\" name=\"button\">");
			        //Image Minus
			        out.println("<img src=\"https://designmodo.com/demo/shopping-cart/minus.svg\" alt=\"\" />");
			        out.println("</button>");
			        out.println("</div>");
			        out.println("<div class=\"total-price\">" + product.getPrice() * quantity + "$</div>");
			        out.println("</div>");
		        }
		        out.println("<a href = \"/shoppingcart\">Put it in cart</a>");
		        out.println("</div>");
				out.println("</body>");
				out.println("</html>");
			}
		}

}
