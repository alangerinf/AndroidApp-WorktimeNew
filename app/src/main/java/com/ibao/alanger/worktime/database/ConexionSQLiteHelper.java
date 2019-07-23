package com.ibao.alanger.worktime.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.ibao.alanger.worktime.database.DataBaseDesign.*;

public class ConexionSQLiteHelper extends SQLiteOpenHelper{

    public static int VERSION_DB = 1;

    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    static final private String TAG = ConexionSQLiteHelper.class.getSimpleName();
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATETABLE_EMPRESA);
        db.execSQL(CREATETABLE_FUNDO);
        db.execSQL(CREATETABLE_SEDE);
        db.execSQL(CREATETABLE_CCOSTE);
        db.execSQL(CREATETABLE_MODULO);
        db.execSQL(CREATETABLE_AGRUPADORACT);
        db.execSQL(CREATETABLE_CULTIVOACTIVIDAD);
        db.execSQL(CREATETABLE_CULTIVO);
        db.execSQL(CREATETABLE_FUND0CULTIVO);
        db.execSQL(CREATETABLE_TRABAJADOR);
        db.execSQL(CREATETABLE_ACTIVIDAD);
        db.execSQL(CREATETABLE_TAREO);
        db.execSQL(CREATETABLE_TAREOTRABAJADOR);
        db.execSQL(CREATETABLE_PRODUCTIVIDAD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TAB_EMPRESA);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_FUNDO);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_SEDE);
        db.execSQL("DROP TABLE IF EXISTS "+ TAB_CCOSTE);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_MODULO);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_AGRUPADORACT);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_CULTIVOACTIVIDAD);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_CULTIVO);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_FUND0CULTIVO);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_TRABAJADOR);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_ACTIVIDAD);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_TAREO);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_TAREOTRABAJADOR);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_PRODUCTIVIDAD);

        onCreate(db);
    }
}