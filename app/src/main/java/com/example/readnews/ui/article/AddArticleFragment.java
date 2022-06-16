package com.example.readnews.ui.article;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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

import com.example.readnews.MyApplication;
import com.example.readnews.R;
import com.bumptech.glide.Glide;
import com.linchaolong.android.imagepicker.ImagePicker;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddArticleFragment extends Fragment {

    private AddArticleViewModel mViewModel;
    //文本框和按钮
    private EditText editTitle, editContent;
    private Button publishBtn;
    //picture
    private ImageView editImageView;
    private ImagePicker imagePicker = new ImagePicker();
    String fileurl;

    public static AddArticleFragment newInstance() {
        return new AddArticleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_article, container, false);
        editImageView = view.findViewById(R.id.add_picture);
        editTitle = view.findViewById(R.id.add_title);
        editContent = view.findViewById(R.id.add_content);
        publishBtn = view.findViewById(R.id.add_publish);

        editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCameraOrGallery();
            }
        });

        publishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Title = editTitle.getText().toString();
                final String Content = editContent.getText().toString();
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                final String Time = sdf.format(calendar.getTime());
                String url = new MyApplication().inserturl;
                //String url="http://172.16.26.242:8080/androidweb/InsertServlet";
                RequestParams params = new RequestParams(url);
                //post
                params.setMultipart(true);
                params.addBodyParameter("articleTitle", Title);
                params.addBodyParameter("articleContent", Content);
                params.addBodyParameter("articleTime", Time);
                params.addBodyParameter("uploadedfile", new File(fileurl));
                final ProgressDialog dia = new ProgressDialog(getActivity());
                dia.setMessage("上传中....");
                dia.show();
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
                        //加载成功回调，返回获取到的数据
                        Log.i("huhao", "onSuccess: " + result);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Toast.makeText(x.app(), ex.toString(), Toast.LENGTH_LONG).show();
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
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
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
                                Glide.with(getActivity()).load(new File(imageUri.getPath())).into(editImageView);
                                fileurl = imageUri.getPath();
                            }
                        };
                        if (which == 0) {
                            // 从相册中选取图片
                            imagePicker.startGallery(AddArticleFragment.this, callback);
                        } else {
                            // 拍照
                            imagePicker.startCamera(AddArticleFragment.this, callback);
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
        mViewModel = new ViewModelProvider(this).get(AddArticleViewModel.class);
        // TODO: Use the ViewModel
    }

}