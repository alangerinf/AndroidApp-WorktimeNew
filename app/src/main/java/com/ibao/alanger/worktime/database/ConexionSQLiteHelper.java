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
        db.execSQL(CREATETABLE_CCOSTE);
        db.execSQL(CREATETABLE_LOTE);
        db.execSQL(CREATETABLE_ACTIVIDAD);
        db.execSQL(CREATETABLE_CULTIVO);
        db.execSQL(CREATETABLE_TRABAJADOR);
        db.execSQL(CREATETABLE_LABOR);
        db.execSQL(CREATETABLE_TAREO);
        db.execSQL(CREATETABLE_TAREODETALLE);
        db.execSQL(CREATETABLE_PRODUCTIVIDAD);
        db.execSQL(CREATETABLE_SALIDA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TAB_EMPRESA);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_FUNDO);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_CCOSTE);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_LOTE);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_CULTIVO);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_ACTIVIDAD);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_TRABAJADOR);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_LABOR);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_TAREO);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_TAREODETALLE);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_PRODUCTIVIDAD);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_SALIDA);

        onCreate(db);
    }
}