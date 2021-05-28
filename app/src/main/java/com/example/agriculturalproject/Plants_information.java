package com.example.agriculturalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.hololo.tutorial.library.PermissionStep;
import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

public class Plants_information extends TutorialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //slide one
        addFragment(new Step.Builder().setTitle("PROJECT OBJECTIVES")
                .setContent("The main objective of this application is to help students choose their future specialization easily and know all the necessary information about all specializations in public universities.")
                .setBackgroundColor(Color.parseColor("#6D63EF")) // int background color
                .setDrawable(R.drawable.search_icon) // int top drawable
                .setSummary("1")
                .build());

        //slide tow
        addFragment(new Step.Builder().setTitle("PROJECT OBJECTIVES ")
                .setContent("The application help all student to register by Post Card .")
                .setBackgroundColor(Color.parseColor("#26A6B5")) // int background color
                .setDrawable(R.drawable.card_icon) // int top drawable
                .setSummary("2")
                .build());

        //slide three
        addFragment(new Step.Builder().setTitle("PROJECT OBJECTIVES ")
                .setContent("The application will educate the students and increase their knowledge regarding the university specialties such as the normal price and the parallel price ")
                .setBackgroundColor(Color.parseColor("#F89688")) // int background color
                .setDrawable(R.drawable.table_icon) // int top drawabledd
                .setSummary("3")
                .build());
        addFragment(new Step.Builder().setTitle("PROJECT OBJECTIVES ")
                .setContent("The application will educate the students and increase their knowledge regarding the university specialties such as the normal price and the parallel price ")
                .setBackgroundColor(Color.parseColor("#F89688")) // int background color
                .setDrawable(R.drawable.table_icon) // int top drawabledd
                .setSummary("4")
                .build());
        // Permission Step
//        addFragment(new PermissionStep.Builder().setTitle(getString(R.string.permission_title))
//                .setContent(getString(R.string.permission_detail))
//                .setBackgroundColor(Color.parseColor("#FF0957"))
//                .setDrawable(R.drawable.ss_1)
//                .setSummary(getString(R.string.continue_and_learn))
//                .setPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
//                .build());
    }
    @Override
    public void finishTutorial() {
finish();
    }
    @Override
    public void currentFragmentPosition(int position) {

    }
}