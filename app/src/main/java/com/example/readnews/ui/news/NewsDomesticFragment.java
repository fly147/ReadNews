package com.example.readnews.ui.news;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.readnews.R;

import java.util.ArrayList;
import java.util.List;

public class NewsDomesticFragment extends Fragment {

    private NewsDomesticViewModel mViewModel;
    RecyclerView recyclerView;
    List<News> newsList;

    public static NewsDomesticFragment newInstance() {
        return new NewsDomesticFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_domestic, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NewsDomesticViewModel.class);
        // TODO: Use the ViewModel
        recyclerView = getActivity().findViewById(R.id.recycler_view3);
        initPersonData();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MyRecycleViewAdapter(newsList,getActivity()));
    }

    private void initPersonData() {
        newsList = new ArrayList<>();
        newsList.add(new News(getString(R.string.news_domestic_one_title),getString(R.string.news_domestic_one_desc),R.mipmap.news_domestic_one));
        newsList.add(new News(getString(R.string.news_domestic_two_title),getString(R.string.news_domestic_two_desc),R.mipmap.news_domestic_two));
        newsList.add(new News(getString(R.string.news_domestic_three_title),getString(R.string.news_domestic_three_desc),R.mipmap.news_domestic_three));
    }

}
