package comp2450.logic;

import com.google.common.base.Preconditions;
import comp2450.model.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class UsingEffect {

    private Random random;

    public UsingEffect() {
        this.random = new Random();
    }

    public void placeChosenEffectRandomly(Board board, Effect effect) {
        Preconditions.checkNotNull(board, "board cannot be null");
        Preconditions.checkNotNull(effect, "effect cannot be null");

        ArrayList<Coordinate> emptyCoordinates = getEmptyCoordinate(board);

        Preconditions.checkArgument(!emptyCoordinates.isEmpty(), "there are no empty spaces for an effect");

        Collections.shuffle(emptyCoordinates);

        Coordinate coordinate = emptyCoordinates.get(0);
        BoardEffect boardEffect = new BoardEffect(effect, coordinate);

        board.addEffect(boardEffect);
    }

    private ArrayList<Coordinate> getEmptyCoordinate(Board board) {
        Preconditions.checkNotNull(board, "board cannot be null");

        ArrayList<Coordinate> emptyCoordinates = new ArrayList<>();

        for (int i = 0; i < board.getXSize(); i++) {
            for (int y = 0; y < board.getYSize(); y++) {

                Coordinate coordinate = new Coordinate(i,y);

                if (!board.getCell(coordinate).containShip() && !hasEffectAt(board, coordinate)) {
                    emptyCoordinates.add(coordinate);
                }
            }
        }

        return emptyCoordinates;
    }

    private boolean hasEffectAt(Board board, Coordinate coordinate) {
        Preconditions.checkNotNull(board, "Board cannot be null");
        Preconditions.checkNotNull(coordinate, "coordinate cannot be null");

        for (BoardEffect boardEffect : board.getEffects()) {
            if (boardEffect.getCoordinate().equals(coordinate)) {
                return true;
            }
        }

        return false;
    }

    private BoardEffect findEffect(Board board, Coordinate coordinate) {

        Preconditions.checkNotNull(board, "board cannot be null");
        Preconditions.checkNotNull(coordinate, "coordinate cannot be null");

        for (BoardEffect boardEffect : board.getEffects()) {
            if (boardEffect.getCoordinate().equals(coordinate)) {
                return boardEffect;
            }
        }
        return null;
    }

    public String applyEffectPresent(Board currentBoard, Board enemyBoard, Ship ship) {

        Preconditions.checkNotNull(currentBoard, "board cannot be null");
        Preconditions.checkNotNull(ship, "ship cannot be null");
        Preconditions.checkNotNull(enemyBoard,"ship cannot be null");

        Coordinate shipCoordinate = ship.getCoordinates().get(0);
        BoardEffect boardEffect = findEffect(currentBoard, shipCoordinate);

        if (boardEffect == null) {
            return "No effect found";
        }

        String message = applyEffect(ship, boardEffect.getEffect(), enemyBoard, shipCoordinate);
        currentBoard.removeEffect(boardEffect);
        return message;

    }

    public String applyEffect(Ship ship, Effect effect, Board enemyBoard, Coordinate shipCoordinate) {

        Preconditions.checkNotNull(ship, "ship cannot be null");
        Preconditions.checkNotNull(effect, "effect cannot be null");
        Preconditions.checkNotNull(enemyBoard, "enemy board cannot be null");
        Preconditions.checkNotNull(shipCoordinate, "ship coordinate cannot be null");

        if (effect == Effect.DOUBLE_DAMAGE) {
            ship.activateDoubleDamage();
            return "Double damage activated. This ship's next successful attack does 2 damage.";
        } else if (effect == Effect.HEAL) {
            ship.heal();
            return "Heal activated. This ship restored 1 health.";

        } else if (effect == Effect.SHIELD) {
            ship.activateShield();
            return "Heal activated. This ship restored 1 health.";

        } else if (effect == Effect.RADAR) {
            boolean foundEnemy = hasEnemyShipNearby(enemyBoard, shipCoordinate);

            if (foundEnemy) {
                return "Radar activated. Enemy ship detected nearby.";
            } else {
                return "Radar activated. No enemy ship nearby.";
            }
        }
        return "Unknown effect";
    }

    private boolean hasEnemyShipNearby(Board enemyBoard, Coordinate center) {
        Preconditions.checkNotNull(enemyBoard, "enemy board cannot be null");
        Preconditions.checkNotNull(center, "center coordinate cannot be null");

        int x = center.getX();
        int y = center.getY();

        ArrayList<Coordinate> nearbyCoordinates = new ArrayList<>();

        return hasShipAt(enemyBoard, x+1,y) || hasShipAt(enemyBoard, x-1,y) || hasShipAt(enemyBoard, x, y+1) || hasShipAt(enemyBoard, x, y-1);
    }

    private boolean hasShipAt(Board board, int x, int y) {
        Preconditions.checkNotNull(board, "Board cannot be null.");

        if (x <0 || y <0 || x>= board.getXSize() || y >= board.getYSize()) {
            return false;
        }

        Coordinate coordinate = new Coordinate(x, y);

        return board.getCell(coordinate).containShip();
    }
}
