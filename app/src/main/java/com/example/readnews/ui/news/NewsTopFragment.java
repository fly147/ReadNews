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

public class NewsTopFragment extends Fragment {

    private NewsTopViewModel mViewModel;
    RecyclerView recyclerView;
    List<News> newsList;

    public static NewsTopFragment newInstance() {
        return new NewsTopFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_top, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NewsTopViewModel.class);
        // TODO: Use the ViewModel
        recyclerView = getActivity().findViewById(R.id.recycler_view2);
        initPersonData();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MyRecycleViewAdapter(newsList,getActivity()));
    }

    private void initPersonData() {
        newsList = new ArrayList<>();
        newsList.add(new News(getString(R.string.news_top_one_title),getString(R.string.news_top_one_desc),R.mipmap.news_top_one));
        newsList.add(new News(getString(R.string.news_top_two_title),getString(R.string.news_top_two_desc),R.mipmap.news_top_two));
        newsList.add(new News(getString(R.string.news_top_three_title),getString(R.string.news_top_three_desc),R.mipmap.news_top_three));
    }


}

