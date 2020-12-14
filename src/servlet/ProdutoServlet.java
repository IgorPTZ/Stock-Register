package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Produto;
import dao.DaoProduto;


@WebServlet("/produtoServlet")
public class ProdutoServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	private DaoProduto daoProduto = new DaoProduto();

    public ProdutoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String acao = request.getParameter("acao");
		
		if(acao.equalsIgnoreCase("listall")) {
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/cadastroproduto.jsp");
			
			request.setAttribute("produtos", daoProduto.listar());
			
			requestDispatcher.forward(request, response);
		}
		else if(acao.equalsIgnoreCase("delete")) {
			
			Long id = Long.parseLong(request.getParameter("id"));
			
			daoProduto.excluir(id);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/cadastroproduto.jsp");
			
			request.setAttribute("produtos", daoProduto.listar());
			
			requestDispatcher.forward(request, response);
		}
		else if(acao.equalsIgnoreCase("put")) {
			
			Long id = Long.parseLong(request.getParameter("id"));
			
			Produto produto = daoProduto.consultar(id);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/cadastroproduto.jsp");
			
			request.setAttribute("produto", produto);
			
			requestDispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String acao = request.getParameter("acao");
		
		String mensagem = "";
		
		Boolean validado = true;
		
		Boolean reiniciarListaDeProdutos = acao != null && acao.equalsIgnoreCase("reset");
		
		if(reiniciarListaDeProdutos) {
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/cadastroproduto.jsp");
			
			request.setAttribute("produtos", daoProduto.listar());
			
			requestDispatcher.forward(request, response);
		}
		else {
			
			String id = request.getParameter("id");
			
			Long produtoId = !id.isEmpty() ? Long.parseLong(id) : null;
			
			String nome = request.getParameter("nome");
					
			Produto produto = new Produto(produtoId,
					                      nome,
					                      Double.parseDouble(request.getParameter("quantidade")),
					                      Double.parseDouble(request.getParameter("valor")));
			
			Boolean ehUmaEdicao = id != null && id.isEmpty() == false;
			
			Boolean nomeDoNovoProdutoValido = (id == null || id.isEmpty()) && 
					                          daoProduto.isNomeProdutoNovoValido(nome);
			
			if(nomeDoNovoProdutoValido == false && ehUmaEdicao == false) {
				
				mensagem += "Inserção - O nome informado nao pode ser cadastrado novamente!";
				
				request.setAttribute("mensagem", mensagem);
				
				validado = false;
			}
			else if(nomeDoNovoProdutoValido == true && ehUmaEdicao == false) {
				
				daoProduto.inserir(produto);
				
				mensagem += "Inserção - O produto foi inserido com sucesso!";
				
				request.setAttribute("mensagem", mensagem);
			}
			else if(ehUmaEdicao) {
				
				Boolean nomeDoProdutoAntigoInvalido = !daoProduto.isNomeProdutoAntigoValido(id, nome);
				
				if(nomeDoProdutoAntigoInvalido) {
					
					mensagem += "Edição - O nome informado nao pode ser cadastrado novamente!";
					
					request.setAttribute("mensagem", mensagem);
					
					validado = false;
				}
				else {
					
					daoProduto.atualizar(produto);
					
					mensagem += "Edição - O usuario foi atualizado com sucesso";
					
					request.setAttribute("mensagem", mensagem);
				}
			}
			
			if(validado == false) {
				
				request.setAttribute("produto", produto);
			}
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/cadastroproduto.jsp");
			
			request.setAttribute("produtos", daoProduto.listar());
			
			requestDispatcher.forward(request, response);
		}
	}

}
