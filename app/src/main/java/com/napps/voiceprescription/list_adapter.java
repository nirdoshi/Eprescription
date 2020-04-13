package com.napps.voiceprescription;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class list_adapter extends ArrayAdapter<Patientinfo>{
    private Context context;
    ArrayList<Patientinfo> info,tempCustomer,suggestions;


    public list_adapter(Context context, ArrayList<Patientinfo> info) {
        super(context, R.layout.cutomlist, info);
        this.context = context;
        this.info = info;
        this.tempCustomer=new ArrayList<Patientinfo>(info);
        this.suggestions=new ArrayList<Patientinfo>(info);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.cutomlist, parent, false);
        TextView tvname = convertView.findViewById(R.id.tv_Cname);
        TextView tvphone = convertView.findViewById(R.id.tv_Cphone);

        tvname.setText(info.get(position).getName());
        tvphone.setText(info.get(position).getPhone());


        return convertView;
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }
    Filter myFilter =new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            Patientinfo patientinfo =(Patientinfo) resultValue ;
            return patientinfo.getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Patientinfo cust : tempCustomer) {
                    if (cust.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(cust);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Patientinfo> c =  (ArrayList<Patientinfo>)results.values ;
            if (results != null && results.count > 0) {
                clear();
                for (Patientinfo cust : c) {
                    add(cust);
                    notifyDataSetChanged();
                }
            }
            else{
                clear();
                notifyDataSetChanged();
            }
        }
    };



}