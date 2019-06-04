package com.Tw1stedRain.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    TaskLayoutAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        // might need refactoring
        List<Task> empty = new ArrayList<>();

        recyclerView = findViewById(R.id.recycleTasks);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TaskLayoutAdapter(empty);
        recyclerView.setAdapter(adapter);

    }


    // retrieving info from the db
    public void onReadClick(View view) {
        //TODO: will remove button at some point
        db.collection("tasks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot snap = task.getResult();
                            List<Task> tasks = new ArrayList<>();
                            for (DocumentSnapshot doc : snap.getDocuments()) {
                                Log.d("TASK", "ID: " + doc.getId() + ", Name: " + doc.get("name"));

                                Task fb = doc.toObject(Task.class);
                                tasks.add(fb);
                            }
                            adapter.setTasks(tasks);
                        }
                    }
                });
    }

    // Navigation Button(s)

    public void newTaskDirectory(View view) {
        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivity(intent);

    }
}
