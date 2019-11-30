package com.ibao.alanger.worktime.views.transference.ui.main;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.ibao.alanger.worktime.R;
import com.ibao.alanger.worktime.models.VO.internal.TareoDetalleVO;
import com.ibao.alanger.worktime.views.transference.PageViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListPersonalAddedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListPersonalAddedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListPersonalAddedFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_EXTRA_MODE = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String MY_EXTRA_MODE;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListPersonalAddedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListPersonalAddedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListPersonalAddedFragment newInstance(String param1, String param2) {
        ListPersonalAddedFragment fragment = new ListPersonalAddedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_EXTRA_MODE, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    static View root;
    static RecyclerView flpa_rView;
    static RViewAdapterTransferListTrabajadores adapter;

    private PageViewModel pageViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            MY_EXTRA_MODE = getArguments().getString(ARG_PARAM_EXTRA_MODE);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root =  inflater.inflate(R.layout.fragment_list_personal_added, container, false);

        return root;
    }

    static String TAG = ListPersonalAddedFragment.class.getSimpleName();
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
        flpa_rView = getView().findViewById(R.id.flpa_rView);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        pageViewModel.get().observe(this, new Observer<List<TareoDetalleVO>>() {
            @Override
            public void onChanged(List<TareoDetalleVO> tareoDetalleVOList) {
                Log.d(TAG,"tamaaño "+tareoDetalleVOList.size());
                adapter = new RViewAdapterTransferListTrabajadores(getContext(),tareoDetalleVOList,MY_EXTRA_MODE);
                new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(flpa_rView);
                flpa_rView.setAdapter(adapter);


                TextView tViewSinItems = getView().findViewById(R.id.tViewSin);
                tViewSinItems.setVisibility(tareoDetalleVOList.size()>0?View.INVISIBLE:View.VISIBLE);
            }
        });

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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

    }

    private ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int index = viewHolder.getAdapterPosition();//esto tiene q ir arriba porq la programacion reaccitva elimina de inmediato en el recycler view sin actualizar
            final TareoDetalleVO item = PageViewModel.removeTrabajador(viewHolder.getAdapterPosition());
            Snackbar snackbar = Snackbar.make(root,"Se eliminó un Trabajador",Snackbar.LENGTH_LONG);
            snackbar.setAction("Deshacer", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PageViewModel.addTrabajador(index,item);
                }
            });
            snackbar.show();
        }
    };
}
