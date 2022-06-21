package com.example.readnews.ui.news;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.example.readnews.MyApplication;
import com.example.readnews.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewsDetailFragment extends Fragment {

    private NewsDetailViewModel mViewModel;
    private String newsId;
    private TextView title,source,ptime,content;
    private ImageView picture;
    private List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    private List<HashMap<String, Object>> mData = new ArrayList<HashMap<String, Object>>();


    public static NewsDetailFragment newInstance() {
        return new NewsDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);
        Bundle bundle = getArguments();
        newsId = bundle.getString("newsId");
        picture = view.findViewById(R.id.news_detail_image_view);
        title = view.findViewById(R.id.news_detail_title);
        source = view.findViewById(R.id.news_detail_source);
        ptime = view.findViewById(R.id.news_detail_ptime);
        content = view.findViewById(R.id.news_detail_content);
        selectNewsDetail();
        return view;
    }

    private void selectNewsDetail() {
        String url2 = new MyApplication().NewsDetailSelectUrl;
        RequestParams params2 = new RequestParams(url2);
        params2.addBodyParameter("newsId",newsId);
        x.http().get(params2, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("select newsdetail",result);
                list = JSON.parseObject(result,
                        new TypeReference<List<HashMap<String, Object>>>() {
                        });
                mData.clear();
                mData.addAll(list);
                Log.i("detail",mData.toString());
                Log.i("cover",mData.get(0).get("cover").toString());
                Glide.with(getActivity()).load(mData.get(0).get("cover").toString()).placeholder(R.mipmap.ic_launcher).into(picture);
                title.setText((String)mData.get(0).get("title"));
                source.setText((String)mData.get(0).get("source"));
                ptime.setText((String)mData.get(0).get("ptime"));
                content.setText(Html.fromHtml((String) mData.get(0).get("content")));
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NewsDetailViewModel.class);
        // TODO: Use the ViewModel

    }

}