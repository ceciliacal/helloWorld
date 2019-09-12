package control;

import bean.UserBean;
import boundary.Main;
import boundary.MenuController;
import boundary.TemplateController;
import dao.DBFunctions;
import entity.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerLogin {

    private static ControllerLogin instance;

    private ControllerLogin() {
    }

    public static ControllerLogin getInstance() {
        if (instance == null) {
            instance = new ControllerLogin();
        }
        return instance;
    }


    public void validateLogin(UserBean bean, Stage stage) {
        int res;
        String userType;

        DBFunctions dbf = new DBFunctions();
        res = dbf.checkLogin(bean.getId(), bean.getPassword()); //controlla risultato e poi crea user usando get


        //NOTIFICHE
        Main notify = new Main();

        if (res == 1) {
            //login ok, ora devi controllare userType
            System.out.println("Login effettuato!");
            checkUserType(bean,stage);

            //notify.notification(1,"LOGIN EFFETTUATO", "prosegui");


        } else {

            //torna al login e reinserisci dati
            System.out.println("ERRORE: ID o password errati!");
            notify.notification(0,"ERRORE", "ID o password errati!");

        }

      

    }


    public void checkUserType(UserBean bean, Stage stage) {

        String userType;
        DBFunctions dbU = new DBFunctions();

        userType = dbU.getUserType(bean.getId(), bean.getPassword());
        System.out.println("userType=" + userType);

        bean.setUserType(userType);
        User myUser= new User(bean.getId(),bean.getPassword(),bean.getUserType());

        //System.out.println("bean userType è " + bean.getUserType());

        Main notify = new Main();

        if (myUser.getUserType().equals("1")) {         //locatore
            notify.notification(1, "LOGIN EFFETTUATO", "Sei entrato come LOCATORE");
        } else if (myUser.getUserType().equals("2")) {  //locatario
            notify.notification(1, "LOGIN EFFETTUATO", "Sei entrato come LOCATARIO");
        }

        //chiama funzione che gestisce schermata del menu a seconda se
        //utente è locatore o locatario



        stage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundary/Menu.fxml"));
            Parent root = loader.load();
            MenuController controller = loader.getController();
            controller.createStage(myUser);
            Scene scene = new Scene(root);

            Stage primaryStage = new Stage();
            primaryStage.setTitle("Menu");
            primaryStage.setScene(scene);

            primaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
    public void startUC(Stage primaryStage) {

        //Stage primaryStage = new Stage();

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("LoginUI.fxml"));
            primaryStage.setTitle("Login ");
            primaryStage.setScene(new Scene(root, 800, 800));
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    */
}

