package beans;

public class Usuario {
	
	private String login;
	
	private String senha;
	
	public Usuario() {
		
	}
	
	public Usuario(String login, String senha) {
		
		this.login = login;
		
		this.senha = senha;
	}
	
	public boolean validar(String login, String senha) {
		
		if(login.equalsIgnoreCase("admin") && senha.equalsIgnoreCase("teste321")) {
			
			return true;
		}
		
		return false;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
