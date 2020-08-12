package com.phananh.quanlishow.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.phananh.quanlishow.R;
import com.phananh.quanlishow.model.BaiHat;

import java.util.ArrayList;
import java.util.Iterator;

public class BaiHatAdapter extends BaseAdapter {
    Context context;
    Filter filter;
    ArrayList<BaiHat> list;
    ArrayList<BaiHat> listSort;

    private class CustomFilter extends Filter {
        private CustomFilter() {
        }

        /* access modifiers changed from: protected */
        public FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = BaiHatAdapter.this.listSort;
                results.count = BaiHatAdapter.this.listSort.size();
            } else {
                ArrayList<BaiHat> lsSach = new ArrayList<>();
                Iterator it = BaiHatAdapter.this.list.iterator();
                while (it.hasNext()) {
                    BaiHat p = (BaiHat) it.next();
                    if (String.valueOf(p.getMaBH()).toUpperCase().startsWith(constraint.toString().toUpperCase()) ||
                            String.valueOf(p.getTenBH()).toUpperCase().startsWith(constraint.toString().toUpperCase()) ||
                            String.valueOf(p.getNamSangTac()).toUpperCase().startsWith(constraint.toString().toUpperCase())) {
                        lsSach.add(p);
                    }
                }
                results.values = lsSach;
                results.count = lsSach.size();
            }
            return results;
        }

        /* access modifiers changed from: protected */
        public void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0) {
                BaiHatAdapter.this.notifyDataSetInvalidated();
                return;
            }
            BaiHatAdapter.this.list = (ArrayList) results.values;
            BaiHatAdapter.this.notifyDataSetChanged();
        }
    }

    class ViewHolder {
        TextView cot1,cot2,cot3,cot4;

        ViewHolder() {
        }
    }

    public BaiHatAdapter(Context context2, ArrayList<BaiHat> list2) {
        this.context = context2;
        this.list = list2;
        this.listSort = list2;
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int i) {
        return this.list.get(i);
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        BaiHat baiHat = (BaiHat) this.list.get(i);
        if (view == null) {
            holder = new ViewHolder();
            view = ((Activity) this.context).getLayoutInflater().inflate(R.layout.one_info2, null);

            holder.cot1 = view.findViewById(R.id.tvTenCot1);
            holder.cot2 = view.findViewById(R.id.tvTenCot2);
            holder.cot3 = view.findViewById(R.id.tvTenCot3);
            holder.cot4 = view.findViewById(R.id.tvTenCot4);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.cot1.setText(baiHat.getMaBH()+"");
        holder.cot2.setText(baiHat.getTenBH());
        holder.cot3.setText(baiHat.getNamSangTac()+"");
        holder.cot4.setText(baiHat.getMaNS()+"");
        return view;
    }

    public void resetData() {
        this.list = this.listSort;
    }

    public Filter getFilter() {
        if (this.filter == null) {
            this.filter = new CustomFilter();
        }
        return this.filter;
    }
}
