package faculty;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Laborator {
    String numeMaterie;
    Integer numarStudenti;
    IntervalOrar intervalOrar;
    ZileleSaptamani zi;
    ArrayList<Student> studenti;

    public Laborator(String zi, String interval, String numeMaterie) {
        this.zi = ZileleSaptamani.valueOf(zi);
        this.intervalOrar = IntervalOrar.valueOf(interval);
        this.numarStudenti = 0;
        this.numeMaterie = numeMaterie;
        this.studenti = new ArrayList<>();
    }

    public Integer getNumarStudenti() {
        return numarStudenti;
    }

    public void setNumarStudenti(Integer numarStudenti) {
        this.numarStudenti = numarStudenti;
    }

    public IntervalOrar getIntervalOrar() {
        return intervalOrar;
    }

    public void setIntervalOrar(IntervalOrar intervalOrar) {
        this.intervalOrar = intervalOrar;
    }

    public ZileleSaptamani getZi() {
        return zi;
    }

    public void setZi(ZileleSaptamani zi) {
        this.zi = zi;
    }

    public boolean adaugaStudent(Student student) {
        if (!estePlin()) {
            this.numarStudenti++;
            studenti.add(student);
            return true;
        } else return false;
    }

    public boolean estePlin() {
        return studenti.size() == NumarStudenti.MAXIM.getValue();
    }

    public void afiseazaRepartizare() {
        PrintWriter file = null;
        try {
            file = new PrintWriter(new FileOutputStream(Utils.OUTPUT_FILE, true));
            file.println("-> Laborator: " + numeMaterie + " - " + this.zi.toString() + ":" + this.intervalOrar);
            for (Student student : studenti)
                file.println(student.getNume() + " " + student.getPrenume() + " : " + student.getGrupa());
            file.println("");
        } catch (Exception exception) {
            System.out.println("Fisierul nu a putut fi creat!");
            System.out.println(exception.getMessage());
        } finally {
            file.close();
        }
    }
}
