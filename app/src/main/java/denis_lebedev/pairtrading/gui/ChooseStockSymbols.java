package denis_lebedev.pairtrading.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ChooseStockSymbols extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private EditText symbolText;
    private ListView listView;

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

        ArrayList<String> symbols = App.getInstance().getSymbols().get();

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, symbols);

        listView.setAdapter(adapter);
    }

    public void addButtonClick(View view){
        String symbol = symbolText.getText().toString();

        if(adapter.getPosition(symbol) == -1) {
            adapter.add(symbol);
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
