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

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import cache.factory.CacheType;
import dao.entity.Product;
import math.Arithmetic;
import prefix.Prefix;
import scenario.Scenario;
import shopping_card.ShoppingCard;

public class ShoppingCartServlet extends TemplateServlet {
	private static final long serialVersionUID = 13453441243L;

	private final Logger logger = Logger.getLogger(ShoppingCartServlet.class);

	public ShoppingCartServlet() {
		super();
	}

	@Override
	public String insertJs() {
		return null;
	}

	@Override
	public String insertCss() {
		return "styleForCard";
	}

	@Override
	public String insertTitle() {
		return "Details";
	}

	@Override
	public void insertLogic(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws IOException {
		
		HttpSession session = request.getSession();
		ShoppingCard shoppingCard = (ShoppingCard) session.getAttribute("card");
		
		//It is part of servlet which is not supposed to 
		//render the page it's just getting the data from ajax
		//and setting shopping card
		JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
		// Getting parameters from JSON
		if (data != null) {
			String productIdString = data.get("productId").getAsString();
			String quantityString = data.get("quantity").getAsString();
			int productId = Integer.parseInt(productIdString);
			int quantity = Integer.parseInt(quantityString);
			logger.debug(productIdString + "  " + quantityString);
			// Got to use dao to get product by Id
			shoppingCard.setQuantityOfProduct((Product) scenario.getById(CacheType.PRODUCT, productId), quantity);
			session.setAttribute("card", shoppingCard);
			shoppingCard = null; session = null; // For GC
			productIdString = null; quantityString = null;// For GC
		}  else {	
		
	      // Variables
		  String isBuying = request.getParameter("buy");
		  //Client buys all stuff
	   	  if (isBuying != null && isBuying.equals("yes")) {
			scenario.buyFromBasket(shoppingCard);
			shoppingCard.removeAllProducts();
			session.setAttribute("card", shoppingCard);
			out.println("<div class=\"contain\">");
			out.println("<strong style = \"color: #00FA9A; font-size: 1.5em;\">Thank you for using our shop. All information will be sent to your mail.</strong>");
			out.println("<a style =\"font: bold 20px Arial;background-color: #ADFF2F;color: #333333;padding: 2px 6px 2px 6px;border: 2px solid #CCCCCC; border-radius: 6px;\" href = \"" + Prefix.prefix + "/\">Choose something else? </a>");
			out.println("</div>");
 		  } else {
			out.println("<form id =\"myForm\">");
			out.println("<div class=\"shopping-cart\">");
			out.println("<div class=\"title\">Shopping Bag</div>");
			//If client hasn't chosen product yet
			if(shoppingCard == null) {
				out.println("<h1 style=\"text-align:  center;\">You haven't chosen anything</h1>");
			} else {
			  // Client has at least one product in shoppingcard
			  for (Entry<Product, Integer> orderUnit : shoppingCard.getProducts().entrySet()) {
				//Variables
				Product product = orderUnit.getKey();
				Integer quantityInMemory = orderUnit.getValue();
				//
				out.println("<div id=\"" + product.getProductid() + "\" class=\"item\">");
				out.println(
						"   <div class=\"buttons\"><span class=\"delete-btn\"></span><span class=\"like-btn\"></span></div>");
				out.println("   <div class=\"image\"><img src=\"" + product.getUrlofimg() + "\" alt=\"\" width=\"120\" height=\"80\" /></div>");
				out.println("   <div class=\"description\"><span>" + product.getName() + "</span><span name=\"price\" class = \"priceOfProduct\">"
						+ Arithmetic.round(product.getPrice(), 2) + "$</span></div>");
				out.println("   <div class=\"quantity\">");
				 out.println("    <button class=\"plus-btn\" type=\"submit\">");
				 // Image plus
				 out.println("        <img src=\"https://designmodo.com/demo/shopping-cart/plus.svg\" alt=\"\" />");
				 out.println("    </button>");
				 out.println("    <input class=\"quantityOfProduct\" type=\"submit\" name=\"quantity\" value=\"" + quantityInMemory + "\">");
				 out.println("    <button class=\"minus-btn\" type=\"submit\">");
				 // Image Minus
				 out.println("      <img src=\"https://designmodo.com/demo/shopping-cart/minus.svg\" alt=\"\" />");
				 out.println("    </button>");
				out.println("   </div>");
				out.println("   <div class=\"total-price\">" + Arithmetic.round(product.getPrice(), 2) * quantityInMemory + "$</div>");
				out.println("</div>");
			  }
			  out.println("<div style = \"text-align: center;\">");
			  out.println("    <a  href= \"" + Prefix.prefix + "/shoppingcart?buy=yes\" class = \"btn\">Buy it all!</a>");
			  out.println("</div>");
			}
			out.println("</div>");
			out.println("</form>");
			out.println("<script type=\"text/javascript\" src=\"settingQuantitie.js\"></script>");
		}
	  }
	}
}
