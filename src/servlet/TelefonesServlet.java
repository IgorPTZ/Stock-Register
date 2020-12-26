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


@WebServlet("/telefonesServlet")
public class TelefonesServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private DaoUsuario daoUsuario = new DaoUsuario();

    public TelefonesServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String usuarioId = request.getParameter("usuarioId");
		
		Usuario usuario = daoUsuario.consultar(Long.parseLong(usuarioId));
		
		request.getSession().setAttribute("usuarioSelecionado", usuario);
		
		request.setAttribute("usuarioSelecionado", usuario);
		
		RequestDispatcher view = request.getRequestDispatcher("/cadastrotelefones.jsp");
		
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioSelecionado");
		
		String numero = request.getParameter("numero");
		
		String tipo   = request.getParameter("tipo");
		
		request.setAttribute("mensagem", "Telefone salvo com sucesso!");
		
		RequestDispatcher view = request.getRequestDispatcher("/cadastrotelefones.jsp");
		
		view.forward(request, response);
	}

}
