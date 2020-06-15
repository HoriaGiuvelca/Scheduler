package faculty;

public enum NumarStudenti {
    MAXIM(4),  //TODO: schimba in alta valoare la sfarsit
    MINIM(1),
    ;

    private int value;

    NumarStudenti(int i) {
        this.value = i;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return  String.valueOf(value);
    }
}
