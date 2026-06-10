package comp2450.model;

import com.google.common.base.Preconditions;

import java.io.StreamCorruptedException;

public class BoardEffect {
    private Effect effect;
    private Coordinate coordinate;

    public BoardEffect(Effect effect, Coordinate coordinate) {
        Preconditions.checkNotNull(effect, "effect cannot be null");
        Preconditions.checkNotNull(coordinate, "coordinate cannot be null");

        this.effect = effect;
        this.coordinate = coordinate;

        checkBoardEffect();
    }

    public Effect getEffect() {
        checkBoardEffect();
        return this.effect;
    }

    public Coordinate getCoordinate() {
        checkBoardEffect();
        return this.coordinate;
    }
    private void checkBoardEffect() {
        Preconditions.checkNotNull(effect, "Effect should not be null.");
        Preconditions.checkNotNull(coordinate, "Coordinate should not be null.");
    }
}
