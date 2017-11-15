package denis_lebedev.pairtrading.logic;




import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GoogleFinanceDownloader implements StockDataDownloader {

    public GoogleFinanceDownloader(){

    }

    @Override
    public ArrayList<Quote> download(String symbol, Calendar startDate, Calendar endDate) {

        String request = createRequestString(
                symbol,
                startDate,
                endDate);

        return executePost(request, "");
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
