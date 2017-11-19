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


import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class GoogleFinanceDownloader implements StockDataDownloader {

    public GoogleFinanceDownloader(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public ArrayList<Quote> download(String symbol, Calendar startDate, Calendar endDate) {

        String request = createRequestString(
                symbol,
                startDate,
                endDate);

        return executePost(request, "");
    }

    @Override
    public ArrayList<Stock> downloadAll(ArrayList<String> symbols, Calendar startDate, Calendar endDate) {
        ArrayList<Stock> stocks = new ArrayList<>();
        for(String symbol : symbols){
            ArrayList<Quote> quotes = download(symbol, startDate, endDate);
            Stock stock = new Stock();
            stock.setName(symbol);
            stock.setQuotes(quotes);
            stocks.add(stock);
        }
        return stocks;
    }

    String createRequestString(String symbol, Calendar startDate, Calendar endDate){
        String result = "https://finance.google.com/finance/historical?q=";
        result += symbol;
        result += String.format("&startdate=%s", datetimeToStr(startDate));
        result += String.format("&enddate=%s", datetimeToStr(endDate));
        return result + "&output=csv";
    }

    String datetimeToStr(Calendar c){
        String month = c.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);
        return  month+"+"+c.get(Calendar.DAY_OF_MONTH)+"%2C+" + c.get(Calendar.YEAR);
    }

    ArrayList<Quote> executePost(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            ArrayList<Quote> result = new ArrayList<>();
            String line;
            rd.readLine();//read header
            while ((line = rd.readLine()) != null) {
                String[] splited = line.split(",");

                Quote quote = new Quote();
                //stock.date = LocalDateTime.parse(splited[0].replace("-", " "),
                        //DateTimeFormatter.ofPattern("dd MMM yy"));

                quote.setPrice(Double.parseDouble(splited[4]));
                result.add(quote);
            }
            rd.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
