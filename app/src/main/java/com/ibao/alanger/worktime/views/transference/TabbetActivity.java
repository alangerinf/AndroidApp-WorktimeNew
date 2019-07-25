package com.ibao.alanger.worktime.views.transference;

import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;


import com.ibao.alanger.worktime.R;
import com.ibao.alanger.worktime.views.transference.ui.main.AddPersonalFragment;
import com.ibao.alanger.worktime.views.transference.ui.main.ListPersonalAddedFragment;
import com.ibao.alanger.worktime.views.transference.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class TabbetActivity extends AppCompatActivity
        implements
        AddPersonalFragment.OnFragmentInteractionListener,
        ListPersonalAddedFragment.OnFragmentInteractionListener {

    private static FloatingActionButton fab;
    private static CustomViewPager viewPager;
    private static TabLayout tabs;
    private static SectionsPagerAdapter sectionsPagerAdapter;
    ArrayList<String> tittles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbet);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);



       // viewPager.setPagingEnabled(false);
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        declare();
        events();
    }

    private void events() {
        fab.setOnClickListener(v->{
            if(viewPager.getCurrentItem()!=1){//si esta en el segundo fragment
                viewPager.setCurrentItem(1);
            }else {
                finish();
            }

        });



    }

    private void declare() {
        tittles = new ArrayList<>();
        tittles.add(getString(R.string.tab_text_1));
        tittles.add(getString(R.string.tab_text_2));


        sectionsPagerAdapter = new SectionsPagerAdapter( getSupportFragmentManager(),tittles);


        fab = findViewById(R.id.fab);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

     //   viewPager.setPagingEnabled(false);  //cancelar el movimiento de swipe

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onFragmentInteraction_upd_eTextDNI(String mensaje) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}