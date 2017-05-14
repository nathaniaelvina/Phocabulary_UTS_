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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(R.drawable.translate);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_main);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // define your fragments here
        fragmentTransaction.replace(R.id.flContainer, new GridViewFragment()).commit();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationHelper.disableShiftMode(bottomNavigationView);

        // handle navigation selection

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
                        switch (item.getItemId()) {
                            case R.id.action_favorites:
                                fragmentTransaction2.replace(R.id.flContainer, new GridViewFragment()).commit();

                                return true;
                            case R.id.action_schedules:
                                fragmentTransaction2.replace(R.id.flContainer, new TranslateFragment()).commit();

                                return true;
                            case R.id.action_music:
                                fragmentTransaction2.replace(R.id.flContainer, new SettingFragment()).commit();
                                return true;
                            case R.id.action_setting:
                                fragmentTransaction2.replace(R.id.flContainer, new SettingFragment()).commit();
                                return true;






                        }
                        return false;
                    }
                });

    }
}

