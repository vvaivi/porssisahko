import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class Scraper {
    public Day scrapeData() {
        String url = "https://www.porssisahkoa.fi";

        try {
            Document doc = Jsoup.connect(url).get();
            Elements cells = doc.select("tr");

            // Remove middle night hours
            if (!cells.isEmpty() && cells.size() > 7) {
                for (int i = 0; i < 8; i++) {
                    cells.remove(0);
                }
            }

            Hour[] hours = new Hour[cells.size()];

            for (Element cell : cells) {
                String[] parts = cell.text().split("\\s+");

                String startHour = parts[0].replaceAll("[^0-9]", "");
                String price = parts[3].replace(',', '.').replaceAll("[^-0-9.]", "");

                hours[cells.indexOf(cell)] = new Hour(Double.parseDouble(price) / 100, Integer.parseInt(startHour));
            }

            return new Day(hours);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Scraper scraper = new Scraper();
        Day day = scraper.scrapeData();
    }
}