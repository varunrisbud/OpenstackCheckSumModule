package htmlscrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by alcohol on 3/9/16.
 */
public class ImageCheckSumExtractor {
    public void verifyCheckSum(String imageName, String calculatedCheckSum) throws Exception {
        String url = "http://checksums.remo-ribeli.ch/sitemap.html";
        //print("Fetching %s...", url);
        String nextUrl = this.getUrlWithName(url, imageName);
        //System.out.println(nextUrl);
        if(isContentPresent(nextUrl, calculatedCheckSum)) System.out.println("Image is Verified");
        else System.out.println("Warning Image is Unverified");
    }

    public String getUrlWithName(String urlToCrawl, String matchString) throws IOException {
        Document doc = Jsoup.connect(urlToCrawl).get();
        Elements links = doc.select("a[href]");
        String imageLink = "Not Found";

        //print("\nLinks: (%d)", links.size());
        for (Element link : links) {
            //print(" * a: <%s>  (%s)", link.attr("abs:href"), link.text());
            if(link.text().equals(matchString)) {
                imageLink = link.attr("abs:href");
                break;
            }
        }

        //print("\nBody: (%s)", doc.body().toString().contains("d942fd8a056f635062899b58e9e875eb89eaec9be09a0fefa713f4e162bb647e") ? "yes":"no");
        return imageLink;
    }

    private boolean isContentPresent(String urlToCrawl, String matchString) throws IOException {
        Document doc = Jsoup.connect(urlToCrawl).get();
        //System.out.println("Match String " + matchString);
        //System.out.println(doc.select("meta[name]").toString());
        return doc.select("meta[name]").toString().contains(" " + matchString + ",");
    }

    private void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
}
