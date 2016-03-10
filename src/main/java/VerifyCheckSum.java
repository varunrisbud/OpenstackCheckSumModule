import checksum.remotecommandexecution.CommandExecutor;
import database.connection.glance.Images;
import htmlscrapper.ImageCheckSumExtractor;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by alcohol on 3/10/16.
 */
public class VerifyCheckSum {
    public static void main(String[] args) {
        VerifyCheckSum verifyCheckSum = new VerifyCheckSum();
        String imageID = verifyCheckSum.getImageIDfromMessage("Sucessfully updated image 9e137c3c-ce0a-40ed-8a10-cc0119f938fc");
        //String imageID = verifyCheckSum.getImageIDfromMessage(args[0]);
        try {
            Images images = new Images();
            String imageLocation = images.getLocationByID(imageID);
            String imageName = images.getNameByID(imageID);
            System.out.println("Image Name: " + imageName + "\nImage Location" + imageLocation);

            CommandExecutor commandExecutor = new CommandExecutor("student", "Cmpe86754231!", "130.65.159.58");
            final List<String> sha256sum = commandExecutor.executeCommandWithSudo("sha256sum", "Cmpe86754231!", imageLocation);
            String checkSum = sha256sum.get(0).split("\\s")[0];
            System.out.println("CheckSum: " + checkSum);

            ImageCheckSumExtractor checkSumExtractor = new ImageCheckSumExtractor();
            checkSumExtractor.verifyCheckSum(imageName, checkSum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getImageIDfromMessage(String message) {
        String[] splits = message.split("\\s");
        return splits[splits.length - 1];
    }
}
