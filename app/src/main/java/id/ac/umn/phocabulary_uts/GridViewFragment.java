package id.ac.umn.phocabulary_uts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.net.ConnectException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

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
        final String USER_ID = sharedPref.getString("USER_ID", "ooo");
        // Inflate the layout for this fragment
        galleryView = inflater.inflate(R.layout.fragment_grid_view, container, false);


        gridView = (GridView) galleryView.findViewById(R.id.gridView);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                final JSONObject json = fetchData(USER_ID);
                Log.d("JASON", json.toString());
                populateListPhocabulary(json);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getBaseContext(), result, Toast.LENGTH_SHORT).show();
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
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                fragmentTransaction.replace(flContainer, fragment).commit();
                            }
                        });

                    }
                });
            }
        });
        t.start();
        return galleryView;
    }

    private void populateListPhocabulary(JSONObject json) {
        try {
            JSONArray arr = json.getJSONArray("phocabularies");
            Log.d("isi ", Integer.toString(arr.length()));
            for (int i = 0; i < arr.length(); i++) {
                final Phocabulary phocabulary = new Phocabulary();
                phocabulary.vocabID = arr.getJSONObject(i).getString("vocabID");
                phocabulary.srcword = arr.getJSONObject(i).getString("srcword");
                phocabulary.targetword = arr.getJSONObject(i).getString("targetword");
                phocabulary.targetlang = arr.getJSONObject(i).getString("targetlang");
                phocabulary.srclang = arr.getJSONObject(i).getString("srclang");
                Log.d("URLNYA BENER KAN",URLLists.URLImagePrefix + arr.getJSONObject(i).getString("image"));
                try{

                    URL u = new URL(URLLists.URLImagePrefix + arr.getJSONObject(i).getString("image"));
                    HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();

                    InputStream is = new BufferedInputStream(conn.getInputStream());
                    phocabulary.images = BitmapFactory.decodeStream(is);
                }
                catch (Exception e){
                    phocabulary.images=BitmapFactory.decodeResource(getContext().getResources(), R.drawable.image_1);
                    Log.e("ERROR SINI","ANTARA FILENYA GA ADA ATAU SALAH KODING");
                }
                listPhocabulary.add(phocabulary);
            }
        }
        catch(JSONException e){
            Log.e("ERROR SINI","KENAPA DAH");
        }
}

    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        // Typ;edArray imgs = getResources().obtainTypedArray()
        Log.d("nia", "jelek");
        Log.d("nia", Integer.toString(listPhocabulary.size()));
        for (int i = 0; i < listPhocabulary.size(); i++) {
            Log.d("haha", listPhocabulary.get(i).srclang.toString());
            Log.d("hihi", listPhocabulary.get(i).images.toString());

            imageItems.add(new ImageItem(listPhocabulary.get(i).images, "Image#" + i));
        }
        return imageItems;
    }

    ArrayList<Phocabulary> listPhocabulary;

    JSONObject fetchData(String USER_ID) {
        JSONParser jsonParser = new JSONParser();
        String url_get = URLLists.url_getVocabularyList;

        JSONObject json;

        HashMap<String, String> p = new HashMap<String, String>();
        p.put("userID", USER_ID);
        String result2 = "nia";
        try {
            json = jsonParser.makeHttpRequest(url_get, "POST", p);
            System.out.println(json.toString());
            return json;
        } catch (final ConnectException e) {
            //                   Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
