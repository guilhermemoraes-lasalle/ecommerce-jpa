package br.com.ecommerce.modelo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

@Entity
@Table(name = "PRODUTO_JPA")
@NamedQueries({
    @NamedQuery(
        name = "ProdutoJpa.listarTodosOrdenados",
        query = "SELECT p FROM ProdutoJpa p ORDER BY p.nome ASC"
    ),
    @NamedQuery(
        name = "ProdutoJpa.buscarPorFaixaPreco",
        query = "SELECT p FROM ProdutoJpa p WHERE p.preco BETWEEN :precoMinimo AND :precoMaximo"
    ),
    @NamedQuery(
        name = "ProdutoJpa.buscarPorCodigoUnico",
        query = "SELECT p FROM ProdutoJpa p WHERE p.codigo = :codigo"
    )
})
public class ProdutoJpa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 10)
    private String codigo;

    @Column(length = 100)
    private String nome;

    @Column(precision = 10, scale = 2)
    private BigDecimal preco;

    private Integer quantidadeEstoque;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CATEGORIA")
    private Categoria categoria;

    public ProdutoJpa() {
    }

    public ProdutoJpa(String codigo, String nome, BigDecimal preco, Integer quantidadeEstoque) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public Integer getQuantidadeEstoque() { return quantidadeEstoque; }
    public void setQuantidadeEstoque(Integer quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    @Override
    public String toString() {
        return "ProdutoJpa{id=" + id + ", codigo='" + codigo + "', nome='" + nome
                + "', preco=" + preco + ", quantidadeEstoque=" + quantidadeEstoque + "}";
    }
}
