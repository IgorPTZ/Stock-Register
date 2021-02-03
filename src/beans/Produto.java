package beans;

public class Produto {
	
	private Long id;
	
	private String nome;
	
	private Double quantidade;
	
	private Double valor;
	
	private Long categoriaId;
	
	public Produto() {
		
	}
	
	public Produto(Long id, String nome, Double quantidade, Double valor, Long categoriaId) {
		
		this.id          = id;
		
		this.nome        = nome;
		
		this.quantidade  = quantidade;
		
		this.valor       = valor;
		
		this.categoriaId = categoriaId;
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

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}

	public String getValorEmTexto() {
		
		return Double.toString(valor).replace('.', ',');
	}
}
