package com.rocky.aienc;

/**
 * Created by zhengdan on 2017/7/6.
 */

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

public class ScreenSlidePageFragment extends Fragment implements
    AdapterView.OnItemSelectedListener {

  private EditText originalNum;
  private TextView convertedNum;

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


    Spinner unitSpinner = (Spinner) rootView.findViewById(R.id.unit_spinner);
    // Create an ArrayAdapter using the string array and a default spinner layout
    ArrayAdapter<CharSequence> unitAdapter = ArrayAdapter.createFromResource(this.getContext(),
        R.array.length_unit_type, android.R.layout.simple_spinner_item);
    // Specify the layout to use when the list of choices appears
    unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // Apply the adapter to the spinner
    unitSpinner.setAdapter(unitAdapter);

    originalNum.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        convertedNum.setText(v.getText());
        return false;
      }
    });
    return rootView;
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view,
      int pos, long id) {
    // An item was selected. You can retrieve the selected item using
    // parent.getItemAtPosition(pos)


  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {
    // Another interface callback
  }


}
