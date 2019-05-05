package com.myapplication.networkutil;

import com.myapplication.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

public class ParseResponse {



   public ArrayList getUserList(JSONObject jsonObject){

       ArrayList<User> arrayList = new ArrayList <>();


           try {
               if(jsonObject.optJSONArray("data") != null) {
                   JSONArray jsonObject1 = jsonObject.getJSONArray("data");
                   for (int i = 0; i < jsonObject1.length(); i++) {

                       JSONArray array = jsonObject1.getJSONArray(i);
                       User user = new User();
                       user.setName(array.getString(0));
                       user.setDesignation(array.getString(1));
                       user.setLocation(array.getString(2));
                       user.setId(array.getInt(3));
                       user.setDate(array.getString(4));
                       user.setSalary((long)NumberFormat.getNumberInstance(Locale.US).parse(array.getString(5).substring(1)));

                       arrayList.add(user);
                   }
               }


           } catch (JSONException e) {
               e.printStackTrace();
           } catch (ParseException e) {
               e.printStackTrace();
           }


       return arrayList;
   }
}
