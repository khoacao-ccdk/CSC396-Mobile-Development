package com.depauw.repairshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.depauw.repairshop.database.RepairWithVehicle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListViewResult extends BaseAdapter {

    public static ListViewResult getInstance(List<RepairWithVehicle> result, Context context){
        if(myInstance == null){
            myInstance = new ListViewResult(context);
        }
        //Search keywords change frequently, result in different list each time
        //Give the List a new data each time the getInstance() method is called on
        //Prevent re-creating myInstance
        myInstance.result = result;
        return myInstance;
    }

    public ListViewResult(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return result.size();
    }

    @Override
    public Object getItem(int i) {
        return result.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_results_row, parent, false);
        }
        RepairWithVehicle record = result.get(i);
        TextView textViewMakeModel = convertView.findViewById(R.id.text_year_make_model);
        TextView textRepairDate = convertView.findViewById(R.id.text_repair_date);
        TextView textRepairCost = convertView.findViewById(R.id.text_repair_cost);
        TextView textRepairDescription = convertView.findViewById(R.id.text_repair_description);

        textViewMakeModel.setText(record.getVehicle().toString());
        try {
            Date dateFromString = new SimpleDateFormat("yyyy-mm-dd").parse(record.getRepair().getRepair_date());
            String displayDate = new SimpleDateFormat("mm/dd/yyyy").format(dateFromString);
            textRepairDate.setText(displayDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        textRepairCost.setText(String.valueOf(record.getRepair().getRepair_cost()));
        textRepairDescription.setText(record.getRepair().getRepair_description());
        return convertView;
    }

    private static ListViewResult myInstance;
    private List<RepairWithVehicle> result;
    Context context;
}
