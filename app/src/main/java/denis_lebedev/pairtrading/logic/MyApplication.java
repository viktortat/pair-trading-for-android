package denis_lebedev.pairtrading.logic;


import java.util.HashMap;

public class MyApplication {

    public HashMap<String, SessionItem> items;

    private SessionItem currentSession;

    private MyApplication(){

    }

    private static final MyApplication myApp = new MyApplication();

    public static MyApplication getInstance(){
        return myApp;
    }

    public SessionItem getCurrentSession(){
        return currentSession;
    }
}
