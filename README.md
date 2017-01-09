## Atomic Games 2017 - (AO/AA/17)

Thanks for looking at my source code! I've divided my solution into
four main components:
* ConnectFourAI - handling CLI parsing, main and exit code
* Board - model which contains the grid passed in via the commandline
* Player  - model which may represent either `player-one` or `player-two`. 
    * Player can run the minimax algorithm in an attempt to find the best
    move at a static depth.
    * A player can know the "next player" (`nextPlayer`) by identifying 
    its own piece number, 1 or 2.


## Getting started
To run the project, `git clone https://github.com/josiahcambell/ao-games-solution.git`

To build and deploy, run `./gradlew deploySolution`, or `gradle deploySolution` if you have the
Gradle daemon installed. Deploying the solution will also run the unit tests.
