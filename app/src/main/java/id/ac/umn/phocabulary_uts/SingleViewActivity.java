package id.ac.umn.phocabulary_uts;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import static android.R.attr.data;
import static id.ac.umn.phocabulary_uts.R.id.gridview;

public class SingleViewActivity extends Fragment {

    public SingleViewActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View singleView;
        singleView = inflater.inflate(R.layout.activity_single_view, container, false);


      //  Get intent data
 //       Intent i = getIntent();

//        Selected image id
 //       int position = i.getExtras().getInt("id");new ImageAdapter(gridview.getContext())
        ImageAdapter imageAdapter = new ImageAdapter(singleView.getContext());

        ImageView imageView = (ImageView) singleView.findViewById(R.id.SingleView);
        imageView.setImageResource(imageAdapter.mThumbIds[1]);

        // Inflate the layout for this fragment
        return singleView;
    }


}