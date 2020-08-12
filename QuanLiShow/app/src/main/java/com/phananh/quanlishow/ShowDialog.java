package com.phananh.quanlishow;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class ShowDialog {
    Activity activity;
    Dialog dialog;

    public ShowDialog() {
    }

    public ShowDialog(Activity activity2) {
        this.activity = activity2;
    }

    public void show(String text) {
        Dialog dialog2 = new Dialog(this.activity);
        this.dialog = dialog2;
        dialog2.setContentView(R.layout.show);
        this.dialog.setCancelable(true);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Dialog dialog3 = this.dialog;
        if (!(dialog3 == null || dialog3.getWindow() == null)) {
            this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        ((TextView) this.dialog.findViewById(R.id.tvInfo)).setText(text);
        ((Button) this.dialog.findViewById(R.id.okDialog)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ShowDialog.this.dialog.dismiss();
            }
        });
        this.dialog.show();
    }

    public void dismiss() {
        this.dialog.dismiss();
    }
}
