package com.phananh.quanlishow.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.swip.swipemenulistview.SwipeMenu;
import com.swip.swipemenulistview.SwipeMenuCreator;
import com.swip.swipemenulistview.SwipeMenuItem;
import com.swip.swipemenulistview.SwipeMenuListView;
import com.swip.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.phananh.quanlishow.R;
import com.phananh.quanlishow.ShowDialog;
import com.phananh.quanlishow.adapter.CameraAdapter;
import com.phananh.quanlishow.database.ImageDao;
import com.phananh.quanlishow.model.Image;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CameraActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int IMAGE_PICK_CAMERA_CODE = 500;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    private static final int STORAGE_REQUEST_CODE = 300;
    CameraAdapter adapter;
    public ImageView anh;
    public Animation blink;
    private String[] cameraPermission;
    /* access modifiers changed from: private */
    public ImageDao imageDao;
    Uri image_uri;
    public ArrayList<Image> list = new ArrayList<>();
    SwipeMenuListView listView;
    public ShowDialog showDialog;
    private String[] storagePermission;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish(); // Or do you own task
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_camera);
        this.showDialog = new ShowDialog(this);
        this.listView = (SwipeMenuListView) findViewById(R.id.lvListImage);
        setTitle("Máy ảnh");
        init();
        this.list = this.imageDao.getALl();
        CameraAdapter cameraAdapter = new CameraAdapter(this, this.list);
        this.adapter = cameraAdapter;
        this.listView.setAdapter((ListAdapter) cameraAdapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        String str = "android.permission.WRITE_EXTERNAL_STORAGE";
        this.cameraPermission = new String[]{"android.permission.CAMERA", str};
        this.storagePermission = new String[]{str};
        this.listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int posision, long l) {
                Picasso.with(CameraActivity.this).load(((Image) CameraActivity.this.list.get(posision)).getImage()).into(CameraActivity.this.anh);
            }
        });
        anh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePickDialog();
            }
        });

        swipeLayout();
    }

    private void swipeLayout() {
        this.listView.setMenuCreator(new SwipeMenuCreator() {
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(CameraActivity.this.getApplicationContext());
                deleteItem.setWidth(170);
                deleteItem.setBackground(R.drawable.border2);
                deleteItem.setIcon((int) R.drawable.ic_baseline_delete_24);
                menu.addMenuItem(deleteItem);
            }
        });
        this.listView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                final Image image = (Image) CameraActivity.this.list.get(position);
                if (index == 0) {
                    Builder builder2 = new Builder(CameraActivity.this);
                    builder2.setTitle("Cảnh báo");
                    StringBuilder sb = new StringBuilder();
                    sb.append("Bạn chắc chắn muốn xóa hình ");
                    sb.append(image.getId());
                    builder2.setMessage(sb.toString());
                    builder2.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (CameraActivity.this.imageDao.xoa(image.getId())) {
                                CameraActivity.this.showDialog.show("Xóa thành công!");
                                CameraActivity.this.list.clear();
                                CameraActivity.this.list.addAll(CameraActivity.this.imageDao.getALl());
                                CameraActivity.this.anh.setImageResource(R.drawable.ic_baseline_image_24);
                                CameraActivity.this.adapter.notifyDataSetChanged();
                                dialog.dismiss();
                                return;
                            }
                            CameraActivity.this.showDialog.show("Xóa thất bại!");
                        }
                    });
                    builder2.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder2.create();
                    dialog.getWindow().getAttributes().windowAnimations = R.style.up_down;
                    dialog.show();
                }
                return false;
            }
        });
    }

    public void showImagePickDialog() {
        String option[] = {"Camera", "Thư viện ảnh", "Xóa ảnh"};

        AlertDialog.Builder builder = new AlertDialog.Builder(CameraActivity.this);
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
                    anh.setImageResource(R.drawable.ic_baseline_image_24);
                }
            }
        });
        builder.create().show();
    }

    public void pickFromGallery() {
        Intent galleryIntent = new Intent("android.intent.action.PICK");
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    }

    public void pickFromCamera() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", "Temp Pic");
        contentValues.put("description", "Temp Description");
        this.image_uri = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", this.image_uri);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);
    }

    private void init() {
        this.anh = (ImageView) findViewById(R.id.ivImage);
        this.blink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.faded);
        this.imageDao = new ImageDao(this);
//        this.home = (Button) findViewById(R.id.btnHome);
    }

    public boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0;
    }

    public void requestStoragetPermission() {
        requestPermissions(this.storagePermission, STORAGE_REQUEST_CODE);
    }

    public boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") == 0;
        boolean result1 = ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0;
        if (!result || !result1) {
            return false;
        }
        return true;
    }

    public void requestCameraPermission() {
        requestPermissions(this.cameraPermission, CAMERA_REQUEST_CODE);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean storageAccept = true;
        if (requestCode != CAMERA_REQUEST_CODE) {
            if (requestCode == STORAGE_REQUEST_CODE && grantResults.length > 0) {
                if (grantResults[0] != 0) {
                    storageAccept = false;
                }
                if (storageAccept) {
                    pickFromGallery();
                } else {
                    this.showDialog.show("Vui lòng bật quyền thư viện");
                }
            }
        } else if (grantResults.length > 0) {
            boolean cameraAccept = grantResults[0] == 0;
            if (grantResults[1] != 0) {
                storageAccept = false;
            }
            if (!cameraAccept || !storageAccept) {
                this.showDialog.show("Không truy cập được vào camera!");
            } else {
                pickFromCamera();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            String str = "Up ảnh thành công!";
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                this.image_uri = data.getData();
                Picasso.with(this).load(this.image_uri).into(this.anh);
                this.imageDao.them(new Image(0, this.image_uri.toString()));
                this.list.clear();
                this.list.addAll(this.imageDao.getALl());
                this.adapter.notifyDataSetChanged();
                this.showDialog.show(str);
            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                Picasso.with(this).load(this.image_uri).into(this.anh);
                this.imageDao.them(new Image(0, this.image_uri.toString()));
                this.list.clear();
                this.list.addAll(this.imageDao.getALl());
                this.adapter.notifyDataSetChanged();
                this.showDialog.show(str);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in, R.anim.out);
        finish();
    }
}
