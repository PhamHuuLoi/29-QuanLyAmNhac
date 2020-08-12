package com.phananh.quanlishow.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;
import com.swip.swipemenulistview.SwipeMenu;
import com.swip.swipemenulistview.SwipeMenuCreator;
import com.swip.swipemenulistview.SwipeMenuItem;
import com.swip.swipemenulistview.SwipeMenuListView;
import com.swip.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.phananh.quanlishow.R;
import com.phananh.quanlishow.ShowDialog;
import com.phananh.quanlishow.adapter.CaSiAdapter;
import com.phananh.quanlishow.database.CaSiDao;
import com.phananh.quanlishow.model.CaSi;

import java.io.PrintStream;
import java.util.ArrayList;

public class CaSiActivity extends AppCompatActivity {
    /* access modifiers changed from: private */
    /* access modifiers changed from: private */
    public CaSiDao caSiDao;
    //    private Button home;
    ArrayList<CaSi> list = new ArrayList<>();
    private SwipeMenuListView listView;
    /* access modifiers changed from: private */
    public ShowDialog showDialog;
    private ImageView them;
    ImageView imageView;
    private String[] cameraPermission;
    private String[] storagePermission;
    Uri image_uri;
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    private static final int IMAGE_PICK_CAMERA_CODE = 500;
    private CaSiAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_ca_si);
        setTitle("THÔNG TIN CA SĨ");
        showDialog = new ShowDialog(this);
        listView = (SwipeMenuListView) findViewById(R.id.lvListCS);
        them = findViewById(R.id.btnThemCaSi);
        caSiDao = new CaSiDao(this);
        //Khai báo xin quyền
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
//        this.home = (Button) findViewById(R.id.btnHomeGV);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        list = caSiDao.getAll();
        adapter = new CaSiAdapter(this, list);
        listView.setAdapter(adapter);
        swipeLayout();

        them.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                themLayout();
            }
        });


        listView.setTextFilterEnabled(true);
        ((EditText) findViewById(R.id.edtSearchCS)).addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append("Text [");
                sb.append(s);
                sb.append("] - Start [");
                sb.append(start);
                sb.append("] - Before [");
                sb.append(before);
                sb.append("] - Count [");
                sb.append(count);
                sb.append("]");
                printStream.println(sb.toString());
                if (count < before) {
                    adapter.resetData();
                }
                adapter.getFilter().filter(s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void themLayout() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(CaSiActivity.this);
        LayoutInflater inflater = ((Activity) CaSiActivity.this).getLayoutInflater();
        View view = inflater.inflate(R.layout.themlayout, null);
        final TextView title = view.findViewById(R.id.titleView);
        final TextInputEditText ten = view.findViewById(R.id.edtCot1);
        Button themCS = view.findViewById(R.id.btnThem);
        Button huy = view.findViewById(R.id.btnHuy);
        TextInputLayout til1 = view.findViewById(R.id.tilTen);
        til1.setHint("Nhập tên ca sĩ");


        //Hiện hình
        imageView = view.findViewById(R.id.ivThemHinh);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePickDialog();
            }
        });
        //Set tiêu đề
        //Set dữ liệu vào alert
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.up_down;

        //Khi nhấn nút Sửa trong alert
        themCS.setOnClickListener(new View.OnClickListener() {
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
                        list.clear();
                        list.addAll(caSiDao.getAll());
                        adapter.notifyDataSetChanged();
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
                deleteItem.setBackground(R.drawable.border2);
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_baseline_delete_24);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                //lấy vị trí
                final CaSi casi = list.get(position);
                switch (index) {
                    case 0:
                        final AlertDialog.Builder builder = new AlertDialog.Builder(CaSiActivity.this);
                        LayoutInflater inflater = ((Activity) CaSiActivity.this).getLayoutInflater();
                        View view = inflater.inflate(R.layout.themlayout, null);
                        final TextView title = view.findViewById(R.id.titleView);
                        final TextInputEditText ten = view.findViewById(R.id.edtCot1);
                        final TextInputEditText ma = view.findViewById(R.id.edtCot2);
                        Button sua = view.findViewById(R.id.btnThem);
                        Button huy = view.findViewById(R.id.btnHuy);
                        TextInputLayout til1 = view.findViewById(R.id.tilMa);
                        TextInputLayout til2 = view.findViewById(R.id.tilTen);
                        til1.setVisibility(View.VISIBLE);
                        til1.setHint("Nhập mã nhạc sĩ");
                        til2.setHint("Nhập tên nhạc sĩ");
                        imageView = view.findViewById(R.id.ivThemHinh);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                showImagePickDialog();
                            }
                        });
                        //Set tiêu đề
                        title.setText("CHỈNH SỬA GIÁO VIÊN");
                        ma.setFocusable(false);
                        //Set dữ liệu vào alert
                        ma.setText(casi.getMaCS() + "");
                        ten.setText(casi.getHoTenCS());
                        if (!casi.getImgCS().isEmpty()) {
                            Picasso.with(CaSiActivity.this).load(Uri.parse(casi.getImgCS())).into(imageView);
                        }
                        sua.setText("SỬA");
                        builder.setView(view);
                        final AlertDialog alertDialog = builder.create();
                        alertDialog.getWindow().getAttributes().windowAnimations = R.style.up_down;
                        //Khi nhấn nút Sửa trong alert
                        sua.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String m = ma.getText().toString();
                                String t = ten.getText().toString();
                                try {
                                    if (image_uri.toString().isEmpty()) {
                                        showDialog.show("Vui lòng thêm hình cho ca sĩ!");
                                    } else if (t.isEmpty()) {
                                        showDialog.show("Tên ca sĩ không được để trống!!");
                                    } else if (t.length() < 5) {
                                        showDialog.show("Tên ca sĩ phải ít nhất 5 ký tự!");
                                    } else {
                                        CaSi casi1 = new CaSi(Integer.parseInt(m), t, image_uri.toString());

                                        if (caSiDao.sua(casi1) == true) {
                                            showDialog.show("Sửa thành công!");
                                            list.clear();
                                            list.addAll(caSiDao.getAll());
                                            adapter.notifyDataSetChanged();
                                            alertDialog.dismiss();
                                        } else {
                                            showDialog.show("Sửa thất bại!");
                                        }
                                    }
                                } catch (Exception e) {
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

                        final AlertDialog.Builder builder2 = new AlertDialog.Builder(CaSiActivity.this);
                        builder2.setTitle("Cảnh báo");
                        builder2.setMessage("Khi xóa ca sĩ, thông tin biểu diễn sẽ bị xóa theo. Bạn muốn xóa không?");
                        builder2.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //code yes để xóa
                                if (caSiDao.xoa(list.get(position)) == true) {
                                    showDialog.show("Xóa thành công!");
                                    list.clear();
                                    list.addAll(caSiDao.getAll());
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
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish(); // Or do you own task
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in, R.anim.out);
        finish();
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(CaSiActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragetPermission() {
        requestPermissions(storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(CaSiActivity.this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(CaSiActivity.this,
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

        AlertDialog.Builder builder = new AlertDialog.Builder(CaSiActivity.this);
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
}
