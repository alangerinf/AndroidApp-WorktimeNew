package com.ibao.alanger.worktime.models.VO.external;

import java.io.Serializable;

public class FundoVO implements Serializable {
    /*
    //TABLE FUNDO
    public static final String  TAB_FUNDO               = "Fundo",
                                TAB_FUNDO_ID                = "id",
                                TAB_FUNDO_ID_TYPE           = TYPE_INTEGER,
                                TAB_FUNDO_COD               = "code",
                                TAB_FUNDO_COD_TYPE          = TYPE_VARCHAR,
                                TAB_FUNDO_NAME              = "name",
                                TAB_FUNDO_NAME_TYPE         = TYPE_VARCHAR,
                                TAB_FUNDO_IDEMPRESA         = "idEmpresa",
                                TAB_FUNDO_IDEMPRESA_TYPE    = TYPE_INTEGER,
                                TAB_FUNDO_STATUS            = "status",
                                TAB_FUNDO_STATUS_TYPE       = TYPE_BOOLEAN;
     */
    private int id;
    private String cod;
    private String name;
    private int idEmpresa;
    private boolean status;

    public FundoVO(){
        this.id=0;
        this.name="";
        this.idEmpresa=0;
        this.cod="";
        this.status=true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
