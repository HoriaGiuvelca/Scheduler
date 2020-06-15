package faculty;

import java.util.*;

import static java.lang.Math.random;

public class Secretariat {
    public static ArrayList<Student> studenti;
    public static ArrayList<Materie> materii;
    public static HashMap<String, Map<String, String>> evidenta;


    public Secretariat(ArrayList<Student> studenti, ArrayList<Materie> materii) {
        evidenta = new HashMap<>();
        this.studenti = studenti;
        this.materii = materii;
    }

    public void distribuieStudentiLaboratoare() {
        for (Student student : studenti) {
            for (Disponibilitate disponibilitate : student.disponibilitate) {
                Laborator laborator = getLaboratorFor(disponibilitate, student);
                if (laborator != null && !laborator.estePlin())
                    laborator.adaugaStudent(student);
            }
        }
        alocareLaboratoareNealocati();
    }

    private void alocareLaboratoareNealocati() {
        for (Student student : studenti)
            for (Materie materie : materii) {
                if (evidenta.get(student.nume) == null && materie.getAn() == Integer.parseInt(student.getGrupa().charAt(1) + "")) {
                    for (Laborator laborator : materie.laboratoare) {
                        student.disponibilitate.add(new Disponibilitate(laborator.zi.toString(), laborator.intervalOrar.toString(), laborator.numeMaterie));
                    }
                    distribuieStudentiLaboratoare();
                } else if (evidenta.get(student.nume) != null && evidenta.get(student.nume).get(materie.numeMaterie) == null && materie.getAn() == Integer.parseInt(student.getGrupa().charAt(1) + "")) {
                    for (Laborator laborator : materie.laboratoare) {
                        student.disponibilitate.add(new Disponibilitate(laborator.zi.toString(), laborator.intervalOrar.toString(), laborator.numeMaterie));
                    }
                    distribuieStudentiLaboratoare();
                }
            }
    }


    private Laborator getLaboratorFor(Disponibilitate disponibilitate, Student student) {
        for (Materie materie : materii)
            for (Laborator laborator : materie.laboratoare) {
                if (disponibilitate.zi == laborator.zi && disponibilitate.ora == laborator.intervalOrar && disponibilitate.numeMaterie.equals(laborator.numeMaterie) && student.esteValida(disponibilitate)) {
                    if (evidenta.get(student.nume) == null) {
                        Map<String, String> map = new TreeMap<>();
                        if (!laborator.estePlin()) {
                            map.put(disponibilitate.numeMaterie, "alocata");
                            evidenta.put(student.nume, map);
                            student.indisponibilitate.add(disponibilitate);
                            return laborator;
                        }
                    } else if (evidenta.get(student.nume) != null && evidenta.get(student.nume).get(disponibilitate.numeMaterie) == null) {
                        Map<String, String> map = evidenta.get(student.nume);
                        if (!laborator.estePlin()) {
                            map.put(disponibilitate.numeMaterie, "alocata");
                            student.indisponibilitate.add(disponibilitate);
                            return laborator;
                        }
                    } else
                        return null;
                }
            }
        return null;
    }

    public void afiseazaRepartizare() {
        for (Materie materie : materii)
            for (Laborator laborator : materie.laboratoare) {
                if (laborator.getNumarStudenti() != 0)
                    laborator.afiseazaRepartizare();
            }
    }

    public static void introducerePrferinte(ArrayList<Student> studenti) {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Doriti sa completati formularul de alegere al laboratoarelor? (da/nu): ");

        var adauga = keyboard.next();
        while (adauga.equalsIgnoreCase("da")) {
            Student student = datePersonale(keyboard, studenti);
            System.out.println("Laboratoarele de anul asta sunt");
            afiseazaLaboratoare(Integer.parseInt(student.grupa.charAt(1) + ""));
            String tip;
            String preferinte;

            if (student != null) {
                System.out.println("Aveti vreo preferinta? (da/nu):");
                preferinte = keyboard.next();
                while (preferinte.equalsIgnoreCase("da")) {
                    System.out.println("Disponibilitate/Indisponibilitate(d/i): ");
                    tip = keyboard.next().toLowerCase();
                    switch (tip) {
                        case "d":
                            adaugaDisponibilitate(student, keyboard);
                            break;
                        case "i":
                            adaugaIndisponibilitate(student, keyboard);
                            break;
                        default:
                            System.out.println("Ai gresit!");
                    }
                    System.out.println("Mai aveti vreo preferinta? (da/nu):");
                    preferinte = keyboard.next();
                }
            }
            System.out.print("Doriti sa completati formularul de alegere al laboratoarelor? (da/nu): ");
            adauga = keyboard.next();
        }
        keyboard.close();
    }

    private static void afiseazaLaboratoare(int an) {
        for (Materie materie : materii)
            if(materie.an == an){
                for(Laborator laborator: materie.laboratoare)
                    System.out.println(laborator.numeMaterie+" - "+laborator.zi+": "+laborator.intervalOrar);
            }
    }

    private static void adaugaIndisponibilitate(Student student, Scanner keyboard) {
        System.out.println("Introdu preferinta: ");
        System.out.print("Materie: ");
        String materie = keyboard.next();
        System.out.print("Zi: ");
        ZileleSaptamani zi = ZileleSaptamani.valueOf(keyboard.next());
        System.out.print("Interval: ");
        IntervalOrar intervalOrar = IntervalOrar.valueOf(keyboard.next());
        student.adaugaIndisponibilitate(new Disponibilitate(zi.toString(), intervalOrar.toString(), materie));
    }

    private static void adaugaDisponibilitate(Student student, Scanner keyboard) {
        System.out.println("Introdu preferinta: ");
        System.out.print("Materie: ");
        String materie = keyboard.next();
        System.out.print("Zi: ");
        ZileleSaptamani zi = ZileleSaptamani.valueOf(keyboard.next());
        System.out.print("Interval: ");
        IntervalOrar intervalOrar = IntervalOrar.valueOf(keyboard.next());
        student.adaugaDisponibilitate(new Disponibilitate(zi.toString(), intervalOrar.toString(), materie));
    }

    private static Student datePersonale(Scanner keyboard, ArrayList<Student> studenti) {
        System.out.print("Introdu numele: ");
        String nume = keyboard.next();
        System.out.print("Introdu prenumele: ");
        String prenume = keyboard.next();
        System.out.print("Introdu grupa: ");
        String grupa = keyboard.next();
        Student student = gasesteStudent(nume, prenume, grupa, studenti);
        return student;
    }

    private static Student gasesteStudent(String nume, String prenume, String grupa, ArrayList<Student> studenti) {
        for (Student student : studenti)
            if (student.getNume().equals(nume) && student.getPrenume().equals(prenume) && student.getGrupa().equals(grupa))
                return student;
        return null;
    }

    public static void distribuieLaboratoare(ArrayList<Materie> materii) {
        String zi, interval;
        Integer laboratorulNumarul;
        for (Materie materie : materii) {
            laboratorulNumarul = 0;
            while (laboratorulNumarul < Utils.NUMAR_MAXIM_LABORATOARE) {
                zi = Utils.zile[Utils.inRange(0, 5)];
                interval = Utils.intervale[Utils.inRange(0, 6)];
                while (!materie.adaugaLaborator(new Laborator(zi, interval, materie.numeMaterie))) {
                    zi = Utils.zile[Utils.inRange(0, 5)];
                    interval = Utils.intervale[Utils.inRange(0, 6)];
                }
                laboratorulNumarul++;
            }
        }
    }


}
