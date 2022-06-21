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

public class RegisterFragment extends Fragment {

    private RegisterViewModel mViewModel;
    private Button registerBtn;
    private EditText userAccount,userPassword,userconfirmPassword;
    private String nickname,password,confirmPassword;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        // TODO: Use the ViewModel
        registerBtn = getActivity().findViewById(R.id.register_click);
        userAccount = getActivity().findViewById(R.id.register_userAccount);
        userPassword = getActivity().findViewById(R.id.register_pwd);
        userconfirmPassword = getActivity().findViewById(R.id.confirm_pwd);
        final NavController navController = Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickname = userAccount.getText().toString();
                password = userPassword.getText().toString();
                confirmPassword = userconfirmPassword.getText().toString();
                if (!password.equals(confirmPassword)){
                    Toast.makeText(getActivity(),"两次密码不一致，请重新输入",Toast.LENGTH_LONG).show();
                    userPassword.setText("");
                    userconfirmPassword.setText("");
                }else{
                    String url = new MyApplication().UserInsertUrl;
                    RequestParams params = new RequestParams(url);
                    params.addBodyParameter("nickname",nickname);
                    params.addBodyParameter("password",password);
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Log.i("huhao","register result:"+result);
                            Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
                            navController.navigate(R.id.nav_login);
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            Log.i("huhao","register error:"+ex.toString());
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });

                }
            }
        });
    }

}
