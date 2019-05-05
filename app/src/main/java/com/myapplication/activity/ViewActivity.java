package com.myapplication.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.myapplication.R;
import com.myapplication.model.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewActivity extends AppCompatActivity {

    private ArrayList<User> arrayList;
    @BindView(R.id.bar_chart)
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        arrayList = (ArrayList<User>)getIntent().getSerializableExtra("arrayList");

        initGraph();

    }


    void initGraph(){

        //set y values
        ArrayList<BarEntry> yvalues = new ArrayList<BarEntry>();
        //set x values
        ArrayList<String> xVals = new ArrayList<String>();
        int valLength = arrayList.size();
        if(arrayList.size()>10)
            valLength = 10;
        for(int i=0;i<valLength;i++){
            xVals.add(arrayList.get(i).getName().split(" ")[0]);
            yvalues.add(new BarEntry(i,(float)arrayList.get(i).getSalary()));
        }





        //X-axis
        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(4f);
        xAxis.setLabelCount(10);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));


        //Y-axis
        barChart.getAxisRight().setEnabled(false);
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(25f);
        leftAxis.setAxisMinimum(0f);




        BarDataSet dataSet = new BarDataSet(yvalues,"");
        BarData data = new BarData(dataSet);

        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        data.setValueTextColor(Color.WHITE);

        barChart.setData(data);
        barChart.getLegend().setEnabled(false);
        barChart.getDescription().setText("");
        barChart.notifyDataSetChanged();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
