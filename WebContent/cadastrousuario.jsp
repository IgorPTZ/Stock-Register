<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Cadastro de clientes</title>
	<link rel="stylesheet" href="resources/css/cadastro.css">
</head>

<body>
	<center>
		<h2>Cadastro de clientes</h2>
	    <a href="acessoliberado.jsp">Retornar para o menu principal</a>
		<h3 style="color: red;">${mensagem}</h3>
	</center>
	
	<form action="usuarioServlet" id="formUsuario" method="post" onsubmit="return validarCamposDoFormulario()">
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
						<td>Telefone:</td>
						<td><input type="text" id="telefone" name="telefone" value="${usuario.telefone}"></td>
					</tr>
					
					<tr>
						<td>Senha:</td>
						<td><input type="password" id="senha" name="senha" value="${usuario.senha}"></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Salvar"> 
						    <input type="submit" value="Cancelar" onclick="document.getElementById('formUsuario').action = 'usuarioServlet?acao=reset'"></td>
					</tr>
				</table>
			</li>
		</ul>
	</form>

	<div class="container">
		<table class="responsive-table">
			<caption>Clientes cadastrados</caption>
			<tr>
				<th style="text-align:center;">Id</th>
				<th style="text-align:center;">Login</th>
				<th style="text-align:center;">Nome</th>
				<th style="text-align:center;">Telefone</th>
				<th style="text-align:center;">Excluir</th>
				<th style="text-align:center;">Editar</th>
			</tr>
			<c:forEach items="${usuarios}" var="usuario">
				<tr>
					<td style="width: 150px"><c:out value="${usuario.id}"></c:out></td>
					<td style="width: 150px"><c:out value="${usuario.login}"></c:out></td>
					<td style="width: 200px"><c:out value="${usuario.nome}"></c:out></td>
					<td><c:out value="${usuario.telefone}"></c:out></td>
					<td><a href="usuarioServlet?acao=delete&id=${usuario.id}"><img src="resources/img/excluir.png" title="Excluir" width="20px" height="20px"></a></td>
					<td><a href="usuarioServlet?acao=put&id=${usuario.id}"><img src="resources/img/editar.png" title="Editar" width="20px" height="20px"></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<script type="text/javascript">
		function validarCamposDoFormulario() {
			
			if(document.getElementById("nome").value == '') {
				
				alert('O campo nome é obrigatório!');
				return false;
			}
			else if(document.getElementById("login").value == '') {
				
				alert('O campo login é obrigatório!');
				return false;
			}
			else if(document.getElementById("telefone").value == '') {
				
				alert('O campo telefone é obrigatório!');
				return false;
			}
			else if(document.getElementById("senha").value == '') {
				
				alert('O campo senha é obrigatório!');
				return false;
			}
			
			return true;
		}
	</script>	
</body>

</html>