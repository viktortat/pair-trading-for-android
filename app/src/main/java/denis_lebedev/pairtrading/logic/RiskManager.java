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


import java.util.List;

public class RiskManager
{
    private List<FinancialPair> pairs;
    private double[] synthIndex;

    public double Balance;

    public RiskManager(List<FinancialPair> pairs, double balance)
    {
        if (balance < 0) throw new RuntimeException("[balance] can't have negative value.");

        this.pairs = pairs;
        Balance = balance;

        setTradeVolumeToDefault();
        setSynthIndex();
    }

    private void setTradeVolumeToDefault() {
        for (FinancialPair pair : pairs) {
            pair.TradeVolume = 0;
            pair.X.setTradeVolume(0);
            pair.Y.setTradeVolume(0);
        }
    }

    private void setSynthIndex() {

        synthIndex = new double[pairs.get(0).DeltaValues.length];

        for (int i = 0; i < pairs.get(0).DeltaValues.length; i++) {
            double value = 0;

            for (int j = 0; j < pairs.size(); j++) {
                value += pairs.get(j).DeltaValues[i];
            }

            synthIndex[i] += (value / pairs.size());
        }
    }

    public void calculate()
    {
        double summary = 0;

        for(FinancialPair pair : pairs) {

            LinearRegressionData regression = MathUtils.calculateRegression(pair.DeltaValues, synthIndex);

            pair.Weight = 1 / (1 + Math.abs(regression.b1));

            summary += pair.Weight;
        }

        for (FinancialPair pair : pairs) {
            pair.Weight = pair.Weight / summary;
            pair.TradeVolume = Balance * pair.Weight;
        }

        for (FinancialPair pair : pairs) {
            double beta = pair.Regression.b1;
            double weight = 1.0 / (1.0 + Math.abs(beta));

            pair.X.setTradeVolume(pair.TradeVolume * (weight * Math.abs(beta)));
            pair.Y.setTradeVolume(pair.TradeVolume * weight);
        }
    }
}