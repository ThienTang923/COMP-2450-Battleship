package comp2450.logic;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import comp2450.model.*;

import java.security.cert.CertPathParameters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class UsingEffect {

    private Random random;

    public UsingEffect() {
        this.random = new Random();
    }

    public void placeRandomEffect(Board board, int numOfEffects) {
        Preconditions.checkNotNull(board, "board cannot be null");
        Preconditions.checkArgument(numOfEffects >= 0,"number of effects cannot be negative");

        ArrayList<Coordinate> emptyCoordinates = getEmptyCoordinate(board);

        Preconditions.checkArgument(numOfEffects <= emptyCoordinates.size(),"number of effects cannot be greater than empty spaces");

        Collections.shuffle(emptyCoordinates);

        for (int i= 0; i < numOfEffects; i++) {
            Coordinate coordinate = emptyCoordinates.get(i);
            Effect effect = getRandomEffect();
            BoardEffect boardEffect = new BoardEffect(effect, coordinate);
            board.addEffect(boardEffect);
        }
    }

    private ArrayList<Coordinate> getEmptyCoordinate(Board board) {
        Preconditions.checkNotNull(board, "board cannot be null");

        ArrayList<Coordinate> emptyCoordinates = new ArrayList<>();

        for (int x = 0; x < board.getXSize(); x++) {
            for (int y = 0; y < board.getYSize(); y++) {

                Coordinate coordinate = new Coordinate(x,y);

                if (!board.getCell(coordinate).containShip()) {
                    emptyCoordinates.add(coordinate);
                }
            }
        }

        return emptyCoordinates;
    }

    private Effect getRandomEffect() {
        Effect[] effects = Effect.values();
        int index = this.random.nextInt(effects.length);

        return effects[index];
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

        if (effect == Effect.DOUBLE_DAMAGE) {
            ship.activateDoubleDamage();
        } else if (effect == Effect.HEAL) {
            ship.heal();
        } else if (effect == Effect.SHIELD) {
            ship.activateShield();
        } else if (effect == Effect.RADAR) {
            boolean foundEnemy = hasEnemyShipNearby(enemyBoard, shipCoordinate);

            if (foundEnemy) {
                return "Radar activated. Eneymy ship detected nearby";
            } else {
                return "Radar activated. No enemy ship nearby";
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

        nearbyCoordinates.add(new Coordinate(x+1,y));
        nearbyCoordinates.add(new Coordinate(x-1,y));
        nearbyCoordinates.add(new Coordinate(x,y+1));
        nearbyCoordinates.add(new Coordinate(x,y-1));

        for (Coordinate coordinate : nearbyCoordinates) {
            if (enemyBoard.isInsideBoard(coordinate)) {
                if (enemyBoard.getCell(coordinate).containShip()) {
                    return true;
                }
            }
        }
        return false;
    }
}
