<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Cadastro de produtos</title>
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js" type="text/javascript"></script>
		<script src="resources/javascript/jquery.maskMoney.min.js" type="text/javascript"></script>
		<link rel="stylesheet" href="resources/css/cadastro.css">
	</head>
	
	<body>
		<center>
			<h2>Cadastro de produtos</h2>
			<a href="acessoliberado.jsp">Retornar para o menu principal</a><br/>
			<a href="index.jsp">Sair</a>
			<h3 style="color: red;">${mensagem}</h3>
		</center>
		
		<form action="produtoServlet" id="formProduto" method="post" onsubmit="return validarCamposDoFormulario()">
			<ul class="form-style-1">
				<li>
					<table>
						<tr>
							<td>C�digo:</td>
							<td><input type="text" readonly="readonly" id="id" name="id" class="field-long" value="${produto.id}"></td>
						</tr>
						
						<tr>
							<td>Nome:</td>
							<td><input type="text" id="nome" name="nome" placeholder="Informe o nome" value="${produto.nome}" maxlength="100"></td>
						</tr>
						
						<tr>
							<td>Quantidade:</td>
							<td><input type="text" id="quantidade" name="quantidade" placeholder="Informe a quantidade" maxlength="6" value="${produto.quantidade}"></td>
						</tr>
						
						<tr>
							<td>Valor:</td>
							<td><input type="text" id="valor" name="valor" placeholder="Informe o valor" value="${produto.valorEmTexto}" maxlength="9" data-thousands="." data-decimal=","></td>
						</tr>
						
						<tr>
							<td>Categoria:</td>
							<td>
								<select id="categorias" name="categoriaId">
									<c:forEach items="${categorias}" var="categoria">
										<option value="${categoria.id}" id="${categoria.id}">
											${categoria.nome}
										</option>
									</c:forEach>
								</select>
							</td>
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
						<td style="width: 150px"><fmt:formatNumber type="number" maxFractionDigits="2" value="${produto.valor}" /></td>
						<td><a href="produtoServlet?acao=delete&id=${produto.id}" onclick="return confirm('Deseja excluir esse produto ?');"><img src="resources/img/excluir.png" title="Excluir" width="20px" height="20px"></a></td>
						<td><a href="produtoServlet?acao=put&id=${produto.id}"><img src="resources/img/editar.png" title="Editar" width="20px" height="20px"></a></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		
		<script type="text/javascript">
			
			function validarCamposDoFormulario() {
				
				if(document.getElementById("nome").value == '') {
					
					alert('O campo nome � obrigat�rio!');
					return false;
				}
				else if(document.getElementById("quantidade").value == '') {
					
					alert('O campo quantidade � obrigatorio!');
					return false;
				}
				else if(document.getElementById("valor").value == '') {
					
					alert('O campo valor � obrigat�rio!');
					return false;
				}
				
				return true;
			}
		</script>	
	</body>
			<script type="text/javascript">
			
				$(function() {
					$('#valor').maskMoney();
				});
				
				$(document).ready(function(){
					$("#quantidade").keyup(function(){
						$("#quantidade").val(this.value.match(/[0-9]*/));
					});
				});
		</script>
</html>