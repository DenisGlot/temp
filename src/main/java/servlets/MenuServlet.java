package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;

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
	public String insertJs() {
		return "js/bootstrap.min";
	}

	@Override
	public String insertCss() {
		return "css/carousel";
	}

	@Override
	public String insertTitle() {
		return "Home Page";
	}

	@Override
	public void insertLogic(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws IOException {
		
		 out.println(" <!-- Carousel\r\n" + 
		 		"    ================================================== -->\r\n" + 
		 		"    <div id=\"myCarousel\" class=\"carousel slide\" data-ride=\"carousel\">\r\n" + 
		 		"      <!-- Indicators -->\r\n" + 
		 		"      <ol class=\"carousel-indicators\">\r\n" + 
		 		"        <li data-target=\"#myCarousel\" data-slide-to=\"0\" class=\"active\"></li>\r\n" + 
		 		"        <li data-target=\"#myCarousel\" data-slide-to=\"1\"></li>\r\n" + 
		 		"        <li data-target=\"#myCarousel\" data-slide-to=\"2\"></li>\r\n" + 
		 		"      </ol>\r\n" + 
		 		"      <div class=\"carousel-inner\" role=\"listbox\">\r\n" + 
		 		"        <div class=\"item active\">\r\n" + 
		 		"          <img class=\"first-slide\" src=\"imgs/slide_1.jpg\" style=\"position: relative; width: 900px; height:900px; top:-200px;\" alt=\"First slide\">\r\n" + 
		 		"          <div class=\"container\">\r\n" + 
		 		"            <div class=\"carousel-caption\">\r\n" + 
		 		"              <h1>New Era of Nikon</h1>\r\n" + 
		 		"              <p>Only today! <code>-40%</code> on all cameras!</p>\r\n" + 
		 		"              <p><a class=\"btn btn-lg btn-primary\" href=\"" + Prefix.prefix + "/item?product=1\" role=\"button\">Buy it now!</a></p>\r\n" + 
		 		"            </div>\r\n" + 
		 		"          </div>\r\n" + 
		 		"        </div>\r\n" + 
		 		"        <div class=\"item\">\r\n" + 
		 		"          <img class=\"second-slide\" src=\"imgs/slide_2.jpg\" style=\"position: relative; width: 900px; height:900px; top:-200px;\" alt=\"Second slide\">\r\n" + 
		 		"          <div class=\"container\">\r\n" + 
		 		"            <div class=\"carousel-caption\">\r\n" + 
		 		"              <h1>Make youself happy</h1>\r\n" + 
		 		"              <p>Acquire a heater with new technologty which keeps you warm th whole winter</p>\r\n" + 
		 		"              <p><a class=\"btn btn-lg btn-primary\" href=\"" + Prefix.prefix + "/item?product=1\" role=\"button\">Acquire it now!</a></p>\r\n" + 
		 		"            </div>\r\n" + 
		 		"          </div>\r\n" + 
		 		"        </div>\r\n" + 
		 		"        <div class=\"item\">\r\n" + 
		 		"          <img class=\"third-slide\" src=\"imgs/slide_3.jpg\" style=\"position: relative; width: 900px; height:900px; top:-200px;\" alt=\"Third slide\">\r\n" + 
		 		"          <div class=\"container\">\r\n" + 
		 		"            <div class=\"carousel-caption\">\r\n" + 
		 		"              <h1>Printer which you've never seen</h1>\r\n" + 
		 		"              <p>With colored glass </p>\r\n" + 
		 		"              <p><a class=\"btn btn-lg btn-primary\" href=\"" + Prefix.prefix + "/item?product=1\" role=\"button\">What is it?</a></p>\r\n" + 
		 		"            </div>\r\n" + 
		 		"          </div>\r\n" + 
		 		"        </div>\r\n" + 
		 		"      </div>\r\n" + 
		 		"      <a class=\"left carousel-control\" href=\"#myCarousel\" role=\"button\" data-slide=\"prev\">\r\n" + 
		 		"        <span class=\"glyphicon glyphicon-chevron-left\" aria-hidden=\"true\"></span>\r\n" + 
		 		"        <span class=\"sr-only\">Previous</span>\r\n" + 
		 		"      </a>\r\n" + 
		 		"      <a class=\"right carousel-control\" href=\"#myCarousel\" role=\"button\" data-slide=\"next\">\r\n" + 
		 		"        <span class=\"glyphicon glyphicon-chevron-right\" aria-hidden=\"true\"></span>\r\n" + 
		 		"        <span class=\"sr-only\">Next</span>\r\n" + 
		 		"      </a>\r\n" + 
		 		"    </div><!-- /.carousel -->\r\n" + 
		 		"\r\n" + 
		 		"\r\n" + 
		 		"    <!-- Marketing messaging and featurettes\r\n" + 
		 		"    ================================================== -->\r\n" + 
		 		"    <!-- Wrap the rest of the page in another container to center all the content. -->\r\n" + 
		 		"\r\n" + 
		 		"    <div class=\"container marketing\">\r\n" + 
		 		"\r\n" + 
		 		"      <!-- Three columns of text below the carousel -->\r\n" + 
		 		"      <div class=\"row\">\r\n" + 
		 		"        <div class=\"col-lg-4\">\r\n" + 
		 		"          <img class=\"img-circle\" src=\"data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==\" alt=\"Generic placeholder image\" width=\"140\" height=\"140\">\r\n" + 
		 		"          <h2>Heading</h2>\r\n" + 
		 		"          <p>Donec sed odio dui. Etiam porta sem malesuada magna mollis euismod. Nullam id dolor id nibh ultricies vehicula ut id elit. Morbi leo risus, porta ac consectetur ac, vestibulum at eros. Praesent commodo cursus magna.</p>\r\n" + 
		 		"          <p><a class=\"btn btn-default\" href=\"#\" role=\"button\">View details &raquo;</a></p>\r\n" + 
		 		"        </div><!-- /.col-lg-4 -->\r\n" + 
		 		"        <div class=\"col-lg-4\">\r\n" + 
		 		"          <img class=\"img-circle\" src=\"data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==\" alt=\"Generic placeholder image\" width=\"140\" height=\"140\">\r\n" + 
		 		"          <h2>Heading</h2>\r\n" + 
		 		"          <p>Duis mollis, est non commodo luctus, nisi erat porttitor ligula, eget lacinia odio sem nec elit. Cras mattis consectetur purus sit amet fermentum. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh.</p>\r\n" + 
		 		"          <p><a class=\"btn btn-default\" href=\"#\" role=\"button\">View details &raquo;</a></p>\r\n" + 
		 		"        </div><!-- /.col-lg-4 -->\r\n" + 
		 		"        <div class=\"col-lg-4\">\r\n" + 
		 		"          <img class=\"img-circle\" src=\"data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==\" alt=\"Generic placeholder image\" width=\"140\" height=\"140\">\r\n" + 
		 		"          <h2>Heading</h2>\r\n" + 
		 		"          <p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Vestibulum id ligula porta felis euismod semper. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.</p>\r\n" + 
		 		"          <p><a class=\"btn btn-default\" href=\"#\" role=\"button\">View details &raquo;</a></p>\r\n" + 
		 		"        </div><!-- /.col-lg-4 -->\r\n" + 
		 		"      </div><!-- /.row -->\r\n" + 
		 		"\r\n" + 
		 		"      <!-- FOOTER -->\r\n" + 
		 		"      <footer>\r\n" + 
		 		"        <p class=\"pull-right\"><a href=\"#\">Back to top</a></p>\r\n" + 
		 		"        <p>&copy; " + Calendar.getInstance().get(Calendar.YEAR) + " Was stolen from bootstrap. &middot; <a href=\"#\">Privacy</a> &middot; <a href=\"#\">Terms</a></p>\r\n" + 
		 		"      </footer>\r\n" + 
		 		"\r\n" + 
		 		"    </div><!-- /.container -->\r\n" + 
		 		"\r\n" + 
		 		"\r\n" + 
		 		"    <!-- Bootstrap core JavaScript\r\n" + 
		 		"    ================================================== -->\r\n" + 
		 		"    <!-- Placed at the end of the document so the pages load faster -->\r\n" + 
		 		"    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js\"></script>\r\n" + 
		 		"    <!-- Just to make our placeholder images work. Don't actually copy the next line! -->\r\n" + 
		 		"    <script src=\"js/holder.min.js\"></script>"); 
		
	}

}
