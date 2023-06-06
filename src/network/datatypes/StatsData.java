package network.datatypes;

public class StatsData extends PlayerOwnedData {
    private int lives;

    public StatsData(int userId, int lives) {
        setUserId(userId);
        this.lives = lives;
    }

    public int getLives() {
        return lives;
    }
}
