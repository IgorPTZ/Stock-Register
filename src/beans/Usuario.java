package beans;

public class Usuario {
	
	private Long id;
	
	private String nome;
	
	private String login;
	
	private String senha;
	
	private String telefone;
	
	private String cep;
	
	private String rua;
	
	private String bairro;
	
	private String cidade;
	
	private String uf;
	
	private String ibge;
	
	private String fotoBase64;
	
	private String contentType;
	
	private String imagem;
	
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
	
	public Usuario(Long id, 
			       String login,
			       String senha,
			       String nome, 
			       String telefone,
			       String cep,
			       String rua,
			       String bairro,
			       String cidade,
			       String uf,
			       String ibge,
			       String fotoBase64,
			       String contentType) {
		
		this.id = id;
		
		this.login = login;
		
		this.senha = senha;
		
		this.nome = nome;
		
		this.telefone = telefone;
		
		this.cep = cep;
		
		this.rua = rua;
		
		this.bairro = bairro;
		
		this.cidade = cidade;
		
		this.uf = uf;
		
		this.ibge = ibge;
		
		this.fotoBase64 = fotoBase64;
		
		this.contentType = contentType;
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

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getIbge() {
		return ibge;
	}

	public void setIbge(String ibge) {
		this.ibge = ibge;
	}

	public String getFotoBase64() {
		return fotoBase64;
	}

	public void setFotoBase64(String fotoBase64) {
		this.fotoBase64 = fotoBase64;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public String getImagem() {
		
		imagem = "data:" + contentType + ";base64," + fotoBase64;
		
		return imagem;
	}
}
