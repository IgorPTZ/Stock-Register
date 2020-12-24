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
						<td>Código:</td>
						<td><input type="text" readonly="readonly" id="id" name="id" class="field-long"></td>
					<tr/>
					
	
					
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
				<th style="text-align:center;">Login</th>
				<th style="text-align:center;">Nome</th>
				<th style="text-align:center;">Telefone</th>
				<th style="text-align:center;">Excluir</th>
				<th style="text-align:center;">Editar</th>
				<th style="text-align:center;">Telefones</th>
			</tr>
			<c:forEach items="${usuarios}" var="usuario">
				<tr>
					<td style="width: 150px"><c:out value="${usuario.id}"></c:out></td>
					<td style="width: 150px"><c:out value="${usuario.login}"></c:out></td>
					<td style="width: 200px"><c:out value="${usuario.nome}"></c:out></td>
					<td><c:out value="${usuario.telefone}"></c:out></td>
					<td><a href="usuarioServlet?acao=delete&id=${usuario.id}"><img src="resources/img/excluir.png" title="Excluir" width="20px" height="20px"></a></td>
					<td><a href="usuarioServlet?acao=put&id=${usuario.id}"><img src="resources/img/editar.png" title="Editar" width="20px" height="20px"></a></td>
					<td><a href="telefonesServlet?acao=telefone&usuarioId=${usuario.id}"><img src="resources/img/telefones.png" title="Telefones" width="20px" height="20px"></a></td>
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
		
		function consultarCep() {
			
			let cep = $("#cep").val();
			
            $.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {

                if (!("erro" in dados)) {
                	
                    //Atualiza os campos com os valores da consulta.
                    $("#rua").val(dados.logradouro);
                    $("#bairro").val(dados.bairro);
                    $("#cidade").val(dados.localidade);
                    $("#uf").val(dados.uf);
                    $("#ibge").val(dados.ibge);
                } 
                else {
                	
                    //CEP pesquisado não foi encontrado.
                    limparCamposDeEndereco();
                    alert("CEP não encontrado.");
                }
            });
		}
		
		function limparCamposDeEndereco() {
			
			$("#rua").val("");
			$("#bairro").val("");
			$("#cidade").val("");
			$("#uf").val("");
			$("#ibge").val("");
		}
	</script>	
</body>

</html>