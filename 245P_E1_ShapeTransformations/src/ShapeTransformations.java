import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.NumberStringConverter;

public class ShapeTransformations extends Application {
    private Button LButton = new Button("Rotate Left");
    private Button RButton = new Button("Rotate Right");

    private Ellipse ellipse = new Ellipse(200, 200, 50, 30);

    private ToggleGroup colorGroup = new ToggleGroup();
    private RadioButton setRed = new RadioButton("Red");
    private RadioButton setPurple = new RadioButton("Purple");
    private RadioButton setGreen = new RadioButton("Green");
    private RadioButton setBlue = new RadioButton("Blue");
    private RadioButton setBlack = new RadioButton("Black");

    private Slider scaleSlider = new Slider(0.2, 2, 1);

    private TextField scaleTextFiled = new TextField();
    private Text translateInfo = new Text("Press WASD on the keyboard.");
    private Text resetInfo = new Text("Click on the shape.");
    private Text myName = new Text("Oscar");

    private final Color RED = Color.RED;
    private final Color PURPLE = Color.PURPLE;
    private final Color GREEN = Color.GREEN;
    private final Color BLUE = Color.BLUE;
    private final Color BLACK = Color.BLACK;
    private final Color WHITE = Color.WHITE;

    private StackPane sp = new StackPane(ellipse, myName);

    private double angle = 0;
    private double translateX = 0;
    private double translateY = 0;

    private TitledPane getRotateTitledPane() {
        HBox buttonBox = new HBox(5);
        buttonBox.getChildren().addAll(LButton, RButton);
        buttonBox.setPadding(new Insets(5));
        return new TitledPane("Rotation", buttonBox);
    }

    private TitledPane getTranslateTitledPane() {
        VBox translateBox = new VBox(5);
        translateBox.getChildren().addAll(translateInfo);
        translateBox.setPadding(new Insets(5));
        return new TitledPane("Translation", translateBox);
    }

    private TitledPane getScaleTitledPane() {
        VBox scaleBox = new VBox(5);
        scaleSlider.setShowTickLabels(true);
        scaleSlider.setShowTickMarks(true);
        scaleSlider.setMajorTickUnit(0.2d);
        scaleBox.getChildren().addAll(scaleSlider, scaleTextFiled);
        scaleBox.setPadding(new Insets(5));
        return new TitledPane("Scaling", scaleBox);
    }

    private TitledPane getColorPane() {
        HBox colorBox = new HBox(5);
        setBlack.setToggleGroup(colorGroup);
        setBlue.setToggleGroup(colorGroup);
        setGreen.setToggleGroup(colorGroup);
        setRed.setToggleGroup(colorGroup);
        setPurple.setToggleGroup(colorGroup);
        setBlack.setSelected(true);
        colorBox.getChildren().addAll(setBlack, setBlue, setGreen, setRed, setPurple);
        colorBox.setPadding(new Insets(5));
        return new TitledPane("Color", colorBox);
    }

    private TitledPane getResetPane() {
        VBox resetBox = new VBox(5);
        resetBox.getChildren().addAll(resetInfo);
        resetBox.setPadding(new Insets(5));
        return new TitledPane("Reset", resetBox);
    }

    private VBox getControls() {
        TitledPane rotatePane = getRotateTitledPane();
        TitledPane translatePane = getTranslateTitledPane();
        TitledPane scalePane = getScaleTitledPane();
        TitledPane colorPane = getColorPane();
        TitledPane resetPane = getResetPane();

        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(rotatePane, translatePane, scalePane, colorPane, resetPane);
        return vBox;
    }

    private void radioGroupHandler() {
        RadioButton selectedButton = (RadioButton) colorGroup.getSelectedToggle();
        switch(selectedButton.getText()) {
            case "Black": {
                ellipse.setFill(BLACK);
                break;
            }
            case "Blue": {
                ellipse.setFill(BLUE);
                break;
            }
            case "Green": {
                ellipse.setFill(GREEN);
                break;
            }
            case "Red": {
                ellipse.setFill(RED);
                break;
            }
            case "Purple": {
                ellipse.setFill(PURPLE);
                break;
            }
        }
    }

    private void setEventHandlers() {
        LButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ellipse.setRotate(angle - 30);
                myName.setRotate(angle - 30);
                angle -= 30;
            }
        });

        RButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ellipse.setRotate(angle + 30);
                myName.setRotate(angle + 30);
                angle += 30;
            }
        });

        colorGroup.selectedToggleProperty().addListener((o, oldV, newV) -> {
            radioGroupHandler();
        });

        scaleSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldV, Number newV) {
                ellipse.setScaleX(scaleSlider.getValue());
                ellipse.setScaleY(scaleSlider.getValue());
                myName.setScaleX(scaleSlider.getValue());
                myName.setScaleY(scaleSlider.getValue());
            }
        });

        ellipse.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FillTransition ft = AnimateColor(ellipse, BLACK);
                ft.play();

                TranslateTransition tt1 = AnimateTranslation(ellipse);
                TranslateTransition tt2 = AnimateTranslation(myName);
                ParallelTransition pt1 = new ParallelTransition(tt1, tt2);
                pt1.play();

                RotateTransition rt1 = AnimateRotation(ellipse);
                RotateTransition rt2 = AnimateRotation(myName);
                ParallelTransition pt2 = new ParallelTransition(rt1, rt2);
                pt2.play();

                ScaleTransition st1 = AnimateScaling(ellipse);
                ScaleTransition st2 = AnimateScaling(myName);
                ParallelTransition pt3 = new ParallelTransition(st1, st2);
                pt3.play();

            }
        });

        ellipse.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setBlack.setSelected(true);
                scaleSlider.setValue(1);
                angle = 0;
                translateX = 0;
                translateY = 0;
            }
        });
    }

    private void setSceneEventHandlers(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            switch (key.getCode()) {
                case L: {
                    ellipse.setRotate(angle - 30);
                    myName.setRotate(angle - 30);
                    angle -= 30;
                    break;
                }
                case R: {
                    ellipse.setRotate(angle + 30);
                    myName.setRotate(angle + 30);
                    angle += 30;
                    break;
                }
                case A: {
                    ellipse.setTranslateX(translateX - 5);
                    myName.setTranslateX(translateX - 5);
                    translateX -= 5;
                    break;
                }
                case D: {
                    ellipse.setTranslateX(translateX + 5);
                    myName.setTranslateX(translateX + 5);
                    translateX += 5;
                    break;
                }
                case W: {
                    ellipse.setTranslateY(translateY - 5);
                    myName.setTranslateY(translateY - 5);
                    translateY -= 5;
                    break;
                }
                case S: {
                    ellipse.setTranslateY(translateY + 5);
                    myName.setTranslateY(translateY + 5);
                    translateY += 5;
                    break;
                }
            }
        });
    }

    private FillTransition AnimateColor(Shape shape, Color toColor) {
        FillTransition ft = new FillTransition(Duration.seconds(2), shape);
        ft.setToValue(toColor);
        return ft;
    }

    private TranslateTransition AnimateTranslation(Node node) {
        TranslateTransition tt = new TranslateTransition(Duration.seconds(2), node);
        tt.setToX(0);
        tt.setToY(0);
        return tt;
    }

    private ScaleTransition AnimateScaling(Node node) {
        ScaleTransition st = new ScaleTransition(Duration.seconds(2), node);
        st.setToX(1);
        st.setToY(1);
        return st;
    }

    private RotateTransition AnimateRotation(Node node) {
        RotateTransition rt = new RotateTransition(Duration.seconds(2), node);
        rt.setToAngle(0);
        return rt;
    }

    @Override
    public void start(Stage stage) {
        scaleTextFiled.textProperty().bindBidirectional(scaleSlider.valueProperty(), new NumberStringConverter());

        ellipse.setFill(BLACK);
        myName.setFill(WHITE);
        myName.setFont(Font.font("Arial", 20));
        StackPane canvas = new StackPane(sp);
        StackPane.setAlignment(sp, Pos.CENTER);

        canvas.setPrefSize(400, 400);

        VBox controls = getControls();
        controls.setPrefSize(320, 400);

        BorderPane root = new BorderPane();
        root.setCenter(canvas);
        root.setRight(controls);

        setEventHandlers();

        Scene scene = new Scene(root, 720, 400);
        setSceneEventHandlers(scene);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(ShapeTransformations.class.getSimpleName());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
