import java.util.LinkedList;
import java.util.Stack;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;

class Monitor{
    protected Stack <Melding> meldinger = new Stack<Melding>();
    private Lock laas = new ReentrantLock();
    private Condition ikkeTom = laas.newCondition();

    public Monitor(){
        
    }
    public void settInn(Melding melding ){
        laas.lock();
        try{
            meldinger.add(melding);
            //tom = false;
            ikkeTom.signalAll(); //signalerer at beholderen er ikke tom
            //System.out.println(melding);
        
        }catch(Exception e){
            System.out.println("Kan ikke sette inn i monitoren ");
            
        }finally{
            laas.unlock();
        }
    }

    public Melding taUt(){
        laas.lock();

        try{
            while (meldinger.size() == 0){
                ikkeTom.await();
            
            }
            //(meldinger.size() != 0 && Telegrafist.ANTALL>0){
                //ikkeTom.await();
            
            return meldinger.pop();
            
        }catch (InterruptedException e){
            System.out.println("Kan ikke ta ut fra monitoren ");
            System.exit(1);

        }finally{
            laas.unlock();

            }
        return null;
    }
    public Stack <Melding> hentMeldinger(){
        return meldinger;
    }
}