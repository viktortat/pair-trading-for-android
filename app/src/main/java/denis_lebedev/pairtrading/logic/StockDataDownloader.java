package denis_lebedev.pairtrading.logic;

import java.util.ArrayList;
import java.util.Calendar;


public interface StockDataDownloader {
    ArrayList<Quote> download(String symbol, Calendar startDate, Calendar endDate);
}
