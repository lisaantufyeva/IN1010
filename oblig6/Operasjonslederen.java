import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;
import java.io.File;
import java.io.FileNotFoundException;


class Operasjonslederen implements Runnable{
    Monitor dekryptMonitor;
    private String [] kanalEn = new String[10];
    private String [] kanalTo = new String[150];
    private String [] kanalTre = new String[50];
    CountDownLatch barriereK;
     

    public Operasjonslederen(Monitor dekryptMonitor, CountDownLatch barriereK){
        this.dekryptMonitor = dekryptMonitor;
        this.barriereK = barriereK;
    }

    public void run(){

        sorter();
        skrivTilFil("KanalEn", kanalEn);
        skrivTilFil("KanalTo", kanalTo);
        skrivTilFil("KanalTre", kanalTre);
        System.out.println("Har skevet til fil");
        }
   
    public void sorter(){
        while (dekryptMonitor.hentMeldinger().size()>0 || barriereK.getCount() !=0){
            Melding dekryptMld = dekryptMonitor.taUt();
            if (dekryptMld.kanalId==1){
                leggTil(dekryptMld,kanalEn);
            }
            else if (dekryptMld.kanalId==2){
                leggTil(dekryptMld, kanalTo);
            }
            else if(dekryptMld.kanalId==3){
                leggTil(dekryptMld, kanalTre);
            }
            else{
                System.out.print("Noe gikk galt i sorteringen");
            }
        }
    }
    public void leggTil(Melding mld, String[]kanal){
        int sekvensNr = mld.hentNr()-1;
        kanal[sekvensNr]=mld.toString();
    }

    public void skrivTilFil(String navn, String[]kanal){
        try{
            PrintWriter printer =new PrintWriter(new File(navn+".txt"),"utf-8");       
            for(int i=0;i<kanal.length;i++){
                if(kanal[i]!=null){
                    printer.write("\n"+ kanal[i]+"\n");  
                }
            }
                printer.close();    
                 
        }catch(Exception e){
            System.out.println("noe gikk galt i skrivingen");
        }
    }
}