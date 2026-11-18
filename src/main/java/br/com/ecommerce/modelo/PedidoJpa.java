package br.com.ecommerce.modelo;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "PEDIDO_JPA")
public class PedidoJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dataPedido;

    @ManyToMany
    @JoinTable(
        name = "ITEM_PEDIDO_ASSOCIACAO",
        joinColumns = @JoinColumn(name = "ID_PEDIDO"),
        inverseJoinColumns = @JoinColumn(name = "ID_PRODUTO")
    )
    private Set<ProdutoJpa> produtos = new LinkedHashSet<>();

    public PedidoJpa() {
        this.dataPedido = LocalDate.now();
    }

    public PedidoJpa(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public void adicionarProduto(ProdutoJpa produto) {
        produtos.add(produto);
    }

    public Long getId() { return id; }
    public LocalDate getDataPedido() { return dataPedido; }
    public void setDataPedido(LocalDate dataPedido) { this.dataPedido = dataPedido; }
    public Set<ProdutoJpa> getProdutos() { return produtos; }
}
