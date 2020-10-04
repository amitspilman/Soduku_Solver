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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class javaFXproject extends Application{

	private GridPane gridBoard;
	private Board board;
	private VBox vbox;
	private HBox hbox;
	private static ObservableList<Node> hlist = null;
	private ObservableList<Node> vlist = null;
	private Scene scene;
	private PseudoClass right, bottom;
	private Button btn_solve, btn_restart;
	private Text resaults;
	private TextField[][] textFieldArr;


	/////////////////////////////////////i make the board easy.
	private void gridToBoard() {
		board = new Board();
		int value;
		for (int i = 0; i < 9; i++) {
			for (int j=0; j<9;j++) {
				if (textFieldArr[i][j].getText() != null && !textFieldArr[i][j].getText().trim().isEmpty()) {
					value = Integer.parseInt(textFieldArr[i][j].getText());
					board.setBaord(j, i, value);
				}
				else {
					board.setBaord(j, i, 0);
				}
			}
		}
		/////////////////////////////////////
		board = board.mediumLevle();
	}

	private void boardTogrid() {
		gridBoard = new GridPane();
		right = PseudoClass.getPseudoClass("right");
		bottom = PseudoClass.getPseudoClass("bottom");
		int value=0;
		for (int i = 0; i < 9; i++) {
			for (int j=0; j<9;j++) {
				value = board.getBaord(i, j);
				StackPane cell = new StackPane();
				cell.pseudoClassStateChanged(right, i == 2 || i == 5);
				cell.pseudoClassStateChanged(bottom, j == 2 || j == 5);
				cell.setMaxSize(50, 50);
				cell.setMinSize(50, 50);
				cell.getStyleClass().add("cell");

				cell.getChildren().add(new TextArea(value + ""));
				gridBoard.add(cell,i,j,1,1);
			}
		}
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

	private void restartGread() {
		gridBoard = new GridPane();
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
				gridBoard.add(cell, i, j,1,1);
			}
		}
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

		restartGread();

		btn_solve.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				gridToBoard();

				int iteration=0;	//limit main loop iterations
				while(!board.solved() && iteration<1000) {
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
					boardTogrid();

				}
				else {
					board.printBoard("unsolved");
					restartGread();
					if (iteration>=1000)
						resaults.setText("to much wotk...");		
					else
						resaults.setText("board canot be solved");			
				}

			}
		});
		btn_restart.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				restartGread();}
		});

		
		hlist = hbox.getChildren();
		hbox.setSpacing(10);
		hbox.setAlignment(Pos.BASELINE_CENTER);
		vlist = vbox.getChildren();

		hlist.addAll(resaults,btn_solve,btn_restart);
		vlist.add(gridBoard);
		vlist.add(hbox);

		scene = new Scene(vbox);		
		scene.getStylesheets().add("sudoku.css");
		stage.setScene(scene);
		stage.show();


	}

	public static void main(String[] args) {
		launch(args);
	}

}
