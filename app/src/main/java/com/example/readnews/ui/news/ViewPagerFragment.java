package com.example.readnews.ui.news;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.readnews.MyApplication;
import com.example.readnews.R;
import com.google.android.material.tabs.TabLayout;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ViewPagerFragment extends Fragment {

    private ViewPagerViewModel mViewModel;
    private TabLayout tableLayout;
    private ViewPager viewPager;
    private List<String> titles;
    private List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    private List<HashMap<String, Object>> mData = new ArrayList<HashMap<String, Object>>();


    public static ViewPagerFragment newInstance() {
        return new ViewPagerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        tableLayout = (TabLayout) view.findViewById(R.id.tabs2);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        titles = new ArrayList<>();
//        NewsTopFragment newsTopFragment = new NewsTopFragment();
//        //请求添加新闻类型接口
        String url = new MyApplication().NewsTypeInsertUrl;
        RequestParams params = new RequestParams(url);
        final ProgressDialog dia = new ProgressDialog(getActivity());
        //get
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //加载成功回调，返回获取到的数据
                Log.i("huhao", "addNewsType: " + result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("huhao", "onError: " + ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {
                dia.dismiss();//加载完成
            }
        });
//        //请求查询新闻类型接口
//        url = new MyApplication().NewsTypeSelectUrl;
//        params = new RequestParams(url);
//        x.http().get(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                Log.i("getNewsType", result);
//                list = JSON.parseObject(result,
//                        new TypeReference<List<HashMap<String, Object>>>() {
//                        });
//                mData.clear();
//                mData.addAll(list);
//
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                Toast.makeText(x.app(), ex.toString(), Toast.LENGTH_LONG).show();
//                Log.i("huhao", "onError: " + ex.toString());
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
//
//            }
//
//            @Override
//            public void onFinished() {
//                dia.dismiss();//加载完成
//            }
//        });

        initTabs();
        viewPager.setAdapter(new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            //以下方法的使用可以查看：https://blog.csdn.net/fyq520521/article/details/80595684
            //得到当前页的标题，也就是设置当前页面显示的标题是tabLayout对应标题
            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
//                return titles.get(position);
                return (String)mData.get(position).get("typeName");
            }

            //返回position位置关联的Fragment。
            @Override
            public Fragment getItem(int position) {
                NewsTopFragment newsTopFragment = new NewsTopFragment();
                //判断所选的标题，进行传值显示
                //Bundle主要用于传递数据；它保存的数据，是以key-value(键值对)的形式存在的。
                //详细讲解：https://blog.csdn.net/yiranruyuan/article/details/78049219
                Bundle bundle = new Bundle();
                bundle.putString("typeId",(String)mData.get(position).get("typeId"));
                Log.i("current typeId",(String)mData.get(position).get("typeId"));
                //设置当前newsFragment的bundle
                //具体讲解：https://www.jb51.net/article/102383.htm
                newsTopFragment.setArguments(bundle);
                return newsTopFragment;
            }

            //创建指定位置的页面视图
            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                NewsTopFragment newsTopFragment1 = (NewsTopFragment) super.instantiateItem(container, position);
                return newsTopFragment1;
            }

            //具体讲解：https://www.cnblogs.com/cheneasternsun/p/6017012.html，但是这样用比较浪费资源
            @Override
            public int getItemPosition(@NonNull Object object) {
                return FragmentStatePagerAdapter.POSITION_NONE;
            }

            //返回当前有效视图的数量，这其实也就是将list和tab选项卡关联起来
            @Override
            public int getCount() {
//                return titles.size();
                return  mData.size();
            }
        });
        //将TabLayout与ViewPager关联显示
        tableLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ViewPagerViewModel.class);
        // TODO: Use the ViewModel
        Log.i("huhao","viewpager activiy");
//        initTabs();
//        viewPager.setAdapter(new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
//            //以下方法的使用可以查看：https://blog.csdn.net/fyq520521/article/details/80595684
//            //得到当前页的标题，也就是设置当前页面显示的标题是tabLayout对应标题
//            @Nullable
//            @Override
//            public CharSequence getPageTitle(int position) {
////                return titles.get(position);
//                return (String)mData.get(position).get("typeName");
//            }
//
//            //返回position位置关联的Fragment。
//            @Override
//            public Fragment getItem(int position) {
//                NewsTopFragment newsTopFragment = new NewsTopFragment();
//                //判断所选的标题，进行传值显示
//                //Bundle主要用于传递数据；它保存的数据，是以key-value(键值对)的形式存在的。
//                //详细讲解：https://blog.csdn.net/yiranruyuan/article/details/78049219
//                Bundle bundle = new Bundle();
//                bundle.putString("typeId",(String)mData.get(position).get("typeId"));
//                Log.i("current typeId",(String)mData.get(position).get("typeId"));
//                //设置当前newsFragment的bundle
//                //具体讲解：https://www.jb51.net/article/102383.htm
//                newsTopFragment.setArguments(bundle);
//                return newsTopFragment;
//            }
//
//            //创建指定位置的页面视图
//            @NonNull
//            @Override
//            public Object instantiateItem(@NonNull ViewGroup container, int position) {
//                NewsTopFragment newsTopFragment1 = (NewsTopFragment) super.instantiateItem(container, position);
//                return newsTopFragment1;
//            }
//
//            //具体讲解：https://www.cnblogs.com/cheneasternsun/p/6017012.html，但是这样用比较浪费资源
//            @Override
//            public int getItemPosition(@NonNull Object object) {
//                return FragmentStatePagerAdapter.POSITION_NONE;
//            }
//
//            //返回当前有效视图的数量，这其实也就是将list和tab选项卡关联起来
//            @Override
//            public int getCount() {
////                return titles.size();
//                return  mData.size();
//            }
//        });
//        //将TabLayout与ViewPager关联显示
//        tableLayout.setupWithViewPager(viewPager);

    }

    private void initTabs() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("typeId", "509");
        map.put("typeName", "财经");
        mData.add(map);

        map = new HashMap<String, Object>();
        map.put("typeId", "510");
        map.put("typeName", "科技");
        mData.add(map);

        map = new HashMap<String, Object>();
        map.put("typeId", "511");
        map.put("typeName", "军事");
        mData.add(map);

        map = new HashMap<String, Object>();
        map.put("typeId", "512");
        map.put("typeName", "时尚");
        mData.add(map);

        map = new HashMap<String, Object>();
        map.put("typeId", "513");
        map.put("typeName", "NBA");
        mData.add(map);

        map = new HashMap<String, Object>();
        map.put("typeId", "514");
        map.put("typeName", "股票");
        mData.add(map);

        map = new HashMap<String, Object>();
        map.put("typeId", "515");
        map.put("typeName", "游戏");
        mData.add(map);

        map = new HashMap<String, Object>();
        map.put("typeId", "516");
        map.put("typeName", "健康");
        mData.add(map);


        map = new HashMap<String, Object>();
        map.put("typeId", "519");
        map.put("typeName", "体育");
        mData.add(map);

        map = new HashMap<String, Object>();
        map.put("typeId", "520");
        map.put("typeName", "娱乐");
        mData.add(map);

        map = new HashMap<String, Object>();
        map.put("typeId", "525");
        map.put("typeName", "热点");
        mData.add(map);

    }

}


