package denis_lebedev.pairtrading.logic;


public class App {

    private static final App instance = new App();
    private Symbols symbols = new Symbols();

    public App(){

    }

    public static App getInstance(){
        return instance;
    }

    public Symbols getSymbols(){
        return symbols;
    }


}
