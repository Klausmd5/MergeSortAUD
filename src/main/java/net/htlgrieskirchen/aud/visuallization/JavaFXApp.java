package net.htlgrieskirchen.aud.visuallization;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class JavaFXApp extends Application {
	private Stage stage;
	private Scene menuScene;

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(new MainMenuScene() {{JavaFXApp.this.menuScene = this;}});
		primaryStage.show();

		stage = primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void executeMergeSort(ActionEvent actionEvent) {
		stage.setScene(new MergeSortScene());
	}

	private void executeInsertionSort(ActionEvent actionEvent) {
		stage.setScene(new InsertionSortScene());
	}

	private class MainMenuScene extends Scene {
		public BorderPane root;
		private MainMenuScene() {
			super(new BorderPane(),400,300);
			root = (BorderPane) getRoot();

			root.setPadding(new Insets(10));

			root.setLeft(new Button("Execute MergeSort"){{
				setPrefSize(MainMenuScene.super.getWidth()/2 - 15, MainMenuScene.super.getHeight());
				setOnAction(JavaFXApp.this::executeMergeSort);
			}});
			root.setRight(new Button("Execute InsertionSort"){{
				setPrefSize(MainMenuScene.super.getWidth()/2 - 15, MainMenuScene.super.getHeight());
				setOnAction(JavaFXApp.this::executeInsertionSort);
			}});

			BorderPane.setAlignment(root.getLeft(), Pos.CENTER_LEFT);
			BorderPane.setAlignment(root.getRight(), Pos.CENTER_RIGHT);
		}
	}

	private class MergeSortScene extends Scene {
		public BorderPane root;
		private MergeSortScene() {
			super(new BorderPane(), 400, 300);
			root = (BorderPane) getRoot();

			root.setPadding(new Insets(10));

			root.setLeft(new Button("<-") {{
				setOnAction(event -> stage.setScene(menuScene));
			}});
			root.setBottom(new Label("MERGE SORT MAMA"));

			BorderPane.setAlignment(root.getLeft(), Pos.TOP_LEFT);
		}
	}

	private class InsertionSortScene extends Scene {
		public BorderPane root;
		private InsertionSortScene() {
			super(new BorderPane(), 400, 300);
			root = (BorderPane) getRoot();

			root.setPadding(new Insets(10));

			root.setLeft(new Button("<-") {{
				setOnAction(event -> stage.setScene(menuScene));
			}});
			root.setBottom(new Label("INSERTION SORT MAMA"));

			BorderPane.setAlignment(root.getLeft(), Pos.TOP_LEFT);
		}
	}
}
