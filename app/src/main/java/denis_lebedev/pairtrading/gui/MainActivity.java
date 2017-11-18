/*
MIT License

Copyright (c) 2017 Denis Lebedev

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

package denis_lebedev.pairtrading.gui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import denis_lebedev.pairtrading.R;
import denis_lebedev.pairtrading.logic.App;
import denis_lebedev.pairtrading.logic.AppInputData;
import denis_lebedev.pairtrading.logic.DateUtils;

public class MainActivity extends AppCompatActivity {

    private AppInputData inputData = new AppInputData();

    private EditText balanceTxt;
    private EditText riskTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        balanceTxt = (EditText)findViewById(R.id.balanceTxt);
        riskTxt = (EditText)findViewById(R.id.riskTxt);
    }

    public void startButton_OnClick(View view){

    }

    public void startSelectSymbols(View view){
        Intent intent = new Intent(this, SelectSymbolsActivity.class);
        startActivity(intent);
    }


    public void setFirstDate(View view){
        Calendar c = Calendar.getInstance();

        new DatePickerDialog(this, firstDateListener,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    DatePickerDialog.OnDateSetListener firstDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            inputData.startDate = DateUtils.getDateFromDatePicker(datePicker);
        }
    };

    public void setLastDate(View view){
        Calendar c = Calendar.getInstance();

        new DatePickerDialog(this, lastDateListener,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    DatePickerDialog.OnDateSetListener lastDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            inputData.endDate = DateUtils.getDateFromDatePicker(datePicker);
        }
    };

    public void OK_ButtonClick(View view){

        inputData.balance = Double.parseDouble(balanceTxt.getText().toString());
        inputData.risk = Double.parseDouble(riskTxt.getText().toString());

        App.current.calculate(inputData);

        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
    }
}
