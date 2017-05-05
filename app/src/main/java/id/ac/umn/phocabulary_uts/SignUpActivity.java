package id.ac.umn.phocabulary_uts;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final EditText fullNameEdit = (EditText) findViewById(R.id.txtFullName);
        final EditText emailEdit = (EditText) findViewById(R.id.txtEmail);
        final EditText passwordEdit = (EditText) findViewById(R.id.txtPassword);
        final EditText passwordConfirmEdit = (EditText) findViewById(R.id.txtConfirmPassword);

        Button signUpBtn = (Button)findViewById(R.id.btnSignUp);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailEdit.getText().toString()=="" || passwordEdit.getText().toString()==""){
                    Context context = getApplicationContext();
                    CharSequence text = "Fields are empty!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
/*
                else if (passwordConfirmEdit.getText().toString()=="demo" || passwordEdit.getText().toString()=="demo"){
                    Context context = getApplicationContext();
                    CharSequence text = "Anggaplah Masuk";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
*/
            }
        });
    }

}
