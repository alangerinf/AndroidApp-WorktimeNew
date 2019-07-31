package com.ibao.alanger.worktime.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ibao.alanger.worktime.R;
import com.ibao.alanger.worktime.adapters.RViewAdapterMainListTareo;
import com.ibao.alanger.worktime.models.DAO.TareoDAO;
import com.ibao.alanger.worktime.models.VO.internal.TareoVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.alanger.worktime.views.CreateTareoActivity.CREATE_MODE_MAIN;
import static com.ibao.alanger.worktime.views.CreateTareoActivity.EXTRA_CREATE_MODE;
import static com.ibao.alanger.worktime.views.TareoActivity.EXTRA_TAREO;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
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

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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

        tareoVOList = new TareoDAO(ctx).listAll();

        fab = getView().findViewById(R.id.fmain_fab);
        fab.setOnClickListener(v->{
            Intent i = new Intent(getContext(),CreateTareoActivity.class);
            i.putExtra(EXTRA_CREATE_MODE,CREATE_MODE_MAIN);
            startActivity(i);
            fab.setClickable(false);
            fab.setFocusable(false);
        });

        rView= getView().findViewById(R.id.fmain_rView);

        adapter = new RViewAdapterMainListTareo(tareoVOList);
        adapter.setOnClicListener(v -> {
            int pos = rView.getChildAdapterPosition(v);
            TareoVO tareoVO = tareoVOList.get(pos);
            goToTareoActivity(tareoVO);
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
            new TareoDAO(ctx).deleteById(item.getId());
            final int index = viewHolder.getAdapterPosition();
            adapter.notifyDataSetChanged();
            //enviar(fp1_tietRefA.getText().toString(),fp1_tietRefB.getText().toString(),productList);

            Snackbar snackbar = Snackbar.make(root,"Se Borró una Labor",Snackbar.LENGTH_LONG);
            snackbar.setAction("Deshacer", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
