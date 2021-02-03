package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Categoria;
import beans.Produto;
import connection.SingleConnection;

public class DaoProduto {
	
	private Connection connection;
	
	public DaoProduto() {
		
		connection = SingleConnection.getConnection();
	}
	
	public boolean isNomeProdutoNovoValido(String nome) {
		
		try {
			String sql = "select count(1) as quantidade from produto where nome = '" + nome + "'";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				
				return (resultSet.getInt("quantidade") <= 0);
			}
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean isNomeProdutoAntigoValido(String id, String nome) {
		
		try {
			String sql = "select count(1) as quantidade from produto where nome = '" + nome + "' and id <> " + id;
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				
				return (resultSet.getInt("quantidade") <= 0);
			}		
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void inserir(Produto produto) {
		
		try {
			
			String sql = "insert into produto (nome, quantidade, valor, categoria_id) values (?, ?, ?, ?)";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, produto.getNome());
			
			preparedStatement.setDouble(2, produto.getQuantidade());
			
			preparedStatement.setDouble(3, produto.getValor());
			
			preparedStatement.setLong(4, produto.getCategoriaId());
			
			preparedStatement.execute();
			
			connection.commit();
		}
		catch(Exception e) {
			
			e.printStackTrace();
			
			try {
				
				connection.rollback();
			}
			catch(SQLException e1) {
				
				e1.printStackTrace();
			}
		}
	}
	
	public Produto consultar(Long id) {
		
		try {
			
			String sql = "select * from produto where id = " + id;
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				
				Produto produto = new Produto (Long.parseLong(resultSet.getString("id")),
											   resultSet.getString("nome"),
											   Double.parseDouble(resultSet.getString("quantidade")),
											   Double.parseDouble(resultSet.getString("valor")),
											   resultSet.getLong("categoria_id"));
				
				return produto;
			}
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<Produto> listar() {
		
		try {
			
			List<Produto> produtos = new ArrayList<Produto>();
			
			String sql = "select * from produto order by nome";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				
				Produto produto = new Produto(Long.parseLong(resultSet.getString("id")),
											  resultSet.getString("nome"),
											  Double.parseDouble(resultSet.getString("quantidade")),
											  Double.parseDouble(resultSet.getString("valor")),
											  resultSet.getLong("categoria_id"));
				
				produtos.add(produto);
			}
			
			return produtos;
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<Categoria> listarCategorias() {
		
		try {
			
			List<Categoria> categorias = new ArrayList<Categoria>();
			
			String sql = "select * from categoria order by nome";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				
				Categoria categoria = new Categoria(Long.parseLong(resultSet.getString("id")),
													resultSet.getString("nome"));
				
				categorias.add(categoria);
			}
			
			return categorias;
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void atualizar(Produto produto) {
		
		try {
			
			String sql = "update produto set nome = ?, quantidade = ?, valor = ?, categoria_id = ? where id = " + produto.getId();
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, produto.getNome());
			
			preparedStatement.setDouble(2, produto.getQuantidade());
			
			preparedStatement.setDouble(3, produto.getValor());
			
			preparedStatement.setLong(4, produto.getCategoriaId());
			
			preparedStatement.executeUpdate();
			
			connection.commit();
		}
		catch(Exception e) {
			
			e.printStackTrace();
			
			try {
				
				connection.rollback();
			}
			catch(SQLException e1) {
				
				e1.printStackTrace();
			}
		}
	}
	
	public void excluir(Long id) {
		
		try {
			
			String sql = "delete from produto where id = '" + id + "'";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.execute();
			
			connection.commit();
		}
		catch(Exception e) {
			
			e.printStackTrace();
			
			try {
				
				connection.rollback();
			}
			catch(SQLException e1) {
				
				e1.printStackTrace();
			}
		}
	}
}
