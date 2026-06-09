package comp2450.logic;

import comp2450.model.*;
import com.google.common.base.Preconditions;
import comp2450.exceptions.InvalidAttackException;

public class Attack {

    public boolean attack(Player attacker, Player defender, Ship attackingShip, Coordinate target) throws InvalidAttackException {

        Preconditions.checkNotNull(attacker, "attacker cannot be null");
        Preconditions.checkNotNull(defender, "defender cannot be null");
        Preconditions.checkNotNull(attackingShip, "attacking ship cannot be null");
        Preconditions.checkNotNull(target, "target cannot be null");

        if (attacker == defender) {
            throw new InvalidAttackException("Invalid attack. Attacker cannot attack their own board. Choose the opponent as the defender.");
        }

        Board attackerBoard = attacker.getBoard();
        Board defenderBoard = defender.getBoard();

        Preconditions.checkNotNull(defenderBoard, "defender board cannot be null");
        Preconditions.checkNotNull(attacker, "attacker board cannot be null");

        if (!attacker.getShips().contains(attackingShip)) {
            throw new InvalidAttackException("Invalid attacking ship. The attacking player does not own this ship");
        }

        if (!attackerBoard.getShips().contains(attackingShip)) {
            throw new InvalidAttackException("Invalid attacking ship. The attacking player did not own this ship.");
        }

        if (attackingShip.isSunk()) {
            throw new InvalidAttackException("Invalid attacking ship. A sunk ship cannot attack.");
        }

        if(!defenderBoard.isInsideBoard(target)) {
            throw new InvalidAttackException("Attack target is outside the board. Choose a coordinate inside the board.");
        }

        Cell targetCell = defenderBoard.getCell(target);

        if (targetCell.isAttacked()) {
            throw new InvalidAttackException("This coordinate was already attacked. Choose a different target");
        }

        targetCell.markAttacked();

        boolean sunkShip = false;

        if (targetCell.containShip()) {
            Ship hitShip = targetCell.getShip();
            int damage = attackingShip.useAttackDamage();

            for (int i = 0; i < damage; i++) {
                if (!hitShip.isSunk()) {
                    hitShip.takeDamage();
                }
            }

            if (hitShip.isSunk()) {
                defenderBoard.removeShip(hitShip);
                defender.removeShip(hitShip);
                sunkShip = true;
            }

            attacker.setSuccessfulHits();
        } else {
            attacker.missedAttack();
        }
        return sunkShip;
    }
}
