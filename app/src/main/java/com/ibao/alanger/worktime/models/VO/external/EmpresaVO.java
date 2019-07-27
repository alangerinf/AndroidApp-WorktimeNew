package com.ibao.alanger.worktime.models.VO.external;

public class EmpresaVO {
    /*
    TAB_EMPRESA         = "Empresa",
                                TAB_EMPRESA_ID          = "id",
                                TAB_EMPRESA_ID_TYPE     = TYPE_INTEGER,
                                TAB_EMPRESA_COD         = "code",
                                TAB_EMPRESA_COD_TYPE    = TYPE_VARCHAR,
                                TAB_EMPRESA_RAZON       = "razonSocial",
                                TAB_EMPRESA_RAZON_TYPE  = TYPE_VARCHAR,
                                TAB_EMPRESA_RUC         = "RUC",
                                TAB_EMPRESA_RUC_TYPE    = TYPE_VARCHAR,
                                TAB_EMPRESA_NAME        = "name",
                                TAB_EMPRESA_NAME_TYPE   = TYPE_VARCHAR,
                                TAB_EMPRESA_STATUS      = "status",
                                TAB_EMPRESA_STATUS_TYPE = TYPE_BOOLEAN;

    */
    private int id;
    private String cod;
    private String razon;
    private String ruc;
    private String name;
    private boolean status;

    public EmpresaVO(){
        this.id=0;
        this.name="";
        this.status=true;
        this.razon="";
        this.ruc="";
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

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
