package com.example.readnews.ui.userinfo;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.example.readnews.MyApplication;
import com.example.readnews.R;
import com.example.readnews.ui.article.EditArticleFragment;
import com.linchaolong.android.imagepicker.ImagePicker;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.afollestad.materialdialogs.MaterialDialog;

public class UserInfoFragment extends Fragment {

    private UserInfoViewModel mViewModel;
    //picture
    private ImagePicker imagePicker = new ImagePicker();
    private ImageView userAvatar;
    // 定义线性布局
    private LinearLayout layout_avatar, layout_nickname, layout_sex, layout_birth, layout_signature;

    private TextView showNickName, showSex, showSignature;

    private Calendar calendar;

    String nickname,sex,sign,nicknameChanged;
    String fileurl;

    private List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    private List<HashMap<String, Object>> mData = new ArrayList<HashMap<String, Object>>();


    public static UserInfoFragment newInstance() {
        return new UserInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userinfo, container, false);
        nickname = MyApplication.nickname;
        layout_avatar = view.findViewById(R.id.lay_avatar);
        layout_nickname = view.findViewById(R.id.lay_nickname);
        layout_sex = view.findViewById(R.id.lay_sex);
        layout_signature = view.findViewById(R.id.lay_signature);

        userAvatar = view.findViewById(R.id.user_avatar);
        showNickName = view.findViewById(R.id.show_name);
        showSex = view.findViewById(R.id.show_sex);
        showSignature = view.findViewById(R.id.show_sign);
        initData();
        
        return view;
    }

    private void initData() {
        String url = new MyApplication().UserSelectUrl;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("nickname",nickname);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("huhao","user info:"+result);
                list = JSON.parseObject(result,
                        new TypeReference<List<HashMap<String, Object>>>() {
                        });
                mData.addAll(list);

                //showpicture
                Glide.with(getActivity()).load(new MyApplication().imagebaseurl+mData.get(0).get("avatar").toString()).placeholder(R.mipmap.ic_launcher).into(userAvatar);
                showNickName.setText(mData.get(0).get("nickname").toString());
                showSex.setText(mData.get(0).get("sex").toString());
                showSignature.setText(mData.get(0).get("sign").toString());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("huhao","user error"+ex.toString());
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
    public void onStart() {
        super.onStart();
        layout_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCameraOrGallery();
            }
        });
//        //修改昵称
//        layout_nickname.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new MaterialDialog.Builder(getActivity())
//                        .title("修改昵称")
//                        .inputRangeRes(2, 8, R.color.colorBlack)
//                        .inputType(InputType.TYPE_CLASS_TEXT)
//                        .input("请输入要修改的昵称", showNickName.getText(), new MaterialDialog.InputCallback() {
//                            @Override
//                            public void onInput(MaterialDialog dialog, CharSequence input) {
//                                // CharSequence的值是可读可写序列，而String的值是只读序列。
//                                //Toast.makeText(UserDetailActivity.this, input, Toast.LENGTH_SHORT).show();
//
//                                System.out.println(input.toString());
//                                // 重新设置值，当前活动被销毁时才保存到数据库
//                                showNickName.setText(input.toString());
//                            }
//                        })
//                        .positiveText("确定")
//                        .show();
//
//            }
//        });
        //修改性别
        layout_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] contentSex = new String[]{"男", "女"};
                new MaterialDialog.Builder(getActivity())
                        .title("修改性别")
                        .items(contentSex)
                        .itemsCallbackSingleChoice(showSex.getText().equals("女") ? 1 : 0, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                System.out.println("选择哪一个" + which);
                                System.out.println("选择的内容是" + text);
                                showSex.setText(text.toString());
                                return true;
                            }
                        })
                        .show();
            }
        });
        //修改个性签名
        layout_signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(getActivity())
                        .title("修改个性签名")
                        .inputRangeRes(1, 38, R.color.colorBlack)
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input("请输入要修改的个性签名", showSignature.getText(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {

                                System.out.println(input.toString());
//                                userInfo.setUserSignature(input.toString());
                                showSignature.setText(input.toString());
                            }
                        })
                        .positiveText("确定")
                        .show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("huhao","onDestroy");

        nicknameChanged = showNickName.getText().toString();
        sex = showSex.getText().toString();
        sign = showSignature.getText().toString();
        String url = new MyApplication().UserUpdateUrl;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("nickname",nickname);
        params.addBodyParameter("sex",sex);
        params.addBodyParameter("sign",sign);
        if (fileurl!=null){
            params.addBodyParameter("avatar",new File(fileurl));
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("huhao","user update"+result);
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
                                Glide.with(getActivity()).load(new File(imageUri.getPath())).into(userAvatar);
                                fileurl = imageUri.getPath();
                            }
                        };
                        if (which == 0) {
                            // 从相册中选取图片
                            imagePicker.startGallery(UserInfoFragment.this, callback);
                        } else {
                            // 拍照
                            imagePicker.startCamera(UserInfoFragment.this, callback);
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
        mViewModel = ViewModelProviders.of(this).get(UserInfoViewModel.class);
        // TODO: Use the ViewModel
    }

}
