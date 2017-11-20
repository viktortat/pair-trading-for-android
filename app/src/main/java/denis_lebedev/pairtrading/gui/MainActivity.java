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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import denis_lebedev.pairtrading.R;
import denis_lebedev.pairtrading.logic.App;
import denis_lebedev.pairtrading.logic.DateUtils;

public class MainActivity extends AppCompatActivity {

    private EditText balanceTxt;
    private EditText riskTxt;
    private Button startDateButton;
    private Button endDateButton;
    private App app = App.current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        balanceTxt = (EditText)findViewById(R.id.balanceTxt);
        riskTxt = (EditText)findViewById(R.id.riskTxt);
        startDateButton = (Button)findViewById(R.id.setStartDate);
        endDateButton = (Button)findViewById(R.id.setEndDate);

        balanceTxt.setText(String.valueOf(app.getInput().balance));
        riskTxt.setText((String.valueOf(app.getInput().risk)));
        startDateButton.setText(App.current.getInput().startDate.getTime().toString());
        endDateButton.setText(App.current.getInput().endDate.getTime().toString());
    }

    public void startSelectSymbols(View view){
        Intent intent = new Intent(this, SelectSymbolsActivity.class);
        startActivity(intent);
    }

    private void createDatePickerDialog(DatePickerDialog.OnDateSetListener listener){
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, listener,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void setFirstDate(View view){
        createDatePickerDialog(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                app.getInput().startDate = DateUtils.getDateFromDatePicker(datePicker);
                startDateButton.setText(app.getInput().startDate.getTime().toString());
            }
        });
    }

    public void setLastDate(View view){
        createDatePickerDialog(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                app.getInput().endDate = DateUtils.getDateFromDatePicker(datePicker);
                endDateButton.setText(app.getInput().endDate.getTime().toString());
            }
        });
    }

    public void OK_ButtonClick(View view){

        app.getInput().balance = Double.parseDouble(balanceTxt.getText().toString());
        app.getInput().risk = Double.parseDouble(riskTxt.getText().toString());

        app.calculate();

        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
    }
}
