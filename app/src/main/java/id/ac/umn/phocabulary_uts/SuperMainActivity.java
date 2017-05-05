package id.ac.umn.phocabulary_uts;



import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class SuperMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_main);
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
                                fragmentTransaction.replace(R.id.flContainer, new MainActivity()).commit();
                                return true;
                            case R.id.action_schedules:
                                fragmentTransaction.replace(R.id.flContainer, new SettingFragment()).commit();
                                return true;
                            case R.id.action_music:
                                fragmentTransaction.replace(R.id.flContainer, new SettingFragment()).commit();
                                return true;
                            case R.id.action_setting:
                                fragmentTransaction.replace(R.id.flContainer, new SettingFragment()).commit();
                                return true;

                        }
                        return false;
                    }
                });

    }
}

