package servlet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

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
		
		if( acao == null || acao.equalsIgnoreCase("listall")) {
			
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
				
				String cep = request.getParameter("cep");
				
				String rua = request.getParameter("rua");
				
				String bairro = request.getParameter("bairro");
				
				String cidade = request.getParameter("cidade");
				
				String uf = request.getParameter("uf");
				
				String ibge = request.getParameter("ibge");
				
				String ativo = request.getParameter("ativo");
				
				String sexo = request.getParameter("sexo");
				
				String perfilDeConsumo = request.getParameter("perfilConsumo");
				
				String[] informacoesDaImagem = new String[3];
				
				String[] informacoesDoDocumento = new String[2];
				
				informacoesDaImagem = obterImagemEnviada(request);
					
				informacoesDoDocumento = obterDocumentoEnviado(request);
				
				Usuario usuario = null;
				
				if(nome == null  || nome.isEmpty()  || 
				   login == null || login.isEmpty() || 
				   senha == null || senha.isEmpty()) {
					
					validado = false;
					
					mensagem = "Nome, login, senha e telefone são campos obrigatórios e não podem estar vazios!";
					
					request.setAttribute("mensagem", mensagem);
				}
				
				if(ativo == null) {
					
					 usuario = new Usuario(usuarioId, 
		                      login, 
		                      senha, 
		                      nome, 
		                      cep,
		                      rua,
		                      bairro,
		                      cidade,
		                      uf,
		                      ibge,
		                      informacoesDaImagem[0],
		                      informacoesDaImagem[1],
		                      informacoesDaImagem[2],
		                      informacoesDoDocumento[0],
		                      informacoesDoDocumento[1],
		                      false,
		                      sexo,
		                      perfilDeConsumo);
				}
				else if(ativo.equalsIgnoreCase("on")) {
					
					 usuario = new Usuario(usuarioId, 
		                      login, 
		                      senha, 
		                      nome, 
		                      cep,
		                      rua,
		                      bairro,
		                      cidade,
		                      uf,
		                      ibge,
		                      informacoesDaImagem[0],
		                      informacoesDaImagem[1],
		                      informacoesDaImagem[2],
		                      informacoesDoDocumento[0],
		                      informacoesDoDocumento[1],
		                      true,
		                      sexo,
		                      perfilDeConsumo);
				}

				
				if(informacoesDaImagem[0] != null && informacoesDaImagem[1] != null) {
					
					usuario.setAtualizacaoDeImagem(true);
				}
				
				if(informacoesDoDocumento[0] != null && informacoesDoDocumento[1] != null) {
					
					usuario.setAtualizacaoDeDocumento(true);
				}
					
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
	private String[] obterImagemEnviada(HttpServletRequest request) {
		
		try {
			/* Inicio - Upload de arquivos */
			
			String[] informacoesDaImagem = new String[3];
			
			if(ServletFileUpload.isMultipartContent(request)) {
				
				Part imagem = request.getPart("foto");
				
				if(imagem != null && imagem.getSize() > 0) {
					
					informacoesDaImagem[0] = new Base64()
							                 .encodeBase64String(Utils.converterDeStreamParaByte(imagem.getInputStream()));
					
					informacoesDaImagem[1] = imagem.getContentType();
					
					/* Inicio - Criação de miniatura da imagem (Miniatura utilizada na listagem de clientes, evitando carregar a imagem original na lista, pois a mesma é muito pesada)*/
					
					// Transformar base64 em imagem (decode)
					byte[] bytesDaImagemDecodificados = new Base64().decodeBase64(informacoesDaImagem[0]);
					
					BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(bytesDaImagemDecodificados));
					
					// Pega o tipo da imagem
					int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
					
					// Cria imagem em miniatura
					BufferedImage resizedImage = new BufferedImage(100, 100, type);
					
					Graphics2D graphics2D = resizedImage.createGraphics();
					
					graphics2D.drawImage(bufferedImage, 0, 0, 100, 100, null);
					
					graphics2D.dispose();
					
					// Passar miniaturar para o output
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					
					ImageIO.write(resizedImage, "png", outputStream);
					
					String miniaturaDaImagemEmBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(outputStream.toByteArray());
					
					informacoesDaImagem[2] = miniaturaDaImagemEmBase64;
					
					/* Fim - Criação de miniatura da imagem */
				}
				else {
					informacoesDaImagem[0] = null;
					informacoesDaImagem[1] = null;
					informacoesDaImagem[2] = null;
				}
			}
			else {
				informacoesDaImagem[0] = null;
				informacoesDaImagem[1] = null;
				informacoesDaImagem[2] = null;
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
				
				if(documento != null && documento.getSize() > 0) {
					informacoesDoDocumento[0] = new Base64()
							 .encodeBase64String(Utils
									             .converterDeStreamParaByte(documento.getInputStream()));
	
					informacoesDoDocumento[1] = documento.getContentType();
				}
				else {
					informacoesDoDocumento[0] = null;
					informacoesDoDocumento[1] = null;
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
