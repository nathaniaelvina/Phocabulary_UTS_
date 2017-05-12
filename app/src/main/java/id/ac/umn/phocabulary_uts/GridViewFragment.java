package id.ac.umn.phocabulary_uts;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import id.ac.umn.phocabulary_uts.R;

import static id.ac.umn.phocabulary_uts.R.id.flContainer;

public class GridViewFragment extends Fragment {
    private GridView gridView;
    private GridViewAdapter gridAdapter;


    public GridViewFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    View galleryView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      galleryView = inflater.inflate(R.layout.fragment_grid_view, container, false);

        gridView = (GridView) galleryView.findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(galleryView.getContext(), R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ImageItem item = (ImageItem) parent.getItemAtPosition(position);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                item.getImage().compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                Fragment fragment = new DetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title", item.getTitle());
                bundle.putByteArray("image", byteArray);
                fragment.setArguments(bundle);


                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();;

                fragmentTransaction.replace(flContainer, fragment).commit();

            }
        });

        return galleryView;
    }
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, "Image#" + i));
        }
        return imageItems;
    }
}
