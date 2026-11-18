package br.com.ecommerce.modelo;

import javax.persistence.*;

@Entity
@Table(name = "CLIENTE")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ID_PERFIL_FISCAL", unique = true)
    private PerfilFiscal perfilFiscal;

    public Cliente() {
    }

    public Cliente(String nome) {
        this.nome = nome;
    }

    public void setPerfilFiscal(PerfilFiscal perfilFiscal) {
        this.perfilFiscal = perfilFiscal;
        if (perfilFiscal != null) {
            perfilFiscal.setCliente(this);
        }
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public PerfilFiscal getPerfilFiscal() { return perfilFiscal; }
}
