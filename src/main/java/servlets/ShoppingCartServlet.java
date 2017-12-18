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

import dao.entity.Product;
import math.Arithmetic;
import prefix.Prefix;
import scenario.Scenario;
import shopping_card.ShoppingCard;
import shopping_card.ShoppingCarts;

public class ShoppingCartServlet extends TemplateServlet {
	private static final long serialVersionUID = 13453441243L;

	private final Logger logger = Logger.getLogger(ShoppingCartServlet.class);

	private Scenario scenario;
	
	private String quantityString;

	public ShoppingCartServlet() {
		super();
		scenario = new Scenario();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
		quantityString = data.get("quantity").getAsString();
		logger.debug("The quantity was = " + quantityString);
		doGet(request, response);
	}

	@Override
	public String insertJs() {
		return null;
	}

	@Override
	public String insertCss() {
		return "styleForCart";
	}

	@Override
	public String insertTitle() {
		return "Details";
	}

	@Override
	public void insertLogic(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws IOException {
		// Logic begins
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		String isBuying = request.getParameter("buy");
		ShoppingCard shoppingCard = ShoppingCarts.shoppingCarts.get(email);
		boolean isBought = false;
		if (isBuying != null && isBuying.equals("yes")) {
			scenario.buyFromBasket(shoppingCard);
			ShoppingCarts.shoppingCarts.remove(email);
			isBought = true;
		}
		// Logic ends
		if (isBought) {
			out.println(
					"<h1 style=\"text-align: center;\">Thank you for using our shop. All information will be sent to your mail.</h1>");
			out.println("<a style =\"font: bold 20px Arial;background-color: #ADFF2F;margin-left: 33%;color: #333333;padding: 2px 6px 2px 6px;border: 2px solid #CCCCCC; border-radius: 6px;\" href = \"" + Prefix.prefix + "/\">Choose something else? </a>");
		} else {
			out.println("<form id =\"myForm\">");
			out.println("<div class=\"shopping-cart\">");
			out.println("<div class=\"title\">Shopping Bag</div>");
			// Products
			for (Entry<Product, Integer> orderUnit : shoppingCard.getProducts().entrySet()) {
				Product product = orderUnit.getKey();
				Integer quantityInMemory = orderUnit.getValue();
				if(quantityString!=null && quantityString.matches("\\d")) {
					Integer quantity = Integer.parseInt(quantityString);
					if(!quantityInMemory.equals(quantity));{
						shoppingCard.setQuantityOfProducts(product, quantity);
						quantityInMemory = quantity;
					}
				} else {
					logger.warn("The quantity from javascript was not number but was " + quantityString);
				}
				
				out.println("<div id=\"myDiv\" class=\"item\">");
				out.println(
						"<div class=\"buttons\"><span class=\"delete-btn\"></span><span class=\"like-btn\"></span></div>");
				out.println("<div class=\"image\"><img src=\"" + product.getUrlofimg() + "\" alt=\"\" width=\"120\" height=\"80\" /></div>");
				out.println("<div class=\"description\"><span>" + product.getName() + "</span><span>"
						+ Arithmetic.round(product.getPrice(), 2) + "$</span></div>");
				out.println("<div class=\"quantity\">");
				 out.println("<button class=\"plus-btn\" type=\"submit\" name=\"button\">");
				 // Image plus
				 out.println("<img src=\"https://designmodo.com/demo/shopping-cart/plus.svg\" alt=\"\" />");
				 out.println("</button>");
				 out.println(" <input id=\"quantity\" type=\"submit\" name=\"name\" value=\"" + quantityInMemory + "\">");
				 out.println("<button class=\"minus-btn\" type=\"submit\" name=\"button\">");
				 // Image Minus
				 out.println("<img src=\"https://designmodo.com/demo/shopping-cart/minus.svg\" alt=\"\" />");
				 out.println("</button>");
				out.println("</div>");
				out.println("<div id=\"total-price\" class=\"total-price\">" + Arithmetic.round(product.getPrice(), 2) * quantityInMemory + "$</div>");
				out.println("</div>");
			}
			out.println("<a style=\"margin-left: 30%;\" href = \"" + Prefix.prefix + "/shoppingcart?buy=yes\">Buy it!</a>");
			out.println("</div>");
			out.println("</form>");
			out.println("<script type=\"text/javascript\" src=\"settingQuantitie.js\"></script>");

		}
	}

}
