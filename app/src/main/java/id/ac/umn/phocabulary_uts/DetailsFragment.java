package id.ac.umn.phocabulary_uts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsFragment extends Fragment {
    public DetailsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    View detailsView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     //   return super.onCreateView(inflater, container, savedInstanceState);
       detailsView= inflater.inflate(R.layout.fragment_details,container,false);
        Bundle bundle = this.getArguments();
        String title = bundle.getString("title");
        byte[] byteArray = bundle.getByteArray("image");

        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);;

        TextView titleTextView = (TextView)detailsView.findViewById(R.id.title);
        titleTextView.setText(title);

        ImageView imageView = (ImageView)detailsView.findViewById(R.id.image);
        imageView.setImageBitmap(bitmap);
        return  detailsView;
    }
}
