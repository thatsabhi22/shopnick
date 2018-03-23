package com.theleafapps.pro.shopnick.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theleafapps.pro.shopnick.R;
import com.theleafapps.pro.shopnick.adapters.TabbedRecyclerAdapter;
import com.theleafapps.pro.shopnick.dialogs.MyProgressDialog;
import com.theleafapps.pro.shopnick.models.SubCategory;
import com.theleafapps.pro.shopnick.utils.Commons;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.Collections;
import java.util.List;

public class OneFragment extends Fragment {

    MyProgressDialog myProgressDialog;
    List<SubCategory> infoList = Collections.emptyList();
    private RecyclerView recyclerView;
    private TabbedRecyclerAdapter adapter;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        int category_id = bundle.getInt("category_id");
        myProgressDialog = (MyProgressDialog) bundle.getSerializable("progressDialog");
        View inflatedView = inflater.inflate(R.layout.fragment_one, container, false);
        recyclerView = (RecyclerView) inflatedView.findViewById(R.id.cardRecyclerView);

        if (Commons.catIdToSubCatMap.containsKey(category_id)) {
            infoList = Commons.catIdToSubCatMap.get(category_id);
        }

        adapter = new TabbedRecyclerAdapter(getActivity(), infoList, category_id, myProgressDialog);
        recyclerView.setAdapter(adapter);

        final LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        .color(Color.RED)
                        .build());

        return inflatedView;
    }
}
