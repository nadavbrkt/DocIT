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
import android.widget.Toast;
import com.nadav.docit.Class.User;
import com.nadav.docit.Models.Model;
import com.nadav.docit.R;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    EditText _fname;
    EditText _lname;
    EditText _email;
    EditText _pass;
    EditText _repass;
    TextView _errFname;
    TextView _errLname;
    TextView _errEmail;
    TextView _errPass;
    TextView _errRepass;

    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        _fname = (EditText) findViewById(R.id.fname);
        _lname = (EditText) findViewById(R.id.lname);
        _email = (EditText) findViewById(R.id.email);
        _pass = (EditText) findViewById(R.id.pass);
        _repass = (EditText) findViewById(R.id.repass);

        _errFname = (TextView) findViewById(R.id.errfname);
        _errLname = (TextView) findViewById(R.id.errlname);
        _errEmail = (TextView) findViewById(R.id.erremail);
        _errPass = (TextView) findViewById(R.id.errpass);
        _errRepass = (TextView) findViewById(R.id.errrepass);

        Button okBtn = (Button) findViewById(R.id.okbtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput())
                    registerUser();
            }
        });

        Button cancelBtn = (Button) findViewById(R.id.cancelbtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Validate data
    protected Boolean checkInput() {
        _errFname.setText("");
        _errFname.setBackgroundColor(Color.TRANSPARENT);

        _errLname.setText("");
        _errLname.setBackgroundColor(Color.TRANSPARENT);

        _errEmail.setText("");
        _errEmail.setBackgroundColor(Color.TRANSPARENT);

        _errPass.setText("");
        _errPass.setBackgroundColor(Color.TRANSPARENT);

        _errRepass.setText("");
        _errRepass.setBackgroundColor(Color.TRANSPARENT);

        Boolean isOk = true;
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

        // Check fname
        if (_fname.getText().toString().isEmpty()) {
            _errFname.setText("Plese enter first name");
            _errFname.setBackgroundColor(Color.RED);
            isOk = false;
        }

        // Check lname
        if (_lname.getText().toString().isEmpty()) {
            _errLname.setText("Plese enter last name");
            _errLname.setBackgroundColor(Color.RED);
            isOk = false;
        }

        // Checks email
        if (_email.getText().toString().isEmpty()) {
            _errEmail.setText("Plese enter email");
            _errEmail.setBackgroundColor(Color.RED);
            isOk = false;
        } else if (!(android.util.Patterns.EMAIL_ADDRESS.matcher(_email.getText().toString()).matches())) {
            _errEmail.setText("Plese enter valid email address");
            _errEmail.setBackgroundColor(Color.RED);
            isOk = false;
        }

        // Checks pass
        if (_pass.getText().toString().isEmpty()) {
            _errPass.setText("Plese enter password");
            _errPass.setBackgroundColor(Color.RED);
            isOk = false;
        } else if (!(pattern.matcher(_pass.getText().toString()).matches())) {
            _errPass.setText("Plese enter password that contains valid input");
            _errPass.setBackgroundColor(Color.RED);
            isOk = false;
        }

        // Checks pass
        if (_repass.getText().toString().isEmpty()) {
            _errRepass.setText("Plese type password again");
            _errRepass.setBackgroundColor(Color.RED);
            isOk = false;
        } else if (!(_repass.getText().toString().equals(_pass.getText().toString()))) {
            _errRepass.setText("Wrong password");
            _errRepass.setBackgroundColor(Color.RED);
            isOk = false;
        }

        if (!(isOk))
            Toast.makeText(this, "Non valid input", Toast.LENGTH_SHORT).show();

        return isOk;
    }

    // Register new user
    protected void registerUser() {
        Model.getInstance().signUp(_email.getText().toString(), _pass.getText().toString()
                , _fname.getText().toString(), _lname.getText().toString(), new Model.SignUpListener() {
            @Override
            public void onError(Exception e) {
                Toast.makeText(getApplicationContext(), "Unable to log you in, try later", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDone(User u) {
                // Login register user
                Model.getInstance().login(_email.getText().toString(), _pass.getText().toString(),
                        new Model.AuthListener() {
                    @Override
                    public void onDone(User u) {
                        Log.d("CURUSER", u.toString());
                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getApplicationContext(), "Unable to log you in, try later", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }
}
