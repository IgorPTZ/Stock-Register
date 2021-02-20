package connection;

import java.sql.Connection;
import java.sql.DriverManager;

/*
 * Responsavel por fazer a conexao com o banco de dados
 */
public class SingleConnection {
	
	private static String banco = "jdbc:postgresql://localhost:5432/jsp-app?autoReconnect=true";
	
	private static String senha = "postgres";
	
	private static String usuario = "postgres";
	
	private static Connection conexao = null;
	
	static {
		conectar();
	}
	
	public SingleConnection () {
		
		conectar();
	}
	
	private static void conectar() {
		
		try {
			
			if(conexao == null) {
				
				Class.forName("org.postgresql.Driver");
				
				conexao = DriverManager.getConnection(banco, usuario, senha);
				
				conexao.setAutoCommit(false);
			}
		}
		catch(Exception e) {
			
			e.printStackTrace();
			
			throw new RuntimeException("Erro ao conectar com o banco de dados");
		}
	}
	
	public static Connection getConnection() {
		
		return conexao;
	}
}
