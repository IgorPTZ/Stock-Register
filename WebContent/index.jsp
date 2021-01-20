<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Cadastro de clientes e produtos</title>
	<link rel="stylesheet" href="resources/css/estilo.css">
</head>
<body>
	<div class="login-page">
		<center><h4>Cadastro de clientes e produtos</h4></center>
		<div class="form">
			<form action="loginServlet" method="post" class="login-form">
				Login:
				<input type="text" id="login" name="login">
				<br/>
				Senha:
				<input type="password" id="senha" name="senha">
				<br/>
				<button type="submit" value="Entrar">Entrar</button>
			</form>
		</div>
	</div>
</body>
</html>