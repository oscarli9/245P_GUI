import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class ShapeTransformationController implements Initializable {
    private double angle = 0;
    private double translateX = 0;
    private double translateY = 0;

    @FXML
    private BorderPane root;

    @FXML
    private Button lRotateButton;

    @FXML
    private Button rRotateButton;

    @FXML
    private Slider scaleSlider;

    @FXML
    private TextField scaleTextField;

    @FXML
    private ToggleGroup colorGroup;
    @FXML
    private RadioButton setBlack;

    @FXML
    private Text myName;

    @FXML
    private Ellipse ellipse;

    public void lBtnHandler(ActionEvent event) {
        ellipse.setRotate(angle - 30);
        myName.setRotate(angle - 30);
        angle -= 30;
    }

    public void rBtnHandler(ActionEvent event) {
        ellipse.setRotate(angle + 30);
        myName.setRotate(angle + 30);
        angle += 30;
    }

    private void radioGroupHandler() {
        RadioButton selectedButton = (RadioButton) colorGroup.getSelectedToggle();
        switch(selectedButton.getText()) {
            case "Black": {
                ellipse.setFill(Color.BLACK);
                break;
            }
            case "Blue": {
                ellipse.setFill(Color.BLUE);
                break;
            }
            case "Green": {
                ellipse.setFill(Color.GREEN);
                break;
            }
            case "Red": {
                ellipse.setFill(Color.RED);
                break;
            }
            case "Purple": {
                ellipse.setFill(Color.PURPLE);
                break;
            }
        }
    }

    private void setRootEventHandlers(Pane pane) {
        pane.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
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

    public void ellipseOnMousePressedHandler(MouseEvent event) {
        FillTransition ft = AnimateColor(ellipse, Color.BLACK);
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

    public void ellipseOnMouseReleasedHandler(MouseEvent event) {
        setBlack.setSelected(true);
        scaleSlider.setValue(1);
        angle = 0;
        translateX = 0;
        translateY = 0;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        scaleTextField.textProperty().bindBidirectional(scaleSlider.valueProperty(), new NumberStringConverter());

        scaleSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldV, Number newV) {
                ellipse.setScaleX(scaleSlider.getValue());
                ellipse.setScaleY(scaleSlider.getValue());
                myName.setScaleX(scaleSlider.getValue());
                myName.setScaleY(scaleSlider.getValue());
            }
        });

        colorGroup.selectedToggleProperty().addListener((o, oldV, newV) -> {
            radioGroupHandler();
        });

        setRootEventHandlers(root);
    }

}
