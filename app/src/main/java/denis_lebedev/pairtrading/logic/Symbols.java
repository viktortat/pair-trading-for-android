package denis_lebedev.pairtrading.logic;


import java.util.ArrayList;

public class Symbols {

    public Symbols(){

    }

    private ArrayList<String> values = new ArrayList<>();

    public ArrayList<String> get(){
        return values;
    }

    public void save(){
        System.out.println("Symbols.save()");
    }
}
