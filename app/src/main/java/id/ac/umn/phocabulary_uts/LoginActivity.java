package id.ac.umn.phocabulary_uts;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    // TODO: Rename parameter arguments, choose names that match
    public LoginActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText emailEdit = (EditText) findViewById(R.id.txtEmail);
        final EditText passwordEdit = (EditText) findViewById(R.id.txtPassword);

        Button loginBtn = (Button)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailEdit.getText().toString()=="" || passwordEdit.getText().toString()==""){
                    Context context = getApplicationContext();
                    CharSequence text = "Fields are empty!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else if (emailEdit.getText().toString()=="demo" || passwordEdit.getText().toString()=="demo"){
                    Context context = getApplicationContext();
                    CharSequence text = "Anggaplah Masuk";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });


    }




}
