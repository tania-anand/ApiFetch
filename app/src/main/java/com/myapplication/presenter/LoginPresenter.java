package com.myapplication.presenter;

import com.myapplication.interfaces.LoginContract;
import com.myapplication.utils.Constants;

public class LoginPresenter implements LoginContract.presenter {

    LoginContract.viewer mViewer;


    public LoginPresenter(LoginContract.viewer mViewer) {
        this.mViewer = mViewer;
    }

    @Override
    public void onClick(String userName ,String password) {

        if(password.equals(Constants.password) && userName.equals(Constants.userName)){
            mViewer.afterValidation();
        }

    }
}
