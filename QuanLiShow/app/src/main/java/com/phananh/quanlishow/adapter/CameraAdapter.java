package com.phananh.quanlishow.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.phananh.quanlishow.R;
import com.phananh.quanlishow.model.Image;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Iterator;

public class CameraAdapter extends BaseAdapter {
    Context context;
    Filter filter;
    ArrayList<Image> list;
    ArrayList<Image> listSort;

    private class CustomFilter extends Filter {
        private CustomFilter() {
        }

        public FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = CameraAdapter.this.listSort;
                results.count = CameraAdapter.this.listSort.size();
            } else {
                ArrayList<Image> lsSach = new ArrayList<>();
                Iterator it = CameraAdapter.this.list.iterator();
                while (it.hasNext()) {
                    Image p = (Image) it.next();
                    if (String.valueOf(p.getId()).toUpperCase().contains(constraint.toString().toUpperCase())) {
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
                CameraAdapter.this.notifyDataSetInvalidated();
                return;
            }
            CameraAdapter.this.list = (ArrayList) results.values;
            CameraAdapter.this.notifyDataSetChanged();
        }
    }

    class ViewHolder {
        ImageView ivImage;
        TextView tvImage;

        ViewHolder() {
        }
    }

    public CameraAdapter(Context context2, ArrayList<Image> list2) {
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
        Image image = (Image) this.list.get(i);
        if (view == null) {
            holder = new ViewHolder();
            view = ((Activity) this.context).getLayoutInflater().inflate(R.layout.item_image, null);
            holder.tvImage = (TextView) view.findViewById(R.id.tvImage);
            holder.ivImage = (ImageView) view.findViewById(R.id.ivImage);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        TextView textView = holder.tvImage;
        StringBuilder sb = new StringBuilder();
        sb.append("Hình ");
        sb.append(image.getId());
        textView.setText(sb.toString());
        Picasso.with(this.context).load(image.getImage()).into(holder.ivImage);
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
