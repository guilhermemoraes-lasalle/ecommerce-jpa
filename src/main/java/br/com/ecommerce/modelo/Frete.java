package br.com.ecommerce.modelo;

import java.math.BigDecimal;
import javax.persistence.*;

@Entity
@Table(name = "FRETE")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Frete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String codigoRastreio;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valorBase;

    public Frete() {
    }

    public Frete(String codigoRastreio, BigDecimal valorBase) {
        this.codigoRastreio = codigoRastreio;
        this.valorBase = valorBase;
    }

    public abstract BigDecimal calcularCustoTotal();

    public Long getId() { return id; }
    public String getCodigoRastreio() { return codigoRastreio; }
    public void setCodigoRastreio(String codigoRastreio) { this.codigoRastreio = codigoRastreio; }
    public BigDecimal getValorBase() { return valorBase; }
    public void setValorBase(BigDecimal valorBase) { this.valorBase = valorBase; }
}
