package faculty;

import java.util.ArrayList;

public class Materie {
    Profesor titular;
    String numeMaterie;
    Integer an;
    ArrayList<Laborator> laboratoare;

    public Materie(Profesor titular, String numeMaterie, Integer an) {
        this.titular = titular;
        this.numeMaterie = numeMaterie;
        this.an = an;
        laboratoare = new ArrayList<>();
    }

    public boolean adaugaLaborator(Laborator laborator) {
        if (!dejaAlocat(laborator)) {
            laboratoare.add(laborator);
            return true;
        }
        return false;
    }

    public Profesor getTitular() {
        return titular;
    }

    public void setTitular(Profesor titular) {
        this.titular = titular;
    }

    public String getNumeMaterie() {
        return numeMaterie;
    }

    public void setNumeMaterie(String numeMaterie) {
        this.numeMaterie = numeMaterie;
    }

    public Integer getAn() {
        return an;
    }

    public void setAn(Integer an) {
        this.an = an;
    }

    public ArrayList<Laborator> getLaboratoare() {
        return laboratoare;
    }

    public void setLaboratoare(ArrayList<Laborator> laboratoare) {
        this.laboratoare = laboratoare;
    }

    private boolean dejaAlocat(Laborator laborator) {
        for (Laborator lab : laboratoare)
            if (lab.getZi().toString().equalsIgnoreCase(laborator.getZi().toString()) && lab.getIntervalOrar().toString().equalsIgnoreCase(laborator.getIntervalOrar().toString()))
                return true;
        return false;
    }
}
