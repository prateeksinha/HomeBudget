package com.crackers.homebudget.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.ULocale;

/**
 * Created by Prateek on 31-07-2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public final static String DBName="BudgetDB";
    public final static String TBLName="BudgetIncomeTBL";
    public final static String ETBLName="BudgetExpensesTBL";
    SQLiteDatabase db=null;
    String Date,Amount,Category;
    public DBHelper(Context context){
        super(context,DBName ,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Create table BudgetIncomeTBL(" +
                "Category  Text," +
                "Amount  Text," +
                "Date Text)");
        sqLiteDatabase.execSQL("Create table BudgetExpensesTBL("+
        "Category Text,"+
        "Amount Text,"+
        "Date Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }

    public long insertIncomeRec(String Category, String Amount, String Date){
        db= this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("Amount",Amount);
        cv.put("Category",Category);
        cv.put("Date",Date);
        long id=db.insert(TBLName,null,cv);
        return id;
    }
    public long insertExpencesRec(String Category, String Amount, String Date){
        db= this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("Amount",Amount);
        cv.put("Category",Category);
        cv.put("Date",Date);

        long id=db.insert(ETBLName,null,cv);
        return id;
    }
    public long deleteRec(String Date,String Amount,String Category){
        db =this.getWritableDatabase();
        long id=db.delete(TBLName,"Date=? AND Amount=? AND Category=?",new String[]{Date,Amount,Category});
        return id;
    }
    public long updateRec(String Date,String Amount,String Category){

        db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put("Date",Date);
        cv.put("Amount",Amount);
        cv.put("Category",Category);
        long id=db.update(TBLName,cv,"Date=? AND Amount=? AND Category=?",new String[]{Date,Amount,Category});
        return id;
    }

    public long deleteRecord(String Date,String Amount,String Category){
        db =this.getWritableDatabase();
        long id=db.delete(ETBLName,"Date=? AND Amount=? AND Category=?",new String[]{Date,Amount,Category});
        return id;
    }

    public String getDate(){return Date;}
    public String getAmount(){
        return Amount;
    }
    public String getCategory(){
        return Category;
    }
}
