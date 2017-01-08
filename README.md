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

## Comments

>What purpose does this comment serve? It’s certainly not more informative than the
 code. It does not justify the code, or provide intent or rationale. It is not easier to read than
 the code. Indeed, it is less precise than the code and entices the reader to accept that lack of
 precision in lieu of true understanding.

I will disagree with Uncle Bob slightly in this case. I've written comments throughout my code to
further explain the method behind my methodology. I have taken time to refactor code in order to
make it clearer, but in most cases, there's a higher concept that I'm trying to convey.

