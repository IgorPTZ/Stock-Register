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
		
		if(acao.equalsIgnoreCase("listall") || acao == null) {
			
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
		else if(acao.equalsIgnoreCase("download")) {
			
			String tipo = request.getParameter("tipo");
			
			id = Long.parseLong(request.getParameter("id"));
						
			Usuario usuario = daoUsuario.consultar(id);	
			
			if(usuario != null) {
				
				byte[] arquivoEmBytes = null;
				
				if(tipo.equalsIgnoreCase("foto")) {
					
					response.setHeader("Content-Disposition", "attachment;filename=arquivo." 
							+ usuario.getContentTypeDaImagem().split("\\/")[1]);
					
					/* Converte a imagem em base64 para um array de bytes*/
					arquivoEmBytes = new Base64().decodeBase64(usuario.getFotoBase64());
				}
				else if(tipo.equalsIgnoreCase("documento")) {
					
					response.setHeader("Content-Disposition", "attachment;filename=arquivo." 
							+ usuario.getContentTypeDoDocumento().split("\\/")[1]);
					
					/* Converte a imagem em base64 para um array de bytes*/
					arquivoEmBytes = new Base64().decodeBase64(usuario.getDocumentoBase64());
				}
				
				
				/* Insere os bytes da imagem em um objeto de entrada para ser processado */
				InputStream inputStream = new ByteArrayInputStream(arquivoEmBytes);
				
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
			else if(acao != null && acao.equalsIgnoreCase("save")){
				
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
				
				String[] informacoesDaImagem = new String[2];
				
				String[] informacoesDoDocumento = new String[2];
				
				if(id != null && id.isEmpty() == false) {
					
					 informacoesDaImagem[0] = request.getParameter("fotoTemp");
					 
					 informacoesDaImagem[1] = request.getParameter("contentTypeDaImagemTemp");
					
					 informacoesDoDocumento[0] = request.getParameter("documentoTemp");
					 
					 informacoesDoDocumento[1] = request.getParameter("contentTypeDoDocumentoTemp");
				}
				else {
					
					 informacoesDaImagem = obterImagemEnviada(request);
						
					 informacoesDoDocumento = obterDocumentoEnviado(request);
				}
				
				if(nome == null  || nome.isEmpty()  || 
				   login == null || login.isEmpty() || 
				   senha == null || senha.isEmpty()) {
					
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
						                      informacoesDaImagem[0],
						                      informacoesDaImagem[1],
						                      informacoesDoDocumento[0],
						                      informacoesDoDocumento[1]);
				
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
	private String[] obterImagemEnviada(HttpServletRequest request) {
		
		try {
			/* Inicio - Upload de arquivos */
			
			String[] informacoesDaImagem = new String[2];
			
			if(ServletFileUpload.isMultipartContent(request)) {
				
				Part imagem = request.getPart("foto");
				
				if(imagem != null) {
					
					byte[] bytesDaImagem = Utils.converterDeStreamParaByte(imagem.getInputStream());
				
					informacoesDaImagem[0] = new Base64()
							                 .encodeBase64String(bytesDaImagem);
					
					informacoesDaImagem[1] = imagem.getContentType();
					
					/* Inicio - Cria��o de miniatura da imagem */
					
					
					/* Fim - Cria��o de miniatura da imagem */
				}
				else {
					informacoesDaImagem[0] = null;
					informacoesDaImagem[1] = null;
				}
			}
			
			/* Fim - Upload de arquivos */
			
			return informacoesDaImagem;
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
	
	@SuppressWarnings("static-access")
	private String[] obterDocumentoEnviado(HttpServletRequest request) {
		
		try {
			/* Inicio - Upload de arquivos */
			
			String[] informacoesDoDocumento = new String[2];
			
			if(ServletFileUpload.isMultipartContent(request)) {
				
				Part documento = request.getPart("documento");
				
				if(documento != null) {
					informacoesDoDocumento[0] = new Base64()
							 .encodeBase64String(Utils
									             .converterDeStreamParaByte(documento.getInputStream()));
	
					informacoesDoDocumento[1] = documento.getContentType();
				}
			}
			else {
				informacoesDoDocumento[0] = null;
				informacoesDoDocumento[1] = null;
			}
			
			/* Inicio - Upload de arquivos */
			
			return informacoesDoDocumento;
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
}
