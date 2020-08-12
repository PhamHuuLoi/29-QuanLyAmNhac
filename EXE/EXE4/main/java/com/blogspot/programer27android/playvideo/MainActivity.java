package com.blogspot.programer27android.playvideo;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int position = 0;
    VideoView videoView;
    ListView listView;
    ArrayList<String>videoList;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        setEvent();
    }
    void setControl()
    {
        videoView=findViewById(R.id.videoview);
        listView=findViewById(R.id.lvideo);
        videoList= new ArrayList<>();
        videoList.add("The Lion King");
        videoList.add("End Game");
        videoList.add("Spiderman");
        videoList.add("PUBG");
    }
    void setEvent(){
        adapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,videoList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.lion));
                        break;
                    case 1:
                        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.endgame));
                        break;
                    case 2:
                        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sipderman));
                        break;
                    case 3:
                        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.pubg1));
                        break;
                    default:
                        break;
                }
                videoView.setMediaController(new MediaController(MainActivity.this));
                videoView.requestFocus();
                videoView.start();
            }
        });
    }
    // Khi bạn xoay điện thoại, phương thức này sẽ được gọi
    // nó lưu trữ lại ví trí file video đang chơi.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Lưu lại vị trí file video đang chơi.
        savedInstanceState.putInt("CurrentPosition", videoView.getCurrentPosition());
        videoView.pause();
    }
    // Sau khi điện thoại xoay chiều xong. Phương thức này được gọi,
    // bạn cần tái tạo lại ví trí file nhạc đang chơi.
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Lấy lại ví trí video đã chơi.
        position = savedInstanceState.getInt("CurrentPosition");
        videoView.seekTo(position);
    }
}
