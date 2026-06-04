# 2450 - Battleship++

**Author: Thien Tang**

**Course: COMP 2450**

**Term: Summer 2026**

# Overview

This project begins with a domain model design for the classic [Battleship] game. Battleship is a two-player strategy game where each player secretly places ships on their own grid. Players then take turns attacking coordinates on the opponent’s grid in order to find and sink all of the opponent’s ships.

The purpose of this phase is to create an abstract design of the game before implementation. The design identifies the main objects in the system, the relationships between those objects, and the rules that describe valid states in the game. This includes players, boards, ships, cells, coordinates, and attacks.

This model represents the basic version of Battleship first. It can later be expanded to include new Battleship++ features such as variable board sizes, movable ships, and special effects.

The purpose of Phase 2 is to implement the domain model from Phase 1 and update the model so that it accurately reflects the Java implementation. The project also includes a command-line REPL so that the model can be tested by creating, reading , updating, and deleting objects during runtime.

[Battleship]: https://en.wikipedia.org/wiki/Battleship_(game)

## Flows of Interaction

### Diagrams

#### Overall Flow

This diagram is showing general of how the game is played, each sub-task here will show the specific of work.

```mermaid
flowchart
subgraph Game Flow
    direction TB
    setupGame[[Setup Game]]
    
    placeShips[Place Ships]
    
    placeEffects[Place Effects]
    
    combat[Combat]
    
    gameOver[[Game Ended]]
    
    setupGame -- Game created --> placeShips
    placeShips -- Ships placed --> placeEffects
    placeEffects -- Effects hidden on board --> combat
    combat -- Combat ended,
        switch player --> combat
    combat -- Game ended --> gameOver
end
```

#### Setup Game

```mermaid
flowchart
subgraph setup Game
    direction TB
    getPlayers[[Enter player names]]
    
    createGame{Create Game}
    
    nextTask[[Move to ship placement]]

    getPlayers -- Player names player one, player two --> createGame
    createGame -. Game created, show player info .-> nextTask
    createGame -. Invalid players or same player .-> getPlayers
end
```
#### Place ships

```mermaid
flowchart
subgraph Place ships
    direction TB
    getShipInfo[[Enter ship placement info]]
    
    placeShip{Place ship}
    
    nextTask[[Move to effect placement]]
    
    getShipInfo -- Ship info player name, ship id, x y cooridnate --> placeShip
    placeShip -. Ship placed, show update board .-> nextTask
    placeShip -.  Invalid location, outside board, or overlap .-> getShipInfo    
end
```

#### Place Effect

```mermaid
flowchart
subgraph Place Effects
    direction TB
    shipsPlaced[[Ships placed]]

    placeEffect{Place random effects}

    effectsReady[[Effects hidden on board]]

    shipsPlaced -- Empty cell locations and number of effects --> placeEffect
    placeEffect -. Effects placed on cells without ships .-> effectsReady
    placeEffect -. No empty cells or placement failed .-> shipsPlaced
end
```
#### Combat

```mermaid
flowchart
subgraph Combat
direction TB

    getAction[[Get action]]

    moveShip{Move ship}
    selectShip[Select ship]
    normalMove{Do normal move}
    submarineMove{Do submarine move}
    applyEffect{Apply effect}
    fireAttack{Fire attack}
    checkWinner{Check winner}
    endGame[[**__Game__** ended]]
    endCombat[[Combat ended]]

    getAction -- Move ship + confirmation --> moveShip
    moveShip -. Show movable ships .-> selectShip
    moveShip -. Skip movement .-> fireAttack

    selectShip -- Selects normal ship --> normalMove
    normalMove -. Invalid move .-> getAction
    normalMove -. Ship moved .-> applyEffect

    selectShip -- Selects submarine --> submarineMove
    submarineMove -. Invalid target or no valid path .-> getAction
    submarineMove -. Submarine moved .-> applyEffect

    applyEffect -. No effect found .-> fireAttack
    applyEffect -. Effect applie and ship survives .-> fireAttack
    applyEffect -. Effect applied and ship destroyed .-> checkWinner

    fireAttack -- Select attacking ship and target x y --> checkWinner
    fireAttack -. Invalid target or already fired .-> getAction
    fireAttack -. Hit or miss recorded .-> checkWinner

    checkWinner -. Player has no ships left .-> endGame
    checkWinner -. Both players still have ships .-> endCombat

    getAction -. Invalid or cancelled .-> getAction
    selectShip -. Invalid selection .-> selectShip
end
```

#### Submarine Movement 

```mermaid
flowchart
subgraph Submarine Movement
    direction TB
    getTarget[[Enter submarine movement]]
    
    checkDestination{Check destination}
    checkPath{Check fired path}
    moveSubmarine[Move submarine]
    
    endMove[[Submarine movement ended]]
    
    getTarget -- Submarine id
        and target x y --> checkDestination
    
    checkDestination -. Destination was already fired on .-> endMove
    checkDestination -. Destination is safe .-> checkPath
    
    checkPath -. No valid fired path .-> endMove
    checkPath -. Valid fired path found .-> moveSubmarine
    
    moveSubmarine -. Show new submarine location .-> endMove
end
```
## REPL

### Building and Running the REPL

The project has been built and tested to be run in IntelliJ IDEA and Maven.

To run this project in IntelliJ: Open the main entry point file: `src/main/java/comp2450/REPL`

and then run the main method in `REPL`.

## Commands

### The REPL supports the following commands:
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
* Added a unique game design using the `Game` singleton pattern because only one game exists in the system at a time.
* Added REPL-related classes to handle user input and output separately from the domain model.
* Update domain model to show concrete Java types and relationships that match the implementation.

## Domain Model

The classic Battleship game contains two players. Each player has their own board and a collection of ships. A board is made up of cells, and each cell represents one coordinate on the grid. A ship occupies one or more cells on a player’s board. During the game, players make attacks by choosing coordinates on the opponent’s board.

An attack can result in a hit if the chosen coordinate contains part of a ship, or a miss if the coordinate is empty. A ship is sunk when all of its occupied cells have been hit. The game ends when one player has sunk all of the opponent’s ships.

The initial version of the model is as follows:

```mermaid
classDiagram 
    class Player {
        -String playerName
        -List~Ship~ ships
        -int successfulHits
        -int missedAttacks
        -Board board
        
        Player(String playerName, Board board)
        addShip(Ship ship)
        moveShip(Ship ship, List~Coordinate~ newCoordinate)
        setSuccessfulHits()
        missedAttack()
        getPlayerName() String
        getShip() List~Ship~
        getBoard() Board
        getSuccessfulHits() int
        getMissedAttack() int
        placeShip(Ship ship)
    }

    note for Player "Invariants:
        * playerName is not null
        * playerName is not blank
        * board is not null
        * ships is not null
        * successfulHits >= 0
        * missedAttacks >= 0"
    
    class Board {
        int xSize
        int ySize
        Cell[][] cells
        List~Ship~ ships
        List~BoardEffect~ effects
        
        Board(int xSize, int ySize)
        addShip(Ship ship)
        removeShip(Ship ship)
        getCell(Coordinate coordinate) Cell
        isInsideBoard(Coordinate coordinate) boolean
        getShips() List~Ship~
        getEffects() List~Effect~
        getXSize() int
        getYSize() int
        addEffect(BoardEffect effect)
        removeEffect(BoardEffect effect)
        getEffect(int index) BoardEffect
        getEffects() List~BoardEffect~
    }

    note for Board "Invariants:
        * xSize > 0
        * ySize > 0
        * cells is not null
        * cells is a two-dimensional Cell array
        * every Cell in cells is not null
        * each Cell has a valid Coordinate inside the board
        * ships is not null
        * effects is not null
    "
    class BoardEffect {
        Effect effect
        Coordinate coordinate
        
        BoardEffect(Effect effect, Coordinate coordinate)
        getEffect() Effect;
        getCoordinate() Cooridate;
    }
    
    note for BoardEffect "Invariants: 
        *effect is not null 
        *coordinate is not null
        *coordinate represents the location of the effect on board
    "
    class Ship {
        int size
        List~Coordinate~ coordinates
        int health
        int currentHealth

        Ship(int size, List~Coordinate~ coordinates)
        move(List~Coordinate~ newCoordinates)
        takeDamage()
        isSunk() boolean
        getSize() int
        getCurrentHealth() int
        getCoordinates() List~Coordinate~
    }

    note for Ship "Invariants:
        * size > 0
        * coordinates is not null
        * coordinates.size() == size
        * currentHealth >= 0
        * currentHealth <= size
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
        boolean attacked;

        Cell(Coordinate coordinate)
        placeShip(Ship ship)
        containShip() boolean
        removeShip()
        markAttacked()
        isAttacked() boolean
        getShip() Ship
        getCoordinate() Coordinate
    }
    
    note for Cell "Invariants: 
        * coordinate is not null
        * ship can be null if the cell is empty
        * ship must not be overwritten if the cell already has a ship
        * attacked is false when the cell is created
        * attacked becomes true after markAttacked()
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
    Player *-- Board
    Board *-- Cell
    Ship --> Coordinate
    Cell --> Coordinate
    Board o-- Effect
    Game o-- Turn
    Game o-- Status
    BoardEffect --> Effect
    BoardEffect --> Coordinate
```
