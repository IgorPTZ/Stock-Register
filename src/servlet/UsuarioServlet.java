package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Usuario;
import dao.DaoUsuario;

@WebServlet("/usuarioServlet")
public class UsuarioServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private DaoUsuario daoUsuario = new DaoUsuario();
       
    public UsuarioServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String acao = request.getParameter("acao");
		
		String login = request.getParameter("login");
			
		if(acao.equalsIgnoreCase("delete")) {
			
			daoUsuario.excluir(login);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/cadastrousuario.jsp");
			
			request.setAttribute("usuarios", daoUsuario.listar());
			
			requestDispatcher.forward(request, response);
		}
		else if(acao.equalsIgnoreCase("put")) {
			
			Usuario usuario = daoUsuario.consultar(login);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/cadastrousuario.jsp");
			
			request.setAttribute("usuario", usuario);
			
			requestDispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String login = request.getParameter("login");
		
		String senha = request.getParameter("senha");
		
		Usuario usuario = new Usuario(login, senha);
		
		daoUsuario.inserir(usuario);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/cadastrousuario.jsp");
		
		request.setAttribute("usuarios", daoUsuario.listar());
		
		requestDispatcher.forward(request, response);
	}
}
