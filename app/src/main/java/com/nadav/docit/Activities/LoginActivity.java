package com.nadav.docit.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.nadav.docit.Class.User;
import com.nadav.docit.DocIT;
import com.nadav.docit.Models.Model;
import com.nadav.docit.R;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LOGIN";
    protected DocIT app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (DocIT)getApplication();
        setContentView(R.layout.activity_login);

        Button signup = (Button) findViewById(R.id.signin_btn);

        assert signup != null;
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(signupIntent);
            }
        });

        Button login = (Button) findViewById(R.id.login_btn);

        assert login != null;
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate data
                EditText user = (EditText) findViewById(R.id.username);
                EditText password = (EditText) findViewById(R.id.pass);

                TextView error = (TextView) findViewById(R.id.err);
                assert error != null;
                error.setText("");

                if((!(user != null && user.getText().toString().isEmpty())) &&
                        (!(password != null && password.getText().toString().isEmpty()))) {
                    Model.getInstance().login(user.getText().toString(), password.getText().toString(), new Model.AuthListener() {
                        @Override
                        public void onDone(User u) {
                            // Sets current user
                            Log.d("CURUSER", u.toString());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onError(Exception e) {
                            // Error login
                            TextView error = (TextView) findViewById(R.id.err);
                            error.setText("Bad username or password");
                            error.setBackgroundColor(Color.RED);
                        }
                    });
                }
            }
        });
    }
}
