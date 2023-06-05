package network.datatypes;

abstract public class PositionData extends PlayerOwnedData {
    private int playerX;
    private int playerY;

    public void setPlayerPosition(int x, int y) {
        playerX = x;
        playerY = y;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }
}
