package com.phananh.quanlishow.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.phananh.quanlishow.R;
import com.phananh.quanlishow.model.CaSi;
import com.phananh.quanlishow.model.Image;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;

public class CaSiAdapter extends BaseAdapter {
    Context context;
    Filter filter;
    ArrayList<CaSi> list;
    ArrayList<CaSi> listSort;

    private class CustomFilter extends Filter {
        private CustomFilter() {
        }

        /* access modifiers changed from: protected */
        public FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = CaSiAdapter.this.listSort;
                results.count = CaSiAdapter.this.listSort.size();
            } else {
                ArrayList<CaSi> lsSach = new ArrayList<>();
                Iterator it = CaSiAdapter.this.list.iterator();
                while (it.hasNext()) {
                    CaSi p = (CaSi) it.next();
                    if (String.valueOf(p.getMaCS()).toUpperCase().contains(constraint.toString().toUpperCase()) || p.getHoTenCS().toUpperCase().contains(constraint.toString().toUpperCase())) {
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
                CaSiAdapter.this.notifyDataSetInvalidated();
                return;
            }
            CaSiAdapter.this.list = (ArrayList) results.values;
            CaSiAdapter.this.notifyDataSetChanged();
        }
    }

    class ViewHolder {
        TextView cot1, cot2;
        ImageView img;


        ViewHolder() {
        }
    }

    public CaSiAdapter(Context context2, ArrayList<CaSi> list2) {
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
        CaSi caSi = (CaSi) this.list.get(i);
        if (view == null) {
            holder = new ViewHolder();
            view = ((Activity) this.context).getLayoutInflater().inflate(R.layout.one_info, null);
            holder.cot1 = view.findViewById(R.id.tvCot1);
            holder.cot2 = view.findViewById(R.id.tvCot2);
            holder.img = view.findViewById(R.id.ivImg);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.cot1.setText(caSi.getMaCS()+"");
        holder.cot2.setText(caSi.getHoTenCS());
        if (!caSi.getImgCS().isEmpty()) {
            Picasso.with(context).load(Uri.parse(caSi.getImgCS())).into(holder.img);
        }
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
