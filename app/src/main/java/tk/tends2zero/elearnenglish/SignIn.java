package tk.tends2zero.elearnenglish;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;


public class SignIn extends AppCompatActivity {

    private EditText email,pass;
    private String s_email,s_pass;
    ProgressBar bar;
    FirebaseAuth mAuth;
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = (EditText) findViewById(R.id.usrEmail);
        pass = (EditText) findViewById(R.id.usrPass);
        bar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

    }

    public void SignIn(View view) {
        s_email = email.getText().toString().trim();
        s_pass = pass.getText().toString().trim();

        boolean checkEmail=true;
        boolean checkPass=true;
        if(s_email.isEmpty() || !isValidEmail(s_email)){
            email.setError("Enter A Valid Email");
            email.requestFocus();
            checkEmail=false;
        }
        if(s_pass.length()<6) {
            pass.setError("Password is Required!");
            email.requestFocus();
            checkPass=false;
        }
        if(checkEmail && checkPass) {
            bar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(s_email, s_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    bar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Welcome " + s_email, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignIn.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else
        {
            checkEmail=true;
            checkPass=true;
        }

    }

    public void SignUp(View view) {
        Intent start = new Intent(this,SignUp.class);
        start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(start);
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        if(pattern.matcher(email).matches()) {
            return true;
        }
        return false;

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
