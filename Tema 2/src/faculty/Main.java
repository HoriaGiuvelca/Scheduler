package faculty;

import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
        Utils.clean();
        ArrayList<Student> studenti = Utils.getStudentiFrom(Utils.STUDENTI);
        ArrayList<Materie> materii = Utils.getMateriiFrom(Utils.MATERII);
        Secretariat secretariat = new Secretariat(studenti, materii);
        Secretariat.distribuieLaboratoare(materii);
        Secretariat.introducerePrferinte(studenti);
        Utils.scriePreferinte(studenti);
        secretariat.distribuieStudentiLaboratoare();
        secretariat.afiseazaRepartizare();
    }
}
