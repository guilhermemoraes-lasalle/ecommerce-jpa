package br.com.ecommerce.app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TesteSetupJpaApp {

    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            emf = Persistence.createEntityManagerFactory("PuEcommerceJpa");
            em = emf.createEntityManager();
            System.out.println("Hibernate/JPA inicializado com sucesso.");
            System.out.println("EntityManager aberto: " + em.isOpen());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        }
    }
}
