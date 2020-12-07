package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Usuario;
import connection.SingleConnection;

public class DaoUsuario {
	
	private Connection connection;
	
	public DaoUsuario() {
		
		connection = SingleConnection.getConnection();
	}
	
	public boolean isLoginValido(String login) {
		
		try {
			String sql = "select count(1) as quantidade from usuario where login = '" + login + "'";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				
				return ( resultSet.getInt("quantidade") <= 0 );
			}
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void inserir(Usuario usuario) {
		
		try {
			
			String sql = "insert into usuario(login, senha, nome, telefone) values (?, ?, ?, ?)";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, usuario.getLogin());
			
			preparedStatement.setString(2, usuario.getSenha());
			
			preparedStatement.setString(3, usuario.getNome());
			
			preparedStatement.setString(4, usuario.getTelefone());
			
			preparedStatement.execute();
			
			connection.commit();
		}
		catch(Exception e) {
			
			e.printStackTrace();
			
			try {
				
				connection.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
		}
	}
	
	public Usuario consultar(Long id) {
		
		try {
			
			String sql = "select * from usuario where id = " + id;
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				
				Usuario usuario = new Usuario(Long.parseLong(resultSet.getString("id")), 
						                      resultSet.getString("login"), 
						                      resultSet.getString("senha"),
						                      resultSet.getString("nome"),
						                      resultSet.getString("telefone"));
				 
				return usuario;
			}
			
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<Usuario> listar() {
		try {
			
			List<Usuario> usuarios = new ArrayList<Usuario>();
			
			String sql = "select * from usuario";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				
				Usuario usuario = new Usuario(Long.parseLong(resultSet.getString("id")), 
						                      resultSet.getString("login"), 
						                      resultSet.getString("senha"),
						                      resultSet.getString("nome"),
						                      resultSet.getString("telefone"));
				
				usuarios.add(usuario);
			}
			
			return usuarios;
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void atualizar(Usuario usuario) {
		
		try {
			
			String sql = "update usuario set login = ?, senha = ?, nome = ?, telefone = ? where id = " + usuario.getId();
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, usuario.getLogin());
			
			preparedStatement.setString(2, usuario.getSenha());
			
			preparedStatement.setString(3, usuario.getNome());
			
			preparedStatement.setString(4, usuario.getTelefone());
			
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
			
			String sql = "delete from usuario where id = '" + id + "'";
			
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
