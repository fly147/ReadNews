package com.example.readnews.ui.login;

import androidx.lifecycle.ViewModelProviders;

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
import android.widget.EditText;
import android.widget.Toast;

import com.example.readnews.MyApplication;
import com.example.readnews.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;
    private Button loginButton,registerButton;
    private EditText userAccount,userPassword;
    private String nickname,password;
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        // TODO: Use the ViewModel
        loginButton = getActivity().findViewById(R.id.login_on);
        registerButton =getActivity().findViewById(R.id.login_register);
        userAccount = getActivity().findViewById(R.id.login_userAccount);
        userPassword = getActivity().findViewById(R.id.login_pwd);
        final NavController navController = Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickname = userAccount.getText().toString();
                password = userPassword.getText().toString();
                String url = new MyApplication().UserLoginUrl;
                RequestParams params = new RequestParams(url);
                params.addBodyParameter("nickname",nickname);
                params.addBodyParameter("password",password);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
                        if(result.equals("登录成功")){
                            MyApplication.nickname = nickname;
                            navController.navigate(R.id.nav_viewpager);
                        }

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.i("huhao","Login Error"+ex.toString());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });

            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_register);
            }
        });
    }

}
