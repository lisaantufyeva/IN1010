class Melding implements Comparable<Melding>{
    String innhold;
    Integer sekvensnr;
    int kanalId;
    
    public Melding (String innhold, int sekvensnr, int kanalId ){
        this.innhold =innhold;
        this.sekvensnr =sekvensnr;
        this.kanalId = kanalId;
    }

    public String toString(){
        return innhold;
    }

    public int hentNr(){
        return sekvensnr;
    }

    public int hentKanalId(){
            return kanalId;
    }
    @Override
    public int compareTo(Melding m) {
        return this.sekvensnr.compareTo(m.sekvensnr);
    }

    
}