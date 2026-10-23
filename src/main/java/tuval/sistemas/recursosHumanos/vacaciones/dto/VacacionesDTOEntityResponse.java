package tuval.sistemas.recursosHumanos.vacaciones.dto;

import java.sql.Timestamp;
import java.util.Date;

public class VacacionesDTOEntityResponse {

    private Short coTipdoc;
    private Short coDoc;
    private String feDoc;
    private String neNumdoc;
    private String stVacade;
    private String stAut;
    private String txObs1;
    private String ideReem;
    private String nomReem;
    private Timestamp feIng;

    public VacacionesDTOEntityResponse(Short  coTipdoc, Short coDoc, String feDoc, String neNumdoc, String stVacade, String stAut, String txObs1, String ideReem, String nomReem, Timestamp feIng, Short neNumdiavacsolpag, Date feInivacgoz, Date feFinvacgoz, Short neNumdiavacsolgoz, Timestamp feReivac) {
        this.coTipdoc = coTipdoc;
        this.coDoc = coDoc;
        this.feDoc = feDoc;
        this.neNumdoc = neNumdoc;
        this.stVacade = stVacade;
        this.stAut = stAut;
        this.txObs1 = txObs1;
        this.ideReem = ideReem;
        this.nomReem = nomReem;
        this.feIng = feIng;
        this.neNumdiavacsolpag = neNumdiavacsolpag;
        this.feInivacgoz = feInivacgoz;
        this.feFinvacgoz = feFinvacgoz;
        this.neNumdiavacsolgoz = neNumdiavacsolgoz;
        this.feReivac = feReivac;
    }

    private Short neNumdiavacsolpag;
    private Date feInivacgoz;
    private Date feFinvacgoz;
    private Short neNumdiavacsolgoz;
    private Timestamp feReivac;



    public Short  getCoTipdoc() {
        return coTipdoc;
    }

    public void setCoTipdoc(Short  coTipdoc) {
        this.coTipdoc = coTipdoc;
    }

    public Short getCoDoc() {
        return coDoc;
    }

    public void setCoDoc(Short coDoc) {
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

    public String getIdeReem() {
        return ideReem;
    }

    public void setIdeReem(String ideReem) {
        this.ideReem = ideReem;
    }

    public String getNomReem() {
        return nomReem;
    }

    public void setNomReem(String nomReem) {
        this.nomReem = nomReem;
    }

    public Timestamp getFeIng() {
        return feIng;
    }

    public void setFeIng(Timestamp feIng) {
        this.feIng = feIng;
    }

    public Short getNeNumdiavacsolpag() {
        return neNumdiavacsolpag;
    }

    public void setNeNumdiavacsolpag(Short neNumdiavacsolpag) {
        this.neNumdiavacsolpag = neNumdiavacsolpag;
    }

    public Date getFeInivacgoz() {
        return feInivacgoz;
    }

    public void setFeInivacgoz(Date feInivacgoz) {
        this.feInivacgoz = feInivacgoz;
    }

    public Date getFeFinvacgoz() {
        return feFinvacgoz;
    }

    public void setFeFinvacgoz(Date feFinvacgoz) {
        this.feFinvacgoz = feFinvacgoz;
    }

    public Short getNeNumdiavacsolgoz() {
        return neNumdiavacsolgoz;
    }

    public void setNeNumdiavacsolgoz(Short neNumdiavacsolgoz) {
        this.neNumdiavacsolgoz = neNumdiavacsolgoz;
    }

    public Timestamp getFeReivac() {
        return feReivac;
    }

    public void setFeReivac(Timestamp feReivac) {
        this.feReivac = feReivac;
    }




}
