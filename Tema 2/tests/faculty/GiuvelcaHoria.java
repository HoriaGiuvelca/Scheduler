package faculty;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;

public class GiuvelcaHoria {
    private static ArrayList<Student> studenti;
    private static ArrayList<Materie> materii;
    private static Secretariat secretariat;

    private static void init() {
        studenti = Utils.getStudentiFrom(Utils.STUDENTI);
        materii = Utils.getMateriiFrom(Utils.MATERII);
        secretariat = new Secretariat(studenti, materii);
    }

    @Test
    public void testDistribuireStudentiLaborator() {
        init();
        Secretariat.distribuieLaboratoare(materii);
        secretariat.distribuieStudentiLaboratoare();
        for (Student student : studenti) {
            var materiiAlocate = secretariat.evidenta.get(student.nume);
            for (String materie : getNume(materii, Integer.parseInt(student.grupa.charAt(1) + ""))) {
                assertFalse("Studentul nu este alocat la toate materiile", materiiAlocate.get(materie) == null);
            }
        }
    }


    private static String[] getNume(ArrayList<Materie> materii, int an) {
        ArrayList<String> mat = new ArrayList<>();
        for (Materie materie : materii)
            if (materie.an == an)
                mat.add(materie.numeMaterie);
        return Arrays.copyOf(mat.toArray(), mat.toArray().length, String[].class);
    }
}
