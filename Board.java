package com.example.sudokugui;

import java.util.Scanner;
import java.util.Random;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Board {
    public static Scanner sc = new Scanner(System.in);
    public static Random rand = new Random();
    //Cell(Inner Class)
    //Each cell holds the information of row, column, value, and tracker
    private class Cell {    //Start of Cell Inner Class
        private int row, col, value, tracker;

        public Cell(int row, int col, int value, int tracker) {
            setRow(row);
            setCol(col);
            setValue(value);
            setTracker(tracker); //Used to check if the board value is correct or not (Empty: 0, Correct: 1, Incorrect: -1)
        }

        public Cell() {     //Default Cell Constructor
            this(0, 0, 0, 0);
        }

        //**********//
        //  Getter  //
        //**********//
        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        public int getValue() {
            return value;
        }
        public int getTracker() {
            return tracker;
        }

        //**********//
        //  Setter  //
        //**********//
        public void setRow(int row) {
            this.row = row;
        }

        public void setCol(int col) {
            this.col = col;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public void setTracker(int tracker) {
            this.tracker = tracker;
        }

    }//End of Inner Class

    //**********************************//
    //             CONSTANTS            //
    //**********************************//
    public static final int NUM_ROWS = 9;
    public static final int NUM_COLS = 9;

    //public static final int boardNum = rand.nextInt(3);
    public static int boardNum = 0;

    //Default Question Board (Sets up the board when the board file cannot be filled.)
    public final Cell[][] DEFAULT_BOARD = {
            //Row 1: 3, 0, 5, 4, 0, 2, 0, 6, 0
            {new Cell(0, 0, 3, 0), new Cell(0, 1, 0, 0), new Cell(0, 2, 5, 0), new Cell(0, 3, 4, 0), new Cell(0, 4, 0, 0), new Cell(0, 5, 2, 0), new Cell(0, 6, 0, 0), new Cell(0, 7, 6, 0), new Cell(0, 8, 0, 0)},
            //Row 2: 4, 9, 0, 7, 6, 0, 1, 0, 8
            {new Cell(1, 0, 4, 0), new Cell(1, 1, 9, 0), new Cell(1, 2, 0, 0), new Cell(1, 3, 7, 0), new Cell(1, 4, 6, 0), new Cell(1, 5, 0, 0), new Cell(1, 6, 1, 0), new Cell(1, 7, 0, 0), new Cell(1, 8, 8, 0)},
            //Row 3: 6, 0, 0, 1, 0, 3, 2, 4, 5
            {new Cell(2, 0, 6, 0), new Cell(2, 1, 0, 0), new Cell(2, 2, 0, 0), new Cell(2, 3, 1, 0), new Cell(2, 4, 0, 0), new Cell(2, 5, 3, 0), new Cell(2, 6, 2, 0), new Cell(2, 7, 4, 0), new Cell(2, 8, 5, 0)},
            //Row 4: 0, 0, 3, 9, 0, 0, 5, 8, 0
            {new Cell(3, 0, 0, 0), new Cell(3, 1, 0, 0), new Cell(3, 2, 3, 0), new Cell(3, 3, 9, 0), new Cell(3, 4, 0, 0), new Cell(3, 5, 0, 0), new Cell(3, 6, 5, 0), new Cell(3, 7, 8, 0), new Cell(3, 8, 0, 0)},
            //Row 5: 9, 6, 0, 0, 5, 8, 7, 0, 3
            {new Cell(4, 0, 9, 0), new Cell(4, 1, 6, 0), new Cell(4, 2, 0, 0), new Cell(4, 3, 0, 0), new Cell(4, 4, 5, 0), new Cell(4, 5, 8, 0), new Cell(4, 6, 7, 0), new Cell(4, 7, 0, 0), new Cell(4, 8, 3, 0)},
            //Row 6: 0, 8, 1, 3, 0, 4, 0, 9, 2
            {new Cell(5, 0, 0, 0), new Cell(5, 1, 8, 0), new Cell(5, 2, 1, 0), new Cell(5, 3, 3, 0), new Cell(5, 4, 0, 0), new Cell(5, 5, 4, 0), new Cell(5, 6, 0, 0), new Cell(5, 7, 9, 0), new Cell(5, 8, 2, 0)},
            //Row 7: 0, 5, 0, 6, 0, 1, 4, 0, 0
            {new Cell(6, 0, 0, 0), new Cell(6, 1, 5, 0), new Cell(6, 2, 0, 0), new Cell(6, 3, 6, 0), new Cell(6, 4, 0, 0), new Cell(6, 5, 1, 0), new Cell(6, 6, 4, 0), new Cell(6, 7, 0, 0), new Cell(6, 8, 0, 0)},
            //Row 8: 2, 0, 0, 5, 4, 9, 0, 7, 0
            {new Cell(7, 0, 2, 0), new Cell(7, 1, 0, 0), new Cell(7, 2, 0, 0), new Cell(7, 3, 5, 0), new Cell(7, 4, 4, 0), new Cell(7, 5, 9, 0), new Cell(7, 6, 0, 0), new Cell(7, 7, 7, 0), new Cell(7, 8, 0, 0)},
            //Row 9: 1, 4, 9, 0, 0, 7, 3, 0, 6
            {new Cell(8, 0, 1, 0), new Cell(8, 1, 4, 0), new Cell(8, 2, 9, 0), new Cell(8, 3, 0, 0), new Cell(8, 4, 0, 0), new Cell(8, 5, 7, 0), new Cell(8, 6, 3, 0), new Cell(8, 7, 0, 0), new Cell(8, 8, 6, 0)},
    };

    //Default Answer Board (Sets up the answer board when the board file cannot be filled.)
    public final Cell[][] DEFAULT_ANSWER_BOARD = {
            //Row 1: 3, 1, 5, 4, 8, 2, 9, 6, 7
            {new Cell(0, 0, 3, 1), new Cell(0, 1, 1, 1), new Cell(0, 2, 5, 1), new Cell(0, 3, 4, 1), new Cell(0, 4, 8, 1), new Cell(0, 5, 2, 1), new Cell(0, 6, 9, 1), new Cell(0, 7, 6, 1), new Cell(0, 8, 7, 1)},
            //Row 2: 4, 9, 2, 7, 6, 5, 1, 3, 8
            {new Cell(1, 0, 4, 1), new Cell(1, 1, 9, 1), new Cell(1, 2, 2, 1), new Cell(1, 3, 7, 1), new Cell(1, 4, 6, 1), new Cell(1, 5, 5, 1), new Cell(1, 6, 1, 1), new Cell(1, 7, 3, 1), new Cell(1, 8, 8, 1)},
            //Row 3: 6, 7, 8, 1, 9, 3, 2, 4, 5
            {new Cell(2, 0, 6, 1), new Cell(2, 1, 7, 1), new Cell(2, 2, 8, 1), new Cell(2, 3, 1, 1), new Cell(2, 4, 9, 1), new Cell(2, 5, 3, 1), new Cell(2, 6, 2, 1), new Cell(2, 7, 4, 1), new Cell(2, 8, 5, 1)},
            //Row 4: 7, 2, 3, 9, 1, 6, 5, 8, 4
            {new Cell(3, 0, 7, 1), new Cell(3, 1, 2, 1), new Cell(3, 2, 3, 1), new Cell(3, 3, 9, 1), new Cell(3, 4, 1, 1), new Cell(3, 5, 6, 1), new Cell(3, 6, 5, 1), new Cell(3, 7, 8, 1), new Cell(3, 8, 4, 1)},
            //Row 5: 9, 6, 4, 2, 5, 8, 7, 1, 3
            {new Cell(4, 0, 9, 1), new Cell(4, 1, 6, 1), new Cell(4, 2, 4, 1), new Cell(4, 3, 2, 1), new Cell(4, 4, 5, 1), new Cell(4, 5, 8, 1), new Cell(4, 6, 7, 1), new Cell(4, 7, 1, 1), new Cell(4, 8, 3, 1)},
            //Row 6: 5, 8, 1, 3, 7, 4, 6, 9, 2
            {new Cell(5, 0, 5, 1), new Cell(5, 1, 8, 1), new Cell(5, 2, 1, 1), new Cell(5, 3, 3, 1), new Cell(5, 4, 7, 1), new Cell(5, 5, 4, 1), new Cell(5, 6, 6, 1), new Cell(5, 7, 9, 1), new Cell(5, 8, 2, 1)},
            //Row 7: 8, 5, 7, 6, 3, 1, 4, 2, 9
            {new Cell(6, 0, 8, 1), new Cell(6, 1, 5, 1), new Cell(6, 2, 7, 1), new Cell(6, 3, 6, 1), new Cell(6, 4, 3, 1), new Cell(6, 5, 1, 1), new Cell(6, 6, 4, 1), new Cell(6, 7, 2, 1), new Cell(6, 8, 9, 1)},
            //Row 8: 2, 3, 6, 5, 4, 9, 8, 7, 1
            {new Cell(7, 0, 2, 1), new Cell(7, 1, 3, 1), new Cell(7, 2, 6, 1), new Cell(7, 3, 5, 1), new Cell(7, 4, 4, 1), new Cell(7, 5, 9, 1), new Cell(7, 6, 8, 1), new Cell(7, 7, 7, 1), new Cell(7, 8, 1, 1)},
            //Row 9: 1, 4, 9, 8, 2, 7, 3, 5, 6
            {new Cell(8, 0, 1, 1), new Cell(8, 1, 4, 1), new Cell(8, 2, 9, 1), new Cell(8, 3, 8, 1), new Cell(8, 4, 2, 1), new Cell(8, 5, 7, 1), new Cell(8, 6, 3, 1), new Cell(8, 7, 5, 1), new Cell(8, 8, 6, 1)},
    };

    //**********************************//
    //        INSTANCE VARIABLES        //
    //**********************************//
    private Cell board[][] = new Cell [NUM_ROWS][NUM_COLS];
    private Cell answerBoard[][] = new Cell [NUM_ROWS][NUM_COLS];

    //**********************************//
    //          CONSTRUCTORS            //
    //**********************************//
    //Default Constructor setting up the board
    public Board() {
        setCompleteBoard(DEFAULT_BOARD);
        setCompleteAnswerBoard(DEFAULT_ANSWER_BOARD);
    }   //End of Default Board Constructor

    //Constructor Used to set up the board by reading the file Board.txt and AnswerBoard.txt
    public Board(String boardFileName, String answerBoardFileName) {
        boardNum = rand.nextInt(16);        //17 lines of board exist in the Board.txt and AnswerBoard.txt
        boolean nextLine;   //Checking to see if there is next line

        //Set the Board
        Scanner boardInputStream = null;
        Cell[][] board = new Cell[NUM_ROWS][NUM_COLS];
        Scanner answerBoardInputStream = null;
        Cell[][] answerBoard = new Cell[NUM_ROWS][NUM_COLS];
        try {
            boardInputStream = new Scanner(new FileInputStream(boardFileName));
            answerBoardInputStream = new Scanner(new FileInputStream(answerBoardFileName));
            for (int i = 0; i < boardNum; i++) {
                nextLine = boardInputStream.hasNextLine() && answerBoardInputStream.hasNextLine();
                if (nextLine) {
                    boardInputStream.nextLine();
                    answerBoardInputStream.nextLine();
                } else {
                    setCompleteBoard(DEFAULT_BOARD);
                    setCompleteBoard(DEFAULT_ANSWER_BOARD);
                    break;
                }
            }
            for (int i = 0; i < NUM_ROWS; i++) {
                for (int j = 0; j < NUM_COLS; j++) {
                    board[i][j] = new Cell(i, j, boardInputStream.nextInt(), 0);
                    answerBoard[i][j] = new Cell(i, j, answerBoardInputStream.nextInt(), 1);
                }
            }
            setCompleteBoard(board);
            setCompleteAnswerBoard(answerBoard);
            boardInputStream.close();
            answerBoardInputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("Board not found");
            setCompleteBoard(DEFAULT_BOARD);
            setCompleteAnswerBoard(DEFAULT_ANSWER_BOARD);
        }
    }   //End of Board Constructor

    //**********************************//
    //          Getters/Setters         //
    //**********************************//
    public int[][] getCompleteBoard() {
        int[][] intArray = new int[NUM_ROWS][NUM_COLS];
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                intArray[i][j] = this.board[i][j].getValue();
            }
        }
        return intArray;
    }

    public int[][] getCompleteAnswerBoard() {
        int[][] intAnswerArray = new int[NUM_ROWS][NUM_COLS];
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                intAnswerArray[i][j] = this.answerBoard[i][j].getValue();
            }
        }
        return intAnswerArray;
    }

    //Used for the Helper Method
    public int[] getRow(int rowNum) {
        int[] row = new int[NUM_ROWS];
        for (int i = 0; i < NUM_ROWS; i++) {
            row[i] = this.board[rowNum][i].getValue();
        }
        return row;
    }

    public int[] getCol(int colNum) {
        int[] col = new int[NUM_COLS];
        for (int i = 0; i < NUM_COLS; i++) {
            col[i] = this.board[i][colNum].getValue();
        }
        return col;
    }

    public int[] getSquare(int squareRow, int squareCol) {
        int[] square = new int[NUM_ROWS];
        int index = 0;
        for (int i = squareRow; i < 3; i++) {
            for (int j = squareCol; j < 3; j++) {
                square[index] = this.board[squareRow][squareCol].getValue();
                index++;
            }
        }
        return square;
    }

    public void setCompleteBoard(Cell board[][]) {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                this.board[i][j] = board[i][j];
            }
        }
    }

    public void setCompleteAnswerBoard(Cell board[][]) {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                this.answerBoard[i][j] = board[i][j];
            }
        }
    }

    //**********************************//
    //           Helper Methods         //
    //**********************************//
    //Method checking if the guess is correct or not. Return boolean for the correctness (value is usually the guess)
    //value will usually input a guess of the user
    public boolean checkGuess(int row, int col, int value) {
        return answerBoard[row][col].getValue() == value;
    }

    //Method changing the cell value to the guessed number
    public void changeCellValue(int row, int col, int value) {
        board[row][col].setValue(value);
    }

    //Method changing the tracker value to indicate if the guess is correct or not
    public void changeTracker(int row, int col, int tracker) {
        board[row][col].setTracker(tracker);
    }

    //Method changing both the tracker and the value when the guess is entered
    public void changeTrackerAndValue(int row, int col, int value, int tracker) {
        changeCellValue(row, col, value);
        changeTracker(row, col, tracker);
    }

    //Method getting the tracker of the cell --> Used to check if the input is correct or not in the show Hint
    public int getCellTracker(int row, int col) {
        return board[row][col].getTracker();
    }


    //**********************************//
    //  UNUSED STATIC METHODS (CONSOLE) // (DEBUGGING METHODS)
    //**********************************//

    //Method printing out the board in the console
    public static void printBoard(int[][] board) {
        for (int i = 0; i < NUM_ROWS; i++) {
            if (i % 3 == 0) {
                System.out.println("-------------------------------");
            }
            for (int j = 0; j < NUM_COLS; j++) {
                if (j % 3 == 0) {
                    System.out.print("|");
                }
                if (board[i][j] == 0) {
                    System.out.print("   ");
                }
                else {
                    System.out.print(" " + board[i][j] + " ");
                }
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("-------------------------------");
    } //End of printBoard

    //Helper Method to identify the input
    public static int input(String rowOrColumn) {
        String inputKind = rowOrColumn;
        boolean escapeLoop = true;
        int input = 0;
        while (escapeLoop) {
            System.out.print(inputKind + ": ");
            input = sc.nextInt();
            if (input < 0 || input > NUM_ROWS) {
                System.out.println("ERROR: Please input number 1 ~ 9");
            } else {
                escapeLoop = false;
            }
        }
        return input;
    } //End of input

    //Count Blanks inside the question board
    public static int countBlanks(Board board) {
        int n = 0;
        int [][] values = board.getCompleteBoard();
        for(int i = 0; i < NUM_ROWS; i++) {
            for(int j = 0; j < NUM_COLS; j++) {
                if(values[i][j] == 0) {
                    n += 1;
                }
            }
        }
        return n;
    } //End of countBlanks

}