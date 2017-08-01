package com.rocky.aienc;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.rocky.aienc.models.*;

import java.util.ArrayList;
import java.util.List;

public class ScreenSlidePageFragment extends Fragment {

    private EditText originalNum;
    private ListView convertedNum;
    private Spinner unitTypeSpinner;
    private Spinner unitSpinner;
    private ArrayAdapter<CharSequence> unitTypeAdapter;
    private ArrayAdapter<String> unitAdapter;
    private List<String> unitAdapterData = new ArrayList<>();
    private ArrayAdapter<String> convertNumAdapter;
    private List<String> convertNumAdapterData = new ArrayList<>();


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

        unitAdapter = new ArrayAdapter<>(this.getContext(),
                android.R.layout.simple_spinner_item, unitAdapterData);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(unitAdapter);
        setUnitAdapter(Conversions.getInstance().getById(Conversion.LENGTH));

        convertNumAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, convertNumAdapterData);
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
                Conversion s = Conversions.getInstance().getById(getConversionFromSpinner
                        (unitTypeSpinner.getSelectedItemPosition()));
                setUnitAdapter(s);
                convert(unitAdapter.getItem(0).toString(), s);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Conversion s = Conversions.getInstance().getById(getConversionFromSpinner
                        (unitTypeSpinner.getSelectedItemPosition()));
                convert(parent.getAdapter().getItem(position).toString(), s);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return rootView;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Conversion.id
    private int getConversionFromSpinner(int pos) {
        switch (pos) {
            case 0:
                return Conversion.LENGTH;

            case 1:
                return Conversion.AREA;

            case 2:
                return Conversion.PRESSURE;

            case 3:
                return Conversion.VOLUME;

            case 4:
                return Conversion.TEMPERATURE;
        }
        return Conversion.AREA;
    }

    public void setUnitAdapter(Conversion c) {
        unitAdapterData.removeAll(unitAdapterData);
        for (int i = 0; i < c.getUnits().size(); i++) {
            Unit u = c.getUnits().get(i);
            unitAdapterData.add(getString(u.getLabelResource()));
        }
        unitAdapter.notifyDataSetChanged();
    }

    private void convert(String unitSelected, Conversion c){

        convertNumAdapterData.removeAll(convertNumAdapterData);
        Unit currentUnit = null;
        for (Unit unit : c.getUnits()) {
            if (getString(unit.getLabelResource()).equals(unitSelected)) {
                currentUnit = unit;
            }
            String s = getString(unit.getLabelResource());
            if (!s.equals(unitSelected))
                convertNumAdapterData.add(getString(unit.getLabelResource()));
        }

        if (currentUnit!=null) convertNumAdapterData.add(getString(currentUnit.getLabelResource()));
        convertNumAdapter.notifyDataSetChanged();

    }
}
