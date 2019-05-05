package com.myapplication.interfaces;

import com.myapplication.model.User;

import java.util.ArrayList;

public interface ViewUsersContract {


    interface viewer{
        void setAdapter(ArrayList<User> arrayList);
        void onFailure();

    }

    interface presenter{

    }
}
