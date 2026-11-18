package br.com.ecommerce.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import br.com.ecommerce.modelo.ProdutoJpa;

public class ProdutoConsultasDAO {

    private final EntityManager em;

    public ProdutoConsultasDAO(EntityManager em) {
        this.em = em;
    }

    public List<ProdutoJpa> listarPorTeto(BigDecimal teto) {
        TypedQuery<ProdutoJpa> query = em.createQuery(
                "SELECT p FROM ProdutoJpa p WHERE p.preco <= :teto ORDER BY p.preco",
                ProdutoJpa.class);
        query.setParameter("teto", teto);
        return query.getResultList();
    }

    public List<ProdutoJpa> buscarPorNomeCategoria(String nomeCategoria) {
        TypedQuery<ProdutoJpa> query = em.createQuery(
                "SELECT p FROM ProdutoJpa p "
                + "WHERE LOWER(p.categoria.nome) LIKE :nomeCat",
                ProdutoJpa.class);
        query.setParameter("nomeCat", "%" + nomeCategoria.toLowerCase() + "%");
        return query.getResultList();
    }

    public ProdutoJpa buscarPorCodigo(String codigo) {
        try {
            return em.createNamedQuery(
                    "ProdutoJpa.buscarPorCodigoUnico", ProdutoJpa.class)
                    .setParameter("codigo", codigo)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException e) {
            throw new IllegalStateException(
                    "Mais de um produto encontrado para o mesmo codigo.", e);
        }
    }

    public List<ProdutoJpa> listarTodosOrdenados() {
        return em.createNamedQuery(
                "ProdutoJpa.listarTodosOrdenados", ProdutoJpa.class)
                .getResultList();
    }

    public List<ProdutoJpa> buscarPorFaixaPreco(BigDecimal minimo, BigDecimal maximo) {
        return em.createNamedQuery(
                "ProdutoJpa.buscarPorFaixaPreco", ProdutoJpa.class)
                .setParameter("precoMinimo", minimo)
                .setParameter("precoMaximo", maximo)
                .getResultList();
    }

    public int aplicarReajustePorCategoria(String siglaCategoria, BigDecimal multiplicador) {
        if (siglaCategoria == null || siglaCategoria.trim().isEmpty()) {
            throw new IllegalArgumentException("Sigla da categoria obrigatoria.");
        }
        if (multiplicador == null || multiplicador.signum() <= 0) {
            throw new IllegalArgumentException("Multiplicador deve ser positivo.");
        }

        int alterados = em.createQuery(
                "UPDATE ProdutoJpa p SET p.preco = p.preco * :multiplicador "
                + "WHERE p.categoria.sigla = :sigla")
                .setParameter("multiplicador", multiplicador)
                .setParameter("sigla", siglaCategoria)
                .executeUpdate();

        // UPDATE em massa nao sincroniza automaticamente entidades ja carregadas.
        em.clear();
        return alterados;
    }
}
