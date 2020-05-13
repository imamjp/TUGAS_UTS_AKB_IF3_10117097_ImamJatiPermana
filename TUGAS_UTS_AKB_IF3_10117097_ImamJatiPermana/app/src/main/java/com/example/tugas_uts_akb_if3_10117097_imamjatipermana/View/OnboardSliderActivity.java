package com.example.tugas_uts_akb_if3_10117097_imamjatipermana.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.tugas_uts_akb_if3_10117097_imamjatipermana.MainActivity;
import com.example.tugas_uts_akb_if3_10117097_imamjatipermana.R;
import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

/** NIM : 10117097
 * Nama : Imam Jati Permana
 * Kelas : IF-3
 * Tanggal : 12-05-2020**/
public class OnboardSliderActivity extends TutorialActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        addFragment(new Step.Builder().setTitle("Add Friend")
                .setContent("Add Your Friend Info")
                .setBackgroundColor(Color.parseColor("#b5b3ac")) // int background color
                .setDrawable(R.drawable.vp1) // int top drawable
                .setSummary("Can record your friend info")
                .build());
        addFragment(new Step.Builder().setTitle("Change Friend Info")
                .setContent("Change Your Friend Info")
                .setBackgroundColor(Color.parseColor("#b5b3ac")) // int background color
                .setDrawable(R.drawable.vp2) // int top drawable
                .setSummary("Can change your friend info")
                .build());
        addFragment(new Step.Builder().setTitle("Display Friend List")
                .setContent("Display Friend List")
                .setBackgroundColor(Color.parseColor("#b5b3ac")) // int background color
                .setDrawable(R.drawable.vp3) // int top drawable
                .setSummary("Can display a list of friends who have been added")
                .build());
        setCancelText("Skip");

    }

    @Override
    public void finishTutorial() {
        super.finishTutorial();
        Intent intent = new Intent(OnboardSliderActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void currentFragmentPosition(int position) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
