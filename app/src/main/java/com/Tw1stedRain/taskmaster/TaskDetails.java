package com.Tw1stedRain.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TaskDetails extends AppCompatActivity {

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        user = FirebaseAuth.getInstance().getCurrentUser();
        TextView userDisplay = findViewById(R.id.details_user_name);
        userDisplay.setText(user.getDisplayName());

    }
}
