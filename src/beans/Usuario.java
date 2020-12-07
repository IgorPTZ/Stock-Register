package beans;

public class Usuario {
	
	private Long id;
	
	private String nome;
	
	private String login;
	
	private String senha;
	
	private String telefone;
	
	public Usuario() {
		
	}
	
	public Usuario(String login, String senha) {
		
		this.login = login;
		
		this.senha = senha;
	}
	
	public Usuario (Long id, String login, String senha, String nome, String telefone) {
		
		this.id = id;
		
		this.login = login;
		
		this.senha = senha;
		
		this.nome = nome;
		
		this.telefone = telefone;
	}
	
	public boolean validar(String login, String senha) {
		
		if(login.equalsIgnoreCase("admin") && senha.equalsIgnoreCase("teste321")) {
			
			return true;
		}
		
		return false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
}
