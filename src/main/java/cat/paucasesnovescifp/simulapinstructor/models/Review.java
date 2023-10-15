package cat.paucasesnovescifp.simulapinstructor.models;

/**
 *
 * @author Miguel
 */
public class Review {
    private int Id;
    private int IdIntent;
    private int IdReviewer;
    private int Valoracio;
    private String Comentari;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getIdIntent() {
        return IdIntent;
    }

    public void setIdIntent(int IdIntent) {
        this.IdIntent = IdIntent;
    }

    public int getIdReviewer() {
        return IdReviewer;
    }

    public void setIdReviewer(int IdReviewer) {
        this.IdReviewer = IdReviewer;
    }

    public int getValoracio() {
        return Valoracio;
    }

    public void setValoracio(int Valoracio) {
        this.Valoracio = Valoracio;
    }

    public String getComentari() {
        return Comentari;
    }

    public void setComentari(String Comentari) {
        this.Comentari = Comentari;
    }
}
