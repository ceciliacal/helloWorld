package boundary;

import bean.TenantAnnounceBean;
import control.ControllerTenantAnnounce;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class TenantAnnounceUIController {

    @FXML
    public TextField titleField, nameField, cityField, room, bathrooms, people;

    @FXML
    public CheckBox wifi, garden, animalsAllowed, airConditioning, parking;

    @FXML
    public DatePicker idDateArr, idDateDep;

    @FXML
    public Button okBtn, deleteBtn, publishBtn;

    //private renterAnnounceBean bean;

    ControllerTenantAnnounce cRA;

    private TenantAnnounceBean bean = new TenantAnnounceBean();


    public void clickedPublishBtn(ActionEvent actionEvent) {



        bean.setTitle(titleField.getText());
        bean.setName(nameField.getText());
        bean.setCity(cityField.getText());
        bean.setRoom(Integer.parseInt(room.getText()));
        bean.setPeople(Integer.parseInt(people.getText()));
        bean.setBath(Integer.parseInt(bathrooms.getText()));

        //data
        bean.setDateArr(idDateArr.getValue());
        bean.setDateDep(idDateDep.getValue());


        if (wifi.isSelected()) {
            bean.setWifi("si");
        } else {
            bean.setWifi("no");
        }

        if (garden.isSelected()) {
            bean.setGarden("si");
        } else {
            bean.setGarden("no");
        }

        if (animalsAllowed.isSelected()) {
            bean.setAnimals("si");
        } else {
            bean.setAnimals("no");
        }

        if (airConditioning.isSelected()) {
            bean.setAirConditionig("si");
        } else {
            bean.setAirConditionig("no");
        }

        if (parking.isSelected()) {
            bean.setParking("si");
        } else {
            bean.setParking("no");
        }


        //System.out.println("city e name sono " + bean.getCity() +   bean.getName());

        //una volta inseriti i dati e cliccato su pubblica si chiude la finestra
        Stage stage = (Stage)titleField.getScene().getWindow();
        stage.close();

        cRA = ControllerTenantAnnounce.getInstance();
        cRA.createRenterAnnounce(bean);
    }


    public int checkDate() {

        int yearArr = idDateArr.getValue().getYear();
        int yearDep =  idDateDep.getValue().getYear();

        int monthArr = idDateArr.getValue().getMonthValue();
        int monthDep=idDateDep.getValue().getMonthValue();

        int dayArr = idDateArr.getValue().getDayOfMonth();
        int dayDep =  idDateDep.getValue().getDayOfMonth();

        if (yearDep < yearArr) {
            System.out.println("anno no");
            return 0;
        }
        if (yearDep == yearArr) {
            if (monthDep < monthArr) {
                System.out.println("mese no");
                return 0;
            }
            else if (monthDep == monthArr) {
                if (dayDep < dayArr) {
                    System.out.println("giorno no");
                    return 0;
                }
            }
        }
        System.out.println("data ok");
        return 1;

    }


    public void clickedOkButton(ActionEvent actionEvent) {
        if (checkDate() == 0) {
            TrayNotification tray = new TrayNotification();
            AnimationType type = AnimationType.POPUP;
            tray.setAnimationType(type);
            tray.setTitle("ERRORE DATA");
            tray.setMessage("Inserisci una data successiva a quella di arrivo!");
            tray.setNotificationType(NotificationType.ERROR);
            tray.showAndDismiss(Duration.seconds(50));
        }
    }


    /*****
    public void createStage(TenantAnnounceBean bean) {

        titleField.setText(bean.getTitle());
        nameField.setText(bean.getName());
        cityField.setText(bean.getCity());
        room.setText(String.valueOf(bean.getRoom()));
        bathrooms.setText(String.valueOf(bean.getBath()));
        people.setText(String.valueOf(bean.getPeople()));


        System.out.println("createStage il bean del title è" + bean.getTitle());


    }
     ******/
}