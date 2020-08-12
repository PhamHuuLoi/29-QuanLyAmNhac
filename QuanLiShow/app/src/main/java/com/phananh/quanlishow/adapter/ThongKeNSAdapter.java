package com.phananh.quanlishow.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phananh.quanlishow.R;
import com.phananh.quanlishow.model.ThongKeNS;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ThongKeNSAdapter extends BaseAdapter {
    Context context;
    ArrayList<ThongKeNS> list;
    ArrayList<ThongKeNS> listSort;
    Filter filter;

    public ThongKeNSAdapter(Context context, ArrayList<ThongKeNS> list) {
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
        final ThongKeNS tk = list.get(i);
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.one_tkcs, null);
            holder.cot1 = view.findViewById(R.id.tvten1);
            holder.cot2 = view.findViewById(R.id.tvTen2);
            holder.cot3 = view.findViewById(R.id.tvCot1);
            holder.cot4 = view.findViewById(R.id.tvCot2);
            holder.tvTen3 = view.findViewById(R.id.tvTen3);
            holder.line = view.findViewById(R.id.view);
            holder.linearLayout = view.findViewById(R.id.ln1);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.line.setVisibility(View.VISIBLE);
        holder.linearLayout.setVisibility(View.VISIBLE);
        holder.cot3.setText("Tên nhạc sĩ: ");
        holder.cot4.setText("Tên bài hát: ");
        holder.cot1.setText(tk.getTenNhacSi());
        holder.cot2.setText(tk.getTenBH());
        int tong = 1;
        String text[] = tk.getTenBH().split("");
        for(int z=0;z<text.length;z++){
            if(text[z].contains(",")){
                tong++;
            }
        }
        holder.tvTen3.setText(tong+"");
        return view;
    }

    class ViewHolder {
        TextView cot1, cot2, cot3, cot4, tvCot3, tvTen3;
        View line;
        LinearLayout linearLayout;

    }

    public void resetData() {
        list = listSort;
    }

    public Filter getFilter() {
        if (filter == null)
            filter = new ThongKeNSAdapter.CustomFilter();
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
                ArrayList<ThongKeNS> lsSach = new ArrayList<>();
                for (ThongKeNS p : list) {
                    if (p.getTenNhacSi().toUpperCase().contains((constraint.toString().toUpperCase())) ||
                            p.getTenBH().toUpperCase().contains(constraint.toString().toUpperCase())) {
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
                list = (ArrayList<ThongKeNS>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}
