package com.phananh.quanlishow.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phananh.quanlishow.R;
import com.phananh.quanlishow.model.ThongTinShow;

import java.util.ArrayList;
import java.util.Iterator;

public class ShowAdapter extends BaseAdapter {
    Context context;
    Filter filter;
    ArrayList<ThongTinShow> list;
    ArrayList<ThongTinShow> listSort;

    private class CustomFilter extends Filter {
        private CustomFilter() {
        }

        /* access modifiers changed from: protected */
        public FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = ShowAdapter.this.listSort;
                results.count = ShowAdapter.this.listSort.size();
            } else {
                ArrayList<ThongTinShow> lsSach = new ArrayList<>();
                Iterator it = ShowAdapter.this.list.iterator();
                while (it.hasNext()) {
                    ThongTinShow p = (ThongTinShow) it.next();
                    if (String.valueOf(p.getMaBD()).toUpperCase().startsWith(constraint.toString().toUpperCase()) ||
                            String.valueOf(p.getMaCS()).toUpperCase().startsWith(constraint.toString().toUpperCase()) ||
                            String.valueOf(p.getMaBH()).toUpperCase().startsWith(constraint.toString().toUpperCase()) ||
                            String.valueOf(p.getNgayBD()).toUpperCase().startsWith(constraint.toString().toUpperCase()) ||
                            String.valueOf(p.getNoiBD()).toUpperCase().startsWith(constraint.toString().toUpperCase())) {
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
                ShowAdapter.this.notifyDataSetInvalidated();
                return;
            }
            ShowAdapter.this.list = (ArrayList) results.values;
            ShowAdapter.this.notifyDataSetChanged();
        }
    }

    class ViewHolder {
        TextView cot1, cot2, cot3, cot4, cot5, tcot1, tcot2, tcot3, tcot4, tcot5;
        LinearLayout linearLayout;
        ViewHolder() {
        }
    }

    public ShowAdapter(Context context, ArrayList<ThongTinShow> list2) {
        this.context = context;
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
        ThongTinShow show = (ThongTinShow) this.list.get(i);
        if (view == null) {
            holder = new ViewHolder();
            view = ((Activity) this.context).getLayoutInflater().inflate(R.layout.one_info2, null);

            holder.cot1 = view.findViewById(R.id.tvTenCot1);
            holder.cot2 = view.findViewById(R.id.tvTenCot2);
            holder.cot3 = view.findViewById(R.id.tvTenCot3);
            holder.cot4 = view.findViewById(R.id.tvTenCot4);
            holder.cot5 = view.findViewById(R.id.tvTenCot5);
            holder.linearLayout = view.findViewById(R.id.ln1);
            holder.tcot1 = view.findViewById(R.id.tvCot1);
            holder.tcot2 = view.findViewById(R.id.tvCot2);
            holder.tcot3 = view.findViewById(R.id.tvCot3);
            holder.tcot4 = view.findViewById(R.id.tvCot4);
            holder.tcot5 = view.findViewById(R.id.tvCot5);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.linearLayout.setVisibility(View.VISIBLE);
        holder.cot1.setText(show.getMaBD() + "");
        holder.cot2.setText(show.getMaCS()+"");
        holder.cot3.setText(show.getMaBH() + "");
        holder.cot4.setText(show.getNgayBD() );
        holder.cot5.setText(show.getNoiBD() );
        holder.tcot1.setText("Mã biểu diễn: ");
        holder.tcot2.setText("Mã ca sĩ: ");
        holder.tcot3.setText("Mã bài hát: ");
        holder.tcot4.setText("Ngày biểu diễn: ");
        holder.tcot5.setText("Địa điểm");
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
