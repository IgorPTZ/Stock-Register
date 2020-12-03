<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Cadastro de usuário</title>
	<link rel="stylesheet" href="resources/css/cadastro.css">
</head>

<body>
	<h2><center>Cadastro de usuário</center></h2>
	
	<form action="usuarioServlet" method="post">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Código:</td>
						<td><input type="text" readonly="readonly" id="id" name="id" class="field-long" value="${usuario.id}"></td>
					<tr/>
					
					<tr>
						<td>Nome:</td>
						<td><input type="text" id="nome" name="nome" value="${usuario.nome}"></td>
					</tr>
									
					<tr>
						<td>Login:</td>
						<td><input type="text" id="login" name="login" value="${usuario.login}"></td>
					</tr>
					
					<tr>
						<td>Senha:</td>
						<td><input type="password" id="senha" name="senha" value="${usuario.senha}"></td>
					</tr>
				</table>
				<input type="submit" value="Salvar">
			</li>
		</ul>
	</form>
	
	<div class="container">
		<table class="responsive-table">
			<caption>Usuários cadastrados</caption>
			<tr>
				<th>Id</th>
				<th>Login</th>
				<th>Nome</th>
				<th>Excluir</th>
				<th>Editar</th>
			</tr>
			<c:forEach items="${usuarios}" var="usuario">
				<tr>
					<td style="width: 150px"><c:out value="${usuario.id}"></c:out></td>
					<td style="width: 150px"><c:out value="${usuario.login}"></c:out></td>
					<td><c:out value="${usuario.nome}"></c:out></td>
					<td><a href="usuarioServlet?acao=delete&login=${usuario.login}"><img src="resources/img/excluir.png" title="Excluir" width="20px" height="20px"></a></td>
					<td><a href="usuarioServlet?acao=put&login=${usuario.login}"><img src="resources/img/editar.png" title="Editar" width="20px" height="20px"></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
	
</html>