package com.phananh.quanlishow.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.phananh.quanlishow.adapter.ShowAdapter;
import com.phananh.quanlishow.database.BaiHatDao;
import com.phananh.quanlishow.database.CaSiDao;
import com.phananh.quanlishow.database.NhacSiDao;
import com.phananh.quanlishow.model.BaiHat;
import com.phananh.quanlishow.model.CaSi;
import com.phananh.quanlishow.model.NhacSi;
import com.squareup.picasso.Picasso;
import com.swip.swipemenulistview.SwipeMenu;
import com.swip.swipemenulistview.SwipeMenuCreator;
import com.swip.swipemenulistview.SwipeMenuItem;
import com.swip.swipemenulistview.SwipeMenuListView;
import com.swip.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.phananh.quanlishow.R;
import com.phananh.quanlishow.ShowDialog;
import com.phananh.quanlishow.adapter.ShowAdapter;
import com.phananh.quanlishow.database.ShowDao;
import com.phananh.quanlishow.database.CaSiDao;
import com.phananh.quanlishow.database.ShowDao;
import com.phananh.quanlishow.model.ThongTinShow;
import com.phananh.quanlishow.model.ThongTinShow;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;

public class ShowActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private SwipeMenuListView listView;
    private ImageView them;
    private ShowDao showDao;
    private ArrayList<ThongTinShow> list = new ArrayList<>();
    private ShowAdapter adapter;
    private ArrayList<CaSi> listCS = new ArrayList<>();
    private ArrayList<BaiHat> listBH = new ArrayList<>();
    NhacSiDao nhacSiDao;
    private ArrayList<NhacSi> listNS = new ArrayList<>();

    private CaSiDao caSiDao;
    private BaiHatDao baiHatDao;
    private TextInputEditText search;
    private ShowDialog showDialog;
    private String[] cameraPermission;
    private String[] storagePermission;
    Uri image_uri;
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    private static final int IMAGE_PICK_CAMERA_CODE = 500;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_hat);
        setTitle("LỊCH BIỂU DIỄN");
        showDialog = new ShowDialog(this);

        //Tham chiếu id
        listView = findViewById(R.id.lvListBH);
        them = findViewById(R.id.ivThemBH);
        showDao = new ShowDao(this);
        list = showDao.getAll();
        adapter = new ShowAdapter(this, list);
        listView.setAdapter(adapter);
        //Khai báo xin quyền
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        //Lây list giáo viên để set vào spinner
        caSiDao = new CaSiDao(this);
        nhacSiDao = new NhacSiDao(this);
        baiHatDao = new BaiHatDao(this);
        listCS = caSiDao.getAll();
        listNS = nhacSiDao.getAll();
        listBH = baiHatDao.getAll();
        //Quẹt icon xóa, sửa
        swipeLayout();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //Khi click button thêm
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themLayout();
            }
        });

        //Lọc theo tìm kiếm
        listView.setTextFilterEnabled(true);
        TextInputEditText edSeach = (TextInputEditText) findViewById(R.id.edtSearchBH);
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

    private void themLayout() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ShowActivity.this);
        LayoutInflater inflater = ((Activity) ShowActivity.this).getLayoutInflater();
        View view = inflater.inflate(R.layout.them_show, null);
        final TextInputEditText ma = view.findViewById(R.id.edtMaShow);
        final Spinner spCS = view.findViewById(R.id.spMaCS);
        final ImageView themCS = view.findViewById(R.id.ivThemCS);
        final Spinner spBH = view.findViewById(R.id.spMaBH);
        final ImageView themBH = view.findViewById(R.id.ivThemBHS);
        final TextInputEditText ngay = view.findViewById(R.id.edtNgayShow);
        final TextInputEditText noi = view.findViewById(R.id.edtNoiShow);
        Button sua = view.findViewById(R.id.btnThemShow);
        Button huy = view.findViewById(R.id.btnHuyShow);
        ma.setVisibility(View.GONE);
        //Đổ list spinner giáo viên
        final ArrayAdapter adapterCS = new ArrayAdapter(ShowActivity.this, R.layout.item_spinner, listCS);
        spCS.setAdapter(adapterCS);

        final ArrayAdapter adapterBH = new ArrayAdapter(ShowActivity.this, R.layout.item_spinner, listBH);
        spBH.setAdapter(adapterBH);

        //Thêm ca sĩ, bài hát

        themCS.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ShowActivity.this);
                LayoutInflater inflater = ((Activity) ShowActivity.this).getLayoutInflater();
                View view2 = inflater.inflate(R.layout.themlayout, null);
                final TextView title = view2.findViewById(R.id.titleView);
                final TextInputEditText ten = view2.findViewById(R.id.edtCot1);
                Button them = view2.findViewById(R.id.btnThem);
                Button huy = view2.findViewById(R.id.btnHuy);
                TextInputLayout til1 = view2.findViewById(R.id.tilTen);
                til1.setHint("Nhập tên ca sĩ");
                //Hiện hình
                imageView = view2.findViewById(R.id.ivThemHinh);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showImagePickDialog();
                    }
                });

                //Set tiêu đề
                //Set dữ liệu vào alert
                builder.setView(view2);
                final AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().getAttributes().windowAnimations = R.style.up_down;

                //Khi nhấn nút Sửa trong alert
                them.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String t = ten.getText().toString();

                        if (image_uri == null) {
                            showDialog.show("Vui lòng thêm hình cho ca sĩ!");
                        } else if (t.isEmpty()) {
                            showDialog.show("Tên ca sĩ không được để trống!!");
                        } else if (t.length() < 5) {
                            showDialog.show("Tên ca sĩ phải ít nhất 5 ký tự!");
                        } else {
                            CaSi caSi = new CaSi(0, t, image_uri.toString());
                            if (caSiDao.them(caSi) == true) {
                                showDialog.show("Thêm thành công!");
                                listCS.clear();
                                listCS.addAll(caSiDao.getAll());
                                adapterCS.notifyDataSetChanged();
                                alertDialog.dismiss();
                            }
                        }
                    }

                });

                huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        themBH.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ShowActivity.this);
                LayoutInflater inflater = ((Activity) ShowActivity.this).getLayoutInflater();
                View view2 = inflater.inflate(R.layout.thembaihat, null);
                final TextInputEditText ma = view2.findViewById(R.id.edtMaBH);
                final TextInputEditText ten = view2.findViewById(R.id.edtTenBaiHat);
                final TextInputEditText nam = view2.findViewById(R.id.edtNam);
                final Spinner spNS = view2.findViewById(R.id.spMaNS);
                final ImageView themNS = view2.findViewById(R.id.ivThemNS);
                themNS.setVisibility(View.GONE);
                Button sua = view2.findViewById(R.id.btnThemBH);
                Button huy = view2.findViewById(R.id.btnHuyBH);
                ma.setVisibility(View.GONE);

                final ArrayAdapter adapterNS = new ArrayAdapter(ShowActivity.this, R.layout.item_spinner, listNS);
                spNS.setAdapter(adapterNS);
                builder.setView(view2);
                final AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().getAttributes().windowAnimations = R.style.up_down;
                //Khi nhấn nút Sửa trong alert
                sua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tens, nams;
                        tens = ten.getText().toString();
                        nams = nam.getText().toString();
                        try {
                            if (tens.isEmpty() || nams.isEmpty() || listNS.size() == 0) {
                                showDialog.show("Các trường không được để trống!");
                            } else if (tens.length() < 2) {
                                showDialog.show("Tên bài hát quá ngắn!");
                            } else if (nams.length() > 5) {
                                showDialog.show("Năm sáng tác không chính xác!");
                            } else {
                                NhacSi nhacSi = (NhacSi) spNS.getSelectedItem();
                                String mans = nhacSi.getMaNS() + "";
                                BaiHat baiHat = new BaiHat(0, tens, nams, mans);
                                if (baiHatDao.them(baiHat) == true) {
                                    showDialog.show("Thêm thành công!");
                                    listBH.clear();
                                    listBH.addAll(baiHatDao.getAll());
                                    adapterBH.notifyDataSetChanged();
                                    alertDialog.dismiss();
                                } else {
                                    showDialog.show("Thêm thất bại!");
                                }

                            }
                        } catch (
                                Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

                huy.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });
//        sua.setText("THÊM");
        builder.setView(view);
        final AlertDialog alertDialog2 = builder.create();
        alertDialog2.getWindow().getAttributes().windowAnimations = R.style.up_down;
        ngay.setFocusable(false);
        ngay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int d = calendar.get(Calendar.DAY_OF_MONTH);
                int m = calendar.get(Calendar.MONTH);
                int y = calendar.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(ShowActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        final String NgayGD = dayOfMonth + "/" + (month + 1) + "/" + year;
                        ngay.setText(NgayGD);
                    }
                }, y, m, d);
                datePickerDialog.show();
            }
        });

        //Khi nhấn nút Sửa trong alert
        sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ngays = ngay.getText().toString();
                String diadiem = noi.getText().toString();
                try {
                    if (ngays.isEmpty() || diadiem.isEmpty() || listCS.isEmpty() || listBH.isEmpty()) {
                        showDialog.show("Các trường không được để trống!");
                    } else if (diadiem.length() < 5) {
                        showDialog.show("Địa diểm quá ngắn!");
                    } else {
                        CaSi caSi = (CaSi) spCS.getSelectedItem();
                        BaiHat baiHat = (BaiHat) spBH.getSelectedItem();
                        int maCS = caSi.getMaCS();
                        int maBH = baiHat.getMaBH();
                        ThongTinShow show = new ThongTinShow(0, maCS, maBH, ngays, diadiem);
                        if (showDao.them(show)) {
                            showDialog.show("Thêm thành công!");
                            list.clear();
                            list.addAll(showDao.getAll());
                            adapter.notifyDataSetChanged();
                            alertDialog2.dismiss();
                        } else {
                            showDialog.show("Thêm thất bại!");
                        }

                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }
        });

        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog2.dismiss();
            }
        });
        alertDialog2.show();
    }

    private void swipeLayout() {
        //Thanh Swipe
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem editItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item width
                editItem.setBackground(R.drawable.border2);
                editItem.setWidth(170);
                // set item title font color
                editItem.setIcon(R.drawable.ic_baseline_edit_24);
                // add to menu
                menu.addMenuItem(editItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item width
                deleteItem.setWidth(170);
                deleteItem.setBackground(R.drawable.border2);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_baseline_delete_24);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                //lấy vị trí
                final ThongTinShow show = list.get(position);
                switch (index) {
                    case 0:
                        final AlertDialog.Builder builder = new AlertDialog.Builder(ShowActivity.this);
                        LayoutInflater inflater = ((Activity) ShowActivity.this).getLayoutInflater();
                        View view = inflater.inflate(R.layout.them_show, null);
                        final TextInputEditText ma = view.findViewById(R.id.edtMaShow);
                        final Spinner spCS = view.findViewById(R.id.spMaCS);
                        final ImageView themCS = view.findViewById(R.id.ivThemCS);
                        final Spinner spBH = view.findViewById(R.id.spMaBH);
                        final ImageView themBH = view.findViewById(R.id.ivThemBHS);

                        themCS.setVisibility(View.GONE);
                        themBH.setVisibility(View.GONE);
                        final TextInputEditText ngay = view.findViewById(R.id.edtNgayShow);
                        final TextInputEditText noi = view.findViewById(R.id.edtNoiShow);
                        Button sua = view.findViewById(R.id.btnThemShow);
                        Button huy = view.findViewById(R.id.btnHuyShow);

                        sua.setText("SỬA");
                        //Đổ list spinner giáo viên
                        final ArrayAdapter adapterCS = new ArrayAdapter(ShowActivity.this, R.layout.item_spinner, listCS);
                        spCS.setAdapter(adapterCS);

                        final ArrayAdapter adapterBH = new ArrayAdapter(ShowActivity.this, R.layout.item_spinner, listBH);
                        spBH.setAdapter(adapterBH);

                        //SetText
                        ma.setText(show.getMaBD() + "");
                        ngay.setText(show.getNgayBD());
                        noi.setText(show.getNoiBD());

                        ngay.setFocusable(false);
                        ngay.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final Calendar calendar = Calendar.getInstance();
                                int d = calendar.get(Calendar.DAY_OF_MONTH);
                                int m = calendar.get(Calendar.MONTH);
                                int y = calendar.get(Calendar.YEAR);
                                datePickerDialog = new DatePickerDialog(ShowActivity.this, new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        final String NgayGD = dayOfMonth + "/" + (month + 1) + "/" + year;
                                        ngay.setText(NgayGD);
                                    }
                                }, y, m, d);
                                datePickerDialog.show();
                            }
                        });
                        //Set đúng vị trí
                        int maCS = show.getMaCS();
                        for (int i = 0; i < listCS.size(); i++) {
                            if (maCS == listCS.get(i).getMaCS()) {
                                spCS.setSelection(i);
                                break;
                            }
                        }

                        int maBH = show.getMaBH();
                        for (int i = 0; i < listBH.size(); i++) {
                            if (maBH == listBH.get(i).getMaBH()) {
                                spBH.setSelection(i);
                                break;
                            }
                        }
                        builder.setView(view);
                        final AlertDialog alertDialog = builder.create();

                        sua.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int ma = show.getMaBD();
                                String ngays, dds;
                                ngays = ngay.getText().toString();
                                dds = noi.getText().toString();
                                try {
                                    if (listCS.isEmpty() || listBH.isEmpty() || ngays.isEmpty() || dds.isEmpty()) {
                                        showDialog.show("Các trường không được để trống!");
                                    } else if (dds.length() < 5) {
                                        showDialog.show("Địa diểm quá ngắn!");
                                    } else {
                                        CaSi caSi = (CaSi) spCS.getSelectedItem();
                                        BaiHat baiHat = (BaiHat) spBH.getSelectedItem();
                                        int maCS = caSi.getMaCS();
                                        int maBH = baiHat.getMaBH();
                                        ThongTinShow show = new ThongTinShow(ma, maCS, maBH, ngays, dds);
                                        if (showDao.sua(show) == true) {
                                            showDialog.show("Sửa thành công!");
                                            list.clear();
                                            list.addAll(showDao.getAll());
                                            adapter.notifyDataSetChanged();
                                            alertDialog.dismiss();
                                        } else {
                                            showDialog.show("Sửa thất bại!");
                                        }

                                    }
                                } catch (
                                        Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                        huy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();


                        break;
                    //xóa
                    case 1:

                        final AlertDialog.Builder builder2 = new AlertDialog.Builder(ShowActivity.this);
                        builder2.setTitle("Cảnh báo");
                        builder2.setMessage("Bạn có chắc chắn muốn xóa bài hát này?");
                        builder2.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //code yes để xóa
                                if (showDao.xoa(show) == true) {
                                    showDialog.show("Xóa thành công!");
                                    list.clear();
                                    list.addAll(showDao.getAll());
                                    adapter.notifyDataSetChanged();
                                    dialog.dismiss();
                                } else {
                                    showDialog.show("Xóa thất bại!");
                                }

                            }
                        });
                        builder2.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        final AlertDialog dialog = builder2.create();
                        dialog.getWindow().getAttributes().windowAnimations = R.style.up_down;
                        dialog.show();

                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in, R.anim.out);
        finish();
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(ShowActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragetPermission() {
        requestPermissions(storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(ShowActivity.this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(ShowActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        requestPermissions(cameraPermission, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean cameraAccept = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccept = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccept && storageAccept) {
                        pickFromCamera();
                    } else {
                        showDialog.show("Không truy cập được vào camera!");
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean writeStorageAccpted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAccpted) {
                        pickFromGallery();
                    } else {
                        showDialog.show("Vui lòng bật quyền thư viện");
                    }
                }
            }
            break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                image_uri = data.getData();
                Picasso.with(this).load(image_uri).into(imageView);
//                Image image = new Image(0, image_uri.toString());
//                imageDao.them(image);
//                list.clear();
//                list.addAll(imageDao.getALl());
//                adapter.notifyDataSetChanged();
//                showDialog.show("Up ảnh thành công!");
            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                Picasso.with(this).load(image_uri).into(imageView);
//                Image image = new Image(0, image_uri.toString());
//                imageDao.them(image);
//                list.clear();
//                list.addAll(imageDao.getALl());
//                adapter.notifyDataSetChanged();
//                showDialog.show("Up ảnh thành công!");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void pickFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "Temp Pic");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Temp Description");
        image_uri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);
    }

    public void showImagePickDialog() {
        String option[] = {"Camera", "Thư viện ảnh", "Xóa ảnh"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ShowActivity.this);
        builder.setTitle("Mời bạn chọn");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else {
                        pickFromCamera();
                    }
                }
                if (which == 1) {
                    if (!checkStoragePermission()) {
                        requestStoragetPermission();
                    } else {
                        pickFromGallery();
                    }
                }
                if (which == 2) {
                    image_uri = Uri.parse("");
                    imageView.setImageResource(R.drawable.user);
                }
            }
        });
        builder.create().show();
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
