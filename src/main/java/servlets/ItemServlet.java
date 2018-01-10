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

import cache.factory.CacheType;
import cache.realization.UserCache;
import cache.realization.simple.ProductCache;
import cache.realization.simple.SuplierCache;
import dao.MyDAO;
import dao.entity.Product;
import dao.entity.Suplier;
import dao.entity.User;
import math.Arithmetic;
import prefix.Prefix;
import scenario.Scenario;
import shopping_card.ShoppingCard;
import shopping_card.ShoppingCarts;

@WebServlet("/item")
public class ItemServlet extends TemplateServlet {
	private static final long serialVersionUID = 1234234534L;

	private final Logger logger = Logger.getLogger(ItemServlet.class);
	
	public ItemServlet() {
		super();
	}

	@Override
	public String insertJs() {
		return "sign";
	}

	@Override
	public String insertCss() {
		return null;
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
		String phone = (String) session.getAttribute("phone");
		String productidString = request.getParameter("product");
		String quantity = request.getParameter("quantity");
		Integer productid;
		Product product;
		if (productidString == null) {
			logger.warn("Parameter item was null!!!");
			response.sendRedirect(Prefix.prefix);
		} else {

			productid = Integer.parseInt(productidString);
			product = (Product) scenario.getById(CacheType.PRODUCT, productid);
			if (product == null) {
				logger.warn("In database was not any appropriate id for product");
				return;
			}
			String isPutting = request.getParameter("cart");
			boolean isPut = isPutting != null && isPutting.equals("yes");
			if (isPut) {
				ShoppingCard shoppingCard = new ShoppingCard((User) scenario.getById(CacheType.USER, phone));
				shoppingCard.addProduct(product, Integer.parseInt(quantity));
				ShoppingCarts.shoppingCarts.put(phone, shoppingCard);
			}

			// Logic ends
			// Real html
			out.println(" <div class=\"container\">");
			out.println("<h1 class=\"my-4\">" + product.getName() + "</h1>");
			out.println("<div class=\"row\">");
			out.println(
					"<div class=\"col-md-8\"><img class=\"img-fluid\" src=\"" + product.getUrlofimg() + "\" alt=\"\" width=\"750\" height=\"500\"></div>");
			out.println("<div class=\"col-md-4\">");
			out.println("<h3 class=\"my-3\">Description</h3>");
			out.println("<p>" + product.getDescription() + "</p>");
			out.println("<h3 class=\"my-3\">Price : " + Arithmetic.round(product.getPrice(), 2) + "$</h3>");
			out.println("<h3 class=\"my-3\">Left : " + product.getQuantity() + "</h3>");
			out.println(
					"<h3 class=\"my-3\">Made by : " + ((Suplier) scenario.getById(CacheType.SUPLIER,product.getSuplierid())).getName() + " </h3>");
			if (isPut) {
				out.println("<a style =\"font: bold 20px Arial;background-color: #ADFF2F;margin-left: 27%;color: #333333;padding: 2px 6px 2px 6px;border: 2px solid #CCCCCC; border-radius: 6px;\" href = \"" + Prefix.prefix + "/shoppingcart?cart=yes\">Go to the shopping cart</a>");
			} else {
				if(product.getQuantity()<=0) {
					out.println("<a style =\"font: bold 20px Arial;background-color: #ADFF2F;margin-left: 27%;color: #333333;padding: 2px 6px 2px 6px;border: 2px solid #CCCCCC; border-radius: 6px;\" href = \"" + Prefix.prefix + "\">Sorry,Nothing left.Go choose something else</a>");
				} else {
					out.println("<a style =\"font: bold 20px Arial;background-color: #ADFF2F;margin-left: 27%;color: #333333;padding: 2px 6px 2px 6px;border: 2px solid #CCCCCC; border-radius: 6px;\" href = \"" + Prefix.prefix + "/item?product=" + productid
							+ "&quantity=1&cart=yes\">Add it in cart</a>");
				}
			}
			
			out.println("</div>");
			out.println("</div>");
			
			out.println("</div");

		}
	}

}
