package comp2450.logic;

import comp2450.model.*;
import com.google.common.base.Preconditions;

public class Attack {

    public boolean attack(Player attacker, Player defender, Ship attackingShip, Coordinate target) {
        Preconditions.checkNotNull(attacker, "attacker cannot be null");
        Preconditions.checkNotNull(defender, "defender cannot be null");
        Preconditions.checkNotNull(attackingShip, "attacking ship cannot be null");
        Preconditions.checkNotNull(target, "target cannot be null");

        Board defenderBoard = defender.getBoard();

        Preconditions.checkNotNull(defenderBoard, "defender board cannot be null");

        if(!defenderBoard.isInsideBoard(target)) {
            return false;
        }

        Cell targetCell = defenderBoard.getCell(target);

        if (targetCell.isAttacked()) {
            return false;
        }

        targetCell.markAttacked();

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
            }

            attacker.setSuccessfulHits();
        } else {
            attacker.missedAttack();
        }
        return true;
    }
}
