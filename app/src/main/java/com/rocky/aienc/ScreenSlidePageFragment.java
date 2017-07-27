package com.rocky.aienc;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ScreenSlidePageFragment extends Fragment {

    private EditText originalNum;
    private TextView convertedNum;
    ArrayAdapter<CharSequence> unitAdapter;
    Spinner unitSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);
        originalNum = (EditText) rootView.findViewById(R.id.originalNum);
        convertedNum = (TextView) rootView.findViewById(R.id.convertedNum);

        Spinner unitTypeSpinner = (Spinner) rootView.findViewById(R.id.unit_type_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> unitTypeAdapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.unit_type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        unitTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        unitTypeSpinner.setAdapter(unitTypeAdapter);

        unitSpinner = (Spinner) rootView.findViewById(R.id.unit_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        setUnitAdapter(R.array.length_unit_type);

        originalNum.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                convertedNum.setText(v.getText());
                return false;
            }
        });

        unitTypeSpinner.setSelection(0, true);
        unitTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        setUnitAdapter(R.array.length_unit_type);
                        break;
                    case 1:
                        setUnitAdapter(R.array.area_unit_type);
                        break;
                    case 2:
                        setUnitAdapter(R.array.pressure_unit_type);
                        break;
                    case 3:
                        setUnitAdapter(R.array.flow_unit_type);
                        break;
                    case 4:
                        setUnitAdapter(R.array.temperature_unit_type);
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return rootView;
    }


    public void setUnitAdapter(int i) {
        unitAdapter = ArrayAdapter.createFromResource(this.getContext(),
                i, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        unitSpinner.setAdapter(unitAdapter);
    }


}
