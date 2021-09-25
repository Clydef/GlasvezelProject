package entities;

public class Gebieden {
    private String naam;
    private int nGebouwen;
    private int nStraten;
    private boolean ADSL;
    //ADSL = FTTC (fiber to the curb)
    //!ADSL = FTTH (fiber to the home)

    public Gebieden(String naam, int nGebouwen, int nStraten, boolean ADSL) {
        this.naam = naam;
        this.nGebouwen = nGebouwen;
        this.nStraten = nStraten;
        this.ADSL = ADSL;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public int getnGebouwen() {
        return nGebouwen;
    }

    public void setnGebouwen(int nGebouwen) {
        this.nGebouwen = nGebouwen;
    }

    public int getnStraten() {
        return nStraten;
    }

    public void setnStraten(int nStraten) {
        this.nStraten = nStraten;
    }

    public boolean isADSL() {
        return ADSL;
    }

    public void setADSL(boolean ADSL) {
        this.ADSL = ADSL;
    }

    @Override
    public String toString() {
        return  naam + " met " + nStraten +
                " straten " + "en ⩲" + nGebouwen +
                " gebouwen.";

    }
}
