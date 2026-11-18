package br.com.ecommerce.app;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.ecommerce.modelo.ProdutoJpa;

public class CicloVidaJpaApp {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PuEcommerceJpa");
        EntityManager em = emf.createEntityManager();

        try {
            ProdutoJpa produto = new ProdutoJpa(
                    "CICLO001", "Produto ciclo de vida", new BigDecimal("149.90"), 15);
            System.out.println("New/Transient: objeto criado, ainda sem ID = " + produto.getId());

            em.getTransaction().begin();
            em.persist(produto);
            em.flush();
            System.out.println("Managed após persist/flush. ID gerado = " + produto.getId());

            produto.setPreco(new BigDecimal("159.90"));
            em.getTransaction().commit();
            System.out.println("Commit executado; dirty checking sincroniza a alteração.");

            em.detach(produto);
            produto.setNome("Alteração local em objeto detached");
            System.out.println("Detached: nome alterado apenas em memória.");

            em.getTransaction().begin();
            ProdutoJpa gerenciado = em.merge(produto);
            em.getTransaction().commit();
            System.out.println("Merge: objeto gerenciado retornado = " + gerenciado.getNome());

            em.getTransaction().begin();
            ProdutoJpa paraRefresh = em.find(ProdutoJpa.class, produto.getId());
            paraRefresh.setNome("Alteração volátil antes do refresh");
            em.refresh(paraRefresh);
            System.out.println("Refresh: nome restaurado do banco = " + paraRefresh.getNome());
            em.getTransaction().commit();

            em.getTransaction().begin();
            ProdutoJpa paraRemover = em.find(ProdutoJpa.class, produto.getId());
            if (paraRemover != null) {
                em.remove(paraRemover);
            }
            em.getTransaction().commit();
            System.out.println("Removed: registro removido do contexto e do banco.");
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
