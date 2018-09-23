package org.afrikcode.pes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.afrikcode.pes.R;
import org.afrikcode.pes.impl.AuthImp;
import org.afrikcode.pes.views.AuthView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthActivity extends AppCompatActivity {

    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_pin)
    EditText et_password;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.forget_pass)
    TextView txt_forget_pass;
    @BindView(R.id.ln_loading_view)
    ProgressBar ln_loading;
    private AuthImp authImp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);

        authImp = new AuthImp();
        authImp.setView(new AuthView() {
            @Override
            public void onAuthSuccess(String userID) {
                Toast.makeText(getApplicationContext(), userID + " has logged in", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onAuthError(String error) {
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
            }

            @Override
            public void showLoadingIndicator() {
                txt_forget_pass.setClickable(false);
                btn_login.setVisibility(View.INVISIBLE);
                ln_loading.setVisibility(View.VISIBLE);
            }

            @Override
            public void hideLoadingIndicator() {
                txt_forget_pass.setClickable(true);
                btn_login.setVisibility(View.VISIBLE);
                ln_loading.setVisibility(View.GONE);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Checking User inputs
                String email = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                authImp.loginwithEmailandPassword(email, password);
            }
        });

    }

}
