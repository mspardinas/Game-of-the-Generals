import java.util.*;
import java.io.*;

class Piece { //the Piece class
    private String name; private String color; private int power; private int enemyRow;

    public Piece(String name, String color, int power, int enemyRow){
        this.name = name; //name/rank of piece e.g. 5G, CP, fl, sp, etc.
        this.color = color; //color is either black or white
        this.power = power; //power of respective piece, detailed explanation below
        this.enemyRow = enemyRow; 
        //enemyRow - if pieces are placed in top-3-most rows, enemy backline is at the bottom; 
        //if pieces are placed in bottom-most rows, enemy backline is topmost row
    }
    //getters
    public int getEnemyRow(){ return enemyRow; }
    public String getName(){ return name; }
    public int getPower(){ return power; }
    public String getColor(){ return color; }
}

class Board { //Board class
    //this is static so it can be modified by PlayerMove class below
    public Piece[][] generalsBoard = new Piece[8][9]; //create 2d array of game board, 8 rows by 9 columns

    public void placePiece(String name, String color, int row, int col, int power, int enemyRow){
        this.generalsBoard[row][col] = new Piece(name, color, power, enemyRow);
        //create new Piece and place in generalsBoard at respective row and col
    }
    public void printBoard(char color) { //prints the board
		for(int i = 0; i < 8; i++){
            for(int j = 0; j < 9; j++){
                if(j > 0 && j < 9){ //if it is not the first piece of each row, print a space before printing next piece
                    System.out.print(" ");
                }
                if(generalsBoard[i][j] == null){ //if there is no piece at the square
                    System.out.print("..");
                }
                else{ //if theres is a piece
                    if(color == generalsBoard[i][j].getColor().charAt(0) || color == 'a'){ //if same color, or print all ('a')
                        System.out.print(generalsBoard[i][j].getName());
                    }
                    else{ //else opposing piece
                        System.out.print("##");
                    }
                }
            }
            System.out.println(); //line after each row
        }
        System.out.println(); //extra line after display command
    }
}

class PlayerMove{ //PlayerMove class extends from Board
    //x and y coordinates for piece to be moved
    public int fromRow; public int fromCol;
    //x and y coordinates for destination
    public int newRow; public int newCol;
    
    public PlayerMove(int fromRow, int fromCol, int newRow, int newCol){ //create an object of the move
        this.fromRow = fromRow; 
        this.fromCol = fromCol;
        this.newRow = newRow;
        this.newCol = newCol;
    }

    public boolean validateMove(char color, int fromRow, int fromCol, int newRow, int newCol, Board generalsBoardNew){ //validates move if it is possible
        String colorString = String.valueOf(color);

        if(fromRow < 0 || fromRow > 7 || fromCol < 0 || fromCol > 8 || newRow < 0 || newRow > 7 || newCol < 0 || newCol > 8 ){
            return false; //out of bounds, invalid immediately
        }
        for(int i = -1; i <= 1; i++){ //nested loop for increment/decrement of coordinate in grid, adjusted to a 1-tile-radius move
            for(int j = -1; j <=1; j++){
                if(i == 0 && j == 1 || i == 0 && j == -1 || i == -1 && j == 0 || i == 1 && j == 0){ //valid moves are only UP, DOWN, LEFT, RIGHT
                    if(fromRow + i == newRow && fromCol + j == newCol){ //if valid up/down/left/right yung move
                        if(generalsBoardNew.generalsBoard[newRow][newCol] == null || !(generalsBoardNew.generalsBoard[newRow][newCol].getColor().equals(colorString))){
                            return true;
                            //if empty tile or enemy piece yung destination then valid move
                        }
                        return false; //else cannot be moved towards to since destination is occupied with ally piece
                    }
                }
            }
        }
        return false; //wala nahanap valid move
    }

    public String performMove(char color, int fromRow, int fromCol, int newRow, int newCol, boolean almostEnd, Board generalsBoardNew){ //performs move     
        if(generalsBoardNew.generalsBoard[newRow][newCol] == null) { // destination is empty
            generalsBoardNew.generalsBoard[newRow][newCol] = generalsBoardNew.generalsBoard[fromRow][fromCol]; //simply move piece
            generalsBoardNew.generalsBoard[fromRow][fromCol] = null;
            //if flag and at enemy backline/ enemyRow
            if(generalsBoardNew.generalsBoard[newRow][newCol].getPower() == 0 && generalsBoardNew.generalsBoard[newRow][newCol].getEnemyRow() == newRow){
                if(almostEnd){ //current flag did not eat an enemy flag that is at his backline, hence talo pa rin siya kasi he did not challenge said flag
                    return "true";
                }
                //if at leftmost corner and no adjacent enemy OR there is an adjacent piece that is an ally, automatic win
                else if(newCol == 0){
                    if(generalsBoardNew.generalsBoard[newRow][1] == null || generalsBoardNew.generalsBoard[newRow][1].getColor().equals(generalsBoardNew.generalsBoard[newRow][newCol].getColor())){
                        return "true";
                    }
                }
                //if at rightmost corner and no adjacent enemy OR there is an adjacent piece that is an ally, automatic win
                else if(newCol == 8){
                    if(generalsBoardNew.generalsBoard[newRow][7] == null || generalsBoardNew.generalsBoard[newRow][7].getColor().equals(generalsBoardNew.generalsBoard[newRow][newCol].getColor())){
                        return "true";
                    }
                }
                //if no adjacant enemies OR there is/are adjacent pieces but are allies, automatic win pa rin because flag cannot be challenged
                else if(generalsBoardNew.generalsBoard[newRow][newCol + 1] == null && generalsBoardNew.generalsBoard[newRow][newCol - 1] == null ||
                generalsBoardNew.generalsBoard[newRow][newCol + 1] == null && generalsBoardNew.generalsBoard[newRow][newCol - 1].getColor().equals(generalsBoardNew.generalsBoard[newRow][newCol].getColor()) ||
                generalsBoardNew.generalsBoard[newRow][newCol - 1] == null && generalsBoardNew.generalsBoard[newRow][newCol + 1].getColor().equals(generalsBoardNew.generalsBoard[newRow][newCol].getColor()) ||
                generalsBoardNew.generalsBoard[newRow][newCol - 1].getColor().equals(generalsBoardNew.generalsBoard[newRow][newCol].getColor()) && generalsBoardNew.generalsBoard[newRow][newCol + 1].getColor().equals(generalsBoardNew.generalsBoard[newRow][newCol].getColor())){
                    return "true";
                }
                return "almostTrue"; //else, there is at least one enemy that can challenge the flag, hence almost win na
            }
            if(almostEnd){
                return "true";
            }
            return "false";
        }
        else if(generalsBoardNew.generalsBoard[newRow][newCol] != null){ //destination is not empty
            if(generalsBoardNew.generalsBoard[fromRow][fromCol].getPower() == 0){ //origin piece is flag
                if(generalsBoardNew.generalsBoard[newRow][newCol].getPower() == 0){ //flag eats flag, win the game
                    generalsBoardNew.generalsBoard[newRow][newCol] = generalsBoardNew.generalsBoard[fromRow][fromCol];
                    generalsBoardNew.generalsBoard[fromRow][fromCol] = null;
                    return "true";
                }
                else{ //flag loses to any other piece that is not a flag
                    generalsBoardNew.generalsBoard[fromRow][fromCol] = null;
                    return "true";
                }
            }
            else if(generalsBoardNew.generalsBoard[newRow][newCol].getPower() == 0){ //any piece that eats enemy flag wins
                generalsBoardNew.generalsBoard[newRow][newCol] = generalsBoardNew.generalsBoard[fromRow][fromCol];
                generalsBoardNew.generalsBoard[fromRow][fromCol] = null;
                return "true";
            }
            else if(generalsBoardNew.generalsBoard[fromRow][fromCol].getPower() == generalsBoardNew.generalsBoard[newRow][newCol].getPower()){ //both pieces are of equal rank, so remove both
                generalsBoardNew.generalsBoard[fromRow][fromCol] = null;
                generalsBoardNew.generalsBoard[newRow][newCol] = null;
                return "false";
            }
            else if(generalsBoardNew.generalsBoard[fromRow][fromCol].getPower() == -1){ //origin piece is spy
                if(generalsBoardNew.generalsBoard[newRow][newCol].getPower() == 1){ //spy attacks private
                    generalsBoardNew.generalsBoard[fromRow][fromCol] = null;
                    return "false";
                }
                else{ //spy wins against any piece
                    generalsBoardNew.generalsBoard[newRow][newCol] = generalsBoardNew.generalsBoard[fromRow][fromCol];
                    generalsBoardNew.generalsBoard[fromRow][fromCol] = null;
                    return "false";
                }
            }
            else if(generalsBoardNew.generalsBoard[newRow][newCol].getPower() == -1){ //destination piece is spy
                if(generalsBoardNew.generalsBoard[fromRow][fromCol].getPower() == 1){ //private attacks spy
                    generalsBoardNew.generalsBoard[newRow][newCol] = generalsBoardNew.generalsBoard[fromRow][fromCol];
                    generalsBoardNew.generalsBoard[fromRow][fromCol] = null;
                    return "false";
                }
                else{ //any other piece loses to spy because deception
                    generalsBoardNew.generalsBoard[fromRow][fromCol] = null;
                    return "false";
                }
            }
            //origin piece is stronger than destination piece
            else if(generalsBoardNew.generalsBoard[fromRow][fromCol].getPower() > generalsBoardNew.generalsBoard[newRow][newCol].getPower()){
                generalsBoardNew.generalsBoard[newRow][newCol] = generalsBoardNew.generalsBoard[fromRow][fromCol];
                generalsBoardNew.generalsBoard[fromRow][fromCol] = null;
                return "false";
            }
            //origin piece is weaker than destination piece
            else if(generalsBoardNew.generalsBoard[fromRow][fromCol].getPower() < generalsBoardNew.generalsBoard[newRow][newCol].getPower()){
                generalsBoardNew.generalsBoard[fromRow][fromCol] = null;
                return "false";
            }
            if(almostEnd){
                return "true";
            }
            return "false";
        }
        return "false";
    }
}

public class gameofgenerals_Pardinas { //public class

    public static int getRow(char c){ //method to determine row from top (8) to bottom (1)
        int charIntValueRow = Character.getNumericValue(c);
        int row = 0;
        for(int a = 8; a > 0; a--){
            if(a == charIntValueRow){
                return row;
            }
            row++;
        }
        return row;
    }
    public static int getCol(char c){ //method to determine column from letters a through i/ left to right
        int charIntValueCol = c;
        int col = 0;
        for(int a = 97; a <= 105; a++){
            if(a == charIntValueCol){
                return col;
            }
            col++;
        }
        return col;
    }
    public static char flipColor(char color){ //method that alternates between white and black player
        if(color == 'w'){
            return 'b';
        }
        return 'w';
    }
    public static int detPower(String piece){ //method for hierarchy/power rank/attack value of piece
        if(piece.equals("5G") || piece.equals("5g")){ //atk value of general of army is 13
            return 13;
        }
        else if(piece.equals("4G") || piece.equals("4g")){ //further decrements by 1 up until seargent
            return 12;
        }
        else if(piece.equals("3G") || piece.equals("3g")){
            return 11;
        }
        else if(piece.equals("2G") || piece.equals("2g")){
            return 10;
        }
        else if(piece.equals("1G") || piece.equals("1g")){
            return 9;
        }
        else if(piece.equals("CL") || piece.equals("cl")){
            return 8;
        }
        else if(piece.equals("LC") || piece.equals("lc")){
            return 7;
        }
        else if(piece.equals("MJ") || piece.equals("mj")){
            return 6;
        }
        else if(piece.equals("CP") || piece.equals("cp")){
            return 5;
        }
        else if(piece.equals("1L") || piece.equals("1l")){
            return 4;
        }
        else if(piece.equals("2L") || piece.equals("2l")){
            return 3;
        }
        else if(piece.equals("SG") || piece.equals("sg")){ //seargent's atk value is 2
            return 2;
        }
        else if(piece.equals("PV") || piece.equals("pv")){ //private is 1
            return 1;
        }
        else if(piece.equals("SP") || piece.equals("sp")){ //spy is -1
            return -1;
        }
        else{ //flag
            return 0; 
        }
    }
    public static void main(String[] args){ //main
        Scanner sc = new Scanner(System.in);
        Board generalsBoardNew = new Board(); //create the board

        for(int i = 0; i < 8; i++){ //initialize all squares in chessboard to null
            for(int j = 0; j < 9; j++){
                generalsBoardNew.generalsBoard[i][j] = null;
            }
        }

        for(int i = 0; i < 42; i++){ //getting all 42 lines of input for initial state of board
            
            String inputPiece = sc.nextLine(); //input eg. 5G a4
            String[] inputFinal = inputPiece.split(" "); //split whitespace hence magiging [0] si 5G && [1] si a4 respectively
            
            String color; //determine color
            if(Character.isUpperCase(inputFinal[0].charAt(1))){ //if uppercase, white
                color = "w";
            }
            else{ //else, black
                color = "b";
            }

            int row = getRow(inputFinal[1].charAt(1)); //get int row value from getRow method
            int col = getCol(inputFinal[1].charAt(0)); //get int col value from getCol method
            int power = detPower(inputFinal[0]); //determine power rank/atk value from name/rank of piece
            int enemyRow; //the respective color's enemyRow or enemy Backline is either it's opposing top or bottom row
            if(row <= 2){ //pieces are placed in top-3-most rows, enemyRow is at the bottom
                enemyRow = 7;
            }
            else{ //else pieces were placed at the bottom, enemyRow is at the top
                enemyRow = 0;
            }

            generalsBoardNew.placePiece(inputFinal[0], color, row, col, power, enemyRow); //place the piece in the board
        }

        char charFirstMove = sc.next().charAt(0); //w or b
        char firstMove = Character.toLowerCase(charFirstMove);
        sc.nextLine(); //clear input stream of new line
        
        boolean almostEnd = false; //this one is for when a flag reaches the enemy backline
        boolean endGame = false; //checks if a flag is captured/killed

        while(true){ //while game is ongoing, both flags are still alive
            
            if(!(sc.hasNextLine())){ //if there are no more input lines -->end the game
                break;
            }

            String moveInputRaw = sc.nextLine(); //input eg. display w OR a3 a4
            String[] actionMove = moveInputRaw.split(" "); //split whitespace

            if(actionMove[0].equals("display")){ //if user inputs the display action
                char colorRaw = actionMove[1].charAt(0); //get player perspective (w - white / b - black / a - all)
                char colorDisplay = Character.toLowerCase(colorRaw);
                if(colorDisplay == 'w' || colorDisplay == 'b' || colorDisplay == 'a'){ //if valid lang mag d-display, else ignore and ask for input again, retains current player
                    generalsBoardNew.printBoard(colorDisplay);
                }
                continue; //player's turn is maintained, regardless if input for display was valid or invalid
            }
            else{ //else, player inputs a move action
                //to determine row and col of piece chosen
                int row = getRow(actionMove[0].charAt(1));
                int col = getCol(actionMove[0].charAt(0));
                //to determine row and col of desired destination, where the player wants to move his/her piece
                int newRow = getRow(actionMove[1].charAt(1));
                int newCol = getCol(actionMove[1].charAt(0));

                PlayerMove moveNew = new PlayerMove(row, col, newRow, newCol); //create move

                if(generalsBoardNew.generalsBoard[row][col] == null) { // if player selects an empty square/non-existing piece, ask for input again, maintain current player's move
                    continue;
                }
                //checks if correct player moves his own piece (white to white or black to black)
                else if(generalsBoardNew.generalsBoard[row][col].getColor().equals("b") && firstMove == 'b' || generalsBoardNew.generalsBoard[row][col].getColor().equals("w") && firstMove == 'w'){
                    if(moveNew.validateMove(firstMove ,row, col, newRow, newCol, generalsBoardNew)){ //validates move first
                        
                        //if the current piece is a flag, and moves towards enemy backline
                        if(generalsBoardNew.generalsBoard[row][col].getPower() == 0 && generalsBoardNew.generalsBoard[row][col].getEnemyRow() == newRow){
                            if(moveNew.performMove(firstMove ,row, col, newRow, newCol, almostEnd, generalsBoardNew).equals("true")){ //if there are no adjacent enemy pieces, automatic win
                                endGame = true;
                            }
                            else{ //else, there is/are adjacent enemy pieces, therefore go for one more turn (enemy's turn)
                                almostEnd = true;
                                firstMove = flipColor(firstMove);
                            }
                        }
                        else{ //else, a flag moves to a tile that is not its enemy's backline, or any other piece moves to another tile
                            if(moveNew.performMove(firstMove ,row, col, newRow, newCol, almostEnd, generalsBoardNew).equals("true")){ //if a flag was captured by any piece
                                endGame = true;
                            }
                            else{ //else, a piece (that is not a flag) is eaten, or a piece simply moves to an empty tile, alternate the color so it will be the next player's turn
                                firstMove = flipColor(firstMove);
                            }
                        }
                    }
                    else{ //if move was not valid (out of bounds OR attempted to move towards an ally), ask for input again, player maintains move
                        continue;
                    }
                }
                else{ //player attempted to move a piece that was not his. ask for input again, player maintains move
                    continue;
                }
            }

            if(!endGame){
                continue;
            }
            else if(endGame || almostEnd){ //if a flag was captured, end the game
                break;
            }
        }
        generalsBoardNew.printBoard('a'); //lastly print contents of the board, 'a' for all
        sc.close();
    }
}