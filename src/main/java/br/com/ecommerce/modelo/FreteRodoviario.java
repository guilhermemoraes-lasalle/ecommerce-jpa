package br.com.ecommerce.modelo;

import java.math.BigDecimal;
import javax.persistence.*;

@Entity
@Table(name = "FRETE_RODOVIARIO")
@PrimaryKeyJoinColumn(name = "ID_FRETE")
public class FreteRodoviario extends Frete {

    @Column(length = 10)
    private String placaVeiculo;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valorPedagios;

    public FreteRodoviario() {
    }

    public FreteRodoviario(String codigoRastreio, BigDecimal valorBase,
                           String placaVeiculo, BigDecimal valorPedagios) {
        super(codigoRastreio, valorBase);
        this.placaVeiculo = placaVeiculo;
        this.valorPedagios = valorPedagios;
    }

    @Override
    public BigDecimal calcularCustoTotal() {
        return getValorBase().add(valorPedagios);
    }

    public String getPlacaVeiculo() { return placaVeiculo; }
    public void setPlacaVeiculo(String placaVeiculo) { this.placaVeiculo = placaVeiculo; }
    public BigDecimal getValorPedagios() { return valorPedagios; }
    public void setValorPedagios(BigDecimal valorPedagios) { this.valorPedagios = valorPedagios; }
}
