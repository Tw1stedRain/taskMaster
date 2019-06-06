package com.Tw1stedRain.taskmaster;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseUser user;

    private static final int RC_SIGN_IN = 456;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    TaskLayoutAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        // recycler view everything
        List<Task> empty = new ArrayList<>();
        recyclerView = findViewById(R.id.recycleTasks);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TaskLayoutAdapter(empty);
        recyclerView.setAdapter(adapter);


        setUI();
    }

    public void onLoginClick(View view) {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build()
        );

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    private void setUI() {
        Button login = findViewById(R.id.login_btn);
        Button logout = findViewById(R.id.logout_btn);
        Button nav = findViewById(R.id.newTaskBtn);
        TextView name = findViewById(R.id.users_name);
        if (user != null) {
            login.setEnabled(false);
            logout.setEnabled(true);
            nav.setEnabled(true);
            name.setText(user.getDisplayName());
        } else {
            login.setEnabled(true);
            logout.setEnabled(false);
            nav.setEnabled(false);
            name.setText("");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                Log.d("MAINACTIVITY", "user email: " + user.getEmail());
            } else {
                Log.e("MAINACTIVITY", "FAIL!!");
            }
            setUI();
        }
    }

    public void onLogoutClick(View view) {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                        Log.d("USER", "we logged out");
                    }
                });
        setUI();
    }


//     retrieving info from the db
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
