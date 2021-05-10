
package itpm_projectnb.Home;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ITPM_ProjectNB extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        
      //  Scene scene = new Scene(root);
        
        primaryStage.setTitle("UIT Home");
        primaryStage.setScene(new Scene(root, 1291, 820));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
