package com.example.kashyap.hm14_putta;

import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.util.ArrayList;

public class Assets {
    static Bitmap background;
    static Bitmap foodbar;
    static Bitmap roach1;
    static Bitmap roach2;
    static Bitmap roach3;


    // States of the Game Screen
    enum GameState {
        GettingReady,	// play "get ready" sound and start timer, goto next state
        Starting,		// when 3 seconds have elapsed, goto next state
        Running, 		// play the game, when livesLeft == 0 goto next state
        GameEnding,	// show game over message
        GameOver,		// game is over, wait for any Touch and go back to title activity screen
    };
    static GameState state;		// current state of the game
    static float gameTimer;	// in seconds
    static int livesLeft;		// 0-3
    public static int highscore;
    public static int presentScore;

    public static String prasenrScorePrint;

    static SoundPool soundPool;
    static int sound_getready;
    static int sound_squish0;
    static int sound_squish1;
    static int sound_squish2;
    static int sound_thump;
    public static Bitmap scorebar;

    static Bug bug;
    static Life lifeobject;
    protected static MediaPlayer mp;

    public static ArrayList<Bug> al;

    public static Bitmap highscoreimage;
    public static Bitmap lifeup;
    public static Bitmap lifeclick;
}
