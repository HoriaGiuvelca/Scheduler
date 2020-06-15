package faculty;

import java.util.ArrayList;

public class Student {
    String nume;
    String prenume;
    String grupa;
    ArrayList<Disponibilitate> disponibilitate;
    ArrayList<Disponibilitate> indisponibilitate;

    public Student(String nume, String prenume, String grupa) {
        this.nume = nume;
        this.grupa = grupa;
        this.prenume = prenume;
        disponibilitate = new ArrayList<>();
        indisponibilitate = new ArrayList<>();
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getGrupa() {
        return grupa;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }

    public void adaugaDisponibilitate(Disponibilitate disponib) {
        disponibilitate.add(disponib);
    }

    public void adaugaIndisponibilitate(Disponibilitate indisp) {
        indisponibilitate.add(indisp);
    }

    public boolean esteValida(Disponibilitate disponibilitate) {
        for (Disponibilitate indisp : indisponibilitate)
            if (indisp.equals(disponibilitate))
                return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(nume);
        builder.append(" ");
        builder.append(prenume);
        builder.append(" ");
        builder.append(grupa + "\n");
        adaugaStringuri(builder,disponibilitate,"D: ");
        adaugaStringuri(builder,indisponibilitate,"I: ");
        return builder.toString();
    }

    private void adaugaStringuri(StringBuilder builder,ArrayList<Disponibilitate> disponibilitate,String label) {
        for(Disponibilitate disp: disponibilitate){
            builder.append(label);
            builder.append(disp.toString());
        }
    }
}
