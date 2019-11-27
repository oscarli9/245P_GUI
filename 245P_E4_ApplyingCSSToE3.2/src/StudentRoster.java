import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class StudentRoster extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StudentRosterDoc.fxml"));
        TabPane modePane = fxmlLoader.load();
        ((StudentRosterController)fxmlLoader.getController()).setStage(stage);

        Scene scene = new Scene(modePane, 600, 780);
        scene.getStylesheets().add(getClass().getResource("StudentRosterStyleSheet.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
