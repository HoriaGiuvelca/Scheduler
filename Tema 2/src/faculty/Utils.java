package faculty;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Math.random;

public class Utils {
    public static final String REPARTIZARE = "resources\\repartizare.txt";
    public static final String OUTPUT_FILE = "resources\\repartizare.txt";
    public static final String STUDENTI = "resources\\studenti.txt";
    public static final String LABORATOARE = "resources\\laboratoare.txt";
    public static final String MATERII = "resources\\materi.txt";
    public static final String PREFERINTE = "resources\\preferinte.txt";

    public static final Integer NUMAR_MAXIM_LABORATOARE = 6;

    public static final String[] zile = {"Luni", "Marti", "Miercuri", "Joi", "Vineri"};
    public static final String[] intervale = {"L8_10", "L10_12", "L12_14", "L14_16", "L16_18", "L18_20"};


    public static ArrayList<Laborator> getLaboratoareFrom(String filename) {
        ArrayList<Laborator> laboratoare = new ArrayList<>();
        Scanner fisier;
        try {
            fisier = new Scanner(new FileInputStream(new File(filename)));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Teapa!");
            return laboratoare;
        }
        laboratoare = parseazaLaboratoareDin(fisier);
        return laboratoare;
    }

    private static boolean valid(Scanner fisier) {
        return fisier.hasNext("Luni *") || fisier.hasNext("Marti *") || fisier.hasNext("Miercuri *") || fisier.hasNext("Joi *") || fisier.hasNext("Vineri *");
    }

    private static ArrayList<Laborator> parseazaLaboratoareDin(Scanner fisier) {
        ArrayList<Laborator> laboratoare = new ArrayList<>();
        String zi, interval;
        String materie, numeProfesor, prenumeProfesor;
        while (fisier.hasNext()) {
            materie = fisier.next();
            numeProfesor = fisier.next();
            prenumeProfesor = fisier.next();
            while (valid(fisier)) {
                zi = fisier.next();
                interval = fisier.next();
                laboratoare.add(new Laborator(zi, interval, materie));
            }
        }
        return laboratoare;
    }

    public static ArrayList<Student> getStudentiFrom(String filename) {
        ArrayList<Student> studenti = new ArrayList<>();
        Scanner fisier;
        try {
            fisier = new Scanner(new FileInputStream(new File(filename)));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Teapa!");
            return studenti;
        }
        studenti = parseazaStudentiDin(fisier);
        return studenti;
    }

    private static ArrayList<Student> parseazaStudentiDin(Scanner fisier) {
        ArrayList<Student> studenti = new ArrayList<Student>();
        String zi, materie, interval;
        while (fisier.hasNext()) {
            String nume = fisier.next();
            String prenume = fisier.next();
            String grupa = fisier.next();
            Student student = new Student(nume, prenume, grupa);
            studenti.add(student);
            while (fisier.hasNext("D: *") || fisier.hasNext("I: *")) {
                switch (fisier.next()) {
                    case "D:":
                        zi = fisier.next();
                        interval = fisier.next();
                        materie = fisier.next();
                        student.adaugaDisponibilitate(new Disponibilitate(zi, interval, materie));
                        break;
                    case "I:":
                        zi = fisier.next();
                        interval = fisier.next();
                        materie = fisier.next();
                        student.adaugaIndisponibilitate(new Disponibilitate(zi, interval, materie));
                        break;
                    default:
                        System.out.println("Ai gresit ceva cumetre!");
                        break;
                }
            }
        }
        return studenti;
    }

    public static void clean() {
        try {
            File file = new File(OUTPUT_FILE);
            file.delete();
            file = new File(Utils.PREFERINTE);
            file.delete();
            file = new File(Utils.REPARTIZARE);
            file.delete();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void scriePreferinte(ArrayList<Student> studenti) {
        PrintWriter file = null;
        try {
            file = new PrintWriter(new FileOutputStream(Utils.PREFERINTE));
            for (Student student : studenti)
                file.print(student);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            file.close();
        }
    }

    public static ArrayList<Materie> getMateriiFrom(String fisierMateri) {
        ArrayList<Materie> materii = new ArrayList<>();
        Scanner file = null;
        try {
            file = new Scanner(new FileInputStream(fisierMateri));
            Materie materie;
            String numeMaterie, numeProfesor, prenumeProfesor;
            Profesor titular;
            Integer an;
            while (file.hasNext()) {
                numeMaterie = file.next();
                prenumeProfesor = file.next();
                numeProfesor = file.next();
                titular = new Profesor(numeProfesor, prenumeProfesor);
                an = file.nextInt();
                materie = new Materie(titular, numeMaterie, an);
                materii.add(materie);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            file.close();
            return materii;
        }
    }

    public static Integer inRange(int start, int end) {
        return (int) (random() * end - random() * start);
    }
}
