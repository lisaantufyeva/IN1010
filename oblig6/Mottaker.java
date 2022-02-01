import java.util.LinkedList;
import java.util.Stack;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

class Mottaker{
    private Stack <Melding>krypterteMeldinger = new Stack<Melding>();
    private Lock laas = new ReentrantLock();
    private java.util.concurrent.locks.Condition ikkeFlere = laas.newCondition();

    public Mottaker(Stack krypterteMeldinger){
        this.krypterteMeldinger = krypterteMeldinger;
        
    }

    public void settInn(Melding melding ){
        laas.lock();
        try{
            krypterteMeldinger.add(melding);

            ikkeFlere.signal();
        }catch(Exception e){
            System.out.println("Kan ikke sette inn meldinger");
        }finally{
            laas.unlock();
        }
    }

    public void taUt(){
        laas.lock();
        try{
            while(krypterteMeldinger.size() == 0){
                ikkeFlere.await();
                krypterteMeldinger.pop();
            }
        }catch (Exception e){

        }finally{
            laas.unlock();

            }

    }
}