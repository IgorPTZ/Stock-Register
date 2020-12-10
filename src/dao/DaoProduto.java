package dao;

import java.sql.Connection;

import connection.SingleConnection;

public class DaoProduto {
	
	private Connection connection;
	
	public DaoProduto() {
		
		connection = SingleConnection.getConnection();
	}
	
	public boolean isNomeProdutoNovoValido(String nome) {
		
		try {
			
			return false;
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return false;
	}
}
