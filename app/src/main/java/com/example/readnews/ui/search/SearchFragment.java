package com.example.readnews.ui.search;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.readnews.R;

public class SearchFragment extends Fragment {
    AutoCompleteTextView searchNews;

    String[] news={
            "Dallas police HQ attack: Suspect believed killed during standof",
            "Hugh Jackman says coffee can change the world",
            "Australia in charge of second Test against West Indies in Jamaica",
            "Sweden royal wedding"
    };
    private SearchViewModel mViewModel;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchNews = view.findViewById(R.id.searchNews);
        ArrayAdapter searchNewsAdapter=new ArrayAdapter(getActivity(),R.layout.list_item,news);
        searchNews.setAdapter(searchNewsAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        // TODO: Use the ViewModel

    }

}
