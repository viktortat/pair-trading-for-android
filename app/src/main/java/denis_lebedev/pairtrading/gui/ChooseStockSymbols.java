package denis_lebedev.pairtrading.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.StringTokenizer;

import denis_lebedev.pairtrading.R;

public class ChooseStockSymbols extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private EditText symbolText;
    private ListView listView;
    private ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_symbols);

        listView = (ListView)findViewById(R.id.symbolsList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String text = adapter.getItem(position);
                symbolText.setText(text);
            }
        });

        symbolText = (EditText)findViewById(R.id.symbolText);

        String[] values = new String[] { "GOOG", "XOM", "JPM", "BP" };



        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);

        listView.setAdapter(adapter);
    }

    public void addButtonClick(View view){
        String symbol = symbolText.getText().toString();
        adapter.add(symbol);
    }

    public void removeButtonClick(View view){
        String symbol = symbolText.getText().toString();
        adapter.remove(symbol);
    }

    public void clearButtonClick(View view){
        adapter.clear();
    }
}
