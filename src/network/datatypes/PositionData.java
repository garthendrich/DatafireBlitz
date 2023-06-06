package network.datatypes;

abstract public class PositionData extends PlayerOwnedData {
    private double playerX;
    private double playerY;

    public void setPlayerPosition(double x, double y) {
        playerX = x;
        playerY = y;
    }

    public double getPlayerX() {
        return playerX;
    }

    public double getPlayerY() {
        return playerY;
    }
}
