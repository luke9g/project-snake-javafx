public class LeaderboardEntry implements Comparable<LeaderboardEntry> {
    private final String name;
    private final String date;
    private final double score;

    public LeaderboardEntry(String name, String date, double score) {
        this.name = name;
        this.date = date;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public double getScore() {
        return score;
    }

    @Override
    public int compareTo(LeaderboardEntry h) {
        return Double.compare(h.score, this.score);
    }

    @Override
    public String toString() {
        return name + "\t" + date + "\t" + score;
    }
}
