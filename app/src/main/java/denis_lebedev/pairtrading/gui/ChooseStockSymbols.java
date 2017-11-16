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
import denis_lebedev.pairtrading.logic.SelectedSymbols;

public class ChooseStockSymbols extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private EditText symbolText;
    private ListView listView;
    private SelectedSymbols selectedSymbols = new SelectedSymbols();
    private String path = "/symbols.dat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_symbols);

        selectedSymbols.read(getPath());

        listView = (ListView)findViewById(R.id.symbolsList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String text = adapter.getItem(position);
                symbolText.setText(text);
            }
        });

        symbolText = (EditText)findViewById(R.id.symbolText);


        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, selectedSymbols.getSymbols());

        listView.setAdapter(adapter);
    }

    private String getPath(){
        return this.getApplicationContext().getFilesDir().getPath() + path;
    }

    @Override
    public void onResume() {
        super.onResume();

        System.out.println("SAAAAAAAAAAAAAVWEE!!!!!!!!!!!");
    }

    public void addButtonClick(View view){
        String symbol = symbolText.getText().toString();

        if(adapter.getPosition(symbol) == -1 && !symbol.equals("")) {
            adapter.add(symbol);
            symbolText.setText("");
            selectedSymbols.save(getPath());
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
