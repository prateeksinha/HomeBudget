package com.crackers.homebudget.Activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crackers.homebudget.Adapters.IncomeListAdapter;
import com.crackers.homebudget.Database.DBHelper;
import com.crackers.homebudget.R;
import com.crackers.homebudget.Utilities.Income;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

public class IncomeList extends Activity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ArrayList<Income> incomeArrayList;

    DBHelper dbHelper = null;
    SQLiteDatabase sqLiteDatabase = null;
    ListView lvIncome;

    String position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_list);

        sharedPreferences = getSharedPreferences("prefname", MODE_APPEND);
        editor = sharedPreferences.edit();
        incomeArrayList = new ArrayList<Income>();
        dbHelper = new DBHelper(getApplicationContext());

        lvIncome = (ListView) findViewById(R.id.lvIncome);

        getDataFromDB();

        IncomeListAdapter adapter = new IncomeListAdapter(IncomeList.this, incomeArrayList);
        lvIncome.setAdapter(adapter);

        eventHandling();
    }

    private void eventHandling() {
        registerForContextMenu(lvIncome);
    }


    private void getDataFromDB() {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from BudgetIncomeTBL", null);
        if (cursor.moveToFirst()) {
            do {
                Income i = new Income();
                i.setCategory( cursor.getString(cursor.getColumnIndex("Category")));
                i.setAmount(cursor.getString(cursor.getColumnIndex("Amount")));
                i.setDate(cursor.getString(cursor.getColumnIndex("Date")));
                incomeArrayList.add(i);
            }
            while (cursor.moveToNext());
        }
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Menu");
        menu.add(0, 1, 0, "Delete");
        menu.add(0, 2, 1, "Update");
    }

    public boolean onContextItemSelected(MenuItem item) {

        if(item.getItemId()==1){
            AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

            final String Category=incomeArrayList.get(info.position).getCategory();
            final String Date=incomeArrayList.get(info.position).getDate();
            final String Amount=incomeArrayList.get(info.position).getAmount();
                    long id=dbHelper.deleteRec(Date,Amount,Category);
                    if (id!=-1){
                        Intent in=new Intent(getApplicationContext(),IncomeList.class);
                        startActivity(in);
                        finish();
                    }
        }
        else if(item.getItemId()==2){
            AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

            String position=incomeArrayList.get(info.position).toString();
            String Date=incomeArrayList.get(info.position).getDate();
            String Category=incomeArrayList.get(info.position).getCategory();
            String Amount=incomeArrayList.get(info.position).getAmount();


            editor.putString("Date",Date);
            editor.putString("Category",Category);
            editor.putString("Amount",Amount);
            editor.putString("position",position);
            editor.commit();

            Intent i=new Intent(IncomeList.this,UpdateIncome.class);
            startActivity(i);
            finish();
        }
        return false;
    }
}