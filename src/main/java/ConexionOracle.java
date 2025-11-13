import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConexionOracle {
    public static void main(String[] args) {
      
        String jdbcURL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
        String dbUsername = "PERS_TABLAS"; 
        String dbPassword = "pers_tablas"; 

        try {
            
            Class.forName("oracle.jdbc.OracleDriver");

            
            Connection connection = DriverManager.getConnection(jdbcURL, dbUsername, dbPassword);
            System.out.println("Conexion exitosa con Oracle");

            
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM zona"; 
            ResultSet resultSet = statement.executeQuery(query);

            
            while (resultSet.next()) {
                System.out.println("ID Zona: " + resultSet.getInt("id_zona"));
                System.out.println("Nombre: " + resultSet.getString("nombre"));
            }

            connection.close();
            System.out.println("?Conexión cerrada correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
