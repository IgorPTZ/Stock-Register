<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Cadastro de clientes</title>
	<link rel="stylesheet" href="resources/css/cadastro.css">
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
</head>

<body>
	<center>
		<h2>Cadastro de clientes</h2>
	    <a href="acessoliberado.jsp">Retornar para o menu principal</a><br/>
	    <a href="index.jsp">Sair</a>
		<h3 style="color: red;">${mensagem}</h3>
	</center>
	
	<form action="usuarioServlet" id="formUsuario" method="post" onsubmit="return validarCamposDoFormulario()">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Código:</td>
						<td><input type="text" readonly="readonly" id="id" name="id" class="field-long" value="${usuario.id}"></td>
						
						<td>Cep:</td>
						<td><input type="text" id="cep" name="cep" onblur="consultarCep();" value="${usuario.cep}"></td>
					<tr/>
					
					<tr>
						<td>Nome:</td>
						<td><input type="text" id="nome" name="nome" value="${usuario.nome}"></td>
						
						<td>Rua:</td>
						<td><input type="text" id="rua" name="rua" value="${usuario.rua}"></td>
					</tr>
									
					<tr>
						<td>Login:</td>
						<td><input type="text" id="login" name="login" value="${usuario.login}"></td>
						
						<td>Bairro:</td>
						<td><input type="text" id="bairro" name="bairro" value="${usuario.bairro}"></td>
					</tr>
					
					<tr>
						<td>Telefone:</td>
						<td><input type="text" id="telefone" name="telefone" value="${usuario.telefone}"></td>
						
						<td>Cidade:</td>
						<td><input type="text" id="cidade" name="cidade" value="${usuario.cidade}"></td>
					</tr>
					
					<tr>
						<td>Senha:</td>
						<td><input type="password" id="senha" name="senha" value="${usuario.senha}"></td>
						
						<td>Estado:</td>
						<td><input type="text" id="uf" name="uf" value="${usuario.uf}"></td>
					</tr>
								
					<tr>
						<td>IBGE:</td>
						<td><input type="text" id="ibge" name="ibge" value="${usuario.ibge}"></td>
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
					<td><a href="telefonesServlet?acao=addTelefone&usuarioId=${usuario.id}"><img src="resources/img/telefones.png" title="Telefones" width="20px" height="20px"></a></td>
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