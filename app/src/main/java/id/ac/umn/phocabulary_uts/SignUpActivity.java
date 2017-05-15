package id.ac.umn.phocabulary_uts;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.util.HashMap;

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
                 final String[] result = new String[1];//kalo null email dan passwordnya
                if(emailEdit.getText().toString()=="" || passwordEdit.getText().toString()=="" || fullNameEdit.getText().toString()=="" ||passwordConfirmEdit.getText().toString()==""){
                    Context context = getApplicationContext();
                    CharSequence text = "Please fill all fields";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else if(!passwordEdit.getText().toString().equals(passwordConfirmEdit.getText().toString()))
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Password did not match";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else{

                    //masukin database
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            result[0] =     register(emailEdit, passwordEdit,fullNameEdit);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getBaseContext(), result[0], Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                    t.start();

                    Intent i = new Intent(getBaseContext(), SuperMainActivity.class);
                    startActivity(i);
                }
            }
        });
    }
    String register(EditText email, EditText password, EditText name){
        JSONParser jsonParser = new JSONParser();
        String url_signup = URL.url_signup;

        JSONObject json;
        HashMap<String,String> p=new HashMap<String,String>();
        //nanti ambil dr sharedPref
        p.put("name",name.getText().toString());
        p.put("email", email.getText().toString());
        p.put("password",password.getText().toString());
        String result2="nia";
        try{
            json = jsonParser.makeHttpRequest(url_signup, "POST", p);
            try {
                result2 = json.getString("message");
                System.out.println(result2);
                return result2;
            } catch (JSONException e) {
                     //   Toast.makeText(getBaseContext(),"salah",Toast.LENGTH_SHORT).show();
                return result2;
            }

        }
        catch (final ConnectException e){
            //                   Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            return result2;
        }

    }

}
