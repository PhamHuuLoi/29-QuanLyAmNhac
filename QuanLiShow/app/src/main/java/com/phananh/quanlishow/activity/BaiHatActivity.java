package com.phananh.quanlishow.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputEditText;

import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.phananh.quanlishow.R;
import com.phananh.quanlishow.ShowDialog;
import com.phananh.quanlishow.adapter.BaiHatAdapter;
import com.phananh.quanlishow.database.BaiHatDao;
import com.phananh.quanlishow.database.NhacSiDao;
import com.phananh.quanlishow.model.BaiHat;
import com.phananh.quanlishow.model.NhacSi;
import com.squareup.picasso.Picasso;
import com.swip.swipemenulistview.SwipeMenu;
import com.swip.swipemenulistview.SwipeMenuCreator;
import com.swip.swipemenulistview.SwipeMenuItem;
import com.swip.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.Calendar;

public class BaiHatActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private SwipeMenuListView listView;
    private ImageView them;
    private BaiHatDao baiHatDao;
    private ArrayList<BaiHat> list = new ArrayList<>();
    private BaiHatAdapter adapter;
    private ArrayList<NhacSi> listNS = new ArrayList<>();
    private NhacSiDao nhacSiDao;
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
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_hat);
        setTitle("THÔNG TIN BÀI HÁT");
        showDialog = new ShowDialog(this);

        //Tham chiếu id
        listView = findViewById(R.id.lvListBH);
        them = findViewById(R.id.ivThemBH);
        baiHatDao = new BaiHatDao(this);
        list = baiHatDao.getAll();
        adapter = new BaiHatAdapter(this, list);
        listView.setAdapter(adapter);

        //Lây list giáo viên để set vào spinner
        nhacSiDao = new NhacSiDao(this);
        listNS = nhacSiDao.getAll();
        //Khai báo xin quyền
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(BaiHatActivity.this);
        LayoutInflater inflater = ((Activity) BaiHatActivity.this).getLayoutInflater();
        View view = inflater.inflate(R.layout.thembaihat, null);
        final TextInputEditText ma = view.findViewById(R.id.edtMaBH);
        final TextInputEditText ten = view.findViewById(R.id.edtTenBaiHat);
        final TextInputEditText nam = view.findViewById(R.id.edtNam);
        final Spinner spNS = view.findViewById(R.id.spMaNS);
        final ImageView themNS = view.findViewById(R.id.ivThemNS);
        Button sua = view.findViewById(R.id.btnThemBH);
        Button huy = view.findViewById(R.id.btnHuyBH);
        ma.setVisibility(View.GONE);

        //Đổ list spinner giáo viên
        final ArrayAdapter sp = new ArrayAdapter(BaiHatActivity.this, R.layout.item_spinner, listNS);
        spNS.setAdapter(sp);

        themNS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(BaiHatActivity.this);
                LayoutInflater inflater = ((Activity) BaiHatActivity.this).getLayoutInflater();
                View view2 = inflater.inflate(R.layout.themlayout, null);
                final TextView title = view2.findViewById(R.id.titleView);
                final TextInputEditText ten = view2.findViewById(R.id.edtCot1);
                Button them = view2.findViewById(R.id.btnThem);
                Button huy = view2.findViewById(R.id.btnHuy);
                TextInputLayout til1 = view2.findViewById(R.id.tilTen);
                til1.setHint("Nhập tên nhạc sĩ");
                title.setText("THÊM NHẠC SĨ");
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

                        if (image_uri==null) {
                            showDialog.show("Vui lòng thêm hình cho nhạc sĩ!");
                        } else if (t.isEmpty()) {
                            showDialog.show("Tên nhạc sĩ không được để trống!!");
                        } else if (t.length() < 5) {
                            showDialog.show("Tên nhạc sĩ phải ít nhất 5 ký tự!");
                        } else {
                            NhacSi caSi = new NhacSi(0, t, image_uri.toString());
                            if (nhacSiDao.them(caSi) == true) {
                                showDialog.show("Thêm thành công!");
                                listNS.clear();
                                listNS.addAll(nhacSiDao.getAll());
                                sp.notifyDataSetChanged();
                                alertDialog.dismiss();
                            } else {
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

//        sua.setText("THÊM");
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.up_down;
        //Khi nhấn nút Sửa trong alert
        sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tens, nams, manss;
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
                            list.clear();
                            list.addAll(baiHatDao.getAll());
                            adapter.notifyDataSetChanged();
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

        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
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
                editItem.setWidth(170);
                editItem.setBackground(R.drawable.border2);
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
                final BaiHat baiHat = list.get(position);
                switch (index) {
                    case 0:
                        final AlertDialog.Builder builder = new AlertDialog.Builder(BaiHatActivity.this);
                        LayoutInflater inflater = ((Activity) BaiHatActivity.this).getLayoutInflater();
                        View view = inflater.inflate(R.layout.thembaihat, null);
                        TextView title = view.findViewById(R.id.titleView);
                        title.setText("SỬA BÀI HÁT");
                        final TextInputEditText ma = view.findViewById(R.id.edtMaBH);
                        final TextInputEditText ten = view.findViewById(R.id.edtTenBaiHat);
                        final TextInputEditText nam = view.findViewById(R.id.edtNam);
                        final Spinner spNS = view.findViewById(R.id.spMaNS);
                        final ImageView themNS = view.findViewById(R.id.ivThemNS);
                        Button sua = view.findViewById(R.id.btnThemBH);
                        Button huy = view.findViewById(R.id.btnHuyBH);
                        //Đổ list spinner nhạc sĩ
                        final ArrayAdapter sp = new ArrayAdapter(BaiHatActivity.this, R.layout.item_spinner, listNS);
                        spNS.setAdapter(sp);
                        themNS.setVisibility(View.GONE);
                        builder.setView(view);
                        final AlertDialog alertDialog = builder.create();
                        alertDialog.getWindow().getAttributes().windowAnimations = R.style.up_down;

                        ma.setFocusable(false);
                        //Set dữ liệu
                        ma.setText(baiHat.getMaBH()+"");
                        ten.setText(baiHat.getTenBH());
                        nam.setText(baiHat.getNamSangTac());

                        //Khi nhấn nút Sửa trong alert
                        sua.setText("SỬA");
                        sua.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String tens, nams, manBh;
                                manBh = ma.getText().toString();
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
                                        BaiHat baiHat = new BaiHat(Integer.parseInt(manBh), tens, nams, mans);
                                        if (baiHatDao.sua(baiHat) == true) {
                                            showDialog.show("Sửa thành công!");
                                            list.clear();
                                            list.addAll(baiHatDao.getAll());
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

                        final AlertDialog.Builder builder2 = new AlertDialog.Builder(BaiHatActivity.this);
                        builder2.setTitle("Cảnh báo");
                        builder2.setMessage("Khi xóa bài hát, bên thông tin biểu diễn sẽ bị xóa theo. Bạn có chắc chắn muốn xóa bài hát này?");
                        builder2.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //code yes để xóa
                                if (baiHatDao.xoa(baiHat) == true) {
                                    showDialog.show("Xóa thành công!");
                                    list.clear();
                                    list.addAll(baiHatDao.getAll());
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
        boolean result = ContextCompat.checkSelfPermission(BaiHatActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragetPermission() {
        requestPermissions(storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(BaiHatActivity.this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(BaiHatActivity.this,
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
            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                Picasso.with(this).load(image_uri).into(imageView);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(BaiHatActivity.this);
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
                    imageView.setImageResource(R.drawable.gallery);
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