package com.myapplication.activity;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.myapplication.R;
import com.myapplication.adapter.ViewUsersAdapter;
import com.myapplication.interfaces.ViewUsersContract;
import com.myapplication.model.User;
import com.myapplication.presenter.ViewUsersPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewUsers extends AppCompatActivity implements ViewUsersContract.viewer {

    @BindView(R.id.rv_view)
    RecyclerView recyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.view_graphs)
    Button fab;

    ViewUsersAdapter adapter;
    ViewUsersPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);
        ButterKnife.bind(this);

        initView();

        mPresenter = new ViewUsersPresenter(this,this);
        mPresenter.callApi();


    }


    void initView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar.setVisibility(View.VISIBLE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem search= menu.findItem( R.id.search);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(search);

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener(){
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {

                        if(adapter!=null)
                            adapter.filter(newText);
                        return false;
                    }
                }
        );
        return true;
    }


    @Override
    public void setAdapter(final ArrayList<User> arrayList) {
        progressBar.setVisibility(View.GONE);
        adapter = new ViewUsersAdapter(arrayList,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewUsers.this,ViewActivity.class);
                i.putExtra("arrayList",arrayList);
                startActivity(i);
            }
        });
    }

    @Override
    public void onFailure() {
        progressBar.setVisibility(View.GONE);

    }
}
