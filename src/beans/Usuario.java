package beans;

public class Usuario {
	
	private Long id;
	
	private String nome;
	
	private String login;
	
	private String senha;
	
	private String cep;
	
	private String rua;
	
	private String bairro;
	
	private String cidade;
	
	private String uf;
	
	private String ibge;
	
	private String fotoBase64;
	
	private String miniaturaDaFotoBase64;
	
	private String contentTypeDaImagem;
	
	private String imagem;
	
	private String documentoBase64;
	
	private String contentTypeDoDocumento;
	
	private String sexo;
	
	private String perfilDeConsumo;
	
	private Boolean ativo;
	
	private Boolean atualizacaoDeImagem = false;
	
	private Boolean atualizacaoDeDocumento = false;
	
	public Usuario() {
		
	}
	
	public Usuario(Long id, 
			       String login,
			       String senha,
			       String nome, 
			       String cep,
			       String rua,
			       String bairro,
			       String cidade,
			       String uf,
			       String ibge,
			       String fotoBase64,
			       String contentTypeDaImagem,
			       String miniaturaDaFotoBase64,
			       String documentoBase64,
			       String contentTypeDoDocumento,
			       Boolean ativo,
			       String sexo,
			       String perfilDeConsumo) {
		
		this.id = id;
		
		this.login = login;
		
		this.senha = senha;
		
		this.nome = nome;
		
		this.cep = cep;
		
		this.rua = rua;
		
		this.bairro = bairro;
		
		this.cidade = cidade;
		
		this.uf = uf;
		
		this.ibge = ibge;
		
		this.fotoBase64 = fotoBase64;
		
		this.contentTypeDaImagem = contentTypeDaImagem;
		
		this.documentoBase64 = documentoBase64;
		
		this.contentTypeDoDocumento = contentTypeDoDocumento;
		
		this.miniaturaDaFotoBase64 = miniaturaDaFotoBase64;
		
		this.ativo = ativo;
		
		this.sexo = sexo;
		
		this.perfilDeConsumo = perfilDeConsumo;
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
	
	public String getMiniaturaDaFotoBase64() {
		return miniaturaDaFotoBase64;
	}

	public void setMiniaturaDaFotoBase64(String miniaturaDaFotoBase64) {
		this.miniaturaDaFotoBase64 = miniaturaDaFotoBase64;
	}

	public String getContentTypeDaImagem() {
		return contentTypeDaImagem;
	}

	public void setContentTypeDaImagem(String contentTypeDaImagem) {
		this.contentTypeDaImagem = contentTypeDaImagem;
	}
	
	public String getDocumentoBase64() {
		return documentoBase64;
	}

	public void setDocumentoBase64(String documentoBase64) {
		this.documentoBase64 = documentoBase64;
	}

	public String getContentTypeDoDocumento() {
		return contentTypeDoDocumento;
	}

	public void setContentTypeDoDocumento(String contentTypeDoDocumento) {
		this.contentTypeDoDocumento = contentTypeDoDocumento;
	}
	
	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getPerfilDeConsumo() {
		return perfilDeConsumo;
	}

	public void setPerfilDeConsumo(String perfilDeConsumo) {
		this.perfilDeConsumo = perfilDeConsumo;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	public Boolean getAtualizacaoDeImagem() {
		return atualizacaoDeImagem;
	}

	public void setAtualizacaoDeImagem(Boolean atualizacaoDeImagem) {
		this.atualizacaoDeImagem = atualizacaoDeImagem;
	}

	public Boolean getAtualizacaoDeDocumento() {
		return atualizacaoDeDocumento;
	}

	public void setAtualizacaoDeDocumento(Boolean atualizacaoDeDocumento) {
		this.atualizacaoDeDocumento = atualizacaoDeDocumento;
	}

	public String getImagem() {
		
		imagem = "data:" + contentTypeDaImagem + ";base64," + fotoBase64;
		
		return imagem;
	}
}
