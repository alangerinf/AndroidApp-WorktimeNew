package com.ibao.alanger.worktimecopa.views.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.ibao.alanger.worktimecopa.BuildConfig;
import com.ibao.alanger.worktimecopa.R;
import com.ibao.alanger.worktimecopa.database.ConexionSQLiteHelper;
import com.ibao.alanger.worktimecopa.database.SharedPreferencesManager;
import com.ibao.alanger.worktimecopa.models.DAO.TareoDAO;
import com.ibao.alanger.worktimecopa.update.view.ActivityUpdate;
import com.ibao.alanger.worktimecopa.upload.ActivityUpload;
import com.ibao.alanger.worktimecopa.views.ActivityPreloader;
import com.ibao.alanger.worktimecopa.views.main.fragments.AllTareoFragment;
import com.ibao.alanger.worktimecopa.views.main.fragments.AsistenciaTareoFragment;
import com.ibao.alanger.worktimecopa.views.main.fragments.DirectTareoFragment;
import com.ibao.alanger.worktimecopa.views.main.fragments.IndirectTareoFragment;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AllTareoFragment.OnFragmentInteractionListener,
        AsistenciaTareoFragment.OnFragmentInteractionListener,
        DirectTareoFragment.OnFragmentInteractionListener,
        IndirectTareoFragment.OnFragmentInteractionListener{


    private Fragment myFragment = null;
    private Context ctx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ctx = this;

        //FakeLoader

        // FakeLoader fl = new FakeLoader(ctx);
        // fl.loadEmpresas();
        // fl.loadFundos();
        //fl.loadCultivos();
        // fl.loadActividades();
        //fl.loadLotes();
        //fl.loadLabores();
        //fl.loadCCoste();

        myFragment = new AllTareoFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, myFragment).commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(0).setChecked(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_version) {
            try {
                Toast.makeText(getBaseContext(),"Versión "+ BuildConfig.VERSION_NAME+" code."+BuildConfig.VERSION_CODE+" db."+ ConexionSQLiteHelper.VERSION_DB,Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getBaseContext(),e.toString(),Toast.LENGTH_LONG).show();
            }
            return true;
        }

        if (id == R.id.action_logout) {
            if(new TareoDAO(ctx).listAll_UPLOAD().size()>0) {

                Snackbar snackbar= Snackbar.make(myFragment.getView(),"Falta finalizar todas sus labores y sincronizar",Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.red_pastel));
                snackbar.setActionTextColor(ContextCompat.getColor(this,R.color.white));
                snackbar.setAction("Sincronizar", v1 -> startActivity(new Intent(this, ActivityUpload.class)));
                snackbar.show();

            }else{
                SharedPreferencesManager.deleteUser(ctx);
                startActivity(new Intent(this, ActivityPreloader.class));
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_all:
                myFragment = new AllTareoFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, myFragment).commit();
                break;
            case R.id.nav_direct:
                myFragment = new DirectTareoFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, myFragment).commit();
                break;
            case R.id.nav_indirect:
                myFragment = new IndirectTareoFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, myFragment).commit();
                break;
            case R.id.nav_asistencia:
                myFragment = new AsistenciaTareoFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, myFragment).commit();
                break;
            case R.id.nav_download:
                startActivity(new Intent(this, ActivityUpdate.class));
                break;

            case R.id.nav_update:
                startActivity(new Intent(this, ActivityUpload.class));
                break;

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            moveTaskToBack(true);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
