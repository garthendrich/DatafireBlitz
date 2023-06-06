package network.datatypes;

public class LivesData extends PlayerOwnedData {
    private int lives;

    public LivesData(int userId, int lives) {
        setUserId(userId);
        this.lives = lives;
    }

    public int getLives() {
        return lives;
    }
}
