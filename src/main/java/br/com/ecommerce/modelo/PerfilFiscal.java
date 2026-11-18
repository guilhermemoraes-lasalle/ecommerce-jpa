package br.com.ecommerce.modelo;

import javax.persistence.*;

@Entity
@Table(name = "PERFIL_FISCAL")
public class PerfilFiscal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String documento;

    @Column(length = 150)
    private String enderecoFiscal;

    @OneToOne(mappedBy = "perfilFiscal")
    private Cliente cliente;

    public PerfilFiscal() {
    }

    public PerfilFiscal(String documento, String enderecoFiscal) {
        this.documento = documento;
        this.enderecoFiscal = enderecoFiscal;
    }

    public Long getId() { return id; }
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public String getEnderecoFiscal() { return enderecoFiscal; }
    public void setEnderecoFiscal(String enderecoFiscal) { this.enderecoFiscal = enderecoFiscal; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}
