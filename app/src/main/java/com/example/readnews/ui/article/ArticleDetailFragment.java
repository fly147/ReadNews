package com.example.readnews.ui.article;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.example.readnews.MyApplication;
import com.example.readnews.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArticleDetailFragment extends Fragment {

    private ArticleDetailViewModel mViewModel;
    private List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    private List<HashMap<String, Object>> mData = new ArrayList<HashMap<String, Object>>();
    String id;

    public static ArticleDetailFragment newInstance() {
        return new ArticleDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_article_detail, container, false);
        View view = inflater.inflate(R.layout.fragment_article_detail, container, false);
        id = getArguments().getString("id");
        String url = new MyApplication().selectbyidurl;
        RequestParams params = new RequestParams(url);
        //get
        params.addQueryStringParameter("id", id);

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
                list = JSON.parseObject(result,
                        new TypeReference<List<HashMap<String, Object>>>() {
                        });
                mData.addAll(list);

                ImageView picture = (ImageView) view.findViewById(R.id.article_image_view);
                //showpicture
                Glide.with(getActivity()).load(new MyApplication().imagebaseurl + mData.get(0).get("articleImagePath").toString()).placeholder(R.mipmap.ic_launcher).into(picture);
                CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
                collapsingToolbar.setTitle(mData.get(0).get("articleTitle").toString());
                TextView tvarticleTitle = (TextView) view.findViewById(R.id.article_author11);
                tvarticleTitle.setText(mData.get(0).get("articleTitle").toString());
                TextView tvarticleContent = (TextView) view.findViewById(R.id.article_content_text);
                tvarticleContent.setText(mData.get(0).get("articleContent").toString());
                TextView tvarticleTime = view.findViewById(R.id.article_time11);
                tvarticleTime.setText(mData.get(0).get("articleTime").toString());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }
        });
        FloatingActionButton edit_article = view.findViewById(R.id.edit_article);
        edit_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                navController.navigate(R.id.nav_edit_article, bundle);
            }
        });
        FloatingActionButton del_article = view.findViewById(R.id.delete_article);
        del_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url=new MyApplication().deletebyidurl;
                RequestParams params = new RequestParams(url);
                //get
                params.addQueryStringParameter("id", id);
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
                        Log.i("delete",result);
                        NavController navController= Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
                        navController.navigate(R.id.nav_article);
                    }
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();

                    }
                    @Override
                    public void onCancelled(CancelledException cex) {
                        Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onFinished() {

                    }
                });

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ArticleDetailViewModel.class);
        // TODO: Use the ViewModel
    }

}