package com.shagiev.konstantin.daybook.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shagiev.konstantin.daybook.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoneTaskFragment extends Fragment {

    private RecyclerView mRvDoneTasks;
    private RecyclerView.LayoutManager mLayoutManager;

    public DoneTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_done_task, container, false);

        mRvDoneTasks = (RecyclerView) rootView.findViewById(R.id.rvDoneTasks);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRvDoneTasks.setLayoutManager(mLayoutManager);

        return rootView;
    }

}
