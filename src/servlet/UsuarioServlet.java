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
		
		if(acao.equalsIgnoreCase("listall")) {
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/cadastrousuario.jsp");
			
			request.setAttribute("usuarios", daoUsuario.listar());
			
			requestDispatcher.forward(request, response);
		}
		else if(acao.equalsIgnoreCase("delete")) {
			
			Long id = Long.parseLong(request.getParameter("id"));
			
			daoUsuario.excluir(id);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/cadastrousuario.jsp");
			
			request.setAttribute("usuarios", daoUsuario.listar());
			
			requestDispatcher.forward(request, response);
		}
		else if(acao.equalsIgnoreCase("put")) {
			
			Long id = Long.parseLong(request.getParameter("id"));
			
			Usuario usuario = daoUsuario.consultar(id);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/cadastrousuario.jsp");
			
			request.setAttribute("usuario", usuario);
			
			requestDispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String acao = request.getParameter("acao");
		
		if(acao != null && acao.equalsIgnoreCase("reset")) {
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/cadastrousuario.jsp");
			
			request.setAttribute("usuarios", daoUsuario.listar());
			
			requestDispatcher.forward(request, response);
		}
		else {
			String id = request.getParameter("id");
			
			Long usuarioId = !id.isEmpty() ? Long.parseLong(id) : 0;
			
			String nome  = request.getParameter("nome");
			
			String login = request.getParameter("login");
			
			String senha = request.getParameter("senha");
			
			String telefone = request.getParameter("telefone");
			
			Usuario usuario = new Usuario(usuarioId, login, senha, nome, telefone);
			
			if(id == null || id.isEmpty() && !daoUsuario.isLoginValido(login)) {
				
				request.setAttribute("mensagemDeErro", "O login informado nao pode ser cadastrado novamente!");
			}	
			else if((id == null || id.isEmpty()) && daoUsuario.isLoginValido(login)) {
				
				daoUsuario.inserir(usuario);
			}
			else if(id != null && id.isEmpty() == false){
				
				daoUsuario.atualizar(usuario);
			}
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/cadastrousuario.jsp");
			
			request.setAttribute("usuarios", daoUsuario.listar());
			
			requestDispatcher.forward(request, response);
		}
	}
}
