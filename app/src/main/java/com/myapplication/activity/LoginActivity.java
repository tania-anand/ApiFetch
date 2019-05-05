package com.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.myapplication.R;
import com.myapplication.interfaces.LoginContract;
import com.myapplication.presenter.LoginPresenter;
import com.myapplication.utils.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginContract.viewer {

    @BindView(R.id.ed_password)
    EditText  edPassword;

    @BindView(R.id.ed_username)
    EditText edUsername;

    @BindView(R.id.btn_login)
    Button btnLogin;

    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SharedPreferencesUtil.getInstance(getApplicationContext()).getIsLoggedIn()){
            Intent intent = new Intent(this,ViewUsers.class);
            startActivity(intent);
            finish();

        }else {
            ButterKnife.bind(this);
            initView();
            loginPresenter = new LoginPresenter(this);
        }
    }

    private void initView(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onValidation())
                    loginPresenter.onClick(edUsername.getText().toString(),edPassword.getText().toString());

            }
        });
    }

    boolean onValidation(){
        boolean flag = true;

        if(edUsername.getText().toString().isEmpty()){
            flag = false;
            edUsername.setError("Username cannot be empty");
        }

        if(edPassword.getText().toString().isEmpty()){
            flag = false;
            edPassword.setError("Password cannot be empty");
        }

        return flag;
    }


    @Override
    public void afterValidation() {
        // as it is success jump to next page
        SharedPreferencesUtil.getInstance(getApplicationContext()).setIsLoggedIn(true);
        Intent intent = new Intent(this,ViewUsers.class);
        startActivity(intent);
        finish();


    }

}
