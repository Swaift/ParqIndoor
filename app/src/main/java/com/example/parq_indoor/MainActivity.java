package com.example.parq_indoor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.parq_indoor.Layout.LayoutGraph;
import com.example.parq_indoor.Layout.LayoutGraphBuilder;

public class MainActivity extends AppCompatActivity {
    public LayoutGraph layoutGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        layoutGraph =
                LayoutGraphBuilder.buildLayoutGraphFromJson(this, R.raw.demo_layout);
        ParkingView parkingView = findViewById(R.id.parkingView);
        parkingView.setLayoutGraph(layoutGraph);
    }
}
