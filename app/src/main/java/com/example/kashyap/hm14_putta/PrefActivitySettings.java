package com.example.kashyap.hm14_putta;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;


public class PrefActivitySettings extends PreferenceFragment
{
    final static String TAG = "PrefActivitySettings";




    public PrefActivitySettings ()
    {
    }



    @Override
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.pref_activity_settings);
    }


    @Override
    public void onResume()
    {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        Preference pref;
        int highscore;

        highscore = sharedPreferences.getInt("Assets.highScore", 0);
        pref = getPreferenceScreen().findPreference("key_highscore_info");
        pref.setSummary(String.valueOf(highscore));
        super.onResume();
    }





}
