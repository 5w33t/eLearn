package tk.tends2zero.elearnenglish;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "Hi" ;
    private EditText email,pass;
    private String s_email,s_pass;
    private FirebaseAuth mAuth;
    ProgressBar bar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = (EditText) findViewById(R.id.usrEmail);
        pass = (EditText) findViewById(R.id.usrPass);
        bar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
    }

    public void SignUp(View view)
    {
        registerUser();
    }

    private void registerUser() {
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
            pass.setError("Password minimum length is 6");
            email.requestFocus();
            checkPass=false;
        }

        if(checkEmail && checkPass) {
            bar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(s_email, s_pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            bar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");

                                Toast.makeText(getApplicationContext(), "Registration Successful, Please Sign In", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(SignUp.this,SignIn.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                                //  FirebaseUser user = mAuth.getCurrentUser();
                                //  updateUI(user);
                            }
                            else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());

                                if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(getApplicationContext(), "User Already Exists", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                }
                                //   updateUI(null);
                            }

                            // ...
                        }
                    });
        }
        else {
            checkEmail=true;
            checkPass=true;

        }
    }

    public void SignIn(View view)
    {
        Intent start = new Intent(this,SignIn.class);
        start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(start);
        // finish();
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        if(pattern.matcher(email).matches()) {
            return true;
        }
        return false;

    }
}
