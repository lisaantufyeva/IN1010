import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;


class Telegrafist implements Runnable{
    private Kanal privatKanal;
    private Monitor kryptMonitor;
    private java.lang.String melding;
    CountDownLatch barriereT;

    public Telegrafist(Kanal privatKanal, Monitor kryptMonitor,CountDownLatch barriereT){
        this.privatKanal = privatKanal;
        this.kryptMonitor=kryptMonitor;
        this.barriereT=barriereT;
    }
    
    @Override
    public void run(){
        System.out.println("Telegrafisten lytter");
        int id = privatKanal.hentId();
        int nr = 1;
        String melding = privatKanal.lytt();
        
        while(melding != null){
            Melding nyMeld = new Melding(melding, nr, id);
            kryptMonitor.settInn(nyMeld);
            System.out.println("Meldingen er lagt inn i kryptert Monitor:"+ Thread.currentThread().getName());

            nr++;
            melding = privatKanal.lytt();
        }
        barriereT.countDown();
        System.out.println("\n"+"Ikke flere meldinger"+ "\n");
   
    }
}