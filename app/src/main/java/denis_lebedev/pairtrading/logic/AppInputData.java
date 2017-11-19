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

public class AppInputData {
    public ArrayList<String> symbols;
    public Calendar startDate;
    public Calendar endDate;
    public double balance;
    public double risk;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!AppInputData.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        final AppInputData data = (AppInputData)obj;

        if(symbols.size() != data.symbols.size()){
            return false;
        }

        for(int i = 0; i < symbols.size(); i++){
            if(!symbols.get(i).equals(data.symbols.get(i))){
                return false;
            }
        }

        if(balance != data.balance || risk != data.risk ||
                !startDate.equals(data.startDate) ||
                !endDate.equals(data.endDate)){
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
