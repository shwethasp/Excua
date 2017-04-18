package com.varsim.myexcua.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.varsim.myexcua.R;
import com.varsim.myexcua.library.Library;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout mUsernameLayout;
    EditText mEditUsername, mEditPassword;
    Button mLoginButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        initializeui();
        mLoginButton.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }

    private void initializeui() {
        mUsernameLayout = (TextInputLayout)findViewById(R.id.layout_username);
      //  mEditUsername = (EditText)findViewById(R.id.edit_username);
        mLoginButton = (Button)findViewById(R.id.login_btn);

        mUsernameLayout.setHint("UserName"); // set the hint to be displayed in the floating label.
        mEditUsername = (EditText) findViewById(R.id.edt_email_id);
        mEditPassword = (EditText) findViewById(R.id.edt_password);
    }

    @Override
    public void onClick(View v) {
        Library.hideKeyboard(LoginActivity.this);
        switch (v.getId()){
            case R.id.login_btn:

                String emailText = mEditUsername.getText().toString();
                String passwordText = mEditPassword.getText().toString();
                if (validateEmailId(emailText, this)) {
                    if (validatePassword(passwordText, this)) {
                        loginToUser(emailText, passwordText);
                    }
                }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private boolean validateEmailId(String emailID, Context context) {
        if (emailID == null || emailID.length() == 0 ) {
            Library.showToastShort(context, "Please enter EmailID.");
            return false;
        }else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailID).matches()){
            Library.showToastShort(context, "Please enter a valid Email Id.");
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password, Context context) {
        if (password == null || password.length() == 0) {
            Library.showToastShort(context, "Please enter Password.");
            return false;
        }
        return true;
    }

    private void loginToUser(String userName, String password) {
        mAuth.signInWithEmailAndPassword(userName, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Library.logDebug("signInWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                                Library.showToastShort(LoginActivity.this, "Please check your email and password");
                            }else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Library.showToastShort(LoginActivity.this, "Please check your email and password");
                            }else {
                                Library.showToastShort(LoginActivity.this, "Login failed, please try again");
                            }
                            Library.logDebug("signInWithEmail:failed" + task.getException());
                        }else {
                            Intent i = new Intent(LoginActivity.this,EventsActivity.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.activity_slide_left_in,
                                    R.anim.activity_slide_left_out);
                        }
                    }
                });
    }

    private void signUpWithData() {

    }
}
