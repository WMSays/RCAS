package com.example.rcas;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class SNotifActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snotif);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.S_dash_BottomNavBar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId())
                {
                    case R.id.ic_item_home:
//                        TODO: logic to choose intent based on userType.
                        Intent intent1 = new Intent(SNotifActivity.this, SDashBoardActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.ic_item_notifications:
                        Intent intent2 = new Intent(SNotifActivity.this, SNotifActivity.class);
                        startActivity(intent2);


                        break;
                    case R.id.ic_item_settings:
                        Intent intent3 = new Intent(SNotifActivity.this, SettingsActivity.class);
                        startActivity(intent3);

                        break;

                }

                return false;
            }
        });
    }
    }

