package com.example.floatimagetext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class FloatActionButtonTestActivity extends AppCompatActivity {

    DragFloatActionButton dragFloatActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_action_button_test);
        dragFloatActionButton = findViewById(R.id.tupian);
        dragFloatActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dragFloatActionButton.hide();
                Toast.makeText(FloatActionButtonTestActivity.this,"yes",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
