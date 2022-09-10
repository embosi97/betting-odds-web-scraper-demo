public class sportsEntity {

  private String homeTeam;

  private String awayTeam;

  private String eventTime;

  private String homeWinOdds;

  private String awayWinOdds;

  private String drawOdds;

  sportsEntity(String homeTeam, String awayTeam, String eventTime, String homeWinOdds, String awayWinOdds,
      String drawOdds) {
    this.homeTeam = homeTeam;
    this.awayTeam = awayTeam;
    this.eventTime = eventTime;
    this.homeWinOdds = homeWinOdds;
    this.awayWinOdds = awayWinOdds;
    this.drawOdds = drawOdds;
  }

  public String getHomeTeam() {
    return homeTeam;
  }

  public String getAwayTeam() {
    return awayTeam;
  }

  public String getEventTime() {
    return eventTime;
  }

  public String getHomeWinOdds() {
    return homeWinOdds;
  }

  public String getAwayWinOdds() {
    return awayWinOdds;
  }

  public String getDrawOdds() {
    return drawOdds;
  }

  public void setHomeTeam(String homeTeam) {
    this.homeTeam = homeTeam;
  }

  public void setAwayTeam(String awayTeam) {
    this.awayTeam = awayTeam;
  }

  public void setEventTime(String eventTime) {
    this.eventTime = eventTime;
  }

  public void setHomeWinOdds(String homeWinOdds) {
    this.homeWinOdds = homeWinOdds;
  }

  public void setAwayWinOdds(String awayWinOdds) {
    this.awayWinOdds = awayWinOdds;
  }

  public void setDrawOdds(String drawOdds) {
    this.drawOdds = drawOdds;
  }

  @Override
  public String toString() {
    Main.atomicNumber.incrementAndGet();
    return String.format("Event #%d %s (%s) v %s (%s) (DRAW %s) %s", Main.atomicNumber.get(), this.homeTeam, this.homeWinOdds,
        this.awayTeam, this.awayWinOdds, this.drawOdds, this.eventTime) + '\n';
  }

}