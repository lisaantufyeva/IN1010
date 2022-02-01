import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Kryptograf implements Runnable{
    private Monitor kryptMonitor;
    private Monitor dekryptMonitor;
    private Melding kryptertMld;
    private Lock laas = new ReentrantLock();
    CountDownLatch barriereT;
    CountDownLatch barriereK;

    public Kryptograf(Monitor kryptMonitor, Monitor dekryptMonitor, CountDownLatch barriereT, CountDownLatch barriereK ){
        this.kryptMonitor =kryptMonitor;
        this.dekryptMonitor = dekryptMonitor;
        this.barriereT = barriereT;
        this.barriereK=barriereK;
    }
    @Override
    public void run(){

        System.out.println("Kryptograf henter melding");

        while(kryptMonitor.hentMeldinger().size()>0 || barriereT.getCount() !=0){
            System.out.println("krypterte meldinger size: "+kryptMonitor.hentMeldinger().size()+
            " ,telegrafist antall: "+barriereT.getCount());
            
            kryptertMld =kryptMonitor.taUt();
        
            int id = kryptertMld.hentKanalId();
            int nr = kryptertMld.hentNr();
            String kryptertInnhold = kryptertMld.toString();

            String dekryptertInnhold = Kryptografi.dekrypter(kryptertInnhold);
            Melding dekryptertMld = new Melding(dekryptertInnhold, nr, id );
            System.out.println("Kryptograf dekrypterte meldingen");
       
            System.out.println("Meldingen er lagt inn i dekryptert Monitor: "+ Thread.currentThread().getName());
            dekryptMonitor.settInn(dekryptertMld);
        }  
        barriereK.countDown();

    }
}