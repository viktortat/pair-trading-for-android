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
import java.util.List;

public class FinancialPair {

    private String name;

    public String getName() {
        return Y.getName()+"|"+X.getName();
    }

    public Stock X;
    public Stock Y;

    public LinearRegressionData Regression;

    public double[] DeltaValues;

    public double TradeVolume;
    public double Weight;

    public FinancialPair(Stock x, Stock y) {
        X = x;
        Y = y;

        setRegression(x.getPrices(), y.getPrices());
        setValues(X.getPrices(), Y.getPrices());

        X.setDeviation(MathUtils.getStandardDeviation(x.getPrices()));
        Y.setDeviation(MathUtils.getStandardDeviation(y.getPrices()));
    }

    protected void setRegression(double[] x, double[] y)
    {
        Regression = MathUtils.calculateRegression(x, y);
    }

    protected void setValues(double[] x, double[] y) {

        int size = x.length;
        DeltaValues = new double[size];

        if (Regression.rValue >= 0) {
            for(int i = 0; i < size; i++){
                DeltaValues[i] = y[i] - x[i] * Regression.b1;
            }
        }
        else {
            for(int i = 0; i < size; i++){
                DeltaValues[i] = y[i] + x[i] * Regression.b1;
            }
        }
    }

    public static List<FinancialPair> createMany(List<Stock> stocks) {

        List<FinancialPair> pairs = new ArrayList<FinancialPair>();

        for (int i = 0; i < stocks.size(); i++)
        {
            for (int j = i + 1; j < stocks.size(); j++)
            {
                Stock x = null;
                Stock y = null;

                try {
                    x = stocks.get(i).clone();
                    y = stocks.get(j).clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }

                pairs.add(new FinancialPair(x, y));
            }
        }
        return pairs;
    }
}