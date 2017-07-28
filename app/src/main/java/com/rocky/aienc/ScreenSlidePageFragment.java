package com.rocky.aienc;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ScreenSlidePageFragment extends Fragment {

    private EditText originalNum;
    private ListView convertedNum;

    private ArrayAdapter<CharSequence> unitTypeAdapter;
    private ArrayAdapter<CharSequence> unitAdapter;
    private Spinner unitTypeSpinner;
    private Spinner unitSpinner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);
        originalNum = (EditText) rootView.findViewById(R.id.originalNum);
        convertedNum = (ListView) rootView.findViewById(R.id.convertedNum);
        unitTypeSpinner = (Spinner) rootView.findViewById(R.id.unit_type_spinner);
        unitSpinner = (Spinner) rootView.findViewById(R.id.unit_spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        unitTypeAdapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.unit_type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        unitTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        unitTypeSpinner.setAdapter(unitTypeAdapter);

        setUnitAdapter(R.array.length_unit_type);

        final ArrayAdapter<String> convertNumAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, getData());
        convertedNum.setAdapter(convertNumAdapter);


        originalNum.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    handled = true;
                    ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(originalNum.getWindowToken(), 0);
                }
                return handled;
            }
        });

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
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(unitAdapter);
    }

    private List<String> getData(){

        List<String> data = new ArrayList<>();
        data.add("测试数据1");
        data.add("测试数据2");
        data.add("测试数据3");
        data.add("测试数据4");

        return data;
    }
}
