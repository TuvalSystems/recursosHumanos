package tuval.sistemas.recursosHumanos.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tbm_cabSolVac")
public class CabSolVacentity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // Si tienes una columna de identificación en la tabla
    private Long id;

    @Column(name = "co_tipdoc")
    private String coTipdoc;

    @Column(name = "co_doc")
    private String coDoc;

    @Column(name = "fe_doc")
    private String feDoc;

    @Column(name = "ne_numdoc")
    private String neNumdoc;

    @Column(name = "st_vacade")
    private String stVacade;

    @Column(name = "st_aut")
    private String stAut;

    @Column(name = "tx_obs1")
    private String txObs1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "co_tra", referencedColumnName = "co_tra")
    private TraEntitySolvacTra tra;  // Relación con la tabla tbm_tra

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCoTipdoc() {
        return coTipdoc;
    }

    public void setCoTipdoc(String coTipdoc) {
        this.coTipdoc = coTipdoc;
    }

    public String getCoDoc() {
        return coDoc;
    }

    public void setCoDoc(String coDoc) {
        this.coDoc = coDoc;
    }

    public String getFeDoc() {
        return feDoc;
    }

    public void setFeDoc(String feDoc) {
        this.feDoc = feDoc;
    }

    public String getNeNumdoc() {
        return neNumdoc;
    }

    public void setNeNumdoc(String neNumdoc) {
        this.neNumdoc = neNumdoc;
    }

    public String getStVacade() {
        return stVacade;
    }

    public void setStVacade(String stVacade) {
        this.stVacade = stVacade;
    }

    public String getStAut() {
        return stAut;
    }

    public void setStAut(String stAut) {
        this.stAut = stAut;
    }

    public String getTxObs1() {
        return txObs1;
    }

    public void setTxObs1(String txObs1) {
        this.txObs1 = txObs1;
    }

    public TraEntitySolvacTra getTra() {
        return tra;
    }

    public void setTra(TraEntitySolvacTra tra) {
        this.tra = tra;
    }
}
