package com.qf.level.day40zxingdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.zijunlin.Zxing.Demo.CaptureActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView= (ImageView) findViewById(R.id.imageId);
    }
    //生成二维码
    public void creatZxing(View view){
        //首先来定义二维码的大小
        int width= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                300,getResources().getDisplayMetrics());
        int height= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                300,getResources().getDisplayMetrics());
        BitMatrix matrix=null;//定义一个矩阵
        QRCodeWriter writer=new QRCodeWriter();//二维码的生成器
        try {
            //用生成器来对内容进行编码
            matrix=writer.encode("我爱你", BarcodeFormat.QR_CODE,width,height);
            //定义一个bitmap来放置位图的内容
            Bitmap bitmap=Bitmap.createBitmap(width,height, Bitmap.Config.RGB_565);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if (matrix.get(i,j)){//判断矩阵对应点是否有值
                        if (i<width/2 && j<height){
                            bitmap.setPixel(i,j, Color.RED);
                        }else{
                            bitmap.setPixel(i,j,Color.BLUE);
                        }

                    }else{
                        bitmap.setPixel(i,j, Color.WHITE);
                    }

                }
            }

            Bitmap bitmap1= BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
            int width1=bitmap1.getWidth();
            int height1=bitmap1.getHeight();//获取要绘制的bitmap的宽高
            Canvas canvas=new Canvas(bitmap);//将bitmap作为画布
            Rect rect=new Rect(width/2-width1/2,height/2-height1/2,
                    width/2+width1/2,height/2+height1/2);//要绘制的区域
            Rect rect1=new Rect(0,0,
                    width1,height1);//要绘制的图片的大小

            canvas.drawBitmap(bitmap1,rect1,rect,new Paint());//将bitmap1绘制到bitmap 上


            imageView.setImageBitmap(bitmap);//最后将生成的bitmap显示到imageview
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }
    //扫描二维码
    public void captureZxing(View view){
        //启动zxing的activity来进行扫描
        Intent intent=new Intent(this, CaptureActivity.class);
        startActivityForResult(intent,200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==200 && resultCode==RESULT_OK){
            Toast.makeText(getApplicationContext(),"扫描成功",Toast.LENGTH_LONG).show();
            Log.d("zsp","扫描成功");
            String result = data.getStringExtra("result");
            Log.d("zsp","扫描成功"+result);
            //得到结果后可以根据二维码内容来处理

        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}
