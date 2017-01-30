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
 * Created by Prateek on 24-07-2016.
 */
public class AddExpences extends Activity implements View.OnClickListener {

    DBHelper dbHelper=null;
    EditText etExpencesAmount,etExpencesCategory;
    TextView tvDate,tvSave,tvCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expences_add);
        initializeContent();

        String date= new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        tvDate.setText(date);

        eventHandling();
    }

    private void eventHandling() {
        tvSave.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    private void initializeContent() {

        dbHelper=new DBHelper(getApplicationContext());

        etExpencesAmount      = (EditText) findViewById(R.id.etExpencesAmount);
        etExpencesCategory    = (EditText) findViewById(R.id.etExpencesCategory);

        tvDate      = (TextView) findViewById(R.id.tvDate);
        tvSave      = (TextView) findViewById(R.id.tvSave);
        tvCancel    = (TextView) findViewById(R.id.tvCancel);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.tvSave:

                String Amount=etExpencesAmount.getText().toString();
                String Category=etExpencesCategory.getText().toString();
                String Date=tvDate.getText().toString();

                long id=dbHelper.insertExpencesRec(Category,Amount,Date);
                if(id!=-1){
                    Toast.makeText(getApplicationContext(),"Expences Added Successfully",Toast.LENGTH_SHORT).show();
                    etExpencesCategory.getText().clear();
                    etExpencesAmount.getText().clear();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Data not Inserted",Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.tvCancel:
                finish();
                break;
        }
    }
}
