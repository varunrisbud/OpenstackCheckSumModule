package database.connection.glance;

/**
 * Created by alcohol on 3/8/16.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Images extends ConnectionClass{
        private Connection connect = null;
        private Statement statement = null;
        private PreparedStatement preparedStatement = null;
        private ResultSet resultSet = null;

        public Images() throws Exception {
            super();
        }

        public String getNameByID(String imageID) {
            StringBuilder query = new StringBuilder();
            query.append("Select Name, disk_format from images where ID=").append("'").append(imageID).append("'");
            String imageName = "not found";
            try {
                ResultSet resultSet = super.executeQuery(query.toString());
                while (resultSet.next()) {
                    imageName = resultSet.getString("Name") + "." + resultSet.getString("disk_format");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return imageName;
        }

        public String getLocationByID(String imageID) {
            StringBuilder query = new StringBuilder();
            query.append("Select value from image_locations where image_id=").append("'").append(imageID).append("'");
            String location = "not found";
            try {
                ResultSet resultSet = super.executeQuery(query.toString());
                while (resultSet.next()) {
                    location = resultSet.getString("value").substring(7);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return location;
        }
}
