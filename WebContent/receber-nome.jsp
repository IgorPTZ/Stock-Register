<jsp:useBean id="curso" class="beans.Curso" type="beans.Curso" scope="page" />

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Receber nome</title>
</head>
<body>
	<jsp:include page="acessoliberado.jsp" />
	
	<jsp:setProperty property="*" name="curso"/>
	
	<br/>
	Nome:<jsp:getProperty property="nome" name="curso"/>
	
	<br/>
	Codigo:<jsp:getProperty property="codigo" name="curso"/>
	
	<br/>
	Semestre:<jsp:getProperty property="semestre" name="curso"/>
	
	<br/>
	<br/>
	<br/>
	Nome: ${param.nome}
	<br/>
	Codigo: ${param.codigo}
	<br/>
	Semestre: ${param.semestre}
	<br />
	Linguagem: ${sessionScope.linguagem}
	
	<jsp:include page="acessonegado.jsp" />
</body>
</html>