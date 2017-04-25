package com.varsim.myexcua.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DatabaseError;
import com.varsim.myexcua.R;
import com.varsim.myexcua.library.Library;
import com.varsim.myexcua.model.CurrentUser;
import com.varsim.myexcua.model.Event;
import com.varsim.myexcua.model.FireDBManager;
import com.varsim.myexcua.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout mUsernameLayout;
    EditText mEditUsername, mEditPassword;
    Button mLoginButton;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        initializeui();

        mProgressDialog = new ProgressDialog(this);

        mLoginButton.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    // TODO: Add
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
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
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                String emailText = mEditUsername.getText().toString();
                String passwordText = mEditPassword.getText().toString();
                if (validateEmailId(emailText, this)) {
                    if (validatePassword(passwordText, this)) {
                        loginToUser(emailText, passwordText);
                    }
                }
                break;
            case R.id.btnSignUp:
                signUpWithData();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private boolean validateEmailId(String emailID, Context context) {
        if (TextUtils.isEmpty(emailID)) {
            Library.showToastShort(context, "Please enter EmailID.");
            return false;
        }else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailID).matches()){
            Library.showToastShort(context, "Please enter a valid Email Id.");
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password, Context context) {
        if (TextUtils.isEmpty(password)) {
            Library.showToastShort(context, "Please enter Password.");
            return false;
        }
        return true;
    }

    private void loginToUser(String userName, String password) {
        mProgressDialog.setMessage("Logging in...");
        mProgressDialog.show();
        mAuth.signInWithEmailAndPassword(userName, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Library.logDebug("signInWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            throughErrorFor(task.getException());
                            Library.logDebug("signInWithEmail:failed" + task.getException());
                        }else {
//                            createAnEvent(mAuth.getCurrentUser().getUid());
                            FireDBManager.getInstance().getUser(mAuth.getCurrentUser().getUid(), new FireDBManager.UserRetrievalCompletion() {
                                @Override
                                public void successfullyRetrieved(User user) {
                                    mProgressDialog.dismiss();
                                    Library.logDebug(user.getName());
                                    Intent i = new Intent(LoginActivity.this,EventsActivity.class);
                                    startActivity(i);
                                    overridePendingTransition(R.anim.activity_slide_left_in,
                                            R.anim.activity_slide_left_out);
                                }

                                @Override
                                public void failedToRetrieve(DatabaseError var1) {
                                    mProgressDialog.dismiss();
                                }
                            });

                        }
                    }
                });
    }

    private void throughErrorFor(Exception exception) {
        if (exception instanceof FirebaseAuthInvalidUserException) {
            Library.showToastShort(LoginActivity.this, "Please check your email and password");
        }else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
            Library.showToastShort(LoginActivity.this, "Please check your email and password");
        }else {
            Library.showToastShort(LoginActivity.this, "Login failed, please try again");
        }
    }






    private void signUpWithData() {
        String UID = "gIcfhMTsBjOESdYZS9gyF2tofc32";

        CurrentUser currentUser = new CurrentUser(UID);
        currentUser.setEmailID("varsim1@gmail.com");
        currentUser.setName("Varghese Simon");
        currentUser.setRole("Trainer");
        currentUser.setPhoneNumber("999999");
        currentUser.saveUser();
    }

    private void createAnEvent(String userID) {
        Event event = new Event(userID);
        event.setEventStartDate(new Date());
        event.setEventType("Swimming");
        event.setEventEndDate(new Date(System.currentTimeMillis() + 60 * 60 * 1000));
        event.createEvent();

        Event event2 = new Event(userID);
        event2.setEventStartDate(new Date(System.currentTimeMillis() + 23 * 60 * 60 * 1000));
        event2.setEventType("Swimming");
        event2.setEventEndDate(new Date(System.currentTimeMillis() + 60 * 60 * 1000));
        event2.createEvent();
    }
    private Date dateForString(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd:HH:mm");
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
