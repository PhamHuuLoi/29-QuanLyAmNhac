package com.phananh.quanlishow.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.phananh.quanlishow.R;
import com.phananh.quanlishow.adapter.ThongKeAdapter;
import com.phananh.quanlishow.adapter.ThongKeNSAdapter;
import com.phananh.quanlishow.database.BaiHatDao;
import com.phananh.quanlishow.database.CaSiDao;
import com.phananh.quanlishow.database.NhacSiDao;
import com.phananh.quanlishow.database.ShowDao;
import com.phananh.quanlishow.model.BaiHat;
import com.phananh.quanlishow.model.CaSi;
import com.phananh.quanlishow.model.NhacSi;
import com.phananh.quanlishow.model.ThongKe;
import com.phananh.quanlishow.model.ThongKeCS;
import com.phananh.quanlishow.model.ThongKeNS;
import com.phananh.quanlishow.model.ThongTinShow;
import com.swip.swipemenulistview.SwipeMenuListView;

import java.io.PrintStream;
import java.util.ArrayList;

public class ThongKeActivity extends AppCompatActivity {
    ArrayList<String> list = new ArrayList<>();
    private Spinner spinner;
    BaiHatDao baiHatDao;
    CaSiDao caSiDao;
    NhacSiDao nhacSiDao;
    ShowDao showDao;
    ArrayList<BaiHat> listBH;
    ArrayList<CaSi> listCS;
    ArrayList<NhacSi> listNS;
    ArrayList<ThongTinShow> listShow;
    ArrayList<ThongKe> listTKCS = new ArrayList<>();
    ArrayList<ThongKeNS> listTKNS = new ArrayList<>();
    ThongKeAdapter adapter;
    ListView listView;
    ThongKeNSAdapter thongKeNSAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        init();
        listTKCS.clear();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("THỐNG KÊ");
        list.add("1. Thống kê theo ca sĩ");
        list.add("2. Thống kê theo nhạc sĩ");
        spinner = findViewById(R.id.locDuLieu);
        final ArrayAdapter sp = new ArrayAdapter(ThongKeActivity.this, R.layout.item_spinner, list);
        spinner.setAdapter(sp);
        spinner.setSelection(0);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                //Khi chọn spinner
                switch (position) {
                    case 0:
                        thongKeCS();
                        adapter.notifyDataSetChanged();
                        break;
                    case 1:
                        listTKCS.clear();
                        thongKeNS();
                        thongKeNSAdapter.notifyDataSetChanged();
                        break;
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {// do nothing
            }

        });

        //Lọc theo tìm kiếm
        listView.setTextFilterEnabled(true);
        TextInputEditText edSeach = findViewById(R.id.edtSearchTK);
        edSeach.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int
                    count) {
                System.out.println("Text [" + s + "] - Start [" + start + "] - Before [" + before + "] - Count [" + count + "]");
                if (count < before) {
                    adapter.resetData();
                }
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void init() {
        baiHatDao = new BaiHatDao(this);
        showDao = new ShowDao(this);
        nhacSiDao = new NhacSiDao(this);
        caSiDao = new CaSiDao(this);

        listBH = baiHatDao.getAll();
        listCS = caSiDao.getAll();
        listNS = nhacSiDao.getAll();
        listShow = showDao.getAll();
        listView = findViewById(R.id.lvListTK);
    }

    //thống kê ca sĩ
    private void thongKeCS() {
        listTKCS.clear();
        for (int i = 0; i < listShow.size(); i++) {
            ThongTinShow show = listShow.get(i);
            listTKCS.add(new ThongKe(String.valueOf(show.getMaCS()), String.valueOf(show.getMaBH())));
        }




        for (int i = 0; i < listTKCS.size(); i++) {
            String maCS = listTKCS.get(i).getTen1();
            for (int j = 0; j < listCS.size(); j++) {
                String tencaSi = String.valueOf(listCS.get(j).getMaCS());
                if (maCS.equalsIgnoreCase(tencaSi)) {
                    listTKCS.set(i, new ThongKe(listCS.get(j).getHoTenCS(), listTKCS.get(i).getTen2()));
                }
            }
        }

        for (int i = 0; i < listTKCS.size(); i++) {
            String maBH = listTKCS.get(i).getTen2();
            for (int z = 0; z < listBH.size(); z++) {
                String tenBH = String.valueOf(listBH.get(z).getMaBH());
                if (maBH.equalsIgnoreCase(tenBH)) {
                    listTKCS.set(i, new ThongKe(listTKCS.get(i).getTen1(), listBH.get(z).getTenBH()));
                }
            }
        }


        //Chưa được
        //Gộp trùng
        for (int i = 0; i < listTKCS.size(); i++) {
            String cs = listTKCS.get(i).getTen1();
            String bh = listTKCS.get(i).getTen2();
            for (int j = i + 1; j <= listTKCS.size() - 1; j++) {
                String cs2 = listTKCS.get(j).getTen1();
                String bh2 = listTKCS.get(j).getTen2();

                if (cs.equalsIgnoreCase(cs2)) {
                    if (bh.equalsIgnoreCase(bh2)) {

                        listTKCS.remove(j);
                    } else {
                        bh += ", " + bh2;
                        listTKCS.set(i, new ThongKe(cs, bh));
                        listTKCS.remove(j);
                    }
                }
            }
        }

        adapter = new ThongKeAdapter(this, listTKCS);
        listView.setAdapter(adapter);
    }

    //Lọc list theo nhạc sĩ
    private void thongKeNS() {
        listTKNS.clear();
        for (int i = 0; i < listBH.size(); i++) {
            listTKNS.add(new ThongKeNS(listBH.get(i).getMaNS(), listBH.get(i).getTenBH()));
        }

        for (int i = 0; i < listTKNS.size(); i++) {
            String maNS = listTKNS.get(i).getTenNhacSi();
            for (int j = 0; j < listNS.size(); j++) {
                String ma = listNS.get(j).getMaNS() + "";
                if (maNS.equalsIgnoreCase(ma)) {
                    listTKNS.set(i, new ThongKeNS(listNS.get(j).getTenNS() + "", listTKNS.get(i).getTenBH()));
                }
            }
        }

        int tong = 0;
        //Gộp trùng
        for (int i = 0; i < listTKNS.size(); i++) {
            String cs = listTKNS.get(i).getTenNhacSi();
            String bh = listTKNS.get(i).getTenBH();
            for (int j = i + 1; j <= listTKNS.size() - 1; j++) {
                String cs2 = listTKNS.get(j).getTenNhacSi();
                String bh2 = listTKNS.get(j).getTenBH();

                if (cs.equalsIgnoreCase(cs2)) {
                    if (bh.equalsIgnoreCase(bh2)) {

                        listTKNS.remove(j);
                    } else {
                        bh += ", " + bh2;
                        listTKNS.set(i, new ThongKeNS(cs, bh));
                        listTKNS.remove(j);
                    }
                }
            }
        }

        thongKeNSAdapter = new ThongKeNSAdapter(this, listTKNS);
        listView.setAdapter(thongKeNSAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish(); // Or do you own task
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}