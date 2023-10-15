package cat.paucasesnovescifp.simulapinstructor.dataaccess;

import cat.paucasesnovescifp.simulapinstructor.models.Intent;
import cat.paucasesnovescifp.simulapinstructor.models.Usuari;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

/**
 *
 * @author Miguel
 */
public class DataAccess {

    private Connection getConnection() {
        Connection connection = null;
        Properties properties = new Properties();
        try {
            //properties.load(DataAccess.class.getClassLoader().getResourceAsStream("properties/application.properties"));
            //connection = DriverManager.getConnection(properties.getProperty("connectionUrl"));
            String connectionUrl = "jdbc:sqlserver://localhost:1433;database=simulapdb;user=sa;password=Pwd1234.;encrypt=false;loginTimeout=10;";
            String connectionUrlAzure = "jdbc:sqlserver://simulap.database.windows.net:1433;database=simulapdb;user=simulapadmin@simulap;password=Pwd12345.;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

            connection = DriverManager.getConnection(connectionUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Usuari getUser(String email) {
        Usuari user = null;
        String sql = "SELECT * FROM Usuaris WHERE Email = ?";
        try (Connection connection = getConnection(); PreparedStatement selectStatement = connection.prepareStatement(sql);) {
            selectStatement.setString(1, email);
            ResultSet resultSet = selectStatement.executeQuery();
            user = new Usuari();
            while (resultSet.next()) {
                user.setId(resultSet.getInt("Id"));
                user.setNom(resultSet.getString("Nom"));
                user.setEmail(resultSet.getString("Email"));
                user.setPasswordHash(resultSet.getString("PasswordHash"));
                user.setInstructor(resultSet.getBoolean("IsInstructor"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public int registerUser(Usuari u) {
        String sql = "INSERT INTO dbo.Usuaris (Nom, Email, PasswordHash, IsInstructor)"
                + " VALUES (?,?,?,?)" +
                " SELECT CAST(SCOPE_IDENTITY() as int)";
        try (   Connection conn = getConnection();
                PreparedStatement insertStatement = conn.prepareStatement(sql)) {
            insertStatement.setString(1, u.getNom());
            insertStatement.setString(2, u.getEmail());
            insertStatement.setString(3, u.getPasswordHash());
            insertStatement.setBoolean(4, u.isInstructor());
            
            int newUserId = insertStatement.executeUpdate();
            return newUserId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public ArrayList<Intent> getAttemptsPendingReview() {
        ArrayList<Intent> intents = new ArrayList<>();
        String sql = "SELECT Intents.Id, Intents.IdUsuari, Usuaris.Nom,"
                + " Intents.IdExercici, Exercicis.NomExercici, Timestamp_Inici,"
                + " Timestamp_Fi, VideoFile"
                + " FROM Intents INNER JOIN Usuaris ON Intents.IdUsuari=Usuaris.Id"
                + " INNER JOIN Exercicis ON Intents.IdExercici=Exercicis.Id" 
                + " WHERE Intents.Id NOT IN"
                + " (SELECT IdIntent FROM Review)"
                + " ORDER BY Timestamp_Inici";
        try (Connection connection = getConnection();
                PreparedStatement selectStatement = connection.prepareStatement(sql);) {
            
            ResultSet resultSet = selectStatement.executeQuery();
            
            while (resultSet.next()) {
                Intent attempt = new Intent();
                attempt.setId(resultSet.getInt("Id"));
                attempt.setIdUsuari(resultSet.getInt("IdUsuari"));
                attempt.setNomUsuari(resultSet.getString("Nom"));
                attempt.setIdExercici(resultSet.getInt("IdExercici"));
                attempt.setNomExercici(resultSet.getString("NomExercici"));
                attempt.setTimestamp_Inici(resultSet.getString("Timestamp_Inici"));
                attempt.setTimestamp_Fi(resultSet.getString("Timestamp_Fi"));
                attempt.setVideofile(resultSet.getString("VideoFile"));
                intents.add(attempt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return intents;
    }
}
