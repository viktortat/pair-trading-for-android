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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

import denis_lebedev.pairtrading.R;
import denis_lebedev.pairtrading.logic.App;
import denis_lebedev.pairtrading.logic.SelectedSymbols;

public class SelectSymbolsActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private EditText symbolText;
    private ListView listView;
    private App app = App.current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_symbols);

        symbolText = (EditText)findViewById(R.id.symbolText);
        symbolText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if( keyCode == KeyEvent.KEYCODE_ENTER ) {
                    addSymbol();
                    return true;
                }
                return false;
            }
        });

        listView = (ListView)findViewById(R.id.symbolsList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String text = adapter.getItem(position);
                symbolText.setText(text);
            }
        });
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                app.getInput().symbols);
        listView.setAdapter(adapter);
    }

    public void addButtonClick(View view){
        addSymbol();
    }

    private void addSymbol(){
        String symbol = symbolText.getText().toString();

        if(symbol.equals("")){
            return;
        }

        if(adapter.getPosition(symbol) == -1) {
            adapter.add(symbol);
            symbolText.setText("");
        }else {
            Toast.makeText(this, "This symbol allready added.", Toast.LENGTH_SHORT).show();
        }
    }

    public void removeButtonClick(View view){
        String symbol = symbolText.getText().toString();
        adapter.remove(symbol);
    }

    public void clearButtonClick(View view){
        adapter.clear();
    }
}
