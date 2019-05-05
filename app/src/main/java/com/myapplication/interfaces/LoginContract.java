package com.myapplication.interfaces;

public interface LoginContract {

    interface viewer{
        void afterValidation();

    }
    interface presenter{
        void onClick(String userName ,String password);
    }


}
