package com.ibao.alanger.worktime.database.webserver;

public class ConectionConfig {


    private static final String ROOT= "http://35.167.15.182/worktimeapi/Requests/";
    public static final String POST_LOGIN=ROOT+"autenticar.php";


    public static final String URL_DOWN_ACTIVIDADES =ROOT+"getActividades";
    public static final String URL_DOWN_CENTROCOSTES =ROOT+"getCentroCostos.php";
    public static final String URL_DOWN_CULTUVOS =ROOT+"getCultivos";
    public static final String URL_DOWN_EMPRESAS =ROOT+"getEmpresas";
    public static final String URL_DOWN_FUNDOS =ROOT+"getFundos";


    public static final int STATUS_CREATED = 0;
    public static final int STATUS_STARTED = 1;
    public static final int STATUS_PROCESSING = 2;
    public static final int STATUS_FINISHED = 3;
    public static final int STATUS_ERROR_PARSE = -1;
    public static final int STATUS_ERROR_HTTP_ERROR = -2;


}
