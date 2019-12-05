package com.ibao.alanger.worktime.database;

public class DataBaseDesign {


    /***NO QUITAR LOS ESPACIOS ENTRE LAS PALABRAS CLAVE PORQ SON NECESARIAS**/

    //database name
    public static final String DATABASE_NAME="dataWorktime";

    //PALABRAS CLAVE
    private static final String TABLE_CREATE        = " CREATE TABLE IF NOT EXISTS ",// IF NOT EXISTS
                                AUTOINCREMET        = "  AUTOINCREMENT ",
                                PK                  = " PRIMARY KEY ",
                                N_NULL              = " NOT NULL ",
                                DEF_DATE_NOW        = " DEFAULT (datetime('now','localtime')) ",
                                AND                 = " , ";

    public static final String  _SELECT     = " SELECT ",
                                _AND        = " AND ",
                                _FROM       = " FROM ",
                                _WHERE      = " WHERE ",
                                _ORDERBY    = " ORDER BY ",
                                _STRASC     = " COLLATE UNICODE ASC ",
                                _n = " , ";

    //TIPOS DE DATOS
    private static final String TYPE_INTEGER    = " INTEGER ",
                                TYPE_VARCHAR    = " VARCHAR(100) ",
                                TYPE_DATETIME   = " DATETIME ",
                                TYPE_BOOLEAN    = " BOOLEAN ",
                                TYPE_FLOAT      = " FLOAT ";

    //TODO: DECLARACION DE TABLAS Y SU CREACION

    //TODO: TABLAS MAESTRAS
    //TABLE EMPRESA
    public static final String  TAB_EMPRESA         = "Empresa",
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

    public static final String CREATETABLE_EMPRESA =
            TABLE_CREATE+TAB_EMPRESA+"("+
                TAB_EMPRESA_ID   + TAB_EMPRESA_ID_TYPE   + PK+ N_NULL+
                AND+
                TAB_EMPRESA_COD   + TAB_EMPRESA_COD_TYPE   +
                AND+
                TAB_EMPRESA_RAZON   + TAB_EMPRESA_RAZON_TYPE   +
                AND+
                TAB_EMPRESA_RUC   + TAB_EMPRESA_RUC_TYPE   +
                AND+
                TAB_EMPRESA_NAME + TAB_EMPRESA_NAME_TYPE + N_NULL+
                AND+
                TAB_EMPRESA_STATUS + TAB_EMPRESA_STATUS_TYPE + N_NULL+
            ")";

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

    public static final String CREATETABLE_FUNDO =
            TABLE_CREATE+TAB_FUNDO+"("+
                    TAB_FUNDO_ID   + TAB_FUNDO_ID_TYPE   + PK+ N_NULL+
                    AND+
                    TAB_FUNDO_COD + TAB_FUNDO_COD_TYPE +
                    AND +
                    TAB_FUNDO_NAME + TAB_FUNDO_NAME_TYPE + N_NULL+
                    AND +
                    TAB_FUNDO_IDEMPRESA + TAB_FUNDO_IDEMPRESA_TYPE + N_NULL+
                    AND +
                    TAB_FUNDO_STATUS + TAB_FUNDO_STATUS_TYPE + N_NULL+
                    ")";


    //TABLE MODULO
    public static final String  TAB_LOTE              = "lote",
                                TAB_LOTE_ID               = "id",
                                TAB_LOTE_ID_TYPE          = TYPE_INTEGER,
                                TAB_LOTE_IDFUNDO          = "idFundo",
                                TAB_LOTE_IDFUNDO_TYPE     = TYPE_INTEGER,
                                TAB_LOTE_IDCULTIVO        = "idCultivo",
                                TAB_LOTE_IDCULTIVO_TYPE   = TYPE_INTEGER,
                                TAB_LOTE_NUM              = "numero",
                                TAB_LOTE_NUM_TYPE         = TYPE_VARCHAR,
                                TAB_LOTE_COD              = "code",//ocdiog completo pk
                                TAB_LOTE_COD_TYPE         = TYPE_VARCHAR,
                                TAB_LOTE_STATUS           = "status",
                                TAB_LOTE_STATUS_TYPE      = TYPE_BOOLEAN;

    public static final String CREATETABLE_LOTE =
            TABLE_CREATE+TAB_LOTE+"("+
                    TAB_LOTE_ID   + TAB_LOTE_ID_TYPE   + PK+ N_NULL+
                    AND +
                    TAB_LOTE_IDFUNDO + TAB_LOTE_IDFUNDO_TYPE+ N_NULL+
                    AND +
                    TAB_LOTE_IDCULTIVO + TAB_LOTE_IDCULTIVO_TYPE+ N_NULL+
                    AND +
                    TAB_LOTE_NUM + TAB_LOTE_NUM_TYPE+ N_NULL+
                    AND +
                    TAB_LOTE_COD + TAB_LOTE_COD_TYPE + N_NULL+
                    AND +
                    TAB_LOTE_STATUS + TAB_LOTE_STATUS_TYPE+ N_NULL+
                    ")";

    //TABLE CENTRO DE COSTE
    public static final String TAB_CCOSTE                       = "CentroCoste",
                                TAB_CCOSTE_ID                       = "id",
                                TAB_CCOSTE_ID_TYPE                  = TYPE_INTEGER,
                                TAB_CCOSTE_COD                      = "code",
                                TAB_CCOSTE_COD_TYPE                 = TYPE_VARCHAR,
                                TAB_CCOSTE_NAME                     = "name",
                                TAB_CCOSTE_NAME_TYPE                = TYPE_VARCHAR,
                                TAB_CCOSTE_IDEMPRESA                = "idEmpresa",
                                TAB_CCOSTE_IDEMPRESA_TYPE           = TYPE_INTEGER,
                                TAB_CCOSTE_STATUS                   = "status",
                                TAB_CCOSTE_STATUS_TYPE              = TYPE_BOOLEAN;

    public static final String CREATETABLE_CCOSTE =
            TABLE_CREATE+ TAB_CCOSTE +"("+
                    TAB_CCOSTE_ID + TAB_CCOSTE_ID_TYPE + PK + N_NULL+
                    AND +
                    TAB_CCOSTE_COD + TAB_CCOSTE_COD_TYPE + N_NULL+
                    AND +
                    TAB_CCOSTE_NAME + TAB_CCOSTE_NAME_TYPE + N_NULL+
                    AND +
                    TAB_CCOSTE_IDEMPRESA + TAB_CCOSTE_IDEMPRESA_TYPE + N_NULL+
                    AND +
                    TAB_CCOSTE_STATUS + TAB_CCOSTE_STATUS_TYPE + N_NULL+
                    ")";

    //TABLE AGRUPACION_ACTIVIDAD
    public static final String  TAB_ACTIVIDAD              = "LaborVO",
                                TAB_ACTIVIDAD_ID               = "id",
                                TAB_ACTIVIDAD_ID_TYPE          = TYPE_INTEGER,
                                TAB_ACTIVIDAD_COD              = "code",
                                TAB_ACTIVIDAD_COD_TYPE         = TYPE_VARCHAR,
                                TAB_ACTIVIDAD_NAME             = "name",
                                TAB_ACTIVIDAD_NAME_TYPE        = TYPE_VARCHAR,
                                TAB_ACTIVIDAD_STATUS           = "status",
                                TAB_ACTIVIDAD_STATUS_TYPE      = TYPE_BOOLEAN;

    public static final String CREATETABLE_ACTIVIDAD =
            TABLE_CREATE+TAB_ACTIVIDAD+"("+
                    TAB_ACTIVIDAD_ID   + TAB_ACTIVIDAD_ID_TYPE + PK + N_NULL+
                    AND +
                    TAB_ACTIVIDAD_COD + TAB_ACTIVIDAD_COD_TYPE + N_NULL+
                    AND +
                    TAB_ACTIVIDAD_NAME + TAB_ACTIVIDAD_NAME_TYPE + N_NULL+
                    AND +
                    TAB_ACTIVIDAD_STATUS + TAB_ACTIVIDAD_STATUS_TYPE + N_NULL+
                    ")";

    public static final String  TAB_CULTIVO             = "Cultivo",
                                TAB_CULTIVO_ID              = "id",
                                TAB_CULTIVO_ID_TYPE         = TYPE_INTEGER,
                                TAB_CULTIVO_COD             = "code",
                                TAB_CULTIVO_COD_TYPE        = TYPE_VARCHAR,
                                TAB_CULTIVO_NAME            = "name",
                                TAB_CULTIVO_NAME_TYPE       = TYPE_VARCHAR,
                                TAB_CULTIVO_HASLABOR        = "hasLabor",
                                TAB_CULTIVO_HASLABOR_TYPE   = TYPE_BOOLEAN,
                                TAB_CULTIVO_STATUS          = "status",
                                TAB_CULTIVO_STATUS_TYPE     = TYPE_BOOLEAN;

    public static final String CREATETABLE_CULTIVO =
            TABLE_CREATE+TAB_CULTIVO+"("+
                    TAB_CULTIVO_ID   + TAB_CULTIVO_ID_TYPE   + PK+ N_NULL+
                    AND +
                    TAB_CULTIVO_COD + TAB_CULTIVO_COD_TYPE + N_NULL+
                    AND +
                    TAB_CULTIVO_NAME + TAB_CULTIVO_NAME_TYPE+ N_NULL+
                    AND +
                    TAB_CULTIVO_HASLABOR + TAB_CULTIVO_HASLABOR_TYPE+ N_NULL+
                    AND +
                    TAB_CULTIVO_STATUS + TAB_CULTIVO_STATUS_TYPE+ N_NULL+
                    ")";

    public static final String  TAB_TRABAJADOR              = "Trabajador",
                                TAB_TRABAJADOR_DNI              = "dni",
                                TAB_TRABAJADOR_DNI_TYPE         = TYPE_VARCHAR,
                                TAB_TRABAJADOR_COD              = "cod",
                                TAB_TRABAJADOR_COD_TYPE         = TYPE_VARCHAR,
                                TAB_TRABAJADOR_NAME             = "name",
                                TAB_TRABAJADOR_NAME_TYPE        = TYPE_VARCHAR,
                                TAB_TRABAJADOR_STATUS           = "status",
                                TAB_TRABAJADOR_STATUS_TYPE      = TYPE_BOOLEAN;

    public static final String CREATETABLE_TRABAJADOR =
            TABLE_CREATE+TAB_TRABAJADOR+"("+
                    TAB_TRABAJADOR_DNI + TAB_TRABAJADOR_DNI_TYPE +  PK + N_NULL+
                    AND +
                    TAB_TRABAJADOR_COD + TAB_TRABAJADOR_COD_TYPE + N_NULL+
                    AND +
                    TAB_TRABAJADOR_NAME + TAB_TRABAJADOR_NAME_TYPE + N_NULL+
                    AND +
                    TAB_TRABAJADOR_STATUS + TAB_TRABAJADOR_STATUS_TYPE + N_NULL+
                    ")";

    public static final String  TAB_LABOR                   = "Labor",
                                TAB_LABOR_ID                    = "id",
                                TAB_LABOR_ID_TYPE               = TYPE_INTEGER,
                                TAB_LABOR_COD                   = "cod",
                                TAB_LABOR_COD_TYPE              = TYPE_VARCHAR,
                                TAB_LABOR_NAME                  = "name",
                                TAB_LABOR_NAME_TYPE             = TYPE_VARCHAR,
                                TAB_LABOR_ISDIRECT              = "isDirecto",
                                TAB_LABOR_ISDIRECT_TYPE         = TYPE_BOOLEAN,
                                TAB_LABOR_THEORICALCOST         = "theoricalCost",
                                TAB_LABOR_THEORICALCOST_TYPE    = TYPE_FLOAT,
                                TAB_LABOR_THEORICALHOURS        = "theoricalHours",
                                TAB_LABOR_THEORICALHOURS_TYPE   = TYPE_FLOAT,
                                TAB_LABOR_ISTAREA               = "isTarea",
                                TAB_LABOR_ISTAREA_TYPE          = TYPE_BOOLEAN,
                                TAB_LABOR_IDACTIVIDAD           = "idActividad",
                                TAB_LABOR_IDACTIVIDAD_TYPE      = TYPE_INTEGER,
                                TAB_LABOR_LISTIDCULTIVO         = "listIdCultivos",
                                TAB_LABOR_LISTIDCULTIVO_TYPE    = TYPE_VARCHAR,
                                TAB_LABOR_MAGNITUD              = "magnitud",
                                TAB_LABOR_MAGNITUD_TYPE         = TYPE_VARCHAR,
                                TAB_LABOR_STATUS                = "status",
                                TAB_LABOR_STATUS_TYPE           = TYPE_BOOLEAN;


    public static final String CREATETABLE_LABOR =
            TABLE_CREATE+TAB_LABOR+"("+
                    TAB_LABOR_ID   + TAB_LABOR_ID_TYPE   + PK + N_NULL+
                    AND +
                    TAB_LABOR_COD + TAB_LABOR_COD_TYPE + N_NULL+
                    AND +
                    TAB_LABOR_NAME + TAB_LABOR_NAME_TYPE + N_NULL+
                    AND +
                    TAB_LABOR_ISDIRECT + TAB_LABOR_ISDIRECT_TYPE + N_NULL+
                    AND +
                    TAB_LABOR_THEORICALCOST + TAB_LABOR_THEORICALCOST_TYPE+ N_NULL+
                    AND +
                    TAB_LABOR_THEORICALHOURS + TAB_LABOR_THEORICALHOURS_TYPE+ N_NULL+
                    AND +
                    TAB_LABOR_ISTAREA + TAB_LABOR_ISTAREA_TYPE+ N_NULL+
                    AND +
                    TAB_LABOR_IDACTIVIDAD + TAB_LABOR_IDACTIVIDAD_TYPE + N_NULL+
                    AND +
                    TAB_LABOR_LISTIDCULTIVO + TAB_LABOR_LISTIDCULTIVO_TYPE + N_NULL+
                    AND +
                    TAB_LABOR_MAGNITUD + TAB_LABOR_MAGNITUD_TYPE + N_NULL+
                    AND +
                    TAB_LABOR_STATUS + TAB_LABOR_STATUS_TYPE + N_NULL+
                    ")";

    //TODO: TABLAS DE INSERSION

    public static final String  TAB_TAREO                   = "Tareo",
                                TAB_TAREO_ID                    = "id",
                                TAB_TAREO_ID_TYPE               = TYPE_INTEGER,
                                TAB_TAREO_IDWEB                 = "idWeb",
                                TAB_TAREO_IDWEB_TYPE            = TYPE_INTEGER,
                                TAB_TAREO_IDLABOR               = "idActividad",
                                TAB_TAREO_IDLABOR_TYPE          = TYPE_INTEGER,
                                TAB_TAREO_IDLOTE                = "idLote",
                                TAB_TAREO_IDLOTE_TYPE           = TYPE_INTEGER,
                                TAB_TAREO_IDCCOSTE              = "idCentroCoste",
                                TAB_TAREO_IDCCOSTE_TYPE         = TYPE_INTEGER,
                                TAB_TAREO_IDFUNDO               = "idFundo",
                                TAB_TAREO_IDFUNDO_TYPE          = TYPE_INTEGER,
                                TAB_TAREO_IDCULTIVO             = "idCultivo",
                                TAB_TAREO_IDCULTIVO_TYPE        = TYPE_INTEGER,
                                TAB_TAREO_DATESTART             = "dateStart",
                                TAB_TAREO_DATESTART_TYPE        = TYPE_DATETIME,
                                TAB_TAREO_DATEEND               = "dateEnd",
                                TAB_TAREO_DATEEND_TYPE          = TYPE_DATETIME,
                                TAB_TAREO_PRODUCTIVIDAD         = "producitividad",
                                TAB_TAREO_PRODUCTIVIDAD_TYPE    = TYPE_FLOAT,
                                TAB_TAREO_ISACTIVE              = "isActive",
                                TAB_TAREO_ISACTIVE_TYPE         = TYPE_BOOLEAN,
                                TAB_TAREO_ISASISTENCIA          = "isAsistencia",
                                TAB_TAREO_ISASISTENCIA_TYPE     = TYPE_BOOLEAN;

    
    public static final String CREATETABLE_TAREO =
            TABLE_CREATE+TAB_TAREO+"("+
                    TAB_TAREO_ID   + TAB_TAREO_ID_TYPE   + PK + AUTOINCREMET+
                    AND +
                    TAB_TAREO_IDWEB   + TAB_TAREO_IDWEB_TYPE +
                    AND +
                    TAB_TAREO_IDLABOR + TAB_TAREO_IDLABOR_TYPE + N_NULL+
                    AND +
                    TAB_TAREO_IDLOTE + TAB_TAREO_IDLOTE_TYPE +
                    AND +
                    TAB_TAREO_IDCCOSTE + TAB_TAREO_IDCCOSTE_TYPE +
                    AND +
                    TAB_TAREO_IDFUNDO + TAB_TAREO_IDFUNDO_TYPE + N_NULL+
                    AND +
                    TAB_TAREO_IDCULTIVO + TAB_TAREO_IDCULTIVO_TYPE + N_NULL+
                    AND +
                    TAB_TAREO_DATESTART + TAB_TAREO_DATESTART_TYPE+ DEF_DATE_NOW+
                    AND +
                    TAB_TAREO_DATEEND + TAB_TAREO_DATEEND_TYPE+
                    AND +
                    TAB_TAREO_PRODUCTIVIDAD + TAB_TAREO_PRODUCTIVIDAD_TYPE +
                    AND +
                    TAB_TAREO_ISASISTENCIA + TAB_TAREO_ISASISTENCIA_TYPE +" DEFAULT 0 "+
                    AND +
                    TAB_TAREO_ISACTIVE + TAB_TAREO_ISACTIVE_TYPE+ " DEFAULT 1 "+
                    ")";

    public static final String  TAB_TAREODETALLE                = "TareoTrabajador",
                                TAB_TAREODETALLE_ID                 = "id",
                                TAB_TAREODETALLE_ID_TYPE            = TYPE_INTEGER,
                                TAB_TAREODETALLE_IDTAREO            = "idTareo",
                                TAB_TAREODETALLE_IDTAREO_TYPE       = TYPE_INTEGER,
                                TAB_TAREODETALLE_PRODUCTIVIDAD      = "productividad",
                                TAB_TAREODETALLE_PRODUCTIVIDAD_TYPE = TYPE_FLOAT,
                                TAB_TAREODETALLE_DNI                = "DNI",
                                TAB_TAREODETALLE_DNI_TYPE           = TYPE_VARCHAR,
                                TAB_TAREODETALLE_DATESTART          = "dateStart",
                                TAB_TAREODETALLE_DATESTART_TYPE     = TYPE_DATETIME,
                                TAB_TAREODETALLE_DATEEND            = "dateEnd",
                                TAB_TAREODETALLE_DATEEND_TYPE       = TYPE_DATETIME,
                                TAB_TAREODETALLE_IDSALIDA           = "idSalida",
                                TAB_TAREODETALLE_IDSALIDA_TYPE      = TYPE_INTEGER;

    public static final String CREATETABLE_TAREODETALLE =
            TABLE_CREATE+TAB_TAREODETALLE+"("+
                    TAB_TAREODETALLE_ID         + TAB_TAREODETALLE_ID_TYPE   + PK + AUTOINCREMET+
                    AND +
                    TAB_TAREODETALLE_IDTAREO    + TAB_TAREODETALLE_IDTAREO_TYPE + N_NULL+
                    AND +
                    TAB_TAREODETALLE_DNI        + TAB_TAREODETALLE_DNI_TYPE + N_NULL+
                    AND +
                    TAB_TAREODETALLE_PRODUCTIVIDAD + TAB_TAREODETALLE_PRODUCTIVIDAD_TYPE+
                    AND +
                    TAB_TAREODETALLE_DATESTART  + TAB_TAREODETALLE_DATESTART_TYPE + DEF_DATE_NOW+
                    AND +
                    TAB_TAREODETALLE_DATEEND    + TAB_TAREODETALLE_DATEEND_TYPE+
                    AND +
                    TAB_TAREODETALLE_IDSALIDA    + TAB_TAREODETALLE_IDSALIDA_TYPE+
                    ")";


    public static final String  TAB_PRODUCTIVIDAD                       = "ProductividadVO",
                                TAB_PRODUCTIVIDAD_ID                        = "id",
                                TAB_PRODUCTIVIDAD_ID_TYPE                   = TYPE_INTEGER,
                                TAB_PRODUCTIVIDAD_IDTAREODETALLE            = "idTareoDetalle",
                                TAB_PRODUCTIVIDAD_IDTAREODETALLE_TYPE       = TYPE_INTEGER,
                                TAB_PRODUCTIVIDAD_VALUE                     = "value",
                                TAB_PRODUCTIVIDAD_VALUE_TYPE                = TYPE_FLOAT,
                                TAB_PRODUCTIVIDAD_DATETIME                  = "datetime",
                                TAB_PRODUCTIVIDAD_DATETIME_TYPE             = TYPE_DATETIME;


    public static final String CREATETABLE_PRODUCTIVIDAD =
            TABLE_CREATE+TAB_PRODUCTIVIDAD+"("+
                    TAB_PRODUCTIVIDAD_ID   + TAB_PRODUCTIVIDAD_ID_TYPE   + PK + AUTOINCREMET+
                    AND +
                    TAB_PRODUCTIVIDAD_IDTAREODETALLE + TAB_PRODUCTIVIDAD_IDTAREODETALLE_TYPE + N_NULL+
                    AND +
                    TAB_PRODUCTIVIDAD_VALUE + TAB_PRODUCTIVIDAD_VALUE_TYPE +
                    AND +
                    TAB_PRODUCTIVIDAD_DATETIME + TAB_PRODUCTIVIDAD_DATETIME_TYPE + DEF_DATE_NOW+
                    ")";

    public static final String  TAB_SALIDA          = "tipoSalida",
                                TAB_SALIDA_ID           = "id",
                                TAB_SALIDA_ID_TYPE      = TYPE_INTEGER,
                                TAB_SALIDA_COD          = "code",
                                TAB_SALIDA_COD_TYPE     = TYPE_VARCHAR,
                                TAB_SALIDA_NAME         = "name",
                                TAB_SALIDA_NAME_TYPE    = TYPE_VARCHAR,
                                TAB_SALIDA_STATUS       = "status",
                                TAB_SALIDA_STATUS_TYPE  = TYPE_BOOLEAN;


    public static final String CREATETABLE_SALIDA =
            TABLE_CREATE+TAB_SALIDA+"("+
                    TAB_SALIDA_ID   + TAB_SALIDA_ID_TYPE   + PK + AUTOINCREMET+
                    AND +
                    TAB_SALIDA_COD + TAB_SALIDA_COD_TYPE + N_NULL+
                    AND +
                    TAB_SALIDA_NAME + TAB_SALIDA_NAME_TYPE + N_NULL+
                    AND +
                    TAB_SALIDA_STATUS + TAB_SALIDA_STATUS_TYPE + N_NULL+
                    ")";



}
