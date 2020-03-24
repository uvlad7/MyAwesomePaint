import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("MyAwesomePaint");
        primaryStage.setScene(new Scene(new PaintPane(primaryStage)));
        primaryStage.getIcons().add(new Image(getClass().getResource("Icon.png").toExternalForm()));
        primaryStage.sizeToScene();
        primaryStage.show();
    }
}