package com.example.bassem.atome_haffeli;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.EachExceptionsHandler;
import com.kosalgeek.asynctask.ExceptionHandler;
import com.kosalgeek.asynctask.PostResponseAsyncTask;
import com.example.bassem.atome_haffeli.Model.Username;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView mUserView;
    private EditText mPasswordView;
    private View mLoginFormView;
    public static String userlogin;
    public static String userpassword;
    public static String chemail;
    private CheckBox checkBoxNewAccount, checkBoxPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the login form.
        mUserView = (AutoCompleteTextView) findViewById(R.id.TF_username);
        //populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.TF_password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button loginbtn = (Button) findViewById(R.id.login_button);
        checkBoxNewAccount =(CheckBox) findViewById(R.id.checkBoxNewAccount);
        checkBoxPassword =(CheckBox) findViewById(R.id.checkBoxPassword);
        checkBoxNewAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is checkBoxNewAccount checked?
                if (((CheckBox) v).isChecked()) {
                    Toast.makeText(MainActivity.this,
                            "new account!", Toast.LENGTH_LONG).show();

                    Intent i = new Intent(MainActivity.this, NewAccountActivity.class);
                    startActivity(i);
                }

            }
        });
        checkBoxPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is checkBoxPassword checked?
                if (((CheckBox) v).isChecked()) {
                    Toast.makeText(MainActivity.this,
                            "new password!", Toast.LENGTH_LONG).show();
                }

            }
        });


        loginbtn.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                hideKeyboard();
                attemptLogin();


            }/*
                EditText a = (EditText) findViewById(R.id.TF_username);
                String str = a.getText().toString();
                Toast.makeText(LoginActivity.this, str, Toast.LENGTH_LONG).show();
                Intent i = new Intent(LoginActivity.this, SecondActivity.class);
                startActivity(i);*/

        });
        mLoginFormView = findViewById(R.id.login_form);
    }

    private void attemptLogin() {
        // Reset errors.
        mUserView.setError(null);
        mPasswordView.setError(null);

        String username = mUserView.getText().toString();
        String password = mPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;

        //postData.put("mobile","android");
        if (!validateUserName(username)) {
            mUserView.setError(getString(R.string.error_invalid_username));
        }
        if (!validatePassword(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(username)) {
            mUserView.setError(getString(R.string.error_field_required));
            focusView = mUserView;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            Log.v("", "init");
            //showProgress(true);
            //System.out.print("erreur-1");
            HashMap postData = new HashMap();
            //System.out.print("erreur0");
            //Log.v("","erreur0");
            postData.put("mUserView", username);
            //Log.v("","erreur1");
            postData.put("mPasswordView", password);//System.out.print("erreur2");Log.v("","erreur2");
            //PostResponseAsyncTask task=new PostResponseAsyncTask((AsyncResponse) this, postData);
//            try {

            PostResponseAsyncTask task = new PostResponseAsyncTask(MainActivity.this, postData, "Attends SVP...", new AsyncResponse() {

                @Override
                public void processFinish(String result) {

                    chemail = "";
                    //result1 = "0";
                    //Toast.makeText(Login.this, result, Toast.LENGTH_LONG).show();
                    /*
                    ProgressDialog mPogressDialog = new ProgressDialog(Login.this);
                    mPogressDialog.show();
                    mPogressDialog.setMessage("Attends SVP...");
                    mPogressDialog.setCancelable(false);
                    */
                    if (result.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Vérifiez votre connexion !", Toast.LENGTH_LONG).show();
                    } else{if ((result.equals("erreur")) || (result.equals("NonClt"))){
                        if (result.equals("NonClt")) {
                            Toast.makeText(getApplicationContext(), "Application pour client uniquement !", Toast.LENGTH_LONG).show();
                            chemail = "";
                            mPasswordView.setText("");
                            mUserView.setText("");
                            mUserView.requestFocus();
                        } else {
                            if (result.equals("erreur")) {
                                Toast.makeText(getApplicationContext(), "Le mot de passe ou l'email entré est incorrect !", Toast.LENGTH_LONG).show();
                                chemail = "";
                                mPasswordView.setText("");
                                mPasswordView.requestFocus();
                            }
                        }/*
                        if ((result.equals("errormdp")) || (result.equals("errorlog")) || (result.equals("NonClt"))) { /*
                            //result1="1";
                            if (result.equals("NonClt")) {
                                Toast.makeText(getApplicationContext(), "Application pour client uniquement !", Toast.LENGTH_LONG).show();
                                chemail = "";
                                mPasswordView.setText("");
                                mUserView.setText("");
                                mUserView.requestFocus();
                            } else {
                                if (result.equals("errormdp")) {
                                    Toast.makeText(getApplicationContext(), "Le mot de passe entré est incorrect !", Toast.LENGTH_LONG).show();
                                    chemail = "";
                                    mPasswordView.setText("");
                                    mPasswordView.requestFocus();
                                } else {
                                    if (result.equals("errorlog")) {
                                        Toast.makeText(getApplicationContext(), "L'identifiant entré ne correspond à aucun compte !", Toast.LENGTH_LONG).show();
                                        mPasswordView.setText("");
                                        mUserView.setText("");
                                        mUserView.requestFocus();
                                    }
                                }
                            }*/
                        } else {

                            if (result.equals("success")) {
                                Toast.makeText(MainActivity.this, "Pas de email", Toast.LENGTH_LONG).show();
                                chemail = "";

                                //showProgress(true);
                                EditText a = (EditText) findViewById(R.id.TF_username);
                                String str = a.getText().toString();

                                userlogin = str;
                                EditText b = (EditText) findViewById(R.id.TF_password);
                                String str2 = b.getText().toString();

                                userpassword = str2;


                                new Username().setUsername(userlogin);

                                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                                i.putExtra("Username", str);
                                startActivity(i);
                            } else {
                                //if (!((result.equals("errormdp")) || (result.equals("errorlog")) || (result.equals("NonClt")))){
//                        if(!(result.equals("errormdp"))&& !(result.equals("errorlog")) && !(result.equals("NonClt")))
//                        {
                                //Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                                chemail = result;

                                //showProgress(true);
                                EditText a = (EditText) findViewById(R.id.TF_username);
                                String str = a.getText().toString();

                                userlogin = str;
                                EditText b = (EditText) findViewById(R.id.TF_password);
                                String str2 = b.getText().toString();

                                userpassword = str2;


                                new Username().setUsername(userlogin);

                                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                                i.putExtra("Username", str);
                                startActivity(i);
                            }
                        }
                    }

                }
            });

            task.execute("http://www.enicarthage-robots.com/prj_android/login.php");
            task.setEachExceptionsHandler(new EachExceptionsHandler() {

                @Override
                public void handleIOException(IOException e) {

                    Toast.makeText(MainActivity.this, "Error with internet or web server.", Toast.LENGTH_LONG).show();
                    mUserView.requestFocus();

                }

                @Override
                public void handleMalformedURLException(MalformedURLException e) {
                    Toast.makeText(MainActivity.this, "Error with the URL.", Toast.LENGTH_LONG).show();
                }

                @Override
                public void handleProtocolException(ProtocolException e) {

                    Toast.makeText(MainActivity.this, "Error with protocol.", Toast.LENGTH_LONG).show();

                }

                @Override
                public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                    Toast.makeText(MainActivity.this, "Error with text encoding.", Toast.LENGTH_LONG).show();

                }
            });

        }
    }

    /**
     * Created by Abir Sleimi
     */


    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public boolean validatePassword(String password) {
        return password.length() > 2;
    }

    public boolean validateUserName(String username) {
        return username.length() > 1;
    }

}

