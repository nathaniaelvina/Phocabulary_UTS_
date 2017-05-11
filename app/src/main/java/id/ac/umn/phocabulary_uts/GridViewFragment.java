package id.ac.umn.phocabulary_uts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;


public class GridViewFragment extends Fragment {
    public GridViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View gridView;
        gridView = inflater.inflate(R.layout.fragment_grid_view, container, false);


        GridView gridview = (GridView) gridView.findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(gridview.getContext()));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {
                // Send intent to SingleViewActivity
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.flContainer, new TranslateFragment()).commit();
                ft.commit();
            }
        });



        // Inflate the layout for this fragment
        return gridView;
    }

}
