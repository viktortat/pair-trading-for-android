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


public class MovingAverages {

    public static double[] SMA(double[] values, int period) {

        double[] result = new double[values.length - (period - 1)];

        for (int i = 0; i < values.length - (period - 1); i++) {
            for (int j = i; j < i + period; j++) {
                result[i] += values[j];
            }
            result[i] /= period;
        }
        return result;
    }

    public static double[] WMA(double[] values, int period) {
        long weight = 0;
        long weightSumm = 0;
        double[] result = new double[values.length - (period - 1)];

        for (int i = 0; i < values.length - (period - 1); i++) {
            for (int j = i; j < i + period; j++) {
                result[i] += values[j] * ++weight;
                weightSumm += weight;
            }
            result[i] /= weightSumm;
            weight = 0;
            weightSumm = 0;
        }

        return result;
    }

    public static double[] VMA(double[] values, long[] volumes, int period) {
        long volumeSumm = 0;
        double[] result = new double[values.length - (period - 1)];

        for (int i = 0; i < values.length - (period - 1); i++)
        {
            for (int j = i; j < i + period; j++)
            {
                volumeSumm += volumes[j];
                result[i] += values[j] * volumes[j];
            }
            result[i] /= volumeSumm;
            volumeSumm = 0;
        }

        return result;
    }
}