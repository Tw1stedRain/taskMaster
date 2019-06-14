package com.Tw1stedRain.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class UserActivity extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseUser user;

    String token;
    String id;

    EditText userName;
    EditText userBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        userName = findViewById(R.id.user_name);
        userBio = findViewById(R.id.user_bio);

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        id = user.getUid();


        retrieveDeviceId();
    }


    public void retrieveDeviceId(){
        FirebaseInstanceId instanceId = FirebaseInstanceId.getInstance();
        instanceId.getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    Log.e("UserActivity", "was not successful");
                    return;
                }
                token = task.getResult().getToken();
            }
        });
    }


    public void onAddUserSave(final View view) {

        User user = new User();
        user.setDisplayName(userName.getText().toString());
        user.setBio(userBio.getText().toString());

        user.getDeviceIds().add(token);

        db.collection("Users").document(id).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Log.e("UserActivity", "was not successful");
                    return;
                }
                onHomeClick(view);
            }
        });
    }


    public void onHomeClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
