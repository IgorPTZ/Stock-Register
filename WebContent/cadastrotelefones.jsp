<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Cadastro de telefones</title>
	<link rel="stylesheet" href="resources/css/cadastro.css">

</head>

<body>
	<center>
		<h2>Cadastro de telefones</h2>
	    <a href="acessoliberado.jsp">Retornar para o cadastro de usuarios</a><br/>
	    <a href="cadastrousuario.jsp">Sair</a>
		<h3 style="color: red;">${mensagem}</h3>
	</center>
	
	<form action="telefonesServlet" id="formTelefone" method="post" onsubmit="return validarCamposDoFormulario()">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Usuário</td>
						<td><input type="text" readonly="readonly" id="usuarioId" name="usuarioId" value="${usuarioSelecionado.id}" class="field-long"></td>
						<td><input type="text" readonly="readonly" id="usuarioNome" name="usuarioNome" value="${usuarioSelecionado.nome}" class="field-long"></td>
					</tr>
				
					<tr>
						<td>Código:</td>
						<td><input type="text" readonly="readonly" id="id" name="id" class="field-long"></td>
					<tr/>
					
					<tr>
						<td>Número:</td>
						<td><input type="text" id="numero" name="numero" class="field-long"></td>
					</tr>
					
					<tr>
						<td>Tipo:</td>
						<td><input type="text" id="tipo" name="tipo" class="field-long"></td>
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
			<caption>Telefones cadastrados</caption>
			<tr>
				<th style="text-align:center;">Id</th>
				<th style="text-align:center;">Número</th>
				<th style="text-align:center;">Tipo</th>
				<th style="text-align:center;">Excluir</th>
			</tr>
			<c:forEach items="${telefones}" var="telefone">
				<tr>
					<td style="width: 150px"><c:out value="${telefone.id}"></c:out></td>
					<td style="width: 150px"><c:out value="${telefone.numero}"></c:out></td>
					<td style="width: 200px"><c:out value="${telefone.tipo}"></c:out></td>
					<td><a href="telefonesServlet?acao=delete&id=${telefone.id}"><img src="resources/img/excluir.png" title="Excluir" width="20px" height="20px"></a></td>
					<td><a href="telefonesServlet?acao=put&id=${telefone.id}"><img src="resources/img/editar.png" title="Editar" width="20px" height="20px"></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<script type="text/javascript">
		function validarCamposDoFormulario() {
			
			if(document.getElementById("numero").value == '') {
				
				alert('O campo número é obrigatório!');
				return false;
			}
			else if(document.getElementById("tipo").value == '') {
				
				alert('O campo tipo é obrigatório!');
				return false;
			}
			
			return true;
		}
	</script>	
</body>

</html>