package com.Tw1stedRain.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TaskDetails extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseUser user;

    TextView userDisplay;
    TextView taskName;
    TextView taskDescription;

    String taskId;
    com.Tw1stedRain.taskmaster.Task thisTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        Intent intent = getIntent();
        taskId = intent.getStringExtra("taskId");

        userDisplay = findViewById(R.id.details_user_name);
        taskName = findViewById(R.id.task_detail_name);
        taskDescription = findViewById(R.id.task_detail_description);


        getMyTask();
    }

    // radio button handling
    public void onRadioFinished(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.accepted:
                if (checked)
                    thisTask.setFinished(false);
                break;
            case R.id.finished:
                if (checked)
                    thisTask.setFinished(true);
                break;
        }
    }

    public void getMyTask() {
        db.collection("tasks").document(taskId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot snap = task.getResult();

                            thisTask = snap.toObject(com.Tw1stedRain.taskmaster.Task.class);
                            thisTask.setId(snap.getId());

                            userDisplay.setText(user.getDisplayName());
                            taskName.setText(thisTask.getName());
                            taskDescription.setText(thisTask.getDescription());
                        }
                    }

                });
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
