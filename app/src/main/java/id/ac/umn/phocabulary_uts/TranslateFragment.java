package id.ac.umn.phocabulary_uts;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
/*
import com.google.api.services.translate.Translate;
import com.google.api.services.translate.model.TranslationsListResponse;
import com.google.api.services.translate.model.TranslationsResource;
*/

public class TranslateFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    public TranslateFragment() {
        // Required empty public constructor
    }

    public String langToConvert, langFromConvert, words;
    View translateView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    Spinner spinnerFrom;
    Spinner spinnerTo;
    EditText txtSrc;
    EditText txtTarget;
    EditText memo;
    ImageView ivThumbnail;
    Button btnSave;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        translateView = inflater.inflate(R.layout.fragment_translate, container, false);

        spinnerFrom = (Spinner) translateView.findViewById(R.id.spinnerFrom);
        spinnerTo = (Spinner) translateView.findViewById(R.id.spinnerTo);
        txtSrc = (EditText) translateView.findViewById(R.id.txtTranslate);
        txtTarget = (EditText) translateView.findViewById(R.id.txtMeaning);
        memo = (EditText) translateView.findViewById(R.id.txtMemo);

        List<String> categories = new ArrayList<String>();

        List<String> list;

        categories.add("Chinese");
        categories.add("English");
        categories.add("French");
        categories.add("Indonesian");
        categories.add("Japanese");
        categories.add("Korean");
        categories.add("Spanish");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFrom.setAdapter(dataAdapter);
        spinnerTo.setAdapter(dataAdapter);

        words = txtSrc.getText().toString();


        Button translateBtn = (Button) translateView.findViewById(R.id.btnTranslate);
        translateBtn.setOnClickListener(new View.OnClickListener() {
            String result;
            @Override
            public void onClick(View v) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        result = translate(langFromConvert,langToConvert,words);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtTarget.setText(result);
                            }
                        });
                    }
                });
                t.start();


            }
        });

        btnSave = (Button) translateView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            String result;
            @Override
            public void onClick(View v) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        result = saveVocab(spinnerFrom , spinnerTo, txtSrc, txtTarget, memo );
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                uploadImage();
                                Toast.makeText(getContext(),result, Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });
                t.start();

            }

        });


        ivThumbnail = (ImageView) translateView.findViewById(R.id.ivThumbnail);
        ivThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
        // Inflate the layout for thi s fragment
        return translateView;
    }

    String convertToLangCode(String lang){
        switch (lang){
            case "English" : return "en";
            case "Indonesian": return "id";
            case "Chinese": return "zh-CN";
            case "French": return "fr";
            case "Spanish": return "es";
            case "Japanese": return "ja";
            case "Korean": return "ko";

            default: return "undefined";
        }
    }


    String saveVocab(Spinner spinnerFrom, Spinner spinnerTo, EditText txtSrc, EditText txtTarget, EditText memo){
        JSONParser jsonParser = new JSONParser();
        String url_save = URLLists.url_save;

        JSONObject json;
        HashMap<String,String> p=new HashMap<String,String>();
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("SESSION", Context.MODE_PRIVATE);
        p.put("userID",sharedPref.getString("USER_ID","0") ); //nanti ambil dr sharedPref
        p.put("srcWord",txtSrc.getText().toString());
        p.put("targetWord", txtTarget.getText().toString());
        p.put("srcLang",convertToLangCode(spinnerFrom.getSelectedItem().toString()));
        p.put("targetLang",convertToLangCode(spinnerTo.getSelectedItem().toString()));
        p.put("memo",memo.getText().toString());

        String result = "nia";
        try{
            json = jsonParser.makeHttpRequest(url_save, "POST", p);
            try {
                result = json.getString("message");
                System.out.println(result);
                return result;
            } catch (JSONException e) {
//                        Toast.makeText(getBaseContext(),"Failed to seat you because of technical reasons",Toast.LENGTH_SHORT).show();
                return result;
            }

        }
        catch (final ConnectException e){
            //                   Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            return result;
        }

    }



    String translate(String langFromConvert, String langToConvert, String words){
        JSONParser jsonParser = new JSONParser();
        String url_translate=URLLists.url_translate;

        JSONObject json;
        HashMap<String,String> p=new HashMap<String,String>();
        p.put("srcWord",txtSrc.getText().toString());
        p.put("langFrom",convertToLangCode(spinnerFrom.getSelectedItem().toString()));
        p.put("langTo",convertToLangCode(spinnerTo.getSelectedItem().toString()));
        String result = "404";
        try{
            json = jsonParser.makeHttpRequest(url_translate, "POST", p);
            try {
                result = json.getString("translatedText");
                System.out.println(result);
                return result;
            } catch (JSONException e) {
//                        Toast.makeText(getBaseContext(),"Failed to seat you because of technical reasons",Toast.LENGTH_SHORT).show();
                return result;
            }

        }
        catch (final ConnectException e){
 //                   Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            return result;
        }


    }

    File myFilesDir;
    void takePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(myFilesDir.toString()+"/temp.jpg")));
        startActivityForResult(intent, 0);
    }
    Bitmap bitmap;
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == getActivity().RESULT_OK) {
            /*Bitmap cameraBitmap;
            cameraBitmap = BitmapFactory.decodeFile(myFilesDir + "/temp.jpg");
            Bitmap.createBitmap(cameraBitmap);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            cameraBitmap.compress(Bitmap.CompressFormat.JPEG, 5, out);
            bitmap=cameraBitmap;
            //Bitmap decoded5 = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
            //bitmap=decoded5;
            ivThumbnail.setImageBitmap(cameraBitmap);*/

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            bitmap=imageBitmap;
            ivThumbnail.setImageBitmap(imageBitmap);
        }
    }
    public static final String UPLOAD_URLLists = URLLists.url_upload;
    public static final String UPLOAD_KEY = "image";
    public static final String TAG = "MY MESSAGE";
    private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                btnSave.setEnabled(false);
                loading = ProgressDialog.show(getActivity(), "Uploading Image", "Please wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                btnSave.setEnabled(true);
                Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();
                data.put(UPLOAD_KEY, uploadImage);

                String result = rh.sendPostRequest(UPLOAD_URLLists,data);
                System.out.println("hasilnya"+result);
                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

}