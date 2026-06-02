package comp2450.model;

import com.google.common.base.Preconditions;

public class BoardEffect {
    private Effect effect;
    private Coordinate coordinate;

    public BoardEffect(Effect effect, Coordinate coordinate) {
        Preconditions.checkNotNull(effect, "effect cannot be null");
        Preconditions.checkNotNull(coordinate, "coordinate cannot be null");

        this.effect = effect;
        this.coordinate = coordinate;
    }

    public Effect getEffect() {
        return effect;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
