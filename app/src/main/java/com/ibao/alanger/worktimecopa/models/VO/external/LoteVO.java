package com.ibao.alanger.worktimecopa.models.VO.external;

import java.io.Serializable;

public class LoteVO implements Serializable {
    private int id;
    private int idFundo;
    private int idCultivo;
    private String numero;
    private String cod;
    private boolean status;

    public LoteVO(){
        this.id=0;
        this.cod="";
        this.idFundo=0;
        this.idCultivo=0;
        this.numero="";
        this.status=true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public int getIdFundo() {
        return idFundo;
    }

    public void setIdFundo(int idFundo) {
        this.idFundo = idFundo;
    }

    public int getIdCultivo() {
        return idCultivo;
    }

    public void setIdCultivo(int idCultivo) {
        this.idCultivo = idCultivo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
