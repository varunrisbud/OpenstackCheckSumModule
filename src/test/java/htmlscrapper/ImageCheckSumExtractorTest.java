package htmlscrapper;

/**
 * Created by alcohol on 3/9/16.
 */
import static org.junit.Assert.*;
import org.junit.Test;

import java.io.IOException;

public class ImageCheckSumExtractorTest {
    @Test
    public void getCheckSumTest() {
        ImageCheckSumExtractor imageCheckSumExtractor = new ImageCheckSumExtractor();
        try {
            imageCheckSumExtractor.verifyCheckSum("ubuntu-12.04.1-desktop-amd64.iso", "d942fd8a056f635062899b58e9e875eb89eaec9be09a0fefa713f4e162bb647e");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getUrlWithNameTest() {
        String url = "http://checksums.remo-ribeli.ch/sitemap.html";
        String toMatch = "ubuntu-12.04.1-desktop-amd64.iso";
        String expect = "http://checksums.remo-ribeli.ch/ubuntu/ubuntu-12-04-1/ubuntu-12.04.1-desktop-amd64.iso/index.html";
        try {
            assertEquals(expect, new ImageCheckSumExtractor().getUrlWithName(url, toMatch));
        } catch (IOException e) {
            e.printStackTrace();
        }
       /* url = expect;
        toMatch = "abc";
        expect = "Not Found";
        try {
            assertEquals(expect, new ImageCheckSumExtractor().getUrlWithName(url, toMatch));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
