package br.com.ecommerce.app;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.ecommerce.modelo.Frete;
import br.com.ecommerce.modelo.FreteAereo;
import br.com.ecommerce.modelo.FreteRodoviario;

public class AppHerancaJpa {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PuEcommerceJpa");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(new FreteAereo(
                    "TRK-AEREO-001", new BigDecimal("100.00"),
                    "G3-1234", new BigDecimal("25.00")));
            em.persist(new FreteRodoviario(
                    "TRK-ROD-001", new BigDecimal("80.00"),
                    "ABC1D23", new BigDecimal("18.50")));
            em.getTransaction().commit();

            List<Frete> fretes = em.createQuery(
                    "SELECT f FROM Frete f ORDER BY f.id", Frete.class
            ).getResultList();

            for (Frete frete : fretes) {
                if (frete instanceof FreteAereo) {
                    System.out.println("Frete aéreo | ");
                } else if (frete instanceof FreteRodoviario) {
                    System.out.println("Frete rodoviário | ");
                }
                System.out.println("Rastreio: " + frete.getCodigoRastreio()
                        + " | Custo total: R$ " + frete.calcularCustoTotal());
            }
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
