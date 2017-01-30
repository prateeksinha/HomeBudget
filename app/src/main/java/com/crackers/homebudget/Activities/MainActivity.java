package com.crackers.homebudget.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.lang.NumberFormatException;

import com.crackers.homebudget.Database.DBHelper;
import com.crackers.homebudget.R;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {

    int sum;
    TextView tvTotalBalance,tvAddIncome,tvAddExpences,tvHelp,tvTotalBalanceShow,tvIncomeShow,tvExpencesshow,tvShowIncome,tvShowExpences;
    SharedPreferences sharedPreferences;
    DBHelper dbHelper=null;
    SQLiteDatabase sqLiteDatabase=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper=new DBHelper(getApplicationContext());
        sharedPreferences=getSharedPreferences("prefName",MODE_APPEND);
        initializeContentView();
        eventHandling();
        frontShow();
    }

    private void frontShow() {

    }


    private void eventHandling() {

        tvTotalBalance.setOnClickListener(this);
        tvAddIncome.setOnClickListener(this);
        tvAddExpences.setOnClickListener(this);
        tvShowIncome.setOnClickListener(this);
        tvShowExpences.setOnClickListener(this);
        tvHelp.setOnClickListener(this);
    }

    private void initializeContentView() {
        tvTotalBalance      = (TextView) findViewById(R.id.tvTotalBalance);
        tvAddIncome         = (TextView) findViewById(R.id.tvAddIncome);
        tvAddExpences       = (TextView) findViewById(R.id.tvAddExpences);
        tvShowIncome        = (TextView) findViewById(R.id.tvShowIncome);
        tvShowExpences      = (TextView) findViewById(R.id.tvShowExpences);
        tvHelp              = (TextView) findViewById(R.id.tvHelp);
        tvTotalBalanceShow  = (TextView) findViewById(R.id.tvTotalBalanceShow);
        tvIncomeShow        = (TextView) findViewById(R.id.tvIncomeShow);
        tvExpencesshow      = (TextView) findViewById(R.id.tvExpencesShow);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvTotalBalance:
                Intent i=new Intent(MainActivity.this,TotalBalance.class);
                startActivity(i);
                break;

            case R.id.tvAddIncome:
                i=new Intent(MainActivity.this,AddIncomeActivity.class);
                startActivity(i);
                break;

            case R.id.tvAddExpences:
                i=new Intent(MainActivity.this,AddExpences.class);
                startActivity(i);
                break;

            case R.id.tvShowIncome:
                i=new Intent(MainActivity.this, IncomeList.class);
                startActivity(i);
                break;
            case R.id.tvShowExpences:
                i=new Intent(MainActivity.this,ExpencesList.class);
                startActivity(i);
                break;
            case R.id.tvHelp:
                i=new Intent(MainActivity.this,HelpApp.class);
                startActivity(i);
                break;
        }
    }
}
