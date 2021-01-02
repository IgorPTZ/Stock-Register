package servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import beans.Usuario;
import dao.DaoUsuario;
import util.Utils;

@WebServlet("/usuarioServlet")
@MultipartConfig
public class UsuarioServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private DaoUsuario daoUsuario = new DaoUsuario();
       
    public UsuarioServlet() {
        super();
    }

	@SuppressWarnings("static-access")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Long id = null;
		
		String acao = request.getParameter("acao");
		
		if(acao.equalsIgnoreCase("listall")) {
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/cadastrousuario.jsp");
			
			request.setAttribute("usuarios", daoUsuario.listar());
			
			requestDispatcher.forward(request, response);
		}
		else if(acao.equalsIgnoreCase("delete")) {
			
			id = Long.parseLong(request.getParameter("id"));
			
			daoUsuario.excluir(id);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/cadastrousuario.jsp");
			
			request.setAttribute("usuarios", daoUsuario.listar());
			
			requestDispatcher.forward(request, response);
		}
		else if(acao.equalsIgnoreCase("put")) {
			
			id = Long.parseLong(request.getParameter("id"));
				
			Usuario usuario = daoUsuario.consultar(id);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/cadastrousuario.jsp");
			
			request.setAttribute("usuario", usuario);
			
			requestDispatcher.forward(request, response);
		}
		else if(acao.equalsIgnoreCase("downloadImagem")) {
			
			id = Long.parseLong(request.getParameter("id"));
						
			Usuario usuario = daoUsuario.consultar(id);	
			
			if(usuario != null) {
				
				response.setHeader("Content-Disposition", "attachment;filename=arquivo." 
				+ usuario.getContentType().split("\\/")[1]);
				
				/* Converte a imagem em base64 para um array de bytes*/
				byte[] imagemEmBytes = new Base64().decodeBase64(usuario.getFotoBase64());
				
				/* Insere os bytes da imagem em um objeto de entrada para ser processado */
				InputStream inputStream = new ByteArrayInputStream(imagemEmBytes);
				
				/* Inicio da resposta para o navegador */
				int leitura = 0;
				
				byte[] bytes = new byte[1024];
				
				OutputStream outputStream = response.getOutputStream();
				
				while((leitura = inputStream.read(bytes)) != -1) {
					
					outputStream.write(bytes, 0, leitura);
				}
				
				outputStream.flush();
				
				outputStream.close();
			}
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
					
					mensagem = "Nome, login, senha e telefone s�o campos obrigat�rios e n�o podem estar vazios!";
					
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
						
					mensagem += "Inser��o - O login e/ou a senha informado nao pode ser cadastrado novamente!";	
						
					request.setAttribute("mensagem", mensagem);
					
					validado = false;
				}	
				else if((id == null || id.isEmpty()) && daoUsuario.isLoginUsuarioNovoValido(login) && validado == true) {
					
					daoUsuario.inserir(usuario);
					
					mensagem += "Inser��o - O usuario foi inserido com sucesso!";
					
					request.setAttribute("mensagem", mensagem);
				}
				else if(id != null && id.isEmpty() == false && validado == true){
					
					if(!daoUsuario.isLoginUsuarioAntigoValido(id, login) || !daoUsuario.isSenhaDeUsuarioAntigoValida(id, senha)) {
						
						mensagem += "Edi��o - O login e/ou a senha informado nao pode ser cadastrado novamente!";
						
						request.setAttribute("mensagem", mensagem);
						
						validado = false;
					}
					else {
						daoUsuario.atualizar(usuario);
						
						mensagem += "Edi��o - O usuario foi atualizado com sucesso";
						
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
		
		try {
			/* Inicio - Upload e arquivos */
			
			String[] informacoesDaImagem = new String[2];
			
			if(ServletFileUpload.isMultipartContent(request)) {
				
				Part imagem = request.getPart("foto");
				
				informacoesDaImagem[0] = new Base64()
						                 .encodeBase64String(Utils
						                		             .converterDeStreamParaByte(imagem.getInputStream()));
				
				informacoesDaImagem[1] = imagem.getContentType();
			}
			
			/* Fim - Upload de arquivos */
			
			return informacoesDaImagem;
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
}
