package com.example.kashyap.hm14_putta;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.media.MediaPlayer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.SurfaceHolder;
import android.widget.Toast;

import com.example.kashyap.hm14_putta.R;

import java.util.ArrayList;
import java.util.Random;

public class MainThread extends Thread {
    private SurfaceHolder holder;
    private Handler handler;		// required for running code in the UI thread
    private boolean isRunning = false;
    static Context context;
    Paint paint;
    int touchx, touchy;	// x,y of touch event
    boolean touched;	// true if touch happened
    private static final Object lock = new Object();

    public MainThread (SurfaceHolder surfaceHolder, Context context) {
        holder = surfaceHolder;
        MainThread.context = context;
        handler = new Handler();
        touched = false;
    }

    public void setRunning(boolean b) {
        isRunning = b;	// no need to synchronize this since this is the only line of code to writes this variable
    }

    // Set the touch event x,y location and flag indicating a touch has happened
    public void setXY (int x, int y) {
        synchronized (lock) {
            touchx = x;
            touchy = y;
            this.touched = true;
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            // Lock the canvas before drawing
            Canvas canvas = holder.lockCanvas();
            if (canvas != null) {
                // Perform drawing operations on the canvas
                render(canvas);
                // After drawing, unlock the canvas and display it
                holder.unlockCanvasAndPost (canvas);
            }
        }
    }

    // Loads graphics, etc. used in game
    private void loadData (Canvas canvas) {
        Bitmap bmp;
        int newWidth, newHeight;
        float scaleFactor;

        // Create a paint object for drawing vector graphics
        paint = new Paint();

        // Load score bar
        // ADD CODE HERE

        // Load food bar
        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.foodbar);
        // Compute size of bitmap needed (suppose want height = 10% of screen height)
        newHeight = (int)(canvas.getHeight() * 0.1f);
        // Scale it to a new size
        Assets.foodbar = Bitmap.createScaledBitmap (bmp, canvas.getWidth(), newHeight, false);
        // Delete the original
        bmp = null;

        // Load ant1
        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.roach1_250);
        // Compute size of bitmap needed (suppose want width = 20% of screen width)
        newWidth = (int)(canvas.getWidth() * 0.2f);
        // What was the scaling factor to get to this?
        scaleFactor = (float)newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int)(bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.roach1 = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
        // Delete the original
        bmp = null;

        // Load ant2
        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.roach2_250);
        // Compute size of bitmap needed (suppose want width = 20% of screen width)
        newWidth = (int)(canvas.getWidth() * 0.2f);
        // What was the scaling factor to get to this?
        scaleFactor = (float)newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int)(bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.roach2 = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
        // Delete the original
        bmp = null;

        // Load score bar
        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.score);
        // Compute size of bitmap needed (suppose want width = 20% of screen width)
        newWidth = (int)(canvas.getWidth() * 1.07f);
        // What was the scaling factor to get to this?
        scaleFactor = (float)newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int)(bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.scorebar = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
        // Delete the original
        bmp = null;

        // Load the other bitmaps similarly
        // ...

        // Load roach3 (dead bug)
        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.roach3_250);
        // Compute size of bitmap needed (suppose want width = 20% of screen width)
        newWidth = (int)(canvas.getWidth() * 0.2f);
        // What was the scaling factor to get to this?
        scaleFactor = (float)newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int)(bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.roach3 = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
        // Delete the original
        bmp = null;


        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.life);
        // Compute size of bitmap needed (suppose want width = 20% of screen width)
        newWidth = (int)(canvas.getWidth() * 0.2f);
        // What was the scaling factor to get to this?
        scaleFactor = (float)newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int)(bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.lifeup = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
        // Delete the original
        bmp = null;

        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.lifeclick);
        // Compute size of bitmap needed (suppose want width = 20% of screen width)
        newWidth = (int)(canvas.getWidth() * 0.2f);
        // What was the scaling factor to get to this?
        scaleFactor = (float)newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int)(bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.lifeclick = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
        // Delete the original
        bmp = null;


        Assets.al = new ArrayList<Bug>();
        // Create a bug
        for(int i=0; i<5;i++)
        {
            Assets.bug = new Bug();
            Assets.al.add(Assets.bug);
        }

        // Create a bug


        Assets.lifeobject= new Life();

    }

    // Load specific background screen
    private void loadBackground (Canvas canvas, int resId) {
        // Load background
        Bitmap bmp = BitmapFactory.decodeResource (context.getResources(), resId);
        // Scale it to fill entire canvas
        Assets.background = Bitmap.createScaledBitmap (bmp, canvas.getWidth(), canvas.getHeight(), false);
        // Delete the original
        bmp = null;
    }
    public void playAudio(int resid) {

        if (Assets.mp != null) {
            Assets.mp.release();
            Assets.mp = null;
        }

        Assets.mp= MediaPlayer.create(context, resid);

        try{
            Assets.mp.start();
            Assets.mp.setLooping(true);

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private void render (Canvas canvas) {
        int i, x, y;

        switch (Assets.state) {
            case GettingReady:
                // Load a special "getting ready screen"
                loadBackground (canvas, R.drawable.getrid);
                // Load data and other graphics needed by game
                loadData(canvas);
                // Draw the background screen
                canvas.drawBitmap (Assets.background, 0, 0, null);
                // Play a sound effect
                Assets.soundPool.play(Assets.sound_getready, 1, 1, 1, 0, 1);
                // Start a timer
                Assets.gameTimer = System.nanoTime() / 1000000000f;
                // Go to next state
                Assets.state = Assets.GameState.Starting;
                break;
            case Starting:
                // Draw the background screen
                canvas.drawBitmap (Assets.background, 0, 0, null);
                Assets.presentScore= 0;
                // Has 3 seconds elapsed?
                float currentTime = System.nanoTime() / 1000000000f;
                if (currentTime - Assets.gameTimer >= 3) {
                    // Load game play background
                    playAudio(R.raw.background);
                    loadBackground (canvas, R.drawable.wood);
                    // Goto next state
                    Assets.state = Assets.GameState.Running;
                }
                break;
            case Running:
                // Draw the background screen
                canvas.drawBitmap (Assets.background, 0, 0, null);
                canvas.drawBitmap (Assets.scorebar, 0, 0, null);
                int spacing = 30;
                int size=(int)(canvas.getWidth() * 0.05f)*2;
                paint.setColor(Color.BLUE);
                paint.setStyle(Style.FILL);
                paint.setTextSize(size);
                Assets.prasenrScorePrint= Integer.toString(Assets.presentScore);
                canvas.drawText(Assets.prasenrScorePrint, 15, (size/2)+spacing, paint);
                // Draw the score bar at top of screen
                // ADD CODE HERE
                // Draw the score on the score bar
                // canvas.drawText(....)

                // Draw the foodbar at bottom of screen
                canvas.drawBitmap (Assets.foodbar, 0, canvas.getHeight()-Assets.foodbar.getHeight(), null);
                // Draw one circle for each life at top right corner of screen
                // Let circle radius be 5% of width of screen
                int radius = (int)(canvas.getWidth() * 0.05f);
                spacing = 8; // spacing in between circles
                x = canvas.getWidth() - radius - spacing;	// coordinates for rightmost circle to draw
                y = radius + spacing;
                for (i=0; i<Assets.livesLeft; i++) {
                    paint.setColor(Color.GREEN);
                    paint.setStyle(Style.FILL);
                    canvas.drawCircle(x, y, radius, paint);
                    paint.setColor(Color.BLACK);
                    paint.setStyle(Style.STROKE);
                    canvas.drawCircle(x, y, radius, paint);
                    // Reposition to draw the next circle to the left
                    x -= (radius*2 + spacing);
                }
//				for(int j=0;j<10;j++){
                // Process a touch
                if (touched) {
                    // Set touch flag to false since we are processing this touch now
                    touched = false;
                    // See if this touch killed a bug
                    boolean bugKilled = false;

                    boolean lifeuptouched = false;
                    // See if this touch killed a bug
                    for( i = 0 ;i<5;i++)
                    {
                        if(bugKilled)
                            break;
                        bugKilled = Assets.al.get(i).touched(canvas, touchx, touchy);
                    }
                    if (bugKilled)
                    {

                        Random rand = new Random();
                        int n = rand.nextInt(3);
                        switch(n) {
                            case 0:
                                Assets.soundPool.play(Assets.sound_squish0, 1, 1, 1, 0, 1);
                                break;
                            case 1:
                                Assets.soundPool.play(Assets.sound_squish1, 1, 1, 1, 0, 1);
                                break;
                            case 2:
                                Assets.soundPool.play(Assets.sound_squish2, 1, 1, 1, 0, 1);
                                break;
                        }
                    }
                    else
                        Assets.soundPool.play(Assets.sound_thump, 1, 1, 1, 0, 1);
                    //touch event for life
                    if(Assets.livesLeft<3)
                        Assets.lifeobject.touched(canvas, touchx, touchy);
                }

                // Draw dead bugs on screen
                for( i = 0 ;i<5;i++)
                {
                    Assets.al.get(i).drawDead(canvas);
                    // Move bugs on screen
                    Assets.al.get(i).move(canvas);
                    // Bring a dead bug to life?
                    Assets.al.get(i).birth(canvas);
                }


                if(Assets.livesLeft<3){
                    Assets.lifeobject.drawDead(canvas);
                    Assets.lifeobject.move(canvas);
                    Assets.lifeobject.birth(canvas);
                }

                paint.setColor(Color.YELLOW);
                paint.setStyle(Style.FILL);
                paint.setTextSize(45);
                //canvas.drawText(score, 10,40, paint);
                // ADD MORE CODE HERE TO PLAY GAME



                // Draw dead bugs on screen
                Assets.bug.drawDead(canvas);
                // Move bugs on screen
                Assets.bug.move(canvas);
                //Assets.bug.move1(canvas);
                // Bring a dead bug to life?
                Assets.bug.birth(canvas);
//				}
                // ADD MORE CODE HERE TO PLAY GAME


                // Are no lives left?
                if (Assets.livesLeft == 0)
                    // Goto next state
                    Assets.state = Assets.GameState.GameEnding;
                break;
            case GameEnding:
                // Show a game over message
                handler.post(new Runnable() {
                    public void run() {


                        Toast.makeText(context, "Game Over", Toast.LENGTH_SHORT).show();
                        if (Assets.mp != null) {
                            Assets.mp.release();
                            Assets.mp = null;

                        }
                    }});
                // Goto next state
                Assets.state = Assets.GameState.GameOver;
                break;
            case GameOver:
                // Fill the entire canvas' bitmap with 'black'
                canvas.drawColor(Color.BLACK);
                int endspacing = 50;
                int endTextSize=(int)(canvas.getWidth() * 0.05f)*2;
                paint.setColor(Color.RED);
                paint.setStyle(Style.FILL);
                paint.setTextSize(endTextSize);
                Assets.prasenrScorePrint= Integer.toString(Assets.presentScore);
                //canvas.drawBitmap (Assets.highscoreimage, 0, 0, null);
                canvas.drawText("Present Score: ", canvas.getWidth()*0.15f, canvas.getHeight()*0.20f, paint);
                paint.setTextSize(endTextSize+20);
                canvas.drawText(Assets.prasenrScorePrint, canvas.getWidth()*0.45f, canvas.getHeight()*0.35f, paint);

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainThread.context);
                int highscore;
                highscore = sharedPreferences.getInt("Assets.highScore", 0);
                if(Assets.presentScore>highscore){
                    //Assets.highscore=Assets.presentScore;
                    paint.setTextSize(endTextSize);
                    canvas.drawText(" New highest score"  , canvas.getWidth()*0.15f, canvas.getHeight()*0.45f+(endspacing/2)+endspacing*3, paint);

                }
                break;
        }
    }

}
