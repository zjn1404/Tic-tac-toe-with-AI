package tictactoe;

import java.util.Random;
import java.util.Scanner;

class Table{
    private final int sizeOfTable = 4; // ignore index 0
    private int amountOfX = 0;
    private int amountOfO = 0;
    private final char[][] table = new char[sizeOfTable][sizeOfTable];

    Table() {
        for (int i = 1; i < sizeOfTable; i++) {
            for (int j = 1; j < this.sizeOfTable; j++) {
                this.table[i][j] = ' ';
            }
        }
    }
    public void printTable() {
        int size = (this.sizeOfTable-1)*2+3;
        for (int i = 0; i < size; i++) {
            System.out.print("-");
        }
        System.out.println();
        for (int i = 1; i < this.sizeOfTable; i++) {
            for (int j = 0; j < size/2+1; j++) {
                if (j == 0) System.out.print("|");
                else if (j == size/2) System.out.print(" |");
                else System.out.print(" " + this.table[i][j]);
            }
            System.out.println();
        }
        for (int i = 0; i < size; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    // return boolean to check whether set coordinate successfully or not
    public boolean setTable(int firstCoor, int secondCoor, char c, boolean isUser) {
        if (firstCoor*secondCoor <= 0 || firstCoor >= this.sizeOfTable || secondCoor >= this.sizeOfTable) {
            if (c == 'X') System.out.println("Coordinates should be from 1 to 3!");
            return false;
        }
        if (this.table[firstCoor][secondCoor] != ' ') {
           if(isUser) System.out.println("This cell is occupied! Choose another one!");
            return false;
        }
        this.table[firstCoor][secondCoor] = c;
        if (c == 'X') this.amountOfX++;
        else if (c == 'O') this.amountOfO++;
        return true;
    }

    public char[][] getTable() {
        return this.table;
    }

    public int getAmountOfX() {
        return this.amountOfX;
    }

    public void setAmountOfX(int x) {
        this.amountOfX = x;
    }
    public  int getAmountOfO() {
        return this.amountOfO;
    }

    public void setAmountOfO(int o) {
        this.amountOfO = o;
    }

    public int getSizeOfTable() {
        return this.sizeOfTable;
    }
}
public class Main {
    enum Scores {
        player1 (1),
        player2 (-1),
        tie (0);

        private final int value;

        Scores(int value) {
            this.value = value;
        }
    }
    public static boolean isWin(Table table, int firstCoor, int secondCoor, char player) {
        int size = table.getSizeOfTable()-1;
        int count = 0;
        // iterate through col
        for (int i = 1; i <= size; i++) {
            if (table.getTable()[firstCoor][i] == player) count++;
        }
        if (count == size) return true;

        // iterate through row
        count = 0;
        for (int i = 1; i <= size; i++) {
            if (table.getTable()[i][secondCoor] == player) count++;
        }
        if (count == size) return true;

        count = 0;
        int x = firstCoor;
        int y = secondCoor;
        while (x >= 1 && y >= 1) {
            if (table.getTable()[x][y] == player) count++;
            x -= 1;
            y -= 1;
        }

        x = firstCoor + 1;
        y = secondCoor + 1;
        while (x <= size && y <= size) {
            if (table.getTable()[x][y] == player) count++;
            x += 1;
            y += 1;
        }
        if (count == size) return true;

        count = 0;
        x = firstCoor;
        y = secondCoor;
        while (x >= 1 && y <= size) {
            if(table.getTable()[x][y] == player) count++;
            x -= 1;
            y += 1;
        }

        x = firstCoor + 1;
        y = secondCoor - 1;
        while (x <= size && y >= 1) {
            if(table.getTable()[x][y] == player) count++;
            x += 1;
            y -= 1;
        }
        if(count == size) return true;

        return false;
    }

    public static boolean blockMove(Table table, char player, char bot, int moves[]) {
        int size = table.getSizeOfTable();
        for (int i = 1; i < size; i++) {
            for (int j = 1; j < size; j++) {
                if (table.getTable()[i][j] == player) {
                    if (j + 1 < size && table.getTable()[i][j+1] == player) {
                        if(j-1 > 0 && table.getTable()[i][j-1] == ' ') {
                            table.setTable(i, j-1, bot, false);
                            moves[0] = i;
                            moves[1] = j-1;
                            return true;
                        }
                    }

                    if (j - 1 > 0 && table.getTable()[i][j-1] == player) {
                        if (j + 1 < size && table.getTable()[i][j + 1] == ' ') {
                            table.setTable(i, j+1, bot, false);
                            moves[0] = i;
                            moves[1] = j+1;
                            return true;
                        }
                    }

                    if (i + 1 < size && table.getTable()[i+1][j] == player) {
                        if (i - 1 > 0 && table.getTable()[i - 1][j] == ' ') {
                            table.setTable(i-1, j, bot, false);
                            moves[0] = i-1;
                            moves[1] = j;
                            return true;
                        }
                    }

                    if (i - 1 > 0 && table.getTable()[i-1][j] == player) {
                        if (i + 1 < size && table.getTable()[i + 1][j] == ' ') {
                            table.setTable(i+1, j, bot, false);
                            moves[0] = i+1;
                            moves[1] = j;
                            return true;
                        }
                    }

                    if (i + 1 < size && j + 1 < size && table.getTable()[i+1][j+1] == player) {
                        if (i - 1 > 0 && j - 1 > 0 && table.getTable()[i - 1][j-1] == ' ') {
                            table.setTable(i-1, j-1, bot, false);
                            moves[0] = i-1;
                            moves[1] = j-1;
                            return true;
                        }
                    }

                    if (i - 1 > 0 && j - 1 > 0 && table.getTable()[i-1][j-1] == player) {
                        if (i + 1 < size && j + 1 < size && table.getTable()[i + 1][j + 1] == ' ') {
                            table.setTable(i+1, j+1, bot, false);
                            moves[0] = i+1;
                            moves[1] = j+1;
                            return true;
                        }
                    }

                    if (i + 1 < size && j - 1 > 0 && table.getTable()[i+1][j-1] == player) {
                        if (i - 1 > 0 && j + 1 < size && table.getTable()[i - 1][j + 1] == ' ') {
                            table.setTable(i-1, j + 1, bot, false);
                            moves[0] = i-1;
                            moves[1] = j+1;
                            return true;
                        }
                    }

                    if (i - 1 > 0 && j + 1 < size && table.getTable()[i-1][j+1] == player) {
                        if (i + 1 < size && j - 1 > 0 && table.getTable()[i + 1][j - 1] == ' ') {
                            table.setTable(i+1, j-1, bot, false);
                            moves[0] = i+1;
                            moves[1] = j-1;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean lastMoveToWin(Table table, char bot, int[] moves) {
        int size = table.getSizeOfTable();
        for(int i = 1; i < size; i++) {
            for (int j = 1; j < size; j++) {
                if (table.getTable()[i][j] == bot) {
                    if (j + 1 < size && table.getTable()[i][j+1] == bot) {
                        if(j-1 > 0 && table.getTable()[i][j-1] == ' ') {
                            table.setTable(i, j-1, bot, false);
                            moves[0] = i;
                            moves[1] = j-1;
                            return true;
                        }
                    }

                    if (j - 1 > 0 && table.getTable()[i][j-1] == bot) {
                        if (j + 1 < size && table.getTable()[i][j + 1] == ' ') {
                            table.setTable(i, j+1, bot, false);
                            moves[0] = i;
                            moves[1] = j+1;
                            return true;
                        }
                    }

                    if (i + 1 < size && table.getTable()[i+1][j] == bot) {
                        if (i - 1 > 0 && table.getTable()[i - 1][j] == ' ') {
                            table.setTable(i-1, j, bot, false);
                            moves[0] = i-1;
                            moves[1] = j;
                            return true;
                        }
                    }

                    if (i - 1 > 0 && table.getTable()[i-1][j] == bot) {
                        if (i + 1 < size && table.getTable()[i + 1][j] == ' ') {
                            table.setTable(i+1, j, bot, false);
                            moves[0] = i+1;
                            moves[1] = j;
                            return true;
                        }
                    }

                    if (i + 1 < size && j + 1 < size && table.getTable()[i+1][j+1] == bot) {
                        if (i - 1 > 0 && j - 1 > 0 && table.getTable()[i - 1][j-1] == ' ') {
                            table.setTable(i-1, j-1, bot, false);
                            moves[0] = i-1;
                            moves[1] = j-1;
                            return true;
                        }
                    }

                    if (i - 1 > 0 && j - 1 > 0 && table.getTable()[i-1][j-1] == bot) {
                        if (i + 1 < size && j + 1 < size && table.getTable()[i + 1][j + 1] == ' ') {
                            table.setTable(i+1, j+1, bot, false);
                            moves[0] = i+1;
                            moves[1] = j+1;
                            return true;
                        }
                    }

                    if (i + 1 < size && j - 1 > 0 && table.getTable()[i+1][j-1] == bot) {
                        if (i - 1 > 0 && j + 1 < size && table.getTable()[i - 1][j + 1] == ' ') {
                            table.setTable(i-1, j + 1, bot, false);
                            moves[0] = i-1;
                            moves[1] = j+1;
                            return true;
                        }
                    }

                    if (i - 1 > 0 && j + 1 < size && table.getTable()[i-1][j+1] == bot) {
                        if (i + 1 < size && j - 1 > 0 && table.getTable()[i + 1][j - 1] == ' ') {
                            table.setTable(i+1, j-1, bot, false);
                            moves[0] = i+1;
                            moves[1] = j-1;
                            return true;
                        }
                    }
                }
            }
        }
            return false;
    }

    private static boolean playPlayerTurn(Table table, char player) {
        boolean isSuccess = false;
        Scanner scanner = new Scanner(System.in);
        String firstCoor = null;
        String secondCoor = null;
        do {
            System.out.print("Enter the coordinates: ");
            String input = scanner.nextLine();
            if (Character.isAlphabetic(input.charAt(0))) {
                System.out.println("You should enter numbers!");
                continue;
            }
            firstCoor = input.split("\\s")[0];
            secondCoor = input.split("\\s")[1];
            if (table.setTable(Integer.parseInt(firstCoor), Integer.parseInt(secondCoor), player, true)) {
                isSuccess = true;
                return isWin(table, Integer.parseInt(firstCoor), Integer.parseInt(secondCoor), player);
            }
        } while(!isSuccess);
        return isWin(table, Integer.parseInt(firstCoor), Integer.parseInt(secondCoor), player);
    }

    private static boolean playMedium(Table table, char player) {
        boolean isSuccess = false;
        System.out.println("Making move level \"medium\"");
        int[] moves = new int[2];
        if(lastMoveToWin(table, player, moves)) {return isWin(table, moves[0], moves[1], player);}
        char user = 'O';
        if(player == 'O') user = 'X';
        if(blockMove(table, user, player, moves)) {return isWin(table, moves[0], moves[1], player);}

        Random random = new Random();
        int firstCoor = random.nextInt(3) + 1;
        int secondCoor = random.nextInt(3) + 1;
        do {
            firstCoor = random.nextInt(3) + 1;
            secondCoor = random.nextInt(3) + 1;
            if (table.setTable(firstCoor, secondCoor, player, false)) {
                isSuccess = true;
                return isWin(table, firstCoor, secondCoor, player);
            }
        } while(!isSuccess);
        return isWin(table, firstCoor, secondCoor, player);
    }

    private static boolean playEasy(Table table, char player) {
        boolean isSuccess = false;
        System.out.println("Making move level \"easy\"");
        Random random = new Random();
        int firstCoor = random.nextInt(3) + 1;
        int secondCoor = random.nextInt(3) + 1;
        do {
            firstCoor = random.nextInt(3) + 1;
            secondCoor = random.nextInt(3) + 1;
            if (table.setTable(firstCoor, secondCoor, player, false)) {
                isSuccess = true;
                return isWin(table, firstCoor, secondCoor, player);
            }
        } while(!isSuccess);
        return isWin(table, firstCoor, secondCoor, player);
    }

    // Hard level helpers

    private static boolean checkEqual(char a, char b, char c) {
        return (a == b && b == c && a != ' ');
    }
    private static char checkWinner(Table table) {
        char winner = '\0';
        char[][] board = table.getTable();
        // horizontal
        for (int i = 1; i < table.getSizeOfTable(); i++) {
            if (checkEqual(board[i][1] ,board[i][2],board[i][3])) {
                winner = board[i][1];
            }
        }

        // vertical
        for (int i = 1; i < table.getSizeOfTable(); i++) {
            if (checkEqual(board[1][i], board[2][i], board[3][i])) {
                winner = board[1][i];
            }
        }

        // diagonal
        if (checkEqual(board[1][1] ,board[2][2], board[3][3])) {
            winner = board[1][1];
        }

        if (checkEqual(board[3][1] , board[2][2] ,board[1][3])) {
            winner = board[2][2];
        }

        if (winner == '\0' && table.getAmountOfO() + table.getAmountOfX() == Math.pow(table.getSizeOfTable() -1, 2)) {
            winner = 't';
        }

        return winner;
    }
    private static int minimax(Table table, char player1, char player2, boolean isMaximizing) {
        char winner = checkWinner(table);
        if (winner != '\0') {
            if (winner == player1) return Scores.player1.value;
            if (winner == player2) return Scores.player2.value;
            return Scores.tie.value;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 1; i < table.getSizeOfTable(); i++) {
                for (int j = 1; j < table.getSizeOfTable(); j++) {
                    if (table.getTable()[i][j] == ' ') {
                        table.setTable(i, j, player1, false);
                        int score = minimax(table, player1, player2,  false);
                        table.getTable()[i][j] = ' ';
                        if(player1 == 'X') table.setAmountOfX(table.getAmountOfX()-1);
                        else table.setAmountOfO(table.getAmountOfO()-1);
                        bestScore = Integer.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int worstScore = Integer.MAX_VALUE;
            for (int i = 1; i < table.getSizeOfTable(); i++) {
                for (int j = 1; j < table.getSizeOfTable(); j++) {
                    if (table.getTable()[i][j] == ' ') {
                        table.setTable(i, j, player2, false);
                        int score = minimax(table,player1, player2, true);
                        table.getTable()[i][j] = ' ';
                        if(player2 == 'X') table.setAmountOfX(table.getAmountOfX()-1);
                        else table.setAmountOfO(table.getAmountOfO()-1);
                        worstScore = Integer.min(score, worstScore);
                    }
                }
            }
            return worstScore;
        }
    }
    private static boolean playHard(Table table, char player1, char player2) {
        System.out.println("Making move level \"hard\"");
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];

        for (int i = 1; i < table.getSizeOfTable(); i++) {
            for (int j = 1; j < table.getSizeOfTable(); j++) {
                if (table.getTable()[i][j] != ' ') continue;
                table.setTable(i, j, player1, false);
                int score = minimax(table,player1, player2, false);
                table.getTable()[i][j] = ' ';
                if(player1 == 'X') table.setAmountOfX(table.getAmountOfX()-1);
                else table.setAmountOfO(table.getAmountOfO()-1);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove[0] = i;
                    bestMove[1] = j;
                }
            }
        }

        table.setTable(bestMove[0], bestMove[1], player1, false);
        return isWin(table, bestMove[0], bestMove[1], player1);
    }
    public static boolean setTable(Table table, char player, String playerType) {
        if (playerType.equals("user")) {
            return playPlayerTurn(table, player);
        } else if(playerType.equals("medium")) {
            return playMedium(table, player);
            } else if(playerType.equals("easy")) {
                return playEasy(table, player);
            } else {
                char player2 = 'X';
                if (player == 'X') player2 = 'O';
                return playHard(table, player, player2);
            }
    }
    public static void playGame(String playerType1, String playerType2) {
        Table table = new Table();
//        setUp(table);
        table.printTable();

        char player1 = 'X';
        char player2 = 'O';
        boolean hasWinner = false;
        char winner = 'X';
        int moveAmount = 0;
        do {
            if (table.getAmountOfO() + table.getAmountOfX() == Math.pow((table.getSizeOfTable()-1),2)) {
                break;
            }
            moveAmount++;
            if(setTable(table, player1, playerType1)) {
                hasWinner = true;
            }
            table.printTable();
            if(hasWinner) break;
            if(moveAmount == Math.pow(table.getSizeOfTable()-1,2)) break;
            if(setTable(table, player2, playerType2)) {
                hasWinner = true;
                winner = player2;
            }
            table.printTable();
            moveAmount++;
            if(moveAmount == Math.pow(table.getSizeOfTable()-1,2)) break;
        } while(!hasWinner);
        if (hasWinner) {
            System.out.println(winner + " wins");
        } else {
            System.out.println("Draw");
        } //else System.out.println("Game not finished");
    }

    public static void startGame() {
        String gameState = "";
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("Input command: ");
            gameState = scanner.nextLine();
            if(gameState.equals("exit")) break;
            String[] input = gameState.split("\\s");
            if (input.length < 3) {
                System.out.println("Bad parameters!");
            } else {
                playGame(input[1], input[2]);
            }
        } while(!gameState.equals("exit"));
    }

    public static void main(String[] args) {
        startGame();
    }
}

