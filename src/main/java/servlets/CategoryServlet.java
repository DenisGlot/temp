package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.entity.Product;
import prefix.Prefix;
import scenario.Scenario;

@WebServlet("/category")
public class CategoryServlet extends TemplateServlet {
	private static final long serialVersionUID = 57865451245435635L;
	
    public CategoryServlet() {
        super();
        
    }

	@Override
	public String insertJs() {
		return null;
	}

	@Override
	public String insertCss() {
		return "css/3-col-portfolio";
	}

	@Override
	public String insertTitle() {
		return null;
	}

	@Override
	public void insertLogic(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws IOException {
		String categoryString = request.getParameter("categoryId");
		if(categoryString==null) {
			throw new NullPointerException("categoryId was null in categoryServlet");
		}
		Integer categoryId = Integer.parseInt(categoryString);
		out.println("<!-- Page Content -->\r\n" + 
				"    <div class=\"container\">\r\n" + 
				"\r\n" + 
				"      <!-- Page Heading -->\r\n" + 
				"      <h1 class=\"my-4\">Page Heading\r\n" + 
				"        <small>Secondary Text</small>\r\n" + 
				"      </h1>\r\n" + 
				"\r\n" + 
				"      <div class=\"row\">");
		 for(Product product : scenario.getCatalogByCategory(categoryId)) {
	         out.println("        <div class=\"col-lg-4 col-sm-6 portfolio-item\">\r\n" + 
				         "          <div class=\"card h-100\">\r\n" + 
				         "            <a href=\"#\"><img width=\"700\" height=\"400\" class=\"card-img-top\" src=\"" + product.getUrlofimg() + "\" alt=\"\"></a>\r\n" + 
				         "            <div class=\"card-body\">\r\n" + 
				         "              <h4 class=\"card-title\">\r\n" + 
				         "                <a href=\"" + Prefix.prefix  + "/item?product=" + product.getProductid() + "\">" + product.getName() + "</a>\r\n" + 
				         "              </h4>\r\n" + 
				         "              <p class=\"card-text\">" + product.getDescription() + "</p>\r\n" + 
				         "            </div>\r\n" + 
				         "          </div>\r\n" +
				         "          <hr style=\" border: none; background-color: black; color: black;height: 2px;\">\r\n" +
				         "        </div>");
		 }
		 out.println("      </div>\r\n" + 
				"      <!-- /.row -->\r\n" + 
				"\r\n" + 
				"      <!-- Pagination -->\r\n" + 
				"      <ul class=\"pagination justify-content-center\">\r\n" + 
				"        <li class=\"page-item\">\r\n" + 
				"          <a class=\"page-link\" href=\"#\" aria-label=\"Previous\">\r\n" + 
				"            <span aria-hidden=\"true\">&laquo;</span>\r\n" + 
				"            <span class=\"sr-only\">Previous</span>\r\n" + 
				"          </a>\r\n" + 
				"        </li>\r\n" + 
				"        <li class=\"page-item\">\r\n" + 
				"          <a class=\"page-link\" href=\"#\">1</a>\r\n" + 
				"        </li>\r\n" + 
				"        <li class=\"page-item\">\r\n" + 
				"          <a class=\"page-link\" href=\"#\">2</a>\r\n" + 
				"        </li>\r\n" + 
				"        <li class=\"page-item\">\r\n" + 
				"          <a class=\"page-link\" href=\"#\">3</a>\r\n" + 
				"        </li>\r\n" + 
				"        <li class=\"page-item\">\r\n" + 
				"          <a class=\"page-link\" href=\"#\" aria-label=\"Next\">\r\n" + 
				"            <span aria-hidden=\"true\">&raquo;</span>\r\n" + 
				"            <span class=\"sr-only\">Next</span>\r\n" + 
				"          </a>\r\n" + 
				"        </li>\r\n" + 
				"      </ul>\r\n" + 
				"\r\n" + 
				"    </div>\r\n" + 
				"    <!-- /.container -->");
		
	}

}
