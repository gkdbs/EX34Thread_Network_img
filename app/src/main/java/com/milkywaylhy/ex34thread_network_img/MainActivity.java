package com.milkywaylhy.ex34thread_network_img;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv= findViewById(R.id.iv);
    }

    public void clickBtn(View view) {

        //Main Thread 는 네트워크 작업 불가!
        //네트워크 작업은 반드시 허가(permission) 받아야 함 - AndroidManifest.xml 에서..
        Thread t= new Thread(){
            @Override
            public void run() {
                //Network에 있는 이미지를 읽어와서
                //이미지 뷰에 설정!
                String imgUrl= "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS-05pQYZoj7zsu5RXH1OLh22dZYTzV2caPpA&usqp=CAU";

                //서버 주소까지 연결되는 무지개로드(Stream) 열기
                try {
                    // 무지개로드를 열어주는 해임달 객체 생성
                    URL url= new URL(imgUrl);
                    //무지개로드 열기
                    InputStream is= url.openStream();

                    // Stream을 통해 읽어들인 파일 데이터를
                    //이미지를 가지는 객체로 생성
                    final Bitmap bm= BitmapFactory.decodeStream(is);

                    //별도의 Thread는 UI 변경 작업 불가!!
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iv.setImageBitmap(bm);
                        }
                    });


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

}
