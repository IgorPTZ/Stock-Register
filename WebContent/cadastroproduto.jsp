<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Cadastro de produtos</title>
		<link rel="stylesheet" href="resources/css/cadastro.css">
	</head>
	
	<body>
		<center>
			<h2>Cadastro de produtos</h2>
			<h3 style="color: red;">${mensagem}</h3>
		</center>
		
		<form action="produtoServlet" id="formProduto" method="post">
			<ul class="form-style-1">
				<li>
					<table>
						<tr>
							<td>Código:</td>
							<td><input type="text" readonly="readonly" id="id" name="id" class="field-long" value="${produto.id}"></td>
						</tr>
						
						<tr>
							<td>Nome:</td>
							<td><input type="text" id="nome" name="nome" value="${produto.nome}"></td>
						</tr>
						
						<tr>
							<td>Quantidade:</td>
							<td><input type="text" id="quantidade" name="quantidade" value="${produto.quantidade}"></td>
						</tr>
						
						<tr>
							<td>Valor:</td>
							<td><input type="text" id="valor" name="valor" value="${produto.valor}"></td>
						</tr>
						
						<tr>
							<td></td>
							<td><input type="submit" value="Salvar">
							    <input type="submit" value="Cancelar" onclick="document.getElementById('formProduto').action = 'produtoServlet?acao=reset'"></td>
						</tr>
					</table>
				</li>
			</ul>
		</form>
		
		<div class="container">
			<table class="responsive-table">
				<caption>Produtos cadastrados</caption>
				<tr>
					<th style="text-align:center;">Id</th>
					<th style="text-align:center;">Nome</th>
					<th style="text-align:center;">Quantidade</th>
					<th style="text-align:center;">Valor</th>
					<th style="text-align:center;">Excluir</th>
					<th style="text-align:center;">Editar</th>
				</tr>
				<c:forEach items="${produtos}" var="produto">
					<tr>
						<td style="width: 150px"><c:out value="${produto.id}"></c:out></td>
						<td style="width: 150px"><c:out value="${produto.nome}"></c:out></td>
						<td style="width: 150px"><c:out value="${produto.quantidade}"></c:out></td>
						<td style="width: 150px"><c:out value="${produto.valor}"></c:out></td>
						<td><a href="produtoServlet?acao=delete&id=${produto.id}"><img src="resources/img/excluir.png" title="Excluir" width="20px" height="20px"></a></td>
						<td><a href="produtoServlet?acao=put&id=${produto.id}"><img src="resources/img/editar.png" title="Editar" width="20px" height="20px"></a></td>
					</tr>
				</c:forEach>
			</table>
		</div>	
	</body>
</html>