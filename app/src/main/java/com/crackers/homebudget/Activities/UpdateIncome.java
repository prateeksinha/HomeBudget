package com.crackers.homebudget.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crackers.homebudget.Database.DBHelper;
import com.crackers.homebudget.R;

/**
 * Created by Admin on 19-09-2016.
 */
public class UpdateIncome extends Activity implements View.OnClickListener {
    EditText etIncomeCategory,etIncomeAmount;
    TextView tvUpdateIncome,tvCancelUpdate,tvIncomeDate;
    ImageView ivDatePicker;
    DBHelper dbHelper=null;
    Calendar calendar;
    SharedPreferences sharedPreferences;
    SQLiteDatabase sqL=null;
   // ArrayList<String> arrayList;
    ArrayAdapter<String> incomeArrayList;
    int years,month,day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_income);

        incomeArrayList=new ArrayAdapter<>(UpdateIncome.this,R.layout.income_list);

        etIncomeCategory= (EditText) findViewById(R.id.etIncomeCategory);
        etIncomeAmount= (EditText) findViewById(R.id.etIncomeAmount);

        ivDatePicker= (ImageView) findViewById(R.id.ivDatePicker);

        tvIncomeDate= (TextView) findViewById(R.id.tvIncomeDate);
        tvUpdateIncome= (TextView) findViewById(R.id.tvUpdateIncome);
        tvCancelUpdate= (TextView) findViewById(R.id.tvCancelUpdate);

        sharedPreferences=getSharedPreferences("prefname",MODE_APPEND);
        String category=sharedPreferences.getString("Category",null);
        String amount=sharedPreferences.getString("Amount",null);
        String date=sharedPreferences.getString("Date",null);
        String Position=sharedPreferences.getString("position",null);

        dbHelper=new DBHelper(getApplicationContext());
        eventHandling();
    }

    private void eventHandling() {
        tvUpdateIncome.setOnClickListener(this);
        tvCancelUpdate.setOnClickListener(this);
        ivDatePicker.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.tvUpdateIncome:
                    String Category=etIncomeCategory.getText().toString();
                    String Amount=etIncomeAmount.getText().toString();
                    String Date=tvIncomeDate.getText().toString();

                    long id=dbHelper.updateRec(Date,Amount,Category);
                try {
                    if (id != -1) {
                        Intent i = new Intent(getApplication(), UpdateIncome.class);
                        startActivity(i);

                        Toast.makeText(getApplicationContext(), "Record Successfully updated", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                }catch(Exception e){

                }
                break;
            case R.id.ivDatePicker:
                DatePickerDialog dpd=new DatePickerDialog(UpdateIncome.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        years=year;
                        month=monthOfYear+1;
                        day=dayOfMonth;
                        tvIncomeDate.setText(years+"-"+month+"-"+day);
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                dpd.show();
                break;
        }
    }
}
