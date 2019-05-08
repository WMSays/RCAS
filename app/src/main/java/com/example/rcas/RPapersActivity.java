package com.example.rcas;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RPapersActivity extends AppCompatActivity {
FloatingActionButton rpapers_add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpapers);
        Init();
        rpapers_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RPapersActivity.this, RAddPaperActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Init() {
        rpapers_add= (FloatingActionButton)findViewById(R.id.rpapers_add);
    }
}
