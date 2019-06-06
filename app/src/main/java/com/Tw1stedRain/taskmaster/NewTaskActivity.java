package com.Tw1stedRain.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewTaskActivity extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseUser user;

    EditText name;
    EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        TextView userDisplay = findViewById(R.id.userDisplay);
        userDisplay.setText(user.getDisplayName());
    }

    // adding task to db
    public void newTaskClick(View view) {
        name = findViewById(R.id.name_task);
        description = findViewById(R.id.description_task);
        Task task = new Task();
        task.setName(name.getText().toString());
        task.setDescription(description.getText().toString());
        task.setAssignedUser(user.getDisplayName());


        db.collection("tasks")
                .add(task)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TASK", "added ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TASK", "IT FAILED!!! AHHHHH");
                    }
                });
    }

    // nav buttons

    public void goHomeClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
