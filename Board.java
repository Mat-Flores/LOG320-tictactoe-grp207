import java.util.ArrayList;

// IMPORTANT: Il ne faut pas changer la signature des méthodes
// de cette classe, ni le nom de la classe.
// Vous pouvez par contre ajouter d'autres méthodes (ça devrait 
// être le cas)
class Board {
  private Mark[][] board;

  // Ne pas changer la signature de cette méthode
  public Board() {
    this.board = new Mark[][] {
        { Mark.EMPTY, Mark.EMPTY, Mark.EMPTY },
        { Mark.EMPTY, Mark.EMPTY, Mark.EMPTY },
        { Mark.EMPTY, Mark.EMPTY, Mark.EMPTY }
    };
  }

  // Place la pièce 'mark' sur le plateau, à la
  // position spécifiée dans Move
  //
  // Ne pas changer la signature de cette méthode
  public void play(Move m, Mark mark) {
    board[m.getRow()][m.getCol()] = mark;
  }

  // retourne 100 pour une victoire
  // -100 pour une défaite
  // 0 pour un match nul
  // Ne pas changer la signature de cette méthode
  // On évalue la grille uniquement quand elle est pleine
  public int evaluate(Mark cpu) {
    Mark opponent = (cpu == Mark.X) ? Mark.O : Mark.X;

    if (hasWon(cpu))
      return 100;
    if (hasWon(opponent))
      return -100;
    return 0;
  }

  public boolean hasWon(Mark m) {
    for (int i = 0; i < 3; i++) {
      if (board[i][0] == m && board[i][1] == m && board[i][2] == m)
        return true;
      if (board[0][i] == m && board[1][i] == m && board[2][i] == m)
        return true;
    }

    return (board[0][0] == m && board[1][1] == m && board[2][2] == m) ||
        (board[0][2] == m && board[1][1] == m && board[2][0] == m);
  }

  // méthode pour générer les coups possible
  public ArrayList<Move> generatePossibleMoves() {
    ArrayList<Move> moves = new ArrayList<>();

    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 3; c++) {
        if (board[r][c] == Mark.EMPTY) {
          moves.add(new Move(r, c));
        }
      }
    }

    return moves;
  }

  public boolean isFull() {
    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 3; c++) {
        if (board[r][c] == Mark.EMPTY) {
          return false;
        }
      }
    }

    return true;
  }

  public boolean isValidMove(int row, int col) {
    if (row < 0 || row >= 3 || col < 0 || col >= 3) {
      return false;
    }
    return board[row][col] == Mark.EMPTY;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    // En-tête colonnes
    sb.append("\n    ");
    for (int c = 0; c < 3; c++) {
      sb.append(Colors.BLUE).append(c).append(Colors.RESET).append("   ");
    }
    sb.append("\n");

    // Bord supérieur
    sb.append("  ╔═══╦═══╦═══╗\n");

    for (int i = 0; i < 3; i++) {
      // Numéro de ligne
      sb.append(Colors.BLUE).append(i).append(Colors.RESET).append(" ║");

      // Cases
      for (int j = 0; j < 3; j++) {
        sb.append(" ");
        if (board[i][j] == Mark.X) {
          sb.append(Colors.RED).append("X").append(Colors.RESET);
        } else if (board[i][j] == Mark.O) {
          sb.append(Colors.GREEN).append("O").append(Colors.RESET);
        } else {
          sb.append(Colors.GRAY).append(".").append(Colors.RESET);
        }
        sb.append(" ║");
      }

      sb.append("\n");

      // Séparateur horizontal
      if (i < 2) {
        sb.append("  ╠═══╬═══╬═══╣\n");
      }
    }

    // Bord inférieur
    sb.append("  ╚═══╩═══╩═══╝\n");

    return sb.toString();
  }

}
