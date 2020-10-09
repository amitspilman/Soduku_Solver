package javaFXproject;


import javafx.application.Application;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class javaFXproject extends Application{

	private GridPane gridBoard;
	private Board board;
	private VBox vbox;
	private HBox hbox;
	private Scene scene;
	private PseudoClass right, bottom;
	private Button btn_solve, btn_restart;
	private Text resaults;
	private TextField[][] textFieldArr;


	private Board gridToBoard(GridPane gridBoard,Board board) {
		int value;
		for (int row = 0; row < 9; row++) {
			for (int col=0; col<9;col++) {
				if (textFieldArr[row][col].getText() != null && !textFieldArr[row][col].getText().trim().isEmpty()) {

					value = Integer.parseInt(textFieldArr[row][col].getText());
					board.setBaord(row, col, value);


				}
				else {
					board.setBaord(row, col, 0);
				}
			}
		}
		return board;
	}

	private void boardTogrid(GridPane gridBoard,Board board) {
		gridBoard.getChildren().clear();
		right = PseudoClass.getPseudoClass("right");
		bottom = PseudoClass.getPseudoClass("bottom");
		for (int row = 0; row < 9; row++) {
			for (int col=0; col<9;col++) {
				StackPane cell = new StackPane();
				cell.pseudoClassStateChanged(right, col == 2 || col == 5);
				cell.pseudoClassStateChanged(bottom, row == 2 || row == 5);
				cell.setMaxSize(50, 50);
				cell.setMinSize(50, 50);
				cell.getStyleClass().add("cell");

				Text text = new Text(board.getBaord(row, col) + "");
				text.setFill(Color.BLUE);
				cell.getChildren().add(text);

				gridBoard.add(cell,col,row);
			}
		}
	}

	private void updateGrid(GridPane gridBoard,Board board) {

		int value;
		for (int row = 0; row < 9; row++) {
			for (int col=0; col<9;col++) {
				if (textFieldArr[row][col].getText() != null && !textFieldArr[row][col].getText().trim().isEmpty()) {
					value = Integer.parseInt(textFieldArr[row][col].getText());

					StackPane cell = new StackPane();
					cell.pseudoClassStateChanged(right, col == 2 || col == 5);
					cell.pseudoClassStateChanged(bottom, row == 2 || row == 5);
					cell.setMaxSize(50, 50);
					cell.setMinSize(50, 50);
					cell.getStyleClass().add("cell");

					Text text = new Text(value + "");
					text.setFill(Color.BLUE);
					cell.getChildren().add(text);

					gridBoard.getChildren().remove(textFieldArr[row][col]);
					gridBoard.add(cell,col,row);


				}

			}
		}

	}

	private TextField createTextField() {
		TextField textField = new TextField();
		// restrict input to integers:
		textField.setTextFormatter(new TextFormatter<Integer>(c -> {
			if (c.getControlNewText().matches("\\d?")){
				return c;
			} else {
				return null ;
			}
		}));
		return textField ;
	}

	private void restartGread(GridPane gridBoard) {
		gridBoard.getChildren().clear();
		right = PseudoClass.getPseudoClass("right");
		bottom = PseudoClass.getPseudoClass("bottom");
		textFieldArr = new TextField[9][9];
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				StackPane cell = new StackPane();
				cell.pseudoClassStateChanged(right, col == 2 || col == 5);
				cell.pseudoClassStateChanged(bottom, row == 2 || row == 5);
				cell.setMaxSize(50, 50);
				cell.setMinSize(50, 50);
				cell.getStyleClass().add("cell");
				textFieldArr[row][col] = createTextField();
				cell.getChildren().add(textFieldArr[row][col]);
				gridBoard.add(cell, col, row);
			}
		}
	}

	public void start (Stage stage) {
		stage.setResizable(false);
		stage.setTitle("Sudoku Solver");

		gridBoard = new GridPane();
		board = new Board();
		textFieldArr = new TextField[9][9];

		vbox = new VBox();
		hbox = new HBox();

		resaults = new Text();
		btn_solve = new Button("Solve !!!");
		btn_restart = new Button("Restart");

		restartGread(gridBoard);

		btn_solve.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				board = gridToBoard(gridBoard, board);
				int iteration=0;	//limit main loop iterations
				boolean boardSolved = false;

				while(!boardSolved && iteration<1000) {
					boardSolved = board.solved(board);
					while (board.findIfMissingOneDigitInRowCol(board)>0) {
						board = board.fillMisingDigit(board, board.findIfMissingOneDigitInRowCol(board));
						System.out.println("1");
					}
					while (board.findIfMissingOneDigitInsquare(board)>0) {
						board = board.fillMisingDigitSquare(board, board.findIfMissingOneDigitInsquare(board));
						System.out.println("2");
					}
					for (int i = 0; i < 9; i++)
						for (int j = 0; j < 9; j++)
							if (board.getBaord(i, j)==0) {
								board = board.tryPutNewNumberInside(board,i,j);
								System.out.println("3");
							}
					iteration++;

				}

				if (boardSolved) {
					board.printBoard(board, "board has solved");
					resaults.setText("board has solved");
					boardTogrid(gridBoard, board);
				}
				else {
					updateGrid(gridBoard, board);
					resaults.setText("can't solve it");		
				}
				if (board.hasProblem(board)) {
					btn_solve.setDisable(true);
					resaults.setText("wrong input");		
				}
			}
		});
		btn_restart.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				restartGread(gridBoard);
				btn_solve.setDisable(false);
				resaults.setText("");		

			}
		});



		hbox.setSpacing(10);
		hbox.setAlignment(Pos.BASELINE_CENTER);

		hbox.getChildren().addAll(resaults,btn_solve,btn_restart);
		vbox.getChildren().add(gridBoard);
		vbox.getChildren().add(hbox);

		scene = new Scene(vbox);		
		scene.getStylesheets().add("sudoku.css");
		stage.setScene(scene);
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}


}
