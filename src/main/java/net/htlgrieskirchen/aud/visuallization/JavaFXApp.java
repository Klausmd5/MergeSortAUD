package net.htlgrieskirchen.aud.visuallization;

import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import net.htlgrieskirchen.aud.IArray;
import net.htlgrieskirchen.aud.insertionsort.InsertionSort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JavaFXApp extends Application {
	private static final Duration stepDuration = Duration.seconds(1);
	private static final int cycleCount = 50;

	private Stage stage;
	private Scene menuScene;

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(new MainMenuScene() {{
			JavaFXApp.this.menuScene = this;
		}});
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
			super(new BorderPane(), 400, 300);
			root = (BorderPane) getRoot();

			root.setPadding(new Insets(10));

			root.setLeft(new Button("Execute MergeSort") {{
				setPrefSize(MainMenuScene.super.getWidth() / 2 - 15, MainMenuScene.super.getHeight());
				setOnAction(JavaFXApp.this::executeMergeSort);
			}});
			root.setRight(new Button("Execute InsertionSort") {{
				setPrefSize(MainMenuScene.super.getWidth() / 2 - 15, MainMenuScene.super.getHeight());
				setOnAction(JavaFXApp.this::executeInsertionSort);
			}});

			BorderPane.setAlignment(root.getLeft(), Pos.CENTER_LEFT);
			BorderPane.setAlignment(root.getRight(), Pos.CENTER_RIGHT);
		}
	}

	private class MergeSortScene extends Scene {
		public BorderPane root;

		private MergeSortScene() {
			super(new BorderPane(), 800, 600);
			root = (BorderPane) getRoot();

			root.setPadding(new Insets(10));

			root.setLeft(new Button("<- fuck go back") {{
				setOnAction(event -> stage.setScene(menuScene));
			}});

			root.setCenter(new ImageView(new Image(MergeSortScene.class.getClassLoader().getResourceAsStream("Mergesort.gif"))){{
				setScaleX(2);
				setScaleY(2);
			}});

			BorderPane.setAlignment(root.getLeft(), Pos.TOP_LEFT);
		}
	}

	private class InsertionSortScene extends Scene {
		private final BarGraph graph;
		private BorderPane root;
		private Thread sortingThread;

		private InsertionSortScene() {
			super(new BorderPane(), 800, 600);
			root = (BorderPane) getRoot();

			root.setPadding(new Insets(10));

			root.setLeft(new Button("<-") {{
				setOnAction(event -> stage.setScene(menuScene));
			}});

			BorderPane.setAlignment(root.getLeft(), Pos.TOP_LEFT);

			graph = new BarGraph(10, 300, 200);
			root.setCenter(graph);

			this.sortingThread = new Thread(() -> {
				InsertionSort.sort(graph.getAdapter());
			});
			this.sortingThread.start();
		}
	}

	private class BarGraph extends Region {
		private List<Pair<Rectangle, Integer>> rectangles;
		private ArrayAdapter adapter = new ArrayAdapter();

		private int number;
		private double width;
		private double height;

		private Rectangle2D calculateElementPosition(int i) {
			double elementWidth = (float) width / number * 0.5;
			double elementHeight = height / 2;
			double xOffset = (float) ((float) width / number * i + elementWidth * 0.5);
			double yOffset = (height - elementHeight);
			return new Rectangle2D(xOffset, yOffset, elementWidth, elementHeight);
		}

		private void drawNew() {
			getChildren().clear();

			this.height = getBoundsInLocal().getHeight();
			this.width = getBoundsInLocal().getWidth();

			rectangles = new ArrayList<Pair<Rectangle, Integer>>();
			for(int i = 0; i < number; i++) {
				Rectangle2D pos = calculateElementPosition(i);
				rectangles.add(new Pair<Rectangle, Integer>(new Rectangle(pos.getMinX(), pos.getMinY(), pos.getWidth(), pos.getHeight()), (int) (Math.random() * 100)));
			}

			getChildren().addAll(rectangles.stream().map(Pair::getKey).collect(Collectors.toList()));
		}

		@Override
		public void resize(double width, double height) {
			super.resize(width, height);
			drawNew();
		}

		private BarGraph(int number, int width, int height) {
			this.number = number;
			this.width = width;
			this.height = height;

			drawNew();
		}

		public IArray<Integer> getAdapter() {
			return adapter;
		}

		private class ArrayAdapter implements IArray<Integer> {

			@Override
			public void swap(int i, int j) {
				Rectangle iRec = rectangles.get(i).getKey();
				Rectangle jRec = rectangles.get(j).getKey();

				Transition[] transitions = new Transition[2];

				createTransition(j, iRec, transitions, 0);
				createTransition(i, jRec, transitions, 1);

				transitions[0].play();
				transitions[1].play();
				Collections.swap(rectangles, i, j);

				try {
					Thread.sleep(((long) stepDuration.toMillis()));
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}

			private void createTransition(int i, Rectangle jRec, Transition[] transitions, int i2) {
				Rectangle2D newPos = calculateElementPosition(i);
				TranslateTransition transition = new TranslateTransition(stepDuration, jRec);
				transition.setByX(newPos.getMinX() - jRec.getX());
				transition.setByY(newPos.getMinY() - jRec.getY());
				transition.setCycleCount(cycleCount);
				transition.setAutoReverse(false);
				transition.setOnFinished(event -> {
					jRec.setX(newPos.getMinX());
					jRec.setY(newPos.getMinY());
					jRec.setWidth(newPos.getWidth());
					jRec.setHeight(newPos.getHeight());
				});
				transitions[i2] = transition;
			}

			@Override
			public int size() {
				return number;
			}

			@Override
			public int compare(int i, int j) {
				return rectangles.get(i).getValue().compareTo(rectangles.get(j).getValue());
			}

			@Override
			public IArray<Integer> subArray(int from, int toExclusive) {
				throw new IllegalStateException("Not yet implemented!");
			}

			@Override
			public void append(IArray<Integer> array) {
				throw new IllegalStateException("Not yet implemented!");
			}
		}
	}
}
