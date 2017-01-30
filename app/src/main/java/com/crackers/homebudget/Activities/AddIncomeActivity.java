package com.crackers.homebudget.Activities;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crackers.homebudget.Database.DBHelper;
import com.crackers.homebudget.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Prateek on 31-07-2016.
 */
public class AddIncomeActivity extends Activity implements View.OnClickListener {

    DBHelper dbHelper=null;
    EditText etAddIncomeCategory,etAddIncomeAmount;
    TextView tvDate,tvSave,tvCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_income);
        initializeContentView();

        String d=new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        tvDate.setText(d);

        eventHandling();
    }

    private void eventHandling() {

        tvSave.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    private void initializeContentView() {
        dbHelper = new DBHelper(getApplicationContext());
        etAddIncomeAmount   = (EditText) findViewById(R.id.etAddIncomeAmount);
        etAddIncomeCategory = (EditText) findViewById(R.id.etAddIncomeCategory);

        tvDate      = (TextView) findViewById(R.id.tvDate);
        tvSave      = (TextView) findViewById(R.id.tvSave);
        tvCancel    = (TextView) findViewById(R.id.tvCancel);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tvSave:

                String Amount=etAddIncomeAmount.getText().toString();
                String Category=etAddIncomeCategory.getText().toString();
                String Date=tvDate.getText().toString();
                long id=dbHelper.insertIncomeRec(Category,Amount,Date);
                if(id!=-1){
                    Toast.makeText(getApplicationContext(),"Income Added Successfully",Toast.LENGTH_SHORT).show();
                    etAddIncomeCategory.getText().clear();
                    etAddIncomeAmount.getText().clear();
                }
                else{
                    Toast.makeText(getApplicationContext(),"unable to save data",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tvCancel:
                finish();
                break;
        }
    }
}
