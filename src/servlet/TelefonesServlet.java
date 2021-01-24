package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Telefone;
import beans.Usuario;
import dao.DaoTelefone;
import dao.DaoUsuario;


@WebServlet("/telefonesServlet")
public class TelefonesServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private DaoUsuario daoUsuario = new DaoUsuario();
	
	private DaoTelefone daoTelefone = new DaoTelefone();

    public TelefonesServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String acao = request.getParameter("acao");
		
		String usuarioId = request.getParameter("usuarioId");
		
		if(acao != null && usuarioId != null) {
			
			if(acao.equals("addTelefone")) {
				
				Usuario usuario = daoUsuario.consultar(Long.parseLong(usuarioId));
				
				request.getSession().setAttribute("usuarioSelecionado", usuario);
				
				request.setAttribute("usuarioSelecionado", usuario);
				
				request.setAttribute("telefones", daoTelefone.listar(usuario.getId()));
				
				RequestDispatcher view = request.getRequestDispatcher("/cadastrotelefones.jsp");
				
				view.forward(request, response);
			}
			else if(acao.equals("delete")) {
				
				String telefoneId = request.getParameter("id");
				
				daoTelefone.excluir(Long.parseLong(telefoneId));
				
				Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioSelecionado");
				
				request.setAttribute("mensagem", "Telefone excluído com sucesso!");
				
				request.setAttribute("telefones", daoTelefone.listar(usuario.getId()));
				
				RequestDispatcher view = request.getRequestDispatcher("/cadastrotelefones.jsp");
				
				view.forward(request, response);
			}
		}
		else {
			request.setAttribute("mensagem", "O usuario associado ao telefone nao foi encontrado. Por favor, selecione o usuário novamente.");
			
			request.setAttribute("usuarios", daoUsuario.listar());
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/cadastrousuario.jsp");
			
			requestDispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String numero = request.getParameter("numero");
		
		String tipo   = request.getParameter("tipo");
		
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioSelecionado");
		
		if(numero == null || numero.isEmpty()) {
			
			request.getSession().setAttribute("usuarioSelecionado", usuario);
			
			request.setAttribute("usuarioSelecionado", usuario);
			
			request.setAttribute("mensagem", "O número é um campo obrigatório!");
			
			request.setAttribute("telefones", daoTelefone.listar(usuario.getId()));
			
			RequestDispatcher view = request.getRequestDispatcher("/cadastrotelefones.jsp");
			
			view.forward(request, response);
		}
		else {
			
			Telefone telefone = new Telefone(numero, 
                    						 tipo,
                    						 usuario.getId());

			daoTelefone.inserir(telefone);
			
			request.getSession().setAttribute("usuarioSelecionado", usuario);
			
			request.setAttribute("usuarioSelecionado", usuario);
			
			request.setAttribute("mensagem", "Telefone salvo com sucesso!");
			
			request.setAttribute("telefones", daoTelefone.listar(usuario.getId()));
			
			RequestDispatcher view = request.getRequestDispatcher("/cadastrotelefones.jsp");
			
			view.forward(request, response);
		}
	}
}
