import java.util.ArrayList;

// IMPORTANT: Il ne faut pas changer la signature des méthodes
// de cette classe, ni le nom de la classe.
// Vous pouvez par contre ajouter d'autres méthodes (ça devrait 
// être le cas)
class CPUPlayer {

  // Contient le nombre de noeuds visités (le nombre
  // d'appel à la fonction MinMax ou Alpha Beta)
  // Normalement, la variable devrait être incrémentée
  // au début de votre MinMax ou Alpha Beta.
  private int numExploredNodes;
  private Mark cpu;
  private Mark opponent;

  // Le constructeur reçoit en paramètre le
  // joueur MAX (X ou O)
  public CPUPlayer(Mark cpu) {
    this.cpu = cpu;
    this.opponent = cpu == Mark.X ? Mark.O : Mark.X;
  }

  // Ne pas changer cette méthode
  public int getNumOfExploredNodes() {
    return numExploredNodes;
  }

  // Retourne la liste des coups possibles. Cette liste contient
  // plusieurs coups possibles si et seuleument si plusieurs coups
  // ont le même score.
  public ArrayList<Move> getNextMoveMinMax(Board board) {
    numExploredNodes = 0;
    ArrayList<Move> bestMoves = new ArrayList<>();
    int bestScore = Integer.MIN_VALUE;

    for (Move m : board.generatePossibleMoves()) {
      board.play(m, cpu);
      int score = minimax(board, false);
      board.play(m, Mark.EMPTY);

      if (score > bestScore) {
        bestScore = score;
        bestMoves.clear();
        bestMoves.add(m);
      } else if (score == bestScore) {
        bestMoves.add(m);
      }
    }

    System.out.println(Colors.RED + "#### " + bestScore + Colors.RESET);
    System.out.println(Colors.RED + "#### " + bestMoves + Colors.RESET);

    return bestMoves;
  }

  // 1. si positionActuelle est finale
  // 2. ___ retourner f(p)
  // 3. si joueur == Max
  // 4. ___ maxScore = −∞
  // 5. ___ pour tous les successeurs pi de PositionActuelle
  // 6. ______ score = Minimax(pi,Min)
  // 7. ______ maxScore = MAX(maxScore,score)
  // 8. ___ retourner maxScore
  // 9. si joueur == Min
  // 10. ___ minScore = ∞
  // 11. ___ pour tous les successeurs pi de PositionActuelle
  // 12. ______ score = Minimax(pi,Max)
  // 13. ______ minScore = MIN(minScore,score)
  // 14. ___ retourner minScore
  private int minimax(Board board, boolean isMax) {
    numExploredNodes++;
    int cpuScore = board.evaluate(cpu);

    if (cpuScore != 0 || board.isFull()) {
      return cpuScore;
    }

    Mark player = isMax ? cpu : opponent;
    int bestScore = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;

    for (Move m : board.generatePossibleMoves()) {
      board.play(m, player);
      int score = minimax(board, !isMax);
      board.play(m, Mark.EMPTY);

      bestScore = isMax
          ? Math.max(bestScore, score)
          : Math.min(bestScore, score);
    }

    return bestScore;
  }

  // Retourne la liste des coups possibles. Cette liste contient
  // plusieurs coups possibles si et seuleument si plusieurs coups
  // ont le même score.
  public ArrayList<Move> getNextMoveAB(Board board) {
    numExploredNodes = 0;
    ArrayList<Move> bestMoves = new ArrayList<>();
    int bestScore = Integer.MIN_VALUE;

    for (Move m : board.generatePossibleMoves()) {
      board.play(m, cpu);
      int score = minimaxAB(board, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
      board.play(m, Mark.EMPTY);

      if (score > bestScore) {
        bestScore = score;
        bestMoves.clear();
        bestMoves.add(m);
      } else if (score == bestScore) {
        bestMoves.add(m);
      }
    }

    return bestMoves;
  }

  private int minimaxAB(Board board, boolean isMax, int alpha, int beta) {
    numExploredNodes++;
    int cpuScore = board.evaluate(cpu);

    if (cpuScore != 0 || board.isFull()) {
      return cpuScore;
    }

    if (isMax) {
      int maxScore = Integer.MIN_VALUE;

      for (Move m : board.generatePossibleMoves()) {
        board.play(m, cpu);
        int score = minimaxAB(board, !isMax, alpha, beta);
        board.play(m, Mark.EMPTY);
        maxScore = Math.max(score, maxScore);

        if (score >= beta)
          break;

        alpha = Math.max(maxScore, alpha);
      }

      return maxScore;
    } else {
      int minScore = Integer.MAX_VALUE;

      for (Move m : board.generatePossibleMoves()) {
        board.play(m, opponent);
        int score = minimaxAB(board, !isMax, alpha, beta);
        board.play(m, Mark.EMPTY);
        minScore = Math.min(score, minScore);

        if (score <= alpha)
          break;

        beta = Math.min(minScore, beta);
      }

      return minScore;
    }
  }
}
