package com.example.rcas;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;

public class RDashBoardActivity extends AppCompatActivity {
    CardView r_dash_CardView_MyPapers;
    CardView r_dash_CardView_MyDeadlines;
    CardView r_dash_CardView_ContinueReading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdash_board);
        Init();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.S_dash_BottomNavBar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId())
                {
                    case R.id.ic_item_home:
//                        TODO: logic to choose intent based on userType.
                        Intent intent1 = new Intent(RDashBoardActivity.this, RDashBoardActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.ic_item_notifications:
                        Intent intent2 = new Intent(RDashBoardActivity.this, SNotifActivity.class);
                        startActivity(intent2);


                        break;
                    case R.id.ic_item_settings:
                        Intent intent3 = new Intent(RDashBoardActivity.this, SettingsActivity.class);
                        startActivity(intent3);

                        break;

                }

                return false;
            }
        });

        r_dash_CardView_MyPapers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(RDashBoardActivity.this,RPapersActivity.class);
                startActivity(intent);
            }
        });

    }

    private void Init() {
        r_dash_CardView_MyPapers= (CardView)findViewById(R.id.r_dash_CardView_MyPapers);
        r_dash_CardView_MyDeadlines= (CardView)findViewById(R.id.r_dash_CardView_MyDeadlines);
        r_dash_CardView_ContinueReading=(CardView)findViewById(R.id.r_dash_CardView_ContinueReading);
    }

}

