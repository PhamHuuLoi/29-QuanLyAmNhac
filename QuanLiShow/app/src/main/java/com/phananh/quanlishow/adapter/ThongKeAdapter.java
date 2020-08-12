package com.phananh.quanlishow.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.phananh.quanlishow.R;
import com.phananh.quanlishow.model.ThongKe;
import com.phananh.quanlishow.model.ThongKeCS;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class ThongKeAdapter extends BaseAdapter {
    Context context;
    ArrayList<ThongKe> list;
    ArrayList<ThongKe> listSort;
    Filter filter;
    DecimalFormat fm = new DecimalFormat("#,###");

    public ThongKeAdapter(Context context, ArrayList<ThongKe> list) {
        this.context = context;
        this.list = list;
        this.listSort = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        final ThongKe tk = list.get(i);
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.one_tkcs, null);
            holder.cot1 = view.findViewById(R.id.tvten1);
            holder.cot2 = view.findViewById(R.id.tvTen2);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.cot1.setText(tk.getTen1());
        holder.cot2.setText(tk.getTen2());
        return view;
    }

    class ViewHolder {
        TextView cot1, cot2, cot3, cot4;
    }

    public void resetData() {
        list = listSort;
    }

    public Filter getFilter() {
        if (filter == null)
            filter = new ThongKeAdapter.CustomFilter();
        return filter;
    }

    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
// We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                results.values = listSort;
                results.count = listSort.size();
            } else {
                ArrayList<ThongKe> lsSach = new ArrayList<>();
                for (ThongKe p : list) {
                    if (p.getTen1().toUpperCase().contains((constraint.toString().toUpperCase())) ||
                            p.getTen2().toUpperCase().contains(constraint.toString().toUpperCase())) {
                        lsSach.add(p);
                    }
                }
                results.values = lsSach;
                results.count = lsSach.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                list = (ArrayList<ThongKe>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}
