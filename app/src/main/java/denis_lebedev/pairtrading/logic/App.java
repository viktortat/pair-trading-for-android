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

package denis_lebedev.pairtrading.logic;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class App {

    private StockDataProvider dataProvider;
    private AppInputData input;
    private AppInputData inputTemp;
    private ArrayList<FinancialPair> financialPairs;

    public static final App current = new App();

    public App(){
        dataProvider = new GoogleFinanceDownloader();
        input = readAppInputData();
        if(input == null){
            input = getDefaultAppInputData();
        }
    }

    public ArrayList<FinancialPair> getFinancialPairs(){
        return financialPairs;
    }

    public AppInputData getInput(){
        return input;
    }

    private AppInputData readAppInputData(){
        return null;
    }

    private void saveAppInputData() throws Exception {
        throw new Exception();
    }

    private AppInputData getDefaultAppInputData(){

        AppInputData defaultData = new AppInputData();

        defaultData.balance = 100000.00;
        defaultData.risk = 0.25;

        defaultData.symbols = new ArrayList<>();
        defaultData.symbols.add("IBM");
        defaultData.symbols.add("MSFT");
        defaultData.symbols.add("GOOG");

        defaultData.endDate = Calendar.getInstance();

        defaultData.startDate = Calendar.getInstance();
        defaultData.startDate.add(Calendar.MONTH, -1);

        defaultData.RValueRange = new RValueRange(0.65, 1);

        return defaultData;
    }

    public void calculate(){

        List<Stock> stocks = dataProvider.downloadAll(input.symbols, input.startDate, input.endDate);

        ArrayList<FinancialPair> pairs = FinancialPair.createMany(stocks);

        pairs = filterPairs(pairs, input.RValueRange);

        RiskManager rm = new RiskManager(pairs, input.balance);
        rm.calculate();

        financialPairs = pairs;
    }

    private ArrayList<FinancialPair> filterPairs(ArrayList<FinancialPair> pairs, RValueRange range){

        ArrayList<FinancialPair> result = new ArrayList<>();

        for(int i = 0; i < pairs.size(); i++){

            FinancialPair pair = pairs.get(i);
            double rValue = pair.Regression.rValue;

            if(range.isInRange(rValue)){
                result.add(pair);
            }
        }
        return result;
    }

    public void dispose(){

    }
}
