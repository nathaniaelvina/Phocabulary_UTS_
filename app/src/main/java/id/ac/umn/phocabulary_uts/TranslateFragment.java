package id.ac.umn.phocabulary_uts;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        translateView = inflater.inflate(R.layout.fragment_translate, container, false);

        spinnerFrom = (Spinner) translateView.findViewById(R.id.spinnerFrom);
        spinnerTo = (Spinner) translateView.findViewById(R.id.spinnerTo);
        txtSrc = (EditText) translateView.findViewById(R.id.txtTranslate);
        txtTarget = (EditText) translateView.findViewById(R.id.txtMeaning);
        memo = (EditText) translateView.findViewById(R.id.txtMemo);

        List<String> categories = new ArrayList<String>();

        List<String> list;

        categories.add("English");
        categories.add("Japanese");
        categories.add("Indonesian");
        categories.add("French");
        categories.add("Spanish");
        categories.add("Chinese");
        categories.add("Korean");

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

        Button btnSave = (Button) translateView.findViewById(R.id.btnSave);
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
                                txtTarget.setText(result);
                                Toast.makeText(getContext(),result, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                t.start();



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
        String url_save = "http://192.168.0.21/phocabulary/save_phocab.php";

        JSONObject json;
        HashMap<String,String> p=new HashMap<String,String>();
        p.put("userID", "1"); //nanti ambil dr sharedPref
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
        String url_translate="http://192.168.43.219/phocabulary/translate.php";

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

}