package com.styx.gta.homeui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.styx.gta.homeui.base.BaseAppCompatActivity;
import com.styx.gta.homeui.model.Home;
import com.styx.gta.homeui.model.User;
import com.styx.gta.homeui.util.Constants;
import com.styx.gta.homeui.util.Util;

import static com.styx.gta.homeui.util.Constants.HOME;

public class LoginActivity extends BaseAppCompatActivity implements View.OnClickListener {
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    // [END declare_auth_listener]
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;
    private Button buttonSignin;
    private SignInButton buttonGoogleSignIn;

    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DEBUG = true;

        super.onCreate(savedInstanceState);

        if (getmAuth().getCurrentUser() != null) {
            isLoggedIn(getmAuth().getCurrentUser());
        }

        setContentView(R.layout.activity_splash_screen);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        buttonSignin = (Button) findViewById(R.id.buttonSignin);
        buttonGoogleSignIn = (SignInButton) findViewById(R.id.buttonGoogleSignIn);

        buttonSignup.setOnClickListener(this);
        buttonSignin.setOnClickListener(this);
        buttonGoogleSignIn.setOnClickListener(this);
        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
    }

    private void isLoggedIn(FirebaseUser user) {
        debug("isLoggedIn");
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    private void firebaseAuthWithCredential(AuthCredential credential) {
        debug("firebaseAuthWithGoogle");
        getmAuth().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideProgressDialog();
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            onAuthSuccess(user);
                        } else {
                            Toast.makeText(getApplicationContext(), "Sign In Failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void signUpWithEmail() {
        debug("signUp");
        if (!validateForm()) {
            return;
        }
        showProgressDialog();
        //Test
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        getmAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        debug("createUser:onComplete:" + task.isSuccessful());
                        hideProgressDialog();
                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(getApplicationContext(), "Sign Up Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signInWithEmail() {
        debug("signIn");
        if (!validateForm()) {
            return;
        }
        showProgressDialog();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        getmAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        debug("signIn:onComplete:" + task.isSuccessful());
                        hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(getApplicationContext(), "Sign In Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(editTextEmail.getText().toString())) {
            editTextEmail.setError("Required");
            result = false;

        } else {
            String regex = "^(.+)@(.+)$";
            String test = editTextEmail.getText().toString();
            if (!test.matches(regex)) {
                editTextEmail.setError("Invalid Email");
                result = false;
            } else {
                editTextEmail.setError(null);
            }
        }

        if (TextUtils.isEmpty(editTextPassword.getText().toString())) {
            editTextPassword.setError("Required");
            result = false;
        } else {
            editTextPassword.setError(null);
        }

        return result;
    }

    private void signInWithGoogle() {
        debug("signInWithGoogle");
        showProgressDialog();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, Constants.GOOGLE_SIGN_IN);
    }

    private void onAuthSuccess(final FirebaseUser user) {
        debug("onAuthSuccess");
        if (getmAuth().getCurrentUser() != null) {
            getmDatabase().child("user").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        debug("USER EXISTS");
                    } else {
                        writeNewUser(user);
                        debug("USER NOT EXISTS");
                    }
                    debug("INTENT STARTED TO MAINACTIVITY");
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }

                @Override
                public void onCancelled(DatabaseError firebaseError) {
                }
            });
        }


    }

    private void writeNewUser(FirebaseUser mUser) {
        debug("writeNewUser");

        User user = new User(mUser.getUid(), Util.usernameFromEmail(mUser.getEmail()), mUser.getEmail());

        DatabaseReference mTempHomeReference = getmDatabase().child(HOME).push();

        Home mTempHome = new Home("BASE HOME");
        mTempHome.setHomeID(mTempHomeReference.getKey());

        mTempHomeReference.setValue(mTempHome);
        mTempHome.setAccess(getmDatabase(), getUid(), Home.USER_ACCESS_PRIVILLEGE.ADMIN);

        user.save(getmDatabase());
        user.addAppInstance(Util.getAppInstallUniqueID(getApplicationContext()));
        user.addHome(mTempHome.getHomeID(), User.HOME_STATUS.ACTIVE_HOME);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == Constants.GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                firebaseAuthWithCredential(GoogleAuthProvider.getCredential(result.getSignInAccount().getIdToken(), null));
            } else {
                hideProgressDialog();
                debug("ERROR GOOGLE SIGNIN GAILER");
                // Google Sign In failed, update UI appropriately
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.stopAutoManage(this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSignup:
                signUpWithEmail();
                break;
            case R.id.buttonSignin:
                signInWithEmail();
                break;
            case R.id.buttonGoogleSignIn:
                signInWithGoogle();
                break;
        }
    }
}
