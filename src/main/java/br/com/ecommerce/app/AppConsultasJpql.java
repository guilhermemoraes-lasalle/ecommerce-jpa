package br.com.ecommerce.app;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.ecommerce.dao.ProdutoConsultasDAO;
import br.com.ecommerce.modelo.ProdutoJpa;

public class AppConsultasJpql {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PuEcommerceJpa");
        EntityManager em = emf.createEntityManager();

        try {
            ProdutoConsultasDAO dao = new ProdutoConsultasDAO(em);

            System.out.println("--- Todos ordenados ---");
            dao.listarTodosOrdenados().forEach(System.out::println);

            System.out.println("--- Produtos até R$ 500 ---");
            List<ProdutoJpa> porTeto = dao.listarPorTeto(new BigDecimal("500.00"));
            porTeto.forEach(System.out::println);

            System.out.println("--- Produtos entre R$ 50 e R$ 4000 ---");
            dao.buscarPorFaixaPreco(
                    new BigDecimal("50.00"), new BigDecimal("4000.00"))
                    .forEach(System.out::println);

            System.out.println("--- Categoria contendo 'info' ---");
            dao.buscarPorNomeCategoria("info").forEach(System.out::println);

            System.out.println("--- Busca pelo código MOU001 ---");
            System.out.println(dao.buscarPorCodigo("MOU001"));

            em.getTransaction().begin();
            int atualizados = dao.aplicarReajustePorCategoria(
                    "INFO", new BigDecimal("1.05"));
            em.getTransaction().commit();
            System.out.println("Produtos reajustados: " + atualizados);

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
