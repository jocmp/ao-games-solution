## Atomic Games 2017

Thanks for looking at my source code! I've divided my solution into
four main components:
* [ConnectFourAI] - handling CLI parsing, main and exit code
* [Board] - model which contains the grid passed in via the command-line
* [GameState] - The game at a point in time as specified by the JSON-formatted board provided at runtime. A GameState be sent back to the minimax algorithm to determine if a win has occurred.
* [Player] - model which may represent either `player-one` or `player-two`.
    * Player can run the minimax algorithm in an attempt to find the best move at a static depth.
    * A player can know the "next player" (`nextPlayer`) by identifying
    its own piece number, 1 or 2.
    * A list of [results] is supplied to the minimax method which will determine which move would be best played.

## Getting started
To download the source code:
`git clone https://github.com/josiahcampbell/ao-games-solution.git`

To build and deploy to the JAR to the tournament directory, run `./gradlew deploySolution` from the root directory, or run `gradle deploySolution` if you have the Gradle daemon installed.

The deploySolution task will also run the unit tests.

[Board]: https://github.com/josiahcampbell/ao-games-solution/blob/master/solution/src/main/java/edu/gvsu/cis/campbjos/connectfour/model/Board.java
[results]: https://github.com/josiahcampbell/ao-games-solution/blob/master/solution/src/main/java/edu/gvsu/cis/campbjos/connectfour/model/Result.java
[gamestate]: https://github.com/josiahcampbell/ao-games-solution/blob/master/solution/src/main/java/edu/gvsu/cis/campbjos/connectfour/model/GameState.java
[Player]: https://github.com/josiahcampbell/ao-games-solution/blob/master/solution/src/main/java/edu/gvsu/cis/campbjos/connectfour/model/Player.java
[ConnectFourAI]: https://github.com/josiahcampbell/ao-games-solution/blob/master/solution/src/main/java/edu/gvsu/cis/campbjos/connectfour/ConnectFourAI.java
