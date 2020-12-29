package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Telefone;
import connection.SingleConnection;

public class DaoTelefone {
	
	private Connection connection;
	
	public DaoTelefone() {
		
		connection = SingleConnection.getConnection();
	}
	
	public void inserir(Telefone telefone) {
		
		try {
			
			String sql = "insert into usuario_telefone (numero, tipo, usuario) values (?, ?, ?)";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, telefone.getNumero());
			
			preparedStatement.setString(2, telefone.getTipo());
			
			preparedStatement.setLong(3, telefone.getUsuario());
			
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
	
	public Telefone consultar(Long id) {
		
		try {
			
			String sql = "select * from usuario_telefone where id = " + id;
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				
				Telefone telefone = new Telefone(Long.parseLong(resultSet.getString("id")),
												 resultSet.getString("numero"),
												 resultSet.getString("tipo"),
												 Long.parseLong(resultSet.getString("usuario")));
				
				return telefone;
			}
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<Telefone> listar(Long usuario) {
		
		try {
			
			List<Telefone> telefones = new ArrayList<Telefone>();
			
			String sql = "select * from usuario_telefone where usuario = " + usuario;
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				
				Telefone telefone = new Telefone(Long.parseLong(resultSet.getString("id")),
						                         resultSet.getString("numero"),
												 resultSet.getString("tipo"),
												 Long.parseLong(resultSet.getString("usuario")));
				
				telefones.add(telefone);
			}
			
			return telefones;
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void atualizar(Telefone telefone) {
		
		try {
			
			String sql = "update usuario_telefone set numero = ?, tipo = ?, usuario = ? where id = " + telefone.getId();
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, telefone.getNumero());
			
			preparedStatement.setString(2, telefone.getTipo());
			
			preparedStatement.setLong(3, telefone.getUsuario());
			
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
			
			String sql = "delete from usuario_telefone where id  = '" + id + "'";
			
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
