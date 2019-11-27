import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ShapeTransformation extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root =  FXMLLoader.load(getClass().getResource("ShapeTransformationDoc.fxml"));

        Scene scene = new Scene(root, 720, 400);
        scene.getStylesheets().add(getClass().getResource("ShapeTransformationDocStyleSheet.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(ShapeTransformation.class.getSimpleName());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
