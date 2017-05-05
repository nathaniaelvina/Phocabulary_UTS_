package id.ac.umn.phocabulary_uts;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class GridViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id){
                // Send intent to SingleViewActivity
                Intent i = new Intent(getApplicationContext(), SingleViewActivity.class);
                // Pass image index
                i.putExtra("id", position);
                startActivity(i);
            }
        });

        final FragmentManager fragmentManager = getSupportFragmentManager();

        // define your fragments here
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        // handle navigation selection

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        switch (item.getItemId()) {
                            case R.id.action_favorites:
                               // fragmentTransaction.replace(R.id.flContainer, new MainActivity()).commit();
                                return true;
                            case R.id.action_schedules:
                                //fragmentTransaction.replace(R.id.flContainer, new LoginActivity()).commit();
                                return true;
                            case R.id.action_music:
                              //  fragmentTransaction.replace(R.id.flContainer, new LoginActivity()).commit();
                                return true;
                            case R.id.action_setting:
                                //fragmentTransaction.replace(R.id.flContainer, new SettingFragment()).commit();
                                return true;

                        }
                        return false;
                    }
                });

    }

}
