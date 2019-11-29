package net.htlgrieskirchen.aud.visuallization;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import net.htlgrieskirchen.aud.IArray;
import net.htlgrieskirchen.aud.insertionsort.InsertionSort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class JavaFXApp extends Application {
	private static final Duration stepDuration = Duration.seconds(1);
	private static final int cycleCount = 50;

	private Stage stage;
	private Scene menuScene;
	private String title = "AUD Mergesort";

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(new MainMenuScene() {{
			JavaFXApp.this.menuScene = this;
		}});
		primaryStage.setTitle(title);
		primaryStage.show();
		primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("MergesortIcon.png")));
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
				setOnAction(event -> {
					{
						stage.setScene(menuScene);
						stage.setTitle(title);
					}
				});
			}});

			root.setCenter(new ImageView(new Image(MergeSortScene.class.getClassLoader().getResourceAsStream("Mergesort.gif"))) {{
				setScaleX(2);
				setScaleY(2);
			}});

			BorderPane.setAlignment(root.getLeft(), Pos.TOP_LEFT);
		}
	}

	private class InsertionSortScene extends Scene {
		private final SortVisualizer graph;
		private BorderPane root;
		private Thread sortingThread;

		private InsertionSortScene() {
			super(new BorderPane(), 800, 600);
			root = (BorderPane) getRoot();

			root.setPadding(new Insets(10));

			root.setLeft(new Button("<-") {{
				setOnAction(event -> {
					{
						stage.setScene(menuScene);
						stage.setTitle(title);
					}
				});
			}});

			BorderPane.setAlignment(root.getLeft(), Pos.TOP_LEFT);

			graph = new SortVisualizer();
			root.setCenter(graph);

			this.sortingThread = new Thread(() -> {
				InsertionSort.sort(graph.adapter);
				Platform.runLater(this::showFinish);
			});
			graph.updateElements();
			this.sortingThread.start();

		}

		public void showFinish() {
			Alert a = new Alert(Alert.AlertType.INFORMATION);
			a.setTitle("Finished");
			a.setHeaderText("Info:");
			a.setContentText("Task failed successfully!");
			a.showAndWait();
		}

	}

	private class BarGraph extends Region {
		private List<Pair<Rectangle, Integer>> rectangles;
		private ArrayAdapter adapter = new ArrayAdapter();

		private int number;
		private double width;
		private double height;
		private boolean running = false;

		private Rectangle2D calculateElementPosition(int i, boolean repeat, int zuf) {
			double elementWidth = (float) width / number * 0.5;
			double elementHeight = !repeat ? zuf * 2 : rectangles.get(i).getKey().getHeight();
			double xOffset = (float) ((float) width / number * i + elementWidth * 0.5);
			double yOffset = (height - elementHeight);
			return new Rectangle2D(xOffset, yOffset, elementWidth, elementHeight);
		}

		public void swapRectangles(int i, int j) {
			Rectangle r1 = rectangles.get(i).getKey();
			Rectangle r2 = rectangles.get(j).getKey();
			Rectangle temp = r1;

			r1.setX(r2.getX());
			r1.setY(r2.getY());
			r2.setX(temp.getX());
			r2.setY(temp.getY());
		}

		private void drawNew() {
			getChildren().clear();

			this.height = getBoundsInLocal().getHeight();
			this.width = getBoundsInLocal().getWidth();

			rectangles = new ArrayList<Pair<Rectangle, Integer>>();
			for(int i = 0; i < number; i++) {
				int zuf = (int) (Math.random() * 100);
				Rectangle2D pos = calculateElementPosition(i, false, zuf);
				rectangles.add(new Pair<Rectangle, Integer>(new Rectangle(pos.getMinX(), pos.getMinY(), pos.getWidth(), pos.getHeight()), zuf));
			}

			getChildren().addAll(rectangles.stream().map(Pair::getKey).collect(Collectors.toList()));
		}

		@Override
		public void resize(double width, double height) {
			super.resize(width, height);
			if(!running) {
				running = true;
				drawNew();
			}
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
				Platform.runLater(() -> swap2(i, j));
			}

			public void swap2(int i, int j) {
				Rectangle iRec = rectangles.get(i).getKey();
				Rectangle jRec = rectangles.get(j).getKey();

				PathTransition[] transitions = new PathTransition[2];

				int heightAddition1 = 50; /*px*/

				Path path1 = new Path();
				path1.getElements().add(new MoveTo(iRec.getX(), iRec.getY() + heightAddition1));
				path1.getElements().add(new MoveTo(jRec.getX(), jRec.getY() + heightAddition1));
				path1.getElements().add(new MoveTo(jRec.getX(), jRec.getY()));
				PathTransition transition1 = new PathTransition();
				transition1.setPath(path1);
				transition1.setDuration(stepDuration);
				transition1.setNode(iRec);
				transition1.setDuration(Duration.seconds(2));
				transition1.setCycleCount(4);
				transition1.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
				transitions[0] = transition1;


				int heightAddition = 50; /*px*/

				Path path = new Path();
				path.getElements().add(new MoveTo(jRec.getX(), jRec.getY() + heightAddition));
				path.getElements().add(new MoveTo(iRec.getX(), iRec.getY() + heightAddition));
				path.getElements().add(new MoveTo(iRec.getX(), iRec.getY()));
				PathTransition transition = new PathTransition();
				transition.setPath(path);
				transition.setNode(jRec);
				transition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
				transitions[1] = transition;

				getChildren().addAll(path, path1);

				transitions[0].setNode(rectangles.get(i).getKey());

				Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
					System.out.println(transitions[0].getStatus() + "\t\t" + transitions[1].getStatus());
				}, 0, 1, TimeUnit.SECONDS);

//				Rectangle2D newPos1 = calculateElementPosition(j, true, 0);
//				TranslateTransition transition1 = new TranslateTransition(stepDuration, iRec);
//				//transition1.setByX(newPos1.getMinX() - iRec.getX());
//				transition1.setByX(jRec.getX() - iRec.getX());
//				//transition1.setByY(newPos1.getMinY() - iRec.getY());
//				transition1.setCycleCount(cycleCount);
//				transition1.setAutoReverse(false);
//				transition1.setOnFinished(event1 -> {
//					/*iRec.setX(newPos1.getMinX());
//					iRec.setY(newPos1.getMinY());*/
//					iRec.setX(jRec.getX());
//					iRec.setY(jRec.getY());
//					iRec.setWidth(newPos1.getWidth());
//					iRec.setHeight(newPos1.getHeight());
//
//				});
//				transitions[0] = transition1;
//
//				Rectangle2D newPos = calculateElementPosition(i, true, 0);
//				TranslateTransition transition = new TranslateTransition(stepDuration, jRec);
//				//transition.setByX(newPos.getMinX() - jRec.getX());
//				transition.setByX(iRec.getX() - jRec.getX());
//				//transition.setByY(newPos.getMinY() - jRec.getY());
//				transition.setCycleCount(cycleCount);
//				transition.setAutoReverse(false);
//				transition.setOnFinished(event -> {
//					/*jRec.setX(newPos.getMinX());
//					jRec.setY(newPos.getMinY());*/
//					jRec.setX(iRec.getX());
//					jRec.setY(iRec.getY());
//					jRec.setWidth(newPos.getWidth());
//					jRec.setHeight(newPos.getHeight());
//				});
//				transitions[1] = transition;

				transitions[0].playFromStart();

				try {
					Thread.sleep(((long) stepDuration.toMillis()));
					transitions[0].stop();
					transitions[1].play();
					Thread.sleep(((long) stepDuration.toMillis()));
					transitions[1].stop();
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
				Collections.swap(rectangles, i, j);
			}

			private PathTransition makeTransition(Rectangle i, Rectangle j) {
				int heightAddition = 50; /*px*/

				Path path = new Path();
				path.getElements().add(new MoveTo(i.getX(), i.getY() + heightAddition));
				path.getElements().add(new MoveTo(j.getX(), j.getY() + heightAddition));
				path.getElements().add(new MoveTo(j.getX(), j.getY()));
				PathTransition transition = new PathTransition();
				transition.setPath(path);
				transition.setDuration(stepDuration);
				transition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
				return transition;
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

	private class SortVisualizer extends Region {
		public ArrayAdapter adapter = new ArrayAdapter();

		private int countAll = 10;

		private void updateElements() {
			for(int i = 0; i < getChildren().size(); i++) ((GraphElement) getChildren().get(i)).update(i);
		}

		public SortVisualizer() {
			for(int i = 0; i < countAll; i++) {
				getChildren().add(new GraphElement());
			}
		}

		private class ArrayAdapter implements IArray<Integer> {
			@Override
			public void swap(int i, int j) {

			}

			@Override
			public int size() {
				return 0;
			}

			@Override
			public int compare(int i, int j) {
				return 0;
			}

			@Override
			public IArray<Integer> subArray(int from, int toExclusive) {
				return null;
			}

			@Override
			public void append(IArray<Integer> array) {

			}
		}

		private class GraphElement extends Rectangle {
			{
				setBackground(new Background(new BackgroundFill(Color.gray(0.2),null,null)));
			}

			private void update(int i) {
				System.out.println("update: "+SortVisualizer.this.getHeight());
				setWidth(SortVisualizer.this.getWidth() / SortVisualizer.this.getChildren().size());
				setHeight(SortVisualizer.this.getHeight() / 2);
			}
		}
	}
}
