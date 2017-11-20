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


public class RValueRange {
    private double min;
    private double max;

    public RValueRange(double min, double max){

        if(min > max){
            throw new IllegalArgumentException("min > max");
        }

        if(!isCorrect(min) || !isCorrect(max)){
            throw new IllegalArgumentException("min or max is not in 0..1 range");
        }

        setMin(min);
        setMax(min);
    }

    public boolean isCorrect(double value){
        return value >= 0 && value <= 1;
    }

    public boolean isInRange(double value){
        return value >= min && value <= max;
    }

    private double getMin(){
        return min;
    }

    private double getMax(){
        return max;
    }

    private void setMin(double min){
        this.min = min;
    }

    private void setMax(double max){
        this.max = max;
    }
}
