package tuval.sistemas.recursosHumanos.vacaciones.entity.postgres;

import jakarta.persistence.*;

@Entity
@Table(name = "tbm_tra")
public class TraEntitySolvacTra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "co_tra")
    private Long coTra;

    @Column(name = "tx_ide")
    private String txIde;

    @Column(name = "tx_nom")
    private String txNom;

    @Column(name = "tx_ape")
    private String txApe;

    // Getters y setters
    public Long getCoTra() {
        return coTra;
    }

    public void setCoTra(Long coTra) {
        this.coTra = coTra;
    }

    public String getTxIde() {
        return txIde;
    }

    public void setTxIde(String txIde) {
        this.txIde = txIde;
    }

    public String getTxNom() {
        return txNom;
    }

    public void setTxNom(String txNom) {
        this.txNom = txNom;
    }

    public String getTxApe() {
        return txApe;
    }

    public void setTxApe(String txApe) {
        this.txApe = txApe;
    }

}
