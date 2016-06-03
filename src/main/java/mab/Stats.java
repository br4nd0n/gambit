package mab;

/**
 * Created by brobrien on 3/31/16.
 */
public class Stats {
    private long trials;
    private long wins;

    public Stats(long trials, long wins) {
        this.trials = trials;
        this.wins = wins;
    }

    public long getTrials() {
        return trials;
    }

    public long getWins() {
        return wins;
    }

    public long getLosses() {
        return trials - wins;
    }
}
