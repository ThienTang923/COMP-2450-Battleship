# 2450 - Battleship++

**Author: Thien Tang**

**Course: COMP 2450**

**Term: Summer 2026**

# Overview

This project begins with a domain model design for the classic [Battleship] game. Battleship is a two-player strategy game where each player secretly places ships on their own grid. Players then take turns attacking coordinates on the opponent’s grid in order to find and sink all of the opponent’s ships.

The purpose of this phase is to create an abstract design of the game before implementation. The design identifies the main objects in the system, the relationships between those objects, and the rules that describe valid states in the game. This includes players, boards, ships, cells, coordinates, and attacks.

This model represents the basic version of Battleship first. It can later be expanded to include new Battleship++ features such as variable board sizes, movable ships, and special effects.

[Battleship]: https://en.wikipedia.org/wiki/Battleship_(game)

## REPL

### Building and Running the REPL

The project has been built and tested to be run in IntelliJ. Open the project there, open "comp2450" folder, then the 

## Commands

The REPL supports the following commands:
* `HELP` — Shows all available commands and input formats.
* `ADD PLAYER` — Adds a new player by name.
* `ADD GAME` — Creates a new game using two existing players.
* `SELECT BOARD` — Selects the current board by player name.
* `ADD SHIP` — Adds a ship to the selected board.
* `ADD EFFECT` — Adds an effect to the selected board.
* `SHOW GAME` — Displays both boards and player information.
* `SHOW SHIPS` — Displays all ships in the current game.
* `SHOW EFFECTS` — Displays all effects in the current game.
* `MOVE SHIP` — Moves a selected ship to new coordinates.
* `REMOVE SHIP` — Removes a selected ship.
* `REMOVE EFFECT` — Removes a selected effect.
* `REMOVE GAME` — Deletes the current game.
* `EXIT` — Exits the REPL.

## Changes

During implementation, I have made several changes to the original domain model in Phase 1

* Renamed `Map` to `Board` because Java already has a built-in `Map` interface, so I used `Board` to avoid with the built-in and better represents the Battleship game area.
* Changed abstract types such as `Name`, `WholeNumber`, and `PositiveNumber` into concrete Java types such as `String` and `int`.
* Changed `Cell cells` into `Cell[][] cells` because the board is implemented as a two-dimensional grid.
* Changed `Status attackStatus` in `Cell` into `boolean attacked` because cell attack state only needs to represent attacked or not attacked.
* Added REPL, input, and output classes to separate user interaction from the domain model.
* Used Guava `Preconditions` to enforce invariants in constructors and methods.
## Domain Model

The classic Battleship game contains two players. Each player has their own board and a collection of ships. A board is made up of cells, and each cell represents one coordinate on the grid. A ship occupies one or more cells on a player’s board. During the game, players make attacks by choosing coordinates on the opponent’s board.

An attack can result in a hit if the chosen coordinate contains part of a ship, or a miss if the coordinate is empty. A ship is sunk when all of its occupied cells have been hit. The game ends when one player has sunk all of the opponent’s ships.

The initial version of the model is as follows:

```mermaid
classDiagram 
    class Player {
        Name playerName
        List~Ship~ ships
        WholeNumber successfulHits
        WholeNumber missedAttacks
        Map map

        moveShip()
        placeShip()
        attack(targetCoordinate)
    }

    note for Player "Invariants:
        * name is non-empty
        * ships is non-empty list
        * successfulHits, missedAttacks are non-negative number
        * map is non-null
    "
    class Map {
        int xSize
        int ySize
        Cell cells
        List~Ship~ ships
        List~Effect~ effects
    }

    note for Map "Invariants:
        * xSize, ySize is non-negative number
        * cell is non-empty
        * number of cells equals xSize x ySize
        * Ships do not overlap
        * All ships are inside map bounds
        * All cells have valid coordinates
    "

    class Ship {
        PositiveNumber size
        List~Coordinate~ coordinates
        PositiveNumber health
        WholeNumber currentHealth

        move()
        takeDamage()
        isSunk()
    }

    note for Ship "Invariants:
        * size, health are non negative number
        * coordinate is non-empty 
        * number of coordinates equals size
        * all coordinate are unique
        * currentHealth is between 0 and health
    "
    
    class Coordinate {
        int x
        int y
    }

    note for Coordinate "Invariants:
        * x,y is non negative number
    "
    
    class Cell {
        Coordinate coordinate
        Ship ship
        Status attackedStatus
        
        placeShip()
        removeShip()
        markAttacked()
        containsShip()
    }
    
    note for Cell "Invariants: 
        * coordinate is valid
        * cell contains at most one ship
        * attackedStatus is valid
    "
    
    class Effect {
        <<enumeration>>
        DOUBLE_DAMAGE
        SHIELD
        HEAL
        RADAR
    }
    
    class Game {
        Game instance
        Player player1
        Player player2
        Turn currentTurn
        Status gameStatus
        
        Game(Player player1, Player player2)
        getInstance(Player player1, Player player2)
        removeGame()
        
        switchTurn()
        checkWinner()
    }
    
    class Turn {
        <<enumeration>>
        PLAYER_ONE
        PLAYER_TWO
    }
    
    class Status {
        <<enumeration>>
        NOT_STARTED
        RUNNING
        FINISHED
    }
    note for Game "Invariants:
        * Both players are non null
        * Players are different
        * CurrentTurn is valid
    "
    Game *-- Player
    Player *-- Ship
    Player *-- Map
    Map *-- Cell
    Ship --> Coordinate
    Cell --> Coordinate
    Map o-- Effect
    Game o-- Turn
    Game o-- Status
```
