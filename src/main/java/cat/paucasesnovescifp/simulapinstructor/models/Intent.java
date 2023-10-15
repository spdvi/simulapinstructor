package cat.paucasesnovescifp.simulapinstructor.models;

/**
 *
 * @author Miguel
 */
public class Intent {
    private int Id;
    private int IdUsuari;
    private String NomUsuari;
    private int IdExercici;
    private String NomExercici;
    private String Timestamp_Inici;
    private String Timestamp_Fi;
    private String Videofile;

    @Override
    public String toString() {
        return NomUsuari + " " + NomExercici + " " + Timestamp_Inici + " " +  Videofile;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getIdUsuari() {
        return IdUsuari;
    }

    public void setIdUsuari(int IdUsuari) {
        this.IdUsuari = IdUsuari;
    }

    public String getNomUsuari() {
        return NomUsuari;
    }

    public void setNomUsuari(String NomUsuari) {
        this.NomUsuari = NomUsuari;
    }

    public int getIdExercici() {
        return IdExercici;
    }

    public void setIdExercici(int IdExercici) {
        this.IdExercici = IdExercici;
    }

    public String getNomExercici() {
        return NomExercici;
    }

    public void setNomExercici(String NomExercici) {
        this.NomExercici = NomExercici;
    }

    public String getTimestamp_Inici() {
        return Timestamp_Inici;
    }

    public void setTimestamp_Inici(String Timestamp_Inici) {
        this.Timestamp_Inici = Timestamp_Inici;
    }

    public String getTimestamp_Fi() {
        return Timestamp_Fi;
    }

    public void setTimestamp_Fi(String Timestamp_Fi) {
        this.Timestamp_Fi = Timestamp_Fi;
    }

    public String getVideofile() {
        return Videofile;
    }

    public void setVideofile(String Videofile) {
        this.Videofile = Videofile;
    }
}
