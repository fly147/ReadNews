package com.example.readnews.ui.article;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.example.readnews.MyApplication;
import com.example.readnews.R;
import com.linchaolong.android.imagepicker.ImagePicker;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class EditArticleFragment extends Fragment {

    private EditArticleViewModel mViewModel;
    //picture
    private ImagePicker imagePicker = new ImagePicker();
    ImageView picture;
    //save
    private EditText editTitle, editContent;
    private Button publishBtn;
    //http
    String id;
    private List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    private List<HashMap<String, Object>> mData = new ArrayList<HashMap<String, Object>>();
    String deletefileurl;
    String fileurl;

    public static EditArticleFragment newInstance() {
        return new EditArticleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_article, container, false);
        id = getArguments().getString("id");
        // picture功能
        picture = (ImageView) view.findViewById(R.id.edit_picture);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCameraOrGallery();
            }
        });
        editTitle = view.findViewById(R.id.edit_title);
        editContent = view.findViewById(R.id.edit_content);
        publishBtn = view.findViewById(R.id.edit_publish);
        showdata();
        publishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  String txteditTitle = editTitle.getText().toString();
                final String txteditContent = editContent.getText().toString();
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                final String txtEditTime = sdf.format(calendar.getTime());
                String url=new MyApplication().updatebyidurl;
                //String url="http://172.16.26.242:8080/androidweb/UpdateServlet";
                RequestParams params = new RequestParams(url);
                //post
                params.setMultipart(true);
                params.addBodyParameter("id", id);
                params.addBodyParameter("articleTitle", txteditTitle);
                params.addBodyParameter("articleContent", txteditContent);
                params.addBodyParameter("articleTime", txtEditTime);
                params.addBodyParameter("picture", new File(fileurl));
                Log.i("upfile",fileurl);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
                        Log.i("update:",result);
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

                NavController navController= Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
                navController.navigate(R.id.nav_article);

            }
        });
        return view;
    }

    //picture
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    private void showdata() {
        String url=new MyApplication().selectbyidurl;
        //String url = "http://172.16.26.242:8080/androidweb/SelectServlet";
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
                //showpicture
                Glide.with(getActivity()).load(new MyApplication().imagebaseurl+mData.get(0).get("articleImagePath").toString()).placeholder(R.mipmap.ic_launcher).into(picture);
                editTitle.setText(mData.get(0).get("articleTitle").toString());
                editContent.setText(mData.get(0).get("articleContent").toString());
                deletefileurl=new MyApplication().imagebaseurl+mData.get(0).get("articleImagePath").toString();
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


    private void startCameraOrGallery() {
        new AlertDialog.Builder(getActivity()).setTitle("设置图片")
                .setItems(new String[]{"从相册中选取图片", "拍照"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 回调
                        ImagePicker.Callback callback = new ImagePicker.Callback() {
                            @Override
                            public void onPickImage(Uri imageUri) {
                            }

                            @Override
                            public void onCropImage(Uri imageUri) {
                                //picture.setImageURI(imageUri);
                                Glide.with(getActivity()).load(new File(imageUri.getPath())).into(picture);
                                fileurl = imageUri.getPath();
                            }
                        };
                        if (which == 0) {
                            // 从相册中选取图片
                            imagePicker.startGallery(EditArticleFragment.this, callback);
                        } else {
                            // 拍照
                            imagePicker.startCamera(EditArticleFragment.this, callback);
                        }
                    }
                })
                .show()
                .getWindow()
                .setGravity(Gravity.BOTTOM);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EditArticleViewModel.class);
        // TODO: Use the ViewModel
    }

}