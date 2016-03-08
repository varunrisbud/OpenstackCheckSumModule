package database.connection.glance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * Created by alcohol on 3/8/16.
 */
public class ConnectionClass {
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public ConnectionClass() throws Exception {
        this.createConnection();
    }

    private void createConnection() throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("org.mariadb.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager.getConnection("jdbc:mariadb://localhost/glance?"
                            + "user=remoteuser&password=iforgotmypassword");
        } catch (Exception e) {
            throw e;
        }
    }

    public ResultSet executeQuery(String query) throws Exception{
        statement = connect.createStatement();
        // Result set get the result of the SQL query
        resultSet = statement.executeQuery(query);
        return resultSet;
    }

    /*private void writeMetaData(ResultSet resultSet) throws SQLException {
        //   Now get some metadata from the database
        // Result set get the result of the SQL query

        System.out.println("The columns in the table are: ");

        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
            System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
        }
    }

    private void writeResultSet(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);
            String name = resultSet.getString("name");
            System.out.println("Name: " + name);
        }
    }*/
}
