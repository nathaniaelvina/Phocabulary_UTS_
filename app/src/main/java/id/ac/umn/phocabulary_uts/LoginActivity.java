package id.ac.umn.phocabulary_uts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    // TODO: Rename parameter arguments, choose names that match
    public LoginActivity() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(R.drawable.translate);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText emailEdit = (EditText) findViewById(R.id.txtEmail);
        final EditText passwordEdit = (EditText) findViewById(R.id.txtPassword);

        Button loginBtn = (Button)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] result = new String[1];
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
                else{
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            result[0] = login(emailEdit, passwordEdit);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getBaseContext(), result[0], Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    });
                    t.start();
                    Intent i = new Intent(getBaseContext(),SuperMainActivity.class);
                    startActivity(i);
                }
            }
        });

        Button signUpBtn = (Button) findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), SignUpActivity.class);
                startActivity(i);
            }
        });


    }
    String login(EditText email, EditText password){
        JSONParser jsonParser = new JSONParser();
        String url_login = URLLists.url_login;

        JSONObject json;
        HashMap<String,String> p=new HashMap<String,String>();
        //nanti ambil dr sharedPref
        p.put("email", email.getText().toString());
        p.put("password",password.getText().toString());
        String result2="nia";
        try{
            json = jsonParser.makeHttpRequest(url_login, "POST", p);

            try {
                result2 = json.getString("message");


                SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("SESSION",Context.MODE_PRIVATE).edit();
                editor.putString("USER_ID", json.getString("userID"));
                editor.putString("EMAIL", json.getString("email"));
                editor.putString("NAME", json.getString("name"));
                editor.putString("PREFLANG", json.getString("preflang"));
                Log.d("nama",json.getString("name"));
                editor.commit();
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
