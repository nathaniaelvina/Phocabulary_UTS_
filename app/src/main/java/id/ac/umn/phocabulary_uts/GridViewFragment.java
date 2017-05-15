package id.ac.umn.phocabulary_uts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    String result;
    View galleryView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listPhocabulary = new ArrayList<Phocabulary>();
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("SESSION", Context.MODE_PRIVATE);
        final String USER_ID = sharedPref.getString("USER_ID","ooo");
        // Inflate the layout for this fragment
      galleryView = inflater.inflate(R.layout.fragment_grid_view, container, false);


        gridView = (GridView) galleryView.findViewById(R.id.gridView);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                result= fetchData(USER_ID);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getBaseContext(), result, Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        t.start();
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
       // Typ;edArray imgs = getResources().obtainTypedArray()
        Log.d("nia","jelek");
        for (int i = 0; i < 2; i++) {
            Log.d("nia",listPhocabulary.get(i).srclang.toString());

            imageItems.add(new ImageItem(listPhocabulary.get(i).images, "Image#" + i));
        }
        return imageItems;
    }

    ArrayList<Phocabulary> listPhocabulary;
    String fetchData(String USER_ID){
        JSONParser jsonParser = new JSONParser();
        String url_get = URL.url_getVocabularyList;

        JSONObject json;

        HashMap<String,String> p=new HashMap<String,String>();
        p.put("userID", USER_ID);
        String result2="nia";
        try{
            json = jsonParser.makeHttpRequest(url_get, "POST", p);
            System.out.println(json.toString());
            try {
                JSONArray arr = json.getJSONArray("phocabularies");
                Log.d("isi ", Integer.toString(arr.length()));
                for (int i = 0; i < arr.length(); i++){
                    Phocabulary phocabulary = new Phocabulary();
                    phocabulary.vocabID=arr.getJSONObject(i).getString("vocabID").toString();
                    phocabulary.srcword=arr.getJSONObject(i).getString("srcword");
                    phocabulary.targetword=arr.getJSONObject(i).getString("targetword");
                    phocabulary.targetlang=arr.getJSONObject(i).getString("targetlang");
                    phocabulary.srclang=arr.getJSONObject(i).getString("srclang");
                    byte[] decodedString = Base64.decode(arr.getJSONObject(i).getString("image"), Base64.DEFAULT);
                    phocabulary.images = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    Log.d("gambar",arr.getJSONObject(i).getString("image"));
                    listPhocabulary.add(phocabulary);
                }
                result2=json.getString("message");

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
