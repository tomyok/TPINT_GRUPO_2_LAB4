package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidades.Prestamo;
import negocio.PrestamoNegocio;
import negocioImpl.PrestamoNegocioImpl;

/**
 * Servlet implementation class RechazarPrestamoServlet
 */
@WebServlet("/RechazarPrestamoServlet")
public class RechazarPrestamoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RechazarPrestamoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int prestamoId = Integer.parseInt(request.getParameter("prestamoId"));
		
		PrestamoNegocio prestamoNegocio = new PrestamoNegocioImpl();
		try {
			prestamoNegocio.bajarPrestamo(prestamoId);
			response.sendRedirect("GestionPrestamosServlet?action=rechazar");
		} catch (Exception e) {
			e.printStackTrace();
			String error= e.getMessage();
			request.getSession().setAttribute("errorMsj", error);
			response.sendRedirect("GestionPrestamosServlet?action=ERechazar");
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
