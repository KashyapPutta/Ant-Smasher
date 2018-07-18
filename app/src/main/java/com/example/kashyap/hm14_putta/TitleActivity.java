package com.example.kashyap.hm14_putta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.kashyap.hm14_putta.R;

public class TitleActivity extends Activity {
    double buttonZonex;
    float buttonZoney;
    float buttonSpacing;
    Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }

    public class MyView extends View {
        Bitmap bmp, play, highScore;
        boolean switchToGameScreen;
        public MyView(Context context) {
            super(context);
            bmp = BitmapFactory.decodeResource (getResources(), R.drawable.titlescreen);
            play= BitmapFactory.decodeResource (getResources(), R.drawable.play1);
            highScore= BitmapFactory.decodeResource (getResources(), R.drawable.highscore1);
            switchToGameScreen = false;
        }

        @Override
        protected void onDraw (Canvas canvas) {

            float x,y;
            // Draw the title full screen
            Rect dstRect = new Rect();
            canvas.getClipBounds(dstRect);
            canvas.drawBitmap(bmp, null, dstRect, null);
            buttonZonex=canvas.getWidth()*0.4f;
            buttonZoney=canvas.getHeight()*0.5f;
            buttonSpacing=canvas.getHeight()*0.2f;
            x=(float)buttonZonex;
            y=buttonZoney;
            canvas.drawBitmap (play, x, y, null);
            y+=buttonSpacing;
            canvas.drawBitmap (highScore, x, y, null);
            // On click switch to main (game) activity
            if (switchToGameScreen) {
                switchToGameScreen = false;
                startActivity(new Intent(TitleActivity.this, MainActivity.class));
            }
            else
                invalidate();
        }
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            // On click set flag to switch to main (game) activity
            float touchx, touchy, x, y, a,b;
            int action = event.getAction();
            touchx = event.getX();
            touchy = event.getY();
            x=(float)buttonZonex+(play.getWidth()/2);
            y=buttonZoney+(play.getHeight()/2);
            a=(float)buttonZonex+(highScore.getWidth());
            b=y+buttonSpacing+(highScore.getHeight()/2);

            if (action== MotionEvent.ACTION_DOWN){
                float dis = (float)(Math.sqrt ((touchx - x) * (touchx - x) + (touchy - y) * (touchy - y)));
                if(dis<=play.getHeight()*0.6f)
                    switchToGameScreen = true;
                float dishigh = (float)(Math.sqrt ((touchx - x) * (touchx - x) + (touchy - b) * (touchy - b)));
                if(dishigh<=highScore.getHeight()*0.6f)
                    startActivity (new Intent(this.getContext(), PrefActivity.class));
                Log.i("ProjectLogging", "Touch at: " + x + "," + y);
            }
            return true; // to indicate we have handled this event
        }
    }

    @Override
    protected void onResume() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Assets.highScore", Assets.highscore);
        editor.commit();
        super.onResume();

    }

    @Override
    protected void onPause() {





        super.onPause();
    }
}