package br.com.ecommerce.modelo;

import java.math.BigDecimal;
import javax.persistence.*;

@Entity
@Table(name = "FRETE_AEREO")
@PrimaryKeyJoinColumn(name = "ID_FRETE")
public class FreteAereo extends Frete {

    @Column(length = 15)
    private String prefixoVoo;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal taxaAeroportuaria;

    public FreteAereo() {
    }

    public FreteAereo(String codigoRastreio, BigDecimal valorBase,
                      String prefixoVoo, BigDecimal taxaAeroportuaria) {
        super(codigoRastreio, valorBase);
        this.prefixoVoo = prefixoVoo;
        this.taxaAeroportuaria = taxaAeroportuaria;
    }

    @Override
    public BigDecimal calcularCustoTotal() {
        return getValorBase().add(taxaAeroportuaria);
    }

    public String getPrefixoVoo() { return prefixoVoo; }
    public void setPrefixoVoo(String prefixoVoo) { this.prefixoVoo = prefixoVoo; }
    public BigDecimal getTaxaAeroportuaria() { return taxaAeroportuaria; }
    public void setTaxaAeroportuaria(BigDecimal taxaAeroportuaria) { this.taxaAeroportuaria = taxaAeroportuaria; }
}
