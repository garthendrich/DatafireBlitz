package network.datatypes;

public class GameInputData extends PlayerOwnedData {
    public static enum Input {
        moveLeft, moveRight, jump, drop, stopHorizontal, stopVertical, startShoot, stopShoot
    }

    private Input input;

    public GameInputData(Input input) {
        this.input = input;
    }

    public Input getInput() {
        return input;
    }
}
