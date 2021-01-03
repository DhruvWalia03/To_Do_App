package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    public static final int RC_SIGN_IN=1;
    int i=0;
    String username1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth=FirebaseAuth.getInstance();
        authStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!= null)
                {
                    //user is signed in
                    if(i==0)
                       Toast.makeText(MainActivity.this, "Already Signed In. Welcome!", Toast.LENGTH_SHORT).show();
                    onSignedInInitialize(user.getDisplayName());
                }
                else
                {
                    //user is signed out
                    onSignedOutInitialize();
                    startActivityForResult(AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.EmailBuilder().build()
                                    ,new AuthUI.IdpConfig.GoogleBuilder().build()))
                                    .build(),
                                     RC_SIGN_IN);
                }
            }
        };

        TextView text1 =(TextView) findViewById(R.id.activity);
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyDay();
            }
        });
        TextView text2 =(TextView) findViewById(R.id.activity1);
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImportant();
            }
        });
        TextView text3 =(TextView) findViewById(R.id.activity2);
        text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewList();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == RC_SIGN_IN)
        {
            if(resultCode == RESULT_OK)
                Toast.makeText(this, "Signed In!",Toast.LENGTH_SHORT).show();
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Signed In Cancelled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.signout_menu , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.sign_out_menu) {
            AuthUI.getInstance().signOut(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void openNewList() {
        Intent i3= new Intent(this , NewListActivity.class);
        startActivity(i3);
    }

    private void openImportant() {
        Intent i2= new Intent(this , Important.class);
        startActivity(i2);
    }

    public void openMyDay() {
        Intent i1 =new Intent(this, MyDayActivity.class);
        startActivity(i1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(authStateListener != null)
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    public void onSignedInInitialize(String username)
    {
        username1 = username;
    }
    public void onSignedOutInitialize()
    {

    }
}
