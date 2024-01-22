package com.example.sudokugui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class SudokuController {
    //Class level variables
    //private Board board = new Board();
    private Board board;
    //private variable indicating if the "Show Rules" button is on or off
    private boolean ruleShow = false;
    //private variable indicating if the "Show Hints" button is on or off
    private boolean showHints = false;
    //private variable to prevent the listeners from being activated when the New Game is populated
    private boolean gameSetup = false;
    //private variable counting the number of incorrect cell
    private int incorrectCell;
    //private variable counting the number of correct cell
    private int correctCell;
    //private variable counting the number of default cell (Cells that already have variable inside)
    private int defaultCell;
    //Counting how many times newGame was populated
    private int newGame = 0;

    @FXML
    private GridPane grid;

    @FXML
    private Button newGameButton;
    @FXML
    private Button rulesButton;
    @FXML
    private Button ShowHintsButton;
    @FXML
    private TextArea message;

    //INITIALIZE THE BOARD (Most important: Add Listeners/Set listeners/Input Validation)
    @FXML
    public void initialize() {
        defaultCell = 0;
        int rowIndex = 0;
        int colIndex = 0;
        this.board = new Board("src/Board.txt", "src/AnswerBoard.txt");
        //board.printBoard(board.getCompleteBoard());
        ObservableList<Node> textFields = grid.getChildren();
        int[][] intBoard = board.getCompleteBoard();
        for(Node node : textFields) {
            if (node instanceof TextField) {
                TextField tField = (TextField) node;
                if (colIndex > 8) {
                    colIndex = 0;
                    rowIndex++;
                }
                int value = intBoard[colIndex][rowIndex];
                if (value != 0) {   //Set up the board with values that are already there
                    tField.setText(Integer.toString(value));
                    tField.setEditable(false);
                    defaultCell++;      //Counting cells already written
                }
                // Listen for TextField text changes
                final int finalColIndex = colIndex;
                final int finalRowIndex = rowIndex;
                tField.textProperty().addListener((observable, oldValue, changeValue) -> {
                    if (newGame == 0) { //First board to work (Without this the first board does not work)
                        gameSetup = false;
                    }
                    if (!gameSetup) {
                        if (changeValue == null || changeValue.isEmpty()) {     //Reading erased value changes
                            if (board.getCellTracker(finalColIndex, finalRowIndex) == 1) {
                                //if cell tracker is 1 it means that the cell was originally correct
                                correctCell--;
                            } else {
                                //if cell tracker is 2 it means that the cell was orignially incorrect
                                incorrectCell--;
                            }
                            board.changeTrackerAndValue(finalColIndex, finalRowIndex,0, 0);
                            //Board.printBoard(board.getCompleteBoard());
                        } else {
                            int newDigit;
                            try { //input validation
                                newDigit = Integer.parseInt(changeValue);
                                if(newDigit >= 1 && newDigit <= 9) {
                                    if (board.checkGuess(finalColIndex, finalRowIndex, newDigit)) {
                                        board.changeTrackerAndValue(finalColIndex, finalRowIndex, newDigit,1);
                                    } else {
                                        board.changeTracker(finalColIndex, finalRowIndex, -1);
                                    }
                                    //Board.printBoard(board.getCompleteBoard());
                                } else {
                                    // not a valid number
                                    tField.clear();
                                }
                            } catch (NumberFormatException e) {
                                //  changeValue is not a digit
                                tField.clear();
                            }
                            //Checking if the board is filled or not
                            incorrectCell = 0;
                            correctCell = 0;
                            for (int i = 0; i < 9; i++) {
                                for (int j = 0; j < 9; j++) {
                                    if (board.getCellTracker(i, j) == 1) {
                                        correctCell++;
                                    } else if (board.getCellTracker(i, j) == -1) {
                                        incorrectCell++;
                                    }
                                }
                            }
                            if (defaultCell + incorrectCell + correctCell == 81) {
                                if (defaultCell + correctCell == 81) {  //If the board is correct
                                    message.setText("Sudoku Complete!");
                                    gameSetup = true;
                                } else {    //If the board is incorrect
                                    message.setText("All the cells are filled.\nBut some are incorrect...\nCheck again...");
                                }
                            }
                        }
                        changeCellColor(showHints);
                    }
                });
                colIndex++;
            }
        }
        gameSetup = true;
    }


    //Pop up the rules in the TextArea
    @FXML
    public void onRulesButtonClick() {
        if (ruleShow) {
            ruleShow = false;
        } else {
            ruleShow = true;
        }
        if(ruleShow) {
            message.setText("1. A sudoku puzzle begins with a grid in\nwhich some of the numbers are already in\nplace\n" +
                    "2. A puzzle is completed when each number\nfrom 1 to 9 appears only in each of the 9\nrows, columns, and blocks.\n" +
                    "3. Study the grid to find the numbers that\nmight fit into each cell.");
        } else {
            message.clear();
        }
    }

    //Show hints (Change the cell color to show if the cell is correct or not)
    @FXML
    public void onShowHintsClick() {
        if (!showHints) {
            showHints = true;
            changeCellColor(true);
        } else {
            showHints = false;
            changeCellColor(false);
        }

    }

    //Populate a new Board(Needs primary set ups and input the default cells)
    @FXML
    public void onNewGameButtonClick() {
        newGame++;
        //The number of cell values already inputed change
        defaultCell = 0;
        //Message on the board needs to be cleared
        message.clear();
        //If the rule was shown, it needs to be false because the message was cleared
        ruleShow = false;
        //Hints should automatically be turned off
        showHints = false;
        changeCellColor(false);
        gameSetup = true;
        //Clearing the Earlier Board & Refreshing Game State
        this.board = new Board("src/Board.txt", "src/AnswerBoard.txt");
        int[][] intBoard = board.getCompleteBoard();
        ObservableList<Node> textFields = grid.getChildren();
        int rowIndex = 0;
        int colIndex = 0;
        for(Node node : textFields) {
            if (node instanceof TextField) {
                TextField tField = (TextField) node;
                tField.setEditable(true);
                //  #1, clear TextFields
                tField.clear();
                if (colIndex > 8) {
                    colIndex = 0;
                    rowIndex++;
                }

                int value = intBoard[colIndex][rowIndex];
                if (value != 0) {
                    tField.setText(Integer.toString(value));
                    tField.setEditable(false);
                    defaultCell++;
                }
                colIndex++;

            }
        }
        gameSetup = false;
    }

    //Method used to change the color of the cell (Get the tracker --> Correct: change to blue, Incorrect: Change to Red, Defualt: Do not change the color)
    public void changeCellColor(boolean showHints) {    //Changes the color of the cell
        if (showHints) {
            int rowIndex = 0;
            int colIndex = 0;
            ObservableList<Node> textFields = grid.getChildren();
            for (Node node : textFields) {
                if (node instanceof TextField) {
                    TextField tField = (TextField) node;
                    int trackerValue = board.getCellTracker(colIndex, rowIndex);
                    if (trackerValue == -1) {
                        tField.setStyle("-fx-background-color: red; -fx-text-fill: white");
                    } else if (trackerValue == 1) {
                        tField.setStyle("-fx-background-color: blue; -fx-text-fill: white");
                    } else {
                        tField.setStyle("-fx-background-color: white; -fx-text-fill: black");
                    }
                    colIndex++;
                    if (colIndex > 8) {
                        rowIndex++;
                        colIndex = 0;
                        if (rowIndex > 8) {
                            break;
                        }
                    }
                }
            }
        } else {
            int rowIndex = 0;
            int colIndex = 0;
            ObservableList<Node> textFields = grid.getChildren();
            for (Node node : textFields) {
                if (node instanceof TextField) {
                    ((TextField) node).setStyle("-fx-text-fill: black");
                    colIndex++;
                    if (colIndex > 8) {
                        colIndex = 0;
                        rowIndex++;
                    }
                }
            }
        }
    }
}