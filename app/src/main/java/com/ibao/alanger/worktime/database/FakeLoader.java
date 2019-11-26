package com.ibao.alanger.worktime.database;

import android.content.Context;

import com.ibao.alanger.worktime.models.DAO.ActividadDAO;
import com.ibao.alanger.worktime.models.DAO.CentroCosteDAO;
import com.ibao.alanger.worktime.models.DAO.CultivoDAO;
import com.ibao.alanger.worktime.models.DAO.EmpresaDAO;
import com.ibao.alanger.worktime.models.DAO.FundoDAO;
import com.ibao.alanger.worktime.models.DAO.LaborDAO;
import com.ibao.alanger.worktime.models.DAO.LoteDAO;

public class FakeLoader {

    private Context ctx;

    public FakeLoader(Context ctx)
    {
        this.ctx=ctx;
    }
    public void loadEmpresas(){
        new EmpresaDAO(ctx).dropTable();
        new EmpresaDAO(ctx).insert(1,"1","1111111","emp1","emp11",true);
        new EmpresaDAO(ctx).insert(2,"2","2222222","emp2","emp22",true);
    }

    public void loadFundos(){
        new FundoDAO(ctx).dropTable();
        new FundoDAO(ctx).insert(1,"f11","fundo11",1,true);
        new FundoDAO(ctx).insert(2,"f21","fundo21",1,true);
        new FundoDAO(ctx).insert(3,"f32","fundo32",2,true);
    }

    public void loadCultivos(){
        new CultivoDAO(ctx).dropTable();
        new CultivoDAO(ctx).insert(1,"c1","cultivo1",false,true);
        new CultivoDAO(ctx).insert(2,"c2","cultivo2",false,true);
    }

    public void loadLotes(){
        new LoteDAO(ctx).dropTable();
        new LoteDAO(ctx).insert(1,"f11-c1","f11-c1",1,1,true);
        new LoteDAO(ctx).insert(2,"f21-c1","f21-c1",2,1,true);
        new LoteDAO(ctx).insert(3,"f32-c1","f32-c1",3,1,true);

        new LoteDAO(ctx).insert(4,"f11-c2","f11-c2",1,2,true);
        new LoteDAO(ctx).insert(5,"f21-c2","f21-c2",2,2,true);
        new LoteDAO(ctx).insert(6,"f32-c2","f32-c2",3,2,true);

        new LoteDAO(ctx).insert(7,"f11-c3","f11-c3",1,3,true);
        new LoteDAO(ctx).insert(8,"f21-c3","f21-c3",2,3,true);
        new LoteDAO(ctx).insert(9,"f32-c3","f32-c3",3,3,true);
    }

    public void loadActividades(){
        new ActividadDAO(ctx).dropTable();
        new ActividadDAO(ctx).insert(1,"act1","actividad1",true);
        new ActividadDAO(ctx).insert(2,"atc2","actividad2",true);
    }

    public void loadLabores(){
        new LaborDAO(ctx).dropTable();
        new LaborDAO(ctx).insert(1,"lab1","labor1",true,0,0,false,"",1,"1,2,3",true);
        new LaborDAO(ctx).insert(2,"lab2","labor2",false,0,0,false,"",1,"1,2,3",true);
    }

    public void loadCCoste(){
        new CentroCosteDAO(ctx).dropTable();
        new CentroCosteDAO(ctx).insert(1,"cc11","ccoste11",1,true);
        new CentroCosteDAO(ctx).insert(2,"cc21","ccoste21",1,true);
    }

}
