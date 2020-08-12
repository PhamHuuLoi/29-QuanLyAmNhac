package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Adapter adapter;
    ArrayList<XeMay> data = new ArrayList<>();
    EditText txtMaxe, txtTenxe;
    RadioButton rbHD, rbYM;
    Button btnThem, btnExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAdapter();
        setControl();
        setEvent();
        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                data.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    void setAdapter() {
        recyclerView = findViewById(R.id.recycleview);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter(this, data,R.layout.layout_item_row);
        recyclerView.setAdapter(adapter);
    }
    void setControl(){
        txtMaxe = (EditText) findViewById(R.id.txtMaxe);
        txtTenxe = (EditText) findViewById(R.id.txtTenxe);
        rbHD = (RadioButton) findViewById(R.id.rbHD);
        rbYM = (RadioButton) findViewById(R.id.rbYM);
        btnThem = (Button) findViewById(R.id.btnThem);
        btnExit = (Button) findViewById(R.id.btnThoat);
    }
    void setEvent(){
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtMaxe.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this,"Nhập mã xe", Toast.LENGTH_SHORT).show();
                    txtMaxe.requestFocus();
                } else if(txtTenxe.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Nhập tên xe", Toast.LENGTH_SHORT).show();
                    txtTenxe.requestFocus();
                } else {
                    XeMay xeMay = new XeMay();
                    xeMay.setMaXe(txtMaxe.getText().toString());
                    xeMay.setTenXe(txtTenxe.getText().toString());
                    if(rbHD.isChecked())
                    {
                        xeMay.setHangSX("Honda");
                        xeMay.setImgIcon(R.drawable.hd);
                    }
                    else
                    {
                        xeMay.setHangSX("Yamaha");
                        xeMay.setImgIcon(R.drawable.ym);
                    }
                    data.add(xeMay);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


}
