package com.crackers.homebudget.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crackers.homebudget.Activities.ExpencesList;
import com.crackers.homebudget.R;
import com.crackers.homebudget.Utilities.Expences;

import java.util.ArrayList;

/**
 * Created by Admin on 10-08-2016.
 */
public class ExpencesListAdapter extends BaseAdapter {

    Context mCtx;
    ArrayList<Expences> expencesArrayList;

    public ExpencesListAdapter(ExpencesList context, ArrayList<Expences> expencesArrayList) {
        this.mCtx=context;
        this.expencesArrayList=expencesArrayList;
    }

    @Override
    public int getCount() {
        return expencesArrayList.size();
    }

    //here i is position of array list
    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Expences e=expencesArrayList.get(i);
        view= LayoutInflater.from(mCtx).inflate(R.layout.row_list_expences,null);

        TextView tvCategory= (TextView) view.findViewById(R.id.tvCategory);
        TextView tvAmount= (TextView) view.findViewById(R.id.tvAmount);
        TextView tvDate= (TextView) view.findViewById(R.id.tvDate);

        tvCategory.setText("Category::"+e.getCategory());
        tvAmount.setText("Amount::"+e.getAmount());
        tvDate.setText("Date::"+e.getDate());
        return view;
    }
}
