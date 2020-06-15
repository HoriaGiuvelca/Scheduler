package faculty;

public class Disponibilitate {
    ZileleSaptamani zi;
    IntervalOrar ora;
    String numeMaterie;

    public Disponibilitate(String zi, String interval,String numeMaterie) {
        this.zi = ZileleSaptamani.valueOf(zi);
        this.ora = IntervalOrar.valueOf(interval);
        this.numeMaterie = numeMaterie;
    }

    @Override
    public boolean equals(Object object){
        if(object==null)
            return false;
        else if(!(object instanceof  Disponibilitate))
            return false;
        else {
            Disponibilitate disp = (Disponibilitate)object;
            return disp.numeMaterie.equals(this.numeMaterie) && disp.ora == this.ora && this.zi == disp.zi;
        }
    }

    @Override
    public String toString() {
        return  zi+ " " + ora + " " +numeMaterie+"\n";
    }
}
