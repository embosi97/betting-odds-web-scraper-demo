import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.net.URLEncoder;
import java.io.IOException;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.http.HttpResponse;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class Main {

  private static String homeTeam;
  private static String awayTeam;
  private static String eventTime;
  private static String homeWinOdds;
  private static String awayWinOdds;
  private static String drawOdds;
  public static AtomicInteger atomicNumber = new AtomicInteger(0);

  //For converting Fraction Odds to American Odds
  private static String convertFractionToAmericanOdds(String fractionOdds) {
    if (fractionOdds.isEmpty()) {
      return "N/A";
    }
    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    float numerator = Float.valueOf(fractionOdds.split("/")[0]);
    float denominator = Float.valueOf(fractionOdds.split("/")[1]);
    float decimal = numerator / denominator;
    
    if (numerator > denominator) {
      int usOdds = (int) Float.parseFloat(decimalFormat.format(decimal)) * 100;
      return String.format("%c%s", '+',
          Integer.toString(usOdds));
    } else {
      int usOdds = (int) Float.parseFloat(decimalFormat.format(100 / decimal));
      return String.format("%c%s", '-', 
          Integer.toString(usOdds));
    }
  }

  //Using Unirest and Jsoup to web-scrape the odds for a given sport
  private static ArrayList<sportsEntity> scrapeOdds(final String sport, ArrayList<sportsEntity> sportEntities) throws UnirestException {

    final String url = String.format("https://easyodds.com/%s", sport);

    final HttpResponse<String> response = Unirest.get(url).asString();

    final Document document = Jsoup.parseBodyFragment(response.getBody());
    
    for (Element element : document.select("div.tournament-event > div")) {

      eventTime = element.select("div.tournament-event__cell.event-timestamp").text().trim();

      homeWinOdds = convertFractionToAmericanOdds(
          element.select("div.tournament-event__cell.event-team.event-team-home > span").text().trim());

      homeTeam = element.select("div.tournament-event__cell.event-team.event-team-home > div > div.event-team__name")
          .text().trim();

      awayWinOdds = convertFractionToAmericanOdds(
          element.select("div.tournament-event__cell.event-team.event-team-away > span").text().trim());

      awayTeam = element.select("div.tournament-event__cell.event-team.event-team-away > div > div.event-team__name")
          .text().trim();

      drawOdds = convertFractionToAmericanOdds(
        element.select("div.tournament-event__cell.event-draw > div > span").text().trim());

      sportsEntity se = new sportsEntity(homeTeam, awayTeam, eventTime, homeWinOdds, awayWinOdds, drawOdds);

      sportEntities.add(se);
    }
    
    return sportEntities;
  }
  public static void main(String[] args) throws Exception {

    ArrayList<sportsEntity> sportEntities = new ArrayList<>();

    //sportEntities = Main.scrapeOdds("boxing-ufc", sportEntities);

    sportEntities = Main.scrapeOdds("football", sportEntities);

    //Print out all scraped events
    for(sportsEntity sp : sportEntities){
      System.out.println(sp.toString());
    }

  }

}