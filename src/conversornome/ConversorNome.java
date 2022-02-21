package conversornome;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Tony
 */
public class ConversorNome extends Application {

    private static Stage stage;
    private Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ConversorNome.fxml"));
            Parent painel = loader.load();
            scene = new Scene(painel);
            stage.setScene(scene);
            stage.setMaximized(false);//janela inteira
            stage.setTitle("Conversor Nome Fotografia 1.0");
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
        }
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        ConversorNome.stage = stage;
    }

}
