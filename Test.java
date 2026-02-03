import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Test {
  private static final Mark cpuMark = Mark.O;
  private static final Mark playerMark = Mark.X;

  private static final Scanner sc = new Scanner(System.in);
  private static final Random rand = new Random();

  private static final String PROMPT_MOVE = Colors.RESET + "> Your move (row col) : " + Colors.BLUE;

  // --- Input -----------------------------------------------------------

  private static int[] readMove() {
    while (true) {
      try {
        System.out.print(PROMPT_MOVE);
        return new int[] { sc.nextInt(), sc.nextInt() };
      } catch (Exception e) {
        sc.nextLine();
      }
    }
  }

  // --- Menu ---

  private static int selectMode() {
    System.out.println(Colors.BLUE + "\n=== Tic Tac Toe ===" + Colors.RESET);
    System.out.println("  | 1 - MinMax");
    System.out.println("  | 2 - Alpha-Beta\n");
    while (true) {
      try {
        System.out.print(Colors.RESET + "  > Your choice : " + Colors.BLUE);
        int mode = sc.nextInt();
        if (mode == 1 || mode == 2) {
          String name = (mode == 1) ? "MinMax" : "Alpha-Beta";
          System.out.println(Colors.GREEN + "\n  > Selected mode : " + name + Colors.RESET);
          return mode;
        }
      } catch (Exception e) {
        sc.nextLine();
      }
    }
  }

  // --- Turns ---

  private static void playerTurn(Board board) {
    while (true) {
      int[] move = readMove();
      if (board.isValidMove(move[0], move[1])) {
        board.play(new Move(move[0], move[1]), playerMark);
        return;
      }
    }
  }

  private static void cpuTurn(Board board, CPUPlayer cpuPlayer, int mode) {
    String modeName = (mode == 1) ? "MinMax" : "Alpha-Beta";
    System.out.println(Colors.RESET + "\n> CPU is thinking...");

    ArrayList<Move> bestMoves = (mode == 1)
        ? cpuPlayer.getNextMoveMinMax(board)
        : cpuPlayer.getNextMoveAB(board);

    Move cpuMove = bestMoves.get(rand.nextInt(bestMoves.size()));
    board.play(cpuMove, cpuMark);

    System.out.println("> CPU plays : ("
        + Colors.BLUE + cpuMove.getRow() + ", " + cpuMove.getCol() + Colors.RESET + ")");
    System.out.println("> Explored nodes (" + modeName + ") : "
        + Colors.YELLOW + cpuPlayer.getNumOfExploredNodes() + Colors.RESET);
  }

  // --- Game ---

  private static boolean isGameOver(Board board) {
    return board.evaluate(cpuMark) != 0 || board.isFull();
  }

  private static void displayResult(Board board) {
    System.out.println(board);
    int result = board.evaluate(cpuMark);
    if (result == 100)
      System.out.println(Colors.RED + ">>> CPU wins!\n" + Colors.RESET);
    else if (result == -100)
      System.out.println(Colors.GREEN + ">>> You win!\n" + Colors.RESET);
    else
      System.out.println(Colors.BLUE + ">>> Draw!\n" + Colors.RESET);
  }

  public static void main(String[] args) {
    Board board = new Board();
    CPUPlayer cpuPlayer = new CPUPlayer(cpuMark);
    int mode = selectMode();

    while (true) {
      System.out.println(board);
      playerTurn(board);
      if (isGameOver(board))
        break;

      cpuTurn(board, cpuPlayer, mode);
      if (isGameOver(board))
        break;
    }

    displayResult(board);
  }
}