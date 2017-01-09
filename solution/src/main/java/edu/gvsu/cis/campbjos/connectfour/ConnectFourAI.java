package edu.gvsu.cis.campbjos.connectfour;

import edu.gvsu.cis.campbjos.connectfour.model.GameState;

import java.util.concurrent.*;

public class ConnectFourAI implements Callable<Integer> {

    private static final String BOARD_FLAG = "-b";
    private static final String PLAYER_FLAG = "-p";
    private static final String TIMEOUT_FLAG = "-t";

    private final GameState gameState;
    private final long timeout;

    private ConnectFourAI(final String board, final String player, final int timeout) {
        this.timeout = (long) timeout;
        gameState = GameState.createFromJson(board, player);
    }

    @Override
    public Integer call() throws Exception {
        return gameState.makeMove();
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Not enough arguments!");
            return;
        }
        final ConnectFourAI connectFourAI = parseConnectFourState(args);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> runAI = executor.submit(connectFourAI);

        int move;

        try {
            move = runAI.get(connectFourAI.timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            // Last ditch effort
            move = 0;
        }

        System.exit(move);
    }

    private static ConnectFourAI parseConnectFourState(final String[] args) {
        final int size = args.length;

        String boardArg = null;
        String playerArg = null;
        int timeout = 0;

        for (int i = 0; i < size; i++) {
            switch (args[i]) {
                case BOARD_FLAG:
                    boardArg = getFlagValue(args, i);
                    break;
                case PLAYER_FLAG:
                    playerArg = getFlagValue(args, i);
                    break;
                case TIMEOUT_FLAG:
                    timeout = parseTimeout(getFlagValue(args, i));
                    break;
                default:
                    break;
            }
        }
        return new ConnectFourAI(boardArg, playerArg, timeout);
    }

    private static String getFlagValue(final String[] args, final int currentIndex) {
        boolean hasNextArg = currentIndex <= args.length - 2;

        if (hasNextArg) {
            return args[currentIndex + 1];
        }
        return null;
    }

    private static int parseTimeout(final String timeoutArgument) {
        try {
            return Integer.parseInt(timeoutArgument);
        } catch (NumberFormatException e) {
            // We shouldn't get here
            return 0;
        }
    }
}
