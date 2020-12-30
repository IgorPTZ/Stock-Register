package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

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
		
		try {
			String acao = request.getParameter("acao");
			
			String mensagem = "";
			
			Boolean validado = true;
			
			if(acao != null && acao.equalsIgnoreCase("reset")) {
				
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/cadastrousuario.jsp");
				
				request.setAttribute("usuarios", daoUsuario.listar());
				
				requestDispatcher.forward(request, response);
			}
			else {
					
				String[] informacoesDaFoto = obterImagemEnviada(request);
				
				String id = request.getParameter("id");
				
				Long usuarioId = (id != null && !id.isEmpty()) ? Long.parseLong(id) : null;
				
				String nome  = request.getParameter("nome");
				
				String login = request.getParameter("login");
				
				String senha = request.getParameter("senha");
				
				String telefone = request.getParameter("telefone");
				
				String cep = request.getParameter("cep");
				
				String rua = request.getParameter("rua");
				
				String bairro = request.getParameter("bairro");
				
				String cidade = request.getParameter("cidade");
				
				String uf = request.getParameter("uf");
				
				String ibge = request.getParameter("ibge");
				
				if(nome == null  || nome.isEmpty()  || 
				   login == null || login.isEmpty() || 
				   senha == null || senha.isEmpty() ||
				   telefone == null || telefone.isEmpty()) {
					
					validado = false;
					
					mensagem = "Nome, login, senha e telefone são campos obrigatórios e não podem estar vazios!";
					
					request.setAttribute("mensagem", mensagem);
				}
				
				Usuario usuario = new Usuario(usuarioId, 
						                      login, 
						                      senha, 
						                      nome, 
						                      telefone,
						                      cep,
						                      rua,
						                      bairro,
						                      cidade,
						                      uf,
						                      ibge,
						                      informacoesDaFoto[0],
						                      informacoesDaFoto[1]);
				
				if((id == null || id.isEmpty()) && 
				   (!daoUsuario.isLoginUsuarioNovoValido(login) || !daoUsuario.isSenhaDeUsuarioNovoValida(senha)) && 
				   validado == true) {
						
					mensagem += "Inserção - O login e/ou a senha informado nao pode ser cadastrado novamente!";	
						
					request.setAttribute("mensagem", mensagem);
					
					validado = false;
				}	
				else if((id == null || id.isEmpty()) && daoUsuario.isLoginUsuarioNovoValido(login) && validado == true) {
					
					daoUsuario.inserir(usuario);
					
					mensagem += "Inserção - O usuario foi inserido com sucesso!";
					
					request.setAttribute("mensagem", mensagem);
				}
				else if(id != null && id.isEmpty() == false && validado == true){
					
					if(!daoUsuario.isLoginUsuarioAntigoValido(id, login) || !daoUsuario.isSenhaDeUsuarioAntigoValida(id, senha)) {
						
						mensagem += "Edição - O login e/ou a senha informado nao pode ser cadastrado novamente!";
						
						request.setAttribute("mensagem", mensagem);
						
						validado = false;
					}
					else {
						daoUsuario.atualizar(usuario);
						
						mensagem += "Edição - O usuario foi atualizado com sucesso";
						
						request.setAttribute("mensagem", mensagem);
					}
				}
				
				if(validado == false) {
					
					request.setAttribute("usuario", usuario);
				}
				
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/cadastrousuario.jsp");
				
				request.setAttribute("usuarios", daoUsuario.listar());
				
				requestDispatcher.forward(request, response);
			}
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("static-access")
	private String[] obterImagemEnviada(HttpServletRequest request) throws FileUploadException {
		
		/* Inicio - Upload e arquivos */
		
		String[] informacoesDaImagem = new String[2];
		
		if(ServletFileUpload.isMultipartContent(request)) {
			
			List<FileItem> fileItems = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
			
			for (FileItem fileItem : fileItems) {
				
				if(fileItem.getFieldName().equals("foto")) {
					
					informacoesDaImagem[0] = new Base64().encodeBase64String(fileItem.get());
					
					informacoesDaImagem[1] = fileItem.getContentType();
					
					System.out.println(informacoesDaImagem[0]);
				}
				
			}
		}
		
		/* Fim - Upload de arquivos */
		
		return informacoesDaImagem;
	}
}
