import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;

class Hovedprogram{
    static int antTelegrafister = 3;
    static int antKryptografister = 25;
    
    public static Lock laas= new ReentrantLock();
    public static Condition vent=laas.newCondition();
    public static CountDownLatch barriereT = new CountDownLatch(antTelegrafister);
    public static CountDownLatch barriereK = new CountDownLatch(antKryptografister);
    public static void main (String []args){
    
    //operasjonssentral
    Operasjonssentral ops = new Operasjonssentral(antTelegrafister);
    Kanal [] kanaler = ops.hentKanalArray();

    //monitorer
    Monitor krypterteMld = new Monitor();
    Monitor dekrypterteMld = new Monitor();

    //telegrafister
    for (int i = 0;i < antTelegrafister; i++) {
         Telegrafist t = new Telegrafist(kanaler[i], krypterteMld, barriereT);
         Thread traaden = new Thread(t);
         traaden.start();
         System.out.println("starter telegrafist");
    }
   
    //kryptografer
    for (int i = 0;i<antKryptografister;i++){
        Kryptograf k = new Kryptograf(krypterteMld, dekrypterteMld, barriereT, barriereK);
        Thread traaden =new Thread(k);
        traaden.start();
        System.out.println("starter kryptograf");
    }
    Operasjonslederen o = new Operasjonslederen(dekrypterteMld, barriereK);
    Thread traaden =new Thread(o);
    traaden.start();
    }
}