package cat.paucasesnovescifp.simulapinstructor.models;

/**
 *
 * @author Miguel
 */
public class Usuari {
    private int Id;
    private String Nom;
    private String Email;
    private String PasswordHash;
    private byte [] Foto;
    private boolean Instructor;

    @Override
    public String toString() {
        return Id + ", " + Nom + ", " + Email;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }
 
    public boolean isInstructor() {
        return Instructor;
    }

    public void setInstructor(boolean Instructor) {
        this.Instructor = Instructor;
    }

    public String getPasswordHash() {
        return PasswordHash;
    }

    public void setPasswordHash(String PasswordHash) {
        this.PasswordHash = PasswordHash;
    }
}
