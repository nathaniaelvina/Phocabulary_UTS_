package id.ac.umn.phocabulary_uts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class TextFragment extends Fragment{

    public TextFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Button translateBtn = (Button) getView().findViewById(R.id.btnTranslate);
        translateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtSrc = (EditText) getView().findViewById(R.id.txtTranslate);
                EditText txtTarget = (EditText) getView().findViewById(R.id.txtMeaning);
                String colors[] = {"Red","Blue","White","Yellow","Black", "Green","Purple","Orange","Grey"};

// Selection of the spinner
                Spinner spinnerFrom = (Spinner) getView().findViewById(R.id.spinnerFrom);
                Spinner spinnerTo = (Spinner) getView().findViewById(R.id.spinnerTo);

// Application of the Array to the Spinner
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, colors);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spinnerFrom.setAdapter(spinnerArrayAdapter);

            }
        });
        return inflater.inflate(R.layout.fragment_text, container, false);

    }

}