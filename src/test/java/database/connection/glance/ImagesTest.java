package database.connection.glance;

/**
 * Created by alcohol on 3/8/16.
 */
import static org.junit.Assert.*;
import org.junit.Test;

public class ImagesTest {

    @Test
    public void getNameByIDTest() {
        try {
            Images glanceImage = new Images();
            assertEquals("Ubuntu 14.04 Server.iso", glanceImage.getNameByID("7f6201b7-94ba-4dc9-96fa-bb2003fe152f"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getLocationByIDTest() {
        try {
            Images glanceImage = new Images();
            assertEquals("/var/lib/glance/images/7f6201b7-94ba-4dc9-96fa-bb2003fe152f", glanceImage.getLocationByID("7f6201b7-94ba-4dc9-96fa-bb2003fe152f"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
