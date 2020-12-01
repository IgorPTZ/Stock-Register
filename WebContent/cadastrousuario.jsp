<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Cadastro de usu�rio</title>
	<link rel="stylesheet" href="resources/css/cadastro.css">
</head>

<body>
	<h3>Cadastro de usu�rio</h3>
	
	<form action="usuarioServlet" method="post">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>C�digo:</td>
						<td><input type="text" readonly="readonly" id="id" name="id" class="field-long" value="${usuario.id}"></td>
					<tr/>
				
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
	
	<table>
		<c:forEach items="${usuarios}" var="usuario">
			<tr>
				<td style="width: 150px"><c:out value="${usuario.id}"></c:out></td>
				<td style="width: 150px"><c:out value="${usuario.login}"></c:out></td>
				<td><c:out value="${usuario.senha}"></c:out></td>
				<td><a href="usuarioServlet?acao=delete&login=${usuario.login}">Excluir</a></td>
				<td><a href="usuarioServlet?acao=put&login=${usuario.login}">Editar</a>
			</tr>
		</c:forEach>
	</table>
</body>
	
</html>