package br.com.ecommerce.modelo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

@Entity
@Table(name = "CATEGORIA")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String nome;

    @Column(nullable = false, unique = true, length = 10)
    private String sigla;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("nome ASC")
    private List<ProdutoJpa> produtos = new ArrayList<>();

    @OneToMany(mappedBy = "categoria")
    @MapKey(name = "codigo")
    private Map<String, ProdutoJpa> produtosPorCodigo = new LinkedHashMap<>();

    public Categoria() {
    }

    public Categoria(String nome, String sigla) {
        this.nome = nome;
        this.sigla = sigla;
    }

    public void adicionarProduto(ProdutoJpa produto) {
        produtos.add(produto);
        produto.setCategoria(this);
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getSigla() { return sigla; }
    public void setSigla(String sigla) { this.sigla = sigla; }
    public List<ProdutoJpa> getProdutos() { return produtos; }
    public Map<String, ProdutoJpa> getProdutosPorCodigo() { return produtosPorCodigo; }
}
