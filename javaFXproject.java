package javaFXproject;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class javaFXproject extends Application{

	GridPane gridBoard;
	VBox vbox;
	HBox hbox;
	PseudoClass right;
	PseudoClass bottom;

	Button btn_solve;
	Button btn_restart;
	Text resaults = new Text();

	TextField[][] textFieldArr;

	private Board gridToBoard(GridPane grid, TextField[][] textFieldArr) {
		System.out.println("gridToBoard");
		Board board = new Board();
		int value;
		for (int i = 0; i < 9; i++) {
			for (int j=0; j<9;j++) {
				if (textFieldArr[i][j].getText() != null && !textFieldArr[i][j].getText().trim().isEmpty()) {
					value = Integer.parseInt(textFieldArr[i][j].getText());
					board.setBaord(i, j, value);
					System.out.println("("+i+","+ j+") ="+value);
				}
				else {
					System.out.println("("+i+","+ j+") =0");
					board.setBaord(i, j, 0);
				}
			}
		}
		board=board.mediumLevle();
		return board;
	}

	private GridPane boardTogrid(Board board) {
		System.out.println("boardTogrid");
		PseudoClass right = PseudoClass.getPseudoClass("right");
		PseudoClass bottom = PseudoClass.getPseudoClass("bottom");
		GridPane gridpane = new GridPane();
		int value;
		for (int i = 0; i < 9; i++) {
			for (int j=0; j<9;j++) {
				value = board.getBaord(i, j);
				StackPane cell = new StackPane();
				cell.pseudoClassStateChanged(right, i == 2 || i == 5);
				cell.pseudoClassStateChanged(bottom, j == 2 || j == 5);
				cell.setMaxSize(50, 50);
				cell.setMinSize(50, 50);
				cell.getStyleClass().add("cell");
			
				cell.getChildren().add(createTextInBox(value));
				gridpane.add(cell, i, j);
			}
		}
		return gridpane;
	}

	private Node createTextField() {
		TextField textField = new TextField();
		// restrict input to integers:
		textField.setTextFormatter(new TextFormatter<Integer>(c -> {
			if (c.getControlNewText().matches("\\d?")) {
				return c ;
			} else {
				return null ;
			}
		}));
		return textField ;
	}

	private Node createTextInBox(int number) {
		System.out.println("createTextInBox");
		Integer num = number;
		Text text = new Text(num.toString());
		return (new VBox(text)) ;	
	}

	//need to be fixed
	private GridPane restartGread(GridPane gridBoard, TextField[][] textFieldArr) {
		System.out.println("restartGread");
		right = PseudoClass.getPseudoClass("right");
		bottom = PseudoClass.getPseudoClass("bottom");

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				StackPane cell = new StackPane();
				cell.pseudoClassStateChanged(right, i == 2 || i == 5);
				cell.pseudoClassStateChanged(bottom, j == 2 || j == 5);
				cell.setMaxSize(50, 50);
				cell.setMinSize(50, 50);
				cell.getStyleClass().add("cell");
				textFieldArr[i][j] = (TextField)createTextField();
				cell.getChildren().add(textFieldArr[i][j]);
				gridBoard.add(cell, i, j);
			}
		}
		return gridBoard;
	}





	public void start (Stage stage) {
		stage.setResizable(false);
		stage.setTitle("MyFirstProject");

		gridBoard = new GridPane();
		textFieldArr = new TextField[9][9];

		vbox = new VBox();
		hbox = new HBox();

		resaults = new Text();
		btn_solve = new Button("Solve !!!");
		btn_restart = new Button("Restart");

		gridBoard =restartGread(gridBoard,textFieldArr);

		btn_solve.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {

				Board board = new Board();
				board=gridToBoard(gridBoard, textFieldArr);
				//				board = board.mediumLevle();

				int iteration=0;	//limit main loop iterations
				while(!board.solved() && iteration<5000) {
					while (board.findIfMissingOneDigitInRowCol()>0)
						board.fillMisingDigit(board.findIfMissingOneDigitInRowCol());
					while (board.findIfMissingOneDigitInsquare()>0)
						board.fillMisingDigitSquare(board.findIfMissingOneDigitInsquare());
					for (int i = 0; i < 9; i++)
						for (int j = 0; j < 9; j++)
							if (board.getBaord(i, j)==0)
								board.tryPutNewNumberInside(i,j);
					iteration++;
				}
				if (board.solved()) {
					resaults.setText("board has solved");
					board.printBoard("solved");
					gridBoard =boardTogrid(board);
				}
				else {
					board.printBoard("unsolved");
					if (iteration>=10000)
						resaults.setText("to much wotk...");		
					else
						resaults.setText("board canot be solved");			
				}

			}
		});
		btn_restart.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				gridBoard = restartGread(gridBoard,textFieldArr);}
		});

		ObservableList<Node> hlist = hbox.getChildren();
		hbox.setSpacing(10);
		hbox.setAlignment(Pos.BASELINE_CENTER);
		ObservableList<Node> vlist = vbox.getChildren();

		hlist.addAll(resaults,btn_solve,btn_restart);
		vlist.add(gridBoard);
		vlist.add(hbox);

		Scene scene = new Scene(vbox);		
		scene.getStylesheets().add("sudoku.css");
		stage.setScene(scene);
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}


}


//not show the number after solving(or not), probably need to update gridpane to update board (fixed board)

//fix position problem. the numbers enter wrong position (arr[0,1] -> arr[1,0]). need to find out why it happened.


