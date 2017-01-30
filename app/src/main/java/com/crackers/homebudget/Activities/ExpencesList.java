package com.crackers.homebudget.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crackers.homebudget.Adapters.ExpencesListAdapter;
import com.crackers.homebudget.Database.DBHelper;
import com.crackers.homebudget.R;
import com.crackers.homebudget.Utilities.Expences;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Admin on 10-08-2016.
 */
public class ExpencesList extends Activity{

    ArrayList<Expences> expencesArrayList;
    ListView lvExpences;
    DBHelper dbHelper=null;
    SQLiteDatabase sqLiteDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expences_list);

        expencesArrayList=new ArrayList<Expences>();
        dbHelper=new DBHelper(getApplicationContext());
        lvExpences= (ListView) findViewById(R.id.lvExpences);

        getDataFromDB();

        ExpencesListAdapter adapter=new ExpencesListAdapter(ExpencesList.this,expencesArrayList);
        lvExpences.setAdapter(adapter);

        eventHandling();
    }

    private void eventHandling() {
        registerForContextMenu(lvExpences);
    }


    private void getDataFromDB() {

        sqLiteDataBase=dbHelper.getReadableDatabase();
        Cursor  cursor=sqLiteDataBase.rawQuery("Select * from BudgetExpensesTBL",null);
        if(cursor.moveToFirst()){
            do{
                Expences e=new Expences();
                e.setCategory(cursor.getString(cursor.getColumnIndex("Category")));
                e.setAmount(cursor.getString(cursor.getColumnIndex("Amount")));
                e.setDate(cursor.getString(cursor.getColumnIndex("Date")));
                expencesArrayList.add(e);
            }
            while (cursor.moveToNext());
        }
    }
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);
        menu.setHeaderTitle("Menu");
        menu.add(0,1,0,"Delete");
        menu.add(0,2,1,"Update");
    }
    public boolean onContextItemSelected(MenuItem item) {

        if(item.getItemId()==1){
            AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
            final String Category=expencesArrayList.get(info.position).getCategory();
            final String Date=expencesArrayList.get(info.position).getDate();
            final String Amount=expencesArrayList.get(info.position).getAmount();
            long id=dbHelper.deleteRecord(Date,Amount,Category);
            if (id!=-1){
                Intent in=new Intent(getApplicationContext(),ExpencesList.class);
                startActivity(in);
                finish();
            }
        }
        return false;
    }
}
