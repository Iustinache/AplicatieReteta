package Domain;

public class Reteta extends Entitate{
    String nume;
    int minute;
    String ingrediente;

    public Reteta(int id, String nume, int minute, String ingrediente) {
        super(id);
        this.nume = nume;
        this.minute = minute;
        this.ingrediente = ingrediente;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }

    public int getNrIngrediente() {
        int nrIngrediente = 0;
        String i = ingrediente;
        String[] campuri = i.split(";");
        return campuri.length;
    }

    @Override
    public String toString() {
        return "Reteta{" +
                "nume='" + nume + '\'' +
                ", minute=" + minute +
                ", ingrediente='" + ingrediente + '\'' +
                '}';
    }
}
