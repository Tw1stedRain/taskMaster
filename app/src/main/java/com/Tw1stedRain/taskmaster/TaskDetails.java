package com.Tw1stedRain.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
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

    // radio button handling
    public void onRadioFinished(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.accepted:
                if (checked)
                    // TODO: set task finished to false
                    break;
            case R.id.finished:
                if (checked)
                    // TODO: set task finished to true
                    break;
        }
    }

    // nav buttons

    public void navHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void navNewTask(View view) {
        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivity(intent);
    }
}
