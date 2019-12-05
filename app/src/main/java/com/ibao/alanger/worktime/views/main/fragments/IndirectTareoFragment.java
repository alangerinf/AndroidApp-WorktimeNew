package com.ibao.alanger.worktime.views.main.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ibao.alanger.worktime.R;
import com.ibao.alanger.worktime.models.DAO.TareoDAO;
import com.ibao.alanger.worktime.models.VO.internal.TareoVO;
import com.ibao.alanger.worktime.views.CreateTareoActivity;
import com.ibao.alanger.worktime.views.main.RViewAdapterMainListTareo;
import com.ibao.alanger.worktime.views.tareo.TareoActivity;

import java.util.List;

import static com.ibao.alanger.worktime.views.CreateTareoActivity.MODE_MAIN;
import static com.ibao.alanger.worktime.views.CreateTareoActivity.EXTRA_MODE;
import static com.ibao.alanger.worktime.views.tareo.TareoActivity.EXTRA_TAREO;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IndirectTareoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IndirectTareoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IndirectTareoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private static RecyclerView rView;
    private static RViewAdapterMainListTareo adapter;
    private static FloatingActionButton fab;

    private static Context ctx;
    private static View root;

    private static List<TareoVO> tareoVOList;

    private static TextView tViewTittle;

    public IndirectTareoFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static IndirectTareoFragment newInstance(String param1, String param2) {
        IndirectTareoFragment fragment = new IndirectTareoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    void declaration(){
        ctx = getContext();

        tareoVOList = new TareoDAO(ctx).listIndirecto();

        tViewTittle = getView().findViewById(R.id.fmain_tViewTittle);
        tViewTittle.setText("Lista de Labores Indirectas");


        fab = getView().findViewById(R.id.fmain_fab);
        fab.setOnClickListener(v->{
            Intent i = new Intent(getContext(), CreateTareoActivity.class);
            i.putExtra(EXTRA_MODE, MODE_MAIN);
            startActivity(i);
            fab.setClickable(false);
            fab.setFocusable(false);
        });

        rView= getView().findViewById(R.id.fmain_rView);

        adapter = new RViewAdapterMainListTareo(ctx,tareoVOList);
        adapter.setOnClicListener(v -> {
            int pos = rView.getChildAdapterPosition(v);
            TareoVO tareoVO = tareoVOList.get(pos);
            goToTareoActivity(tareoVO);
            v.setClickable(false);
            v.setFocusable(false);
        });
        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(rView);
        rView.setAdapter(adapter);
    }

    void goToTareoActivity(TareoVO t){
        Intent intent = new Intent(ctx, TareoActivity.class);
        intent.putExtra(EXTRA_TAREO,t);
        startActivity(intent);
    }


    @Override
    public void onStart() {
        super.onStart();
        declaration();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_main, container, false);
        return  root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }




    private ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            final TareoVO item = tareoVOList.remove(viewHolder.getAdapterPosition());
            new TareoDAO(ctx).deleteLogicById(item.getId());
            final int index = viewHolder.getAdapterPosition();
            adapter.notifyDataSetChanged();

            Snackbar snackbar = Snackbar.make(root,"Se Borró una Labor",Snackbar.LENGTH_LONG);
            snackbar.setAction("Deshacer", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new TareoDAO(ctx).unDeleteLogicById(item.getId());
                    tareoVOList.add(index,item);
                    adapter.notifyDataSetChanged();
                }
            });
            snackbar.show();
        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
