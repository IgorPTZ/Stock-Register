package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Usuario;
import dao.DaoUsuario;

/**
 * Servlet implementation class PesquisaServlet
 */
@WebServlet("/pesquisaServlet")
public class PesquisaServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private DaoUsuario daoUsuario = new DaoUsuario();
       
    public PesquisaServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String descricao = request.getParameter("consultapordescricao");
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		if(descricao != null && !descricao.isEmpty()) {
			
			usuarios = daoUsuario.listarPorDescricao(descricao);
		}
		else {
			
			usuarios = daoUsuario.listar();
		}
		
		request.setAttribute("usuarios", usuarios);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/cadastrousuario.jsp");
		
		requestDispatcher.forward(request, response);
	}
}
