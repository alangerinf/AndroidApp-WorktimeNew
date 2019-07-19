package com.ibao.alanger.worktime;

public class DataBaseDesign {


    /***NO QUITAR LOS ESPACIOS ENTRE LAS PALABRAS CLAVE PORQ SON NECESARIAS**/

    //PALABRAS CLAVE
    private static final String TABLE_CREATE        = " CREATE TABLE ",
                                AUTOINCREMET        = "  AUTOINCREMENT ",
                                PK                  = " PRIMARY KEY ",
                                N_NULL              = " NOT NULL ",
                                DEF_DATE_NOW        = " DEFAULT  ",
                                AND                 =" , ";

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
                                TAB_EMPRESA_NAME        = "name",
                                TAB_EMPRESA_NAME_TYPE   = TYPE_VARCHAR;

    public static final String CREATETABLE_EMPRESA =
            TABLE_CREATE+TAB_EMPRESA+"("+
                TAB_EMPRESA_ID   + TAB_EMPRESA_ID_TYPE   + PK+ N_NULL+
                AND+
                TAB_EMPRESA_NAME + TAB_EMPRESA_NAME_TYPE + N_NULL+
            ")";

    //TABLE FUNDO
    public static final String  TAB_FUNDO               = "Fundo",
                                TAB_FUNDO_ID                = "id",
                                TAB_FUNDO_ID_TYPE           = TYPE_INTEGER,
                                TAB_FUNDO_NAME              = "name",
                                TAB_FUNDO_NAME_TYPE         = TYPE_VARCHAR,
                                TAB_FUNDO_IDEMPRESA         = "idEmpresa",
                                TAB_FUNDO_IDEMPRESA_TYPE    = TYPE_INTEGER;

    public static final String CREATETABLE_FUNDO =
            TABLE_CREATE+TAB_FUNDO+"("+
                    TAB_FUNDO_ID   + TAB_FUNDO_ID_TYPE   + PK+ N_NULL+
                    AND +
                    TAB_FUNDO_NAME + TAB_FUNDO_NAME_TYPE + N_NULL+
                    AND +
                    TAB_FUNDO_IDEMPRESA + TAB_FUNDO_IDEMPRESA_TYPE + N_NULL+
                    ")";

    //TABLE SEDE
    public static final String  TAB_SEDE                = "Sede",
                                TAB_SEDE_ID                 = "id",
                                TAB_SEDE_ID_TYPE            = TYPE_INTEGER,
                                TAB_SEDE_NAME               = "name",
                                TAB_SEDE_NAME_TYPE          = TYPE_VARCHAR,
                                TAB_SEDE_IDEMPRESA          = "idEmpresa",
                                TAB_SEDE_IDEMPRESA_TYPE     = TYPE_INTEGER;

    public static final String CREATETABLE_SEDE =
            TABLE_CREATE+TAB_SEDE+"("+
                    TAB_SEDE_ID   + TAB_SEDE_ID_TYPE   + PK+ N_NULL+
                    AND +
                    TAB_SEDE_NAME + TAB_SEDE_NAME_TYPE + N_NULL+
                    AND +
                    TAB_SEDE_IDEMPRESA + TAB_SEDE_IDEMPRESA_TYPE + N_NULL+
                    ")";

    //TABLE CENTRO DE COSTO
    public static final String  TAB_CCOSTO              = "CentroCosto",
                                TAB_CCOSTO_ID               = "id",
                                TAB_CCOSTO_ID_TYPE          = TYPE_INTEGER,
                                TAB_CCOSTO_COD              = "code",
                                TAB_CCOSTO_COD_TYPE         = TYPE_VARCHAR,
                                TAB_CCOSTO_NAME             = "name",
                                TAB_CCOSTO_NAME_TYPE        = TYPE_VARCHAR,
                                TAB_CCOSTO_DESC             = "descripcion",
                                TAB_CCOSTO_DESC_TYPE        = TYPE_VARCHAR,
                                TAB_CCOSTO_IDEMPRESA        = "idEmpresa",
                                TAB_CCOSTO_IDEMPRESA_TYPE   = TYPE_INTEGER;

    public static final String CREATETABLE_CCOSTO =
            TABLE_CREATE+TAB_CCOSTO+"("+
                    TAB_CCOSTO_ID   + TAB_CCOSTO_ID_TYPE   + PK + N_NULL+
                    AND +
                    TAB_CCOSTO_COD + TAB_CCOSTO_COD_TYPE + N_NULL+
                    AND +
                    TAB_CCOSTO_NAME + TAB_CCOSTO_NAME_TYPE + N_NULL+
                    AND +
                    TAB_CCOSTO_DESC + TAB_CCOSTO_DESC_TYPE + N_NULL+
                    AND +
                    TAB_CCOSTO_IDEMPRESA + TAB_CCOSTO_IDEMPRESA_TYPE + N_NULL+
                    ")";
    
    //TABLE MODULO
    public static final String  TAB_MODULO              = "Modulo",
                                TAB_MODULO_ID               = "id",
                                TAB_MODULO_ID_TYPE          = TYPE_INTEGER,
                                TAB_MODULO_COD              = "code",
                                TAB_MODULO_COD_TYPE         = TYPE_VARCHAR,
                                TAB_MODULO_NAME             = "name",
                                TAB_MODULO_NAME_TYPE        = TYPE_VARCHAR,
                                TAB_MODULO_IDFUNDO          = "idFundo",
                                TAB_MODULO_IDFUNDO_TYPE     = TYPE_INTEGER;

    public static final String CREATETABLE_MODULO =
            TABLE_CREATE+TAB_MODULO+"("+
                    TAB_MODULO_ID   + TAB_MODULO_ID_TYPE   + PK+ N_NULL+
                    AND +
                    TAB_MODULO_COD + TAB_MODULO_COD_TYPE + N_NULL+
                    AND +
                    TAB_MODULO_NAME + TAB_MODULO_NAME_TYPE + N_NULL+
                    AND +
                    TAB_CCOSTO_DESC + TAB_CCOSTO_DESC_TYPE + N_NULL+
                    AND +
                    TAB_MODULO_IDFUNDO + TAB_MODULO_IDFUNDO_TYPE+ N_NULL+
                    ")";
    
    //TABLE AGRUPACION_ACTIVIDAD
    public static final String  TAB_AGRUPADORACT              = "Modulo",
                                TAB_AGRUPADORACT_ID               = "id",
                                TAB_AGRUPADORACT_ID_TYPE          = TYPE_INTEGER,
                                TAB_AGRUPADORACT_COD              = "code",
                                TAB_AGRUPADORACT_COD_TYPE         = TYPE_VARCHAR,
                                TAB_AGRUPADORACT_NAME             = "name",
                                TAB_AGRUPADORACT_NAME_TYPE        = TYPE_VARCHAR;

    public static final String CREATETABLE_AGRUPADORACT =
            TABLE_CREATE+TAB_AGRUPADORACT+"("+
                    TAB_AGRUPADORACT_ID   + TAB_AGRUPADORACT_ID_TYPE + PK + N_NULL+
                    AND +
                    TAB_AGRUPADORACT_COD + TAB_AGRUPADORACT_COD_TYPE + N_NULL+
                    AND +
                    TAB_AGRUPADORACT_NAME + TAB_AGRUPADORACT_NAME_TYPE + N_NULL+
                    ")";

    //TABLE CULTIVO_ACTIVIDAD
    public static final String  TAB_CULTIVOACTIVIDAD                = "CultivoActividad",
                                TAB_CULTIVOACTIVIDAD_ID                 = "id",
                                TAB_CULTIVOACTIVIDAD_ID_TYPE            = TYPE_INTEGER,
                                TAB_CULTIVOACTIVIDAD_IDACTIVIDAD        = "idActividad",
                                TAB_CULTIVOACTIVIDAD_IDACTIVIDAD_TYPE   = TYPE_INTEGER,
                                TAB_CULTIVOACTIVIDAD_IDCULTIVO          = "idCultivo",
                                TAB_CULTIVOACTIVIDAD_IDCULTIVO_TYPE     = TYPE_INTEGER;

    public static final String CREATETABLE_CULTIVOACTIVIDAD =
            TABLE_CREATE+TAB_CULTIVOACTIVIDAD+"("+
                    TAB_CULTIVOACTIVIDAD_ID   + TAB_CULTIVOACTIVIDAD_ID_TYPE   + PK + N_NULL+
                    AND +
                    TAB_CULTIVOACTIVIDAD_IDACTIVIDAD + TAB_CULTIVOACTIVIDAD_IDACTIVIDAD_TYPE + N_NULL+
                    AND +
                    TAB_CULTIVOACTIVIDAD_IDCULTIVO + TAB_CULTIVOACTIVIDAD_IDCULTIVO_TYPE+ N_NULL+
                    ")";

    public static final String  TAB_CULTIVO             = "Cultivo",
                                TAB_CULTIVO_ID              = "id",
                                TAB_CULTIVO_ID_TYPE         = TYPE_INTEGER,
                                TAB_CULTIVO_COD             = "code",
                                TAB_CULTIVO_COD_TYPE        = TYPE_VARCHAR,
                                TAB_CULTIVO_NAME            = "name",
                                TAB_CULTIVO_NAME_TYPE       = TYPE_VARCHAR;

    public static final String CREATETABLE_CULTIVO =
            TABLE_CREATE+TAB_CULTIVO+"("+
                    TAB_CULTIVO_ID   + TAB_CULTIVO_ID_TYPE   + PK+ N_NULL+
                    AND +
                    TAB_CULTIVO_COD + TAB_CULTIVO_COD_TYPE + N_NULL+
                    AND +
                    TAB_CULTIVO_NAME + TAB_CULTIVO_NAME_TYPE+ N_NULL+
                    ")";

    public static final String  TAB_FUND0CULTIVO            = "FundoCultivo",
                                TAB_FUND0CULTIVO_ID             = "id",
                                TAB_FUND0CULTIVO_ID_TYPE        = TYPE_INTEGER,
                                TAB_FUND0CULTIVO_IDFUNDO        = "idFundo",
                                TAB_FUND0CULTIVO_IDFUNDO_TYPE   = TYPE_INTEGER,
                                TAB_FUND0CULTIVO_IDCULTIVO      = "idCultivo",
                                TAB_FUND0CULTIVO_IDCULTIVO_TYPE = TYPE_INTEGER;

    public static final String CREATETABLE_FUND0CULTIVO =
            TABLE_CREATE+TAB_FUND0CULTIVO+"("+
                    TAB_FUND0CULTIVO_ID   + TAB_FUND0CULTIVO_ID_TYPE   + PK+ N_NULL+
                    AND +
                    TAB_FUND0CULTIVO_IDFUNDO + TAB_FUND0CULTIVO_IDFUNDO_TYPE + N_NULL+
                    AND +
                    TAB_FUND0CULTIVO_IDCULTIVO + TAB_FUND0CULTIVO_IDCULTIVO_TYPE+ N_NULL+
                    ")";

    public static final String  TAB_TRABAJADOR              = "Trabajador",
                                TAB_TRABAJADOR_ID               = "id",
                                TAB_TRABAJADOR_ID_TYPE          = TYPE_INTEGER,
                                TAB_TRABAJADOR_COD              = "cod",
                                TAB_TRABAJADOR_COD_TYPE         = TYPE_VARCHAR,
                                TAB_TRABAJADOR_DNI              = "dni",
                                TAB_TRABAJADOR_DNI_TYPE         = TYPE_VARCHAR,
                                TAB_TRABAJADOR_NAME             = "name",
                                TAB_TRABAJADOR_NAME_TYPE        = TYPE_VARCHAR,
                                TAB_TRABAJADOR_IDEMPRESA        = "idEmpresa",
                                TAB_TRABAJADOR_IDEMPRESA_TYPE   = TYPE_INTEGER;

    public static final String CREATETABLE_TRABAJADOR =
            TABLE_CREATE+TAB_TRABAJADOR+"("+
                    TAB_TRABAJADOR_ID   + TAB_TRABAJADOR_ID_TYPE   + PK + N_NULL+
                    AND +
                    TAB_TRABAJADOR_COD + TAB_TRABAJADOR_COD_TYPE + N_NULL+
                    AND +
                    TAB_TRABAJADOR_DNI + TAB_TRABAJADOR_DNI_TYPE + N_NULL+
                    AND +
                    TAB_TRABAJADOR_NAME + TAB_TRABAJADOR_NAME_TYPE + N_NULL+
                    AND +
                    TAB_TRABAJADOR_IDEMPRESA + TAB_TRABAJADOR_IDEMPRESA_TYPE+ N_NULL+
                    ")";

    public static final String  TAB_ACTIVIDAD                   = "Trabajador",
                                TAB_ACTIVIDAD_ID                    = "id",
                                TAB_ACTIVIDAD_ID_TYPE               = TYPE_INTEGER,
                                TAB_ACTIVIDAD_COD                   = "cod",
                                TAB_ACTIVIDAD_COD_TYPE              = TYPE_VARCHAR,
                                TAB_ACTIVIDAD_NAME                  = "name",
                                TAB_ACTIVIDAD_NAME_TYPE             = TYPE_VARCHAR,
                                TAB_ACTIVIDAD_ISDIRECTO             = "isDirecto",
                                TAB_ACTIVIDAD_ISDIRECTO_TYPE        = TYPE_BOOLEAN,
                                TAB_ACTIVIDAD_THEORICALCOST         = "theoricalCost",
                                TAB_ACTIVIDAD_THEORICALCOST_TYPE    = TYPE_FLOAT,
                                TAB_ACTIVIDAD_THEORICALHOURS        = "theoricalHours",
                                TAB_ACTIVIDAD_THEORICALHOURS_TYPE   = TYPE_FLOAT,
                                TAB_ACTIVIDAD_ISTAREA               = "isTarea",
                                TAB_ACTIVIDAD_ISTAREA_TYPE          = TYPE_BOOLEAN,
                                TAB_ACTIVIDAD_ISASISTENCIA          = "isAsistencia",
                                TAB_ACTIVIDAD_ISASISTENCIA_TYPE     = TYPE_BOOLEAN,
                                TAB_ACTIVIDAD_IDAGRUPADORACT        = "idAgrupadorActividad",
                                TAB_ACTIVIDAD_IDAGRUPADORACT_TYPE   = TYPE_INTEGER;


    public static final String CREATETABLE_ACTIVIDAD =
            TABLE_CREATE+TAB_ACTIVIDAD+"("+
                    TAB_ACTIVIDAD_ID   + TAB_ACTIVIDAD_ID_TYPE   + PK + N_NULL+
                    AND +
                    TAB_ACTIVIDAD_COD + TAB_ACTIVIDAD_COD_TYPE + N_NULL+
                    AND +
                    TAB_ACTIVIDAD_NAME + TAB_ACTIVIDAD_NAME_TYPE + N_NULL+
                    AND +
                    TAB_ACTIVIDAD_ISDIRECTO + TAB_ACTIVIDAD_ISDIRECTO_TYPE + N_NULL+
                    AND +
                    TAB_ACTIVIDAD_THEORICALCOST + TAB_ACTIVIDAD_THEORICALCOST_TYPE+ N_NULL+
                    AND +
                    TAB_ACTIVIDAD_THEORICALHOURS + TAB_ACTIVIDAD_THEORICALHOURS_TYPE+ N_NULL+
                    AND +
                    TAB_ACTIVIDAD_ISTAREA + TAB_ACTIVIDAD_ISTAREA_TYPE+ N_NULL+
                    AND +
                    TAB_ACTIVIDAD_ISASISTENCIA + TAB_ACTIVIDAD_ISASISTENCIA_TYPE+ N_NULL+
                    AND +
                    TAB_ACTIVIDAD_IDAGRUPADORACT + TAB_ACTIVIDAD_IDAGRUPADORACT_TYPE+ N_NULL+
                    ")";

    //TODO: TABLAS DE INSERSION

    public static final String  TAB_TAREO                   = "Tareo",
                                TAB_TAREO_ID                    = "id",
                                TAB_TAREO_ID_TYPE               = TYPE_INTEGER,
                                TAB_TAREO_IDFUNDOCULTIVO        = "idFundoCultivo",
                                TAB_TAREO_IDFUNDOCULTIVO_TYPE   = TYPE_INTEGER,
                                TAB_TAREO_IDACTIVIDAD           = "idActividad",
                                TAB_TAREO_IDACTIVIDAD_TYPE      = TYPE_INTEGER,
                                TAB_TAREO_IDMODULO              = "idModulo",
                                TAB_TAREO_IDMODULO_TYPE         = TYPE_INTEGER,
                                TAB_TAREO_IDCCOSTO              = "idCentroCosto",
                                TAB_TAREO_IDCCOSTO_TYPE         = TYPE_INTEGER,
                                TAB_TAREO_IDSEDE                = "idSede",
                                TAB_TAREO_IDSEDE_TYPE           = TYPE_INTEGER,
                                TAB_TAREO_DATESTART             = "dateStart",
                                TAB_TAREO_DATESTART_TYPE        = TYPE_DATETIME,
                                TAB_TAREO_DATEEND               = "dateEnd",
                                TAB_TAREO_DATEEND_TYPE          = TYPE_DATETIME,
                                TAB_TAREO_ISACTIVE              = "isActive",
                                TAB_TAREO_ISACTIVE_TYPE         = TYPE_BOOLEAN;


    public static final String CREATETABLE_TAREO =
            TABLE_CREATE+TAB_TAREO+"("+
                    TAB_TAREO_ID   + TAB_TAREO_ID_TYPE   + PK + AUTOINCREMET+
                    AND +
                    TAB_TAREO_IDFUNDOCULTIVO + TAB_TAREO_IDFUNDOCULTIVO_TYPE + N_NULL+
                    AND +
                    TAB_TAREO_IDACTIVIDAD + TAB_TAREO_IDACTIVIDAD_TYPE + N_NULL+
                    AND +
                    TAB_TAREO_IDMODULO + TAB_TAREO_IDMODULO_TYPE +
                    AND +
                    TAB_TAREO_IDCCOSTO + TAB_TAREO_IDCCOSTO_TYPE +
                    AND +
                    TAB_TAREO_IDSEDE + TAB_TAREO_IDSEDE_TYPE+
                    AND +
                    TAB_TAREO_DATESTART + TAB_TAREO_DATESTART_TYPE+ DEF_DATE_NOW+
                    AND +
                    TAB_TAREO_DATEEND + TAB_TAREO_DATEEND_TYPE+
                    AND +
                    TAB_TAREO_ISACTIVE + TAB_TAREO_ISACTIVE_TYPE+ " DEFAULT 1 "+
                    ")";

    public static final String  TAB_TAREOTRABAJADOR                 = "TareoTrabajador",
                                TAB_TAREOTRABAJADOR_ID                  = "id",
                                TAB_TAREOTRABAJADOR_ID_TYPE             = TYPE_INTEGER,
                                TAB_TAREOTRABAJADOR_IDTAREO             = "idTareo",
                                TAB_TAREOTRABAJADOR_IDTAREO_TYPE        = TYPE_INTEGER,
                                TAB_TAREOTRABAJADOR_IDTRABAJADOR        = "idTrabajador",
                                TAB_TAREOTRABAJADOR_IDTRABAJADOR_TYPE   = TYPE_INTEGER,
                                TAB_TAREOTRABAJADOR_DATESTART           = "dateStart",
                                TAB_TAREOTRABAJADOR_DATESTART_TYPE      = TYPE_DATETIME,
                                TAB_TAREOTRABAJADOR_DATEEND             = "dateEnd",
                                TAB_TAREOTRABAJADOR_DATEEND_TYPE        = TYPE_DATETIME;

    public static final String CREATETABLE_TAREOTRABAJADOR =
            TABLE_CREATE+TAB_TAREOTRABAJADOR+"("+
                    TAB_TAREOTRABAJADOR_ID   + TAB_TAREOTRABAJADOR_ID_TYPE   + PK + AUTOINCREMET+
                    AND +
                    TAB_TAREOTRABAJADOR_IDTAREO + TAB_TAREOTRABAJADOR_IDTAREO_TYPE + N_NULL+
                    AND +
                    TAB_TAREOTRABAJADOR_IDTRABAJADOR + TAB_TAREOTRABAJADOR_IDTRABAJADOR_TYPE + N_NULL+
                    AND +
                    TAB_TAREO_IDMODULO + TAB_TAREOTRABAJADOR_IDTRABAJADOR_TYPE +
                    AND +
                    TAB_TAREOTRABAJADOR_DATESTART + TAB_TAREOTRABAJADOR_DATESTART_TYPE + DEF_DATE_NOW+
                    AND +
                    TAB_TAREOTRABAJADOR_DATEEND + TAB_TAREOTRABAJADOR_DATEEND_TYPE+
                    ")";


    public static final String  TAB_PRODUCTIVIDAD                       = "TareoTrabajador",
                                TAB_PRODUCTIVIDAD_ID                        = "id",
                                TAB_PRODUCTIVIDAD_ID_TYPE                   = TYPE_INTEGER,
                                TAB_PRODUCTIVIDAD_IDTAREOTRABAJADOR         = "idTareo",
                                TAB_PRODUCTIVIDAD_IDTAREOTRABAJADOR_TYPE    = TYPE_INTEGER,
                                TAB_PRODUCTIVIDAD_VALUE                     = "idTrabajador",
                                TAB_PRODUCTIVIDAD_VALUE_TYPE                = TYPE_INTEGER,
                                TAB_TAREOTRABAJADOR_DATETIME                = "date",
                                TAB_TAREOTRABAJADOR_DATETIME_TYPE           = TYPE_DATETIME;


    public static final String CREATETABLE_PRODUCTIVIDAD =
            TABLE_CREATE+TAB_PRODUCTIVIDAD+"("+
                    TAB_PRODUCTIVIDAD_ID   + TAB_PRODUCTIVIDAD_ID_TYPE   + PK + AUTOINCREMET+
                    AND +
                    TAB_PRODUCTIVIDAD_IDTAREOTRABAJADOR + TAB_PRODUCTIVIDAD_IDTAREOTRABAJADOR_TYPE + N_NULL+
                    AND +
                    TAB_PRODUCTIVIDAD_VALUE + TAB_PRODUCTIVIDAD_VALUE_TYPE +
                    AND +
                    TAB_TAREOTRABAJADOR_DATETIME + TAB_TAREOTRABAJADOR_DATETIME_TYPE + DEF_DATE_NOW+
                    ")";



}
