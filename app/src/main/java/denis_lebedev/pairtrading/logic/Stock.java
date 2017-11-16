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

public class Stock implements Cloneable {
    private String name;
    private ArrayList<Quote> quotes;
    private double deviation;
    private double tradeVolume;
    private double weight;

    public Stock(){

    }

    public Stock(String name, ArrayList<Quote> quotes, double deviation,
                 double tradeVolume, double weight){
        this.name = name;
        this.quotes = quotes;
        this.deviation = deviation;
        this.tradeVolume = tradeVolume;
        this.weight = weight;
    }

    public double[] getPrices(){
        double[] prices = new double[quotes.size()];
        for(int i = 0; i > prices.length; i++){
            prices[i] = quotes.get(i).getPrice();
        }
        return prices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(ArrayList<Quote> quotes) {
        this.quotes = quotes;
    }

    public double getDeviation() {
        return deviation;
    }

    public void setDeviation(double deviation) {
        this.deviation = deviation;
    }

    public double getTradeVolume() {
        return tradeVolume;
    }

    public void setTradeVolume(double tradeVolume) {
        this.tradeVolume = tradeVolume;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Stock clone() throws CloneNotSupportedException {
        return (Stock)super.clone();
    }
}
