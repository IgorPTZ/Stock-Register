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
	
	public boolean isLoginUsuarioNovoValido(String login) {
		
		try {
			String sql = "select count(1) as quantidade from usuario where login = '" + login + "'";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				
				return ( resultSet.getInt("quantidade") <= 0 );
			}
			
			return false;
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean isSenhaDeUsuarioNovoValida(String senha) {
		
		try {
			
			String sql = "select count(1) as quantidade from usuario where senha = '" + senha + "'";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				
				return (resultSet.getInt("quantidade") <= 0);
			}
			
			return false;
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean isSenhaDeUsuarioAntigoValida(String id, String senha) {
		
		try {
			String sql = "select count (1) as quantidade from usuario where senha = '" + senha + "' and id <> " + id;
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				
				return (resultSet.getInt("quantidade") <= 0);
			}
			
			return false;
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean isLoginUsuarioAntigoValido(String id, String login) {
		
		try {
			String sql = "select count(1) as quantidade from usuario where login = '" + login + "' and id <> " + id;
		
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				
				return (resultSet.getInt("quantidade") <= 0);
			}
			
			return false;
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void inserir(Usuario usuario) {
		
		try {
			
			String sql = "insert into usuario(login, senha, nome, telefone, cep, rua, bairro, cidade, uf, ibge, imagem, tipo_imagem, documento, tipo_documento, miniatura_imagem) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, usuario.getLogin());
			
			preparedStatement.setString(2, usuario.getSenha());
			
			preparedStatement.setString(3, usuario.getNome());
			
			preparedStatement.setString(4, usuario.getTelefone());
			
			preparedStatement.setString(5, usuario.getCep());
			
			preparedStatement.setString(6, usuario.getRua());
			
			preparedStatement.setString(7, usuario.getBairro());
			
			preparedStatement.setString(8, usuario.getCidade());
			
			preparedStatement.setString(9, usuario.getUf());
			
			preparedStatement.setString(10, usuario.getIbge());
			
			preparedStatement.setString(11, usuario.getFotoBase64());
			
			preparedStatement.setString(12, usuario.getContentTypeDaImagem());
			
			preparedStatement.setString(13, usuario.getDocumentoBase64());
			
			preparedStatement.setString(14, usuario.getContentTypeDoDocumento());
			
			preparedStatement.setString(15, usuario.getMiniaturaDaFotoBase64());
			
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
			
			String sql = "select * from usuario where id = '" + id + "' and login <> 'admin'";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				
				Usuario usuario = new Usuario(Long.parseLong(resultSet.getString("id")), 
						                      resultSet.getString("login"), 
						                      resultSet.getString("senha"),
						                      resultSet.getString("nome"),
						                      resultSet.getString("telefone"),
						                      resultSet.getString("cep"),
						                      resultSet.getString("rua"),
						                      resultSet.getString("bairro"),
						                      resultSet.getString("cidade"),
						                      resultSet.getString("uf"),
						                      resultSet.getString("ibge"),
						                      resultSet.getString("imagem"),
						                      resultSet.getString("tipo_imagem"),
						                      resultSet.getString("miniatura_imagem"),
						                      resultSet.getString("documento"),
						                      resultSet.getString("tipo_documento"));
				 
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
			
			String sql = "select * from usuario where login <> 'admin' order by nome";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				
				Usuario usuario = new Usuario(Long.parseLong(resultSet.getString("id")), 
						                      resultSet.getString("login"), 
						                      resultSet.getString("senha"),
						                      resultSet.getString("nome"),
						                      resultSet.getString("telefone"),
						                      resultSet.getString("cep"),
						                      resultSet.getString("rua"),
						                      resultSet.getString("bairro"),
						                      resultSet.getString("cidade"),
						                      resultSet.getString("uf"),
						                      resultSet.getString("ibge"),
						                      null, // A imagem sera apenas carregada na apos o click para download, evitando uma lista de contatos com imagem muito pesadas
						                      resultSet.getString("tipo_imagem"),
						                      resultSet.getString("miniatura_imagem"),
						                      resultSet.getString("documento"),
						                      resultSet.getString("tipo_documento"));
				
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
			StringBuilder sql = new StringBuilder();
			
			sql.append("update usuario set login = ?, senha = ?, nome = ?, telefone = ?,");
			
			sql.append("cep = ?, rua = ?, bairro = ?, cidade = ?, uf = ?, ibge = ?,");
			
			if(usuario.getAtualizacaoDeImagem()) {
				
				sql.append(" imagem = ?, tipo_imagem = ?, miniatura_imagem = ? ");
			}
			
			
			if(usuario.getAtualizacaoDeDocumento()) {
				
				sql.append(", documento = ?, tipo_documento = ? ");
			}
			 
			sql.append("where id = " + usuario.getId());   
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
			
			preparedStatement.setString(1, usuario.getLogin());
			
			preparedStatement.setString(2, usuario.getSenha());
			
			preparedStatement.setString(3, usuario.getNome());
			
			preparedStatement.setString(4, usuario.getTelefone());
			
			preparedStatement.setString(5, usuario.getCep());
			
			preparedStatement.setString(6, usuario.getRua());
			
			preparedStatement.setString(7, usuario.getBairro());
			
			preparedStatement.setString(8, usuario.getCidade());
			
			preparedStatement.setString(9, usuario.getUf());
			
			preparedStatement.setString(10, usuario.getIbge());
			
			
			if(usuario.getAtualizacaoDeImagem()) {
				
				preparedStatement.setString(11, usuario.getFotoBase64());
				
				preparedStatement.setString(12, usuario.getContentTypeDaImagem());
				
				preparedStatement.setString(13, usuario.getMiniaturaDaFotoBase64());
			}

			if(usuario.getAtualizacaoDeDocumento()) {
				
				preparedStatement.setString(14, usuario.getDocumentoBase64());
				
				preparedStatement.setString(15, usuario.getContentTypeDoDocumento());
			}

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
			
			String sql = "delete from usuario where id = '" + id + "' and login <> 'admin'";
			
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
