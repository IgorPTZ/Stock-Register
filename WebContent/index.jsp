<jsp:useBean id="curso" class="beans.Curso" type="beans.Curso" scope="page" />

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Curso de JSP</title>
</head>
<body>
	<c:out value="${'Tela de Acesso'}"/>
	
	<br/>
	<br/>

	<form action="loginServlet" method="post">
		Login:
		<input type="text" id="login" name="login">
		<br/>
		Senha:
		<input type="password" id="senha" name="senha">
		<br/>
		<input type="submit" value="Entrar">
	</form>
</body>
</html>