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
	
	public void inserir(Usuario usuario) {
		
		try {
			
			String sql = "insert into usuario(login, senha) values (?, ?)";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, usuario.getLogin());
			
			preparedStatement.setString(2, usuario.getSenha());
			
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
	
	public Usuario consultar(String login) {
		
		try {
			
			String sql = "select * from usuario where login = '" + login + "'";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				
				Usuario usuario = new Usuario(Long.parseLong(resultSet.getString("id")), resultSet.getString("login"), resultSet.getString("senha"));
				
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
				
				Usuario usuario = new Usuario(Long.parseLong(resultSet.getString("id")), resultSet.getString("login"), resultSet.getString("senha"));
				
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
			
			String sql = "update usuario set login = ?, senha = ? where id = " + usuario.getId();
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, usuario.getLogin());
			
			preparedStatement.setString(2, usuario.getSenha());
			
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
	
	public void excluir(String login) {
		
		try {
			
			String sql = "delete from usuario where login = '" + login + "'";
			
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
