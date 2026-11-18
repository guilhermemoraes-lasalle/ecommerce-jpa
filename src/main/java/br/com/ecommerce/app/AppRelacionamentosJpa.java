package br.com.ecommerce.app;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.ecommerce.modelo.*;

public class AppRelacionamentosJpa {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PuEcommerceJpa");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Cliente cliente = new Cliente("Mariana Costa");
            PerfilFiscal perfil = new PerfilFiscal("12345678900", "Rua das Flores, 100");
            cliente.setPerfilFiscal(perfil);

            Categoria categoria = new Categoria("Informática", "INFO");
            ProdutoJpa notebook = new ProdutoJpa(
                    "NOTE001", "Notebook", new BigDecimal("3500.00"), 10);
            ProdutoJpa mouse = new ProdutoJpa(
                    "MOU001", "Mouse sem fio", new BigDecimal("95.00"), 50);
            categoria.adicionarProduto(notebook);
            categoria.adicionarProduto(mouse);

            PedidoJpa pedido = new PedidoJpa(LocalDate.now());
            pedido.adicionarProduto(notebook);
            pedido.adicionarProduto(mouse);

            em.persist(cliente);       // Cascade ALL grava PerfilFiscal
            em.persist(categoria);     // Cascade ALL grava os produtos
            em.persist(pedido);

            em.getTransaction().commit();

            em.clear();
            Categoria categoriaLida = em.find(Categoria.class, categoria.getId());
            System.out.println("Produtos ordenados da categoria:");
            categoriaLida.getProdutos().forEach(System.out::println);

            System.out.println("Busca indexada pelo codigo NOTE001:");
            System.out.println(categoriaLida.getProdutosPorCodigo().get("NOTE001"));

            PedidoJpa pedidoLido = em.find(PedidoJpa.class, pedido.getId());
            System.out.println("Produtos associados ao pedido:");
            pedidoLido.getProdutos().forEach(System.out::println);

            Cliente clienteLido = em.find(Cliente.class, cliente.getId());
            System.out.println("Perfil fiscal do cliente: "
                    + clienteLido.getPerfilFiscal().getDocumento());

        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
