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


public class MathUtils {

    public static double getStandardDeviation(double[] values){

        double result = 0;

        double average = average(values);

        for (int i = 0; i < values.length; i++) {
            result += Math.pow(values[i] - average, 2);
        }
        return Math.sqrt(result /= (values.length - 1));
    }

    public static double average(double[] values){

        double result = 0;

        for(int i = 0; i < values.length; i++){
            result += values[i];
        }

        return result / values.length;
    }

    public static double multiplyArrays(double[] x, double[] y){
        double total = 0;
        int size = x.length;
        for(int i = 0; i < size; i++){
            total += x[i] * y[i];
        }
        return total;
    }

    public static double powArray(double[] array){
        double total = 0;
        for(int i = 0; i < array.length; i++) {
            total += Math.pow(array[i], 2);
        }
        return total;
    }

    public static LinearRegressionData calculateRegression(double[] x, double[] y){
        if(x.length != y.length) {
            throw new RuntimeException("Arrays have different length!");
        }

        int size = x.length;
        double xAverage = average(x);
        double yAverage = average(y);
        double sx2 = powArray(x) / size - Math.pow(xAverage, 2);
        double xy = multiplyArrays(x, y);
        double cov_xy = xy / size - xAverage * yAverage;
        double b1 = cov_xy / sx2;
        double b0 = yAverage - b1 * xAverage;
        double r_value = b1 * (getStandardDeviation(x) / getStandardDeviation(y));
        double r_squared = Math.pow(r_value, 2);

        LinearRegressionData result = new LinearRegressionData();
        result.b0 = b0;
        result.b1 = b1;
        result.rValue = r_value;
        result.rSquared = r_squared;
        return result;
    }
}
