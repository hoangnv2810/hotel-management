package Controller;

import DBConnection.DBConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class MenuBarBorderPane implements Initializable {
    @FXML
    private BorderPane borderPane;

    @FXML
    private Label labelUsername;

    @FXML
    private MenuItem menuItemQLNV;

    @FXML
    void handleMenuItem(ActionEvent event) throws IOException {
        MenuItem mItem = (MenuItem) event.getSource();
        String side = mItem.getText();
        Stage stage = (Stage) borderPane.getScene().getWindow();
        if ("Đặt phòng".equalsIgnoreCase(side)) {
            FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("../View/CheckIn.fxml"));
            stage.getIcons().add(new Image("Images/icons8-hotel-check-in-48.png"));
            try {
                stage.setHeight(570);
                stage.setWidth(710);
                stage.centerOnScreen();
                borderPane.setCenter(menuLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if ("Thanh toán".equalsIgnoreCase(side)) {
            FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("../View/CheckOut.fxml"));

            StackPane secondLayout = new StackPane();
            secondLayout.getChildren().add(menuLoader.load());
            Scene secondScene = new Scene(secondLayout);

            Stage newWindow = new Stage();
            newWindow.setTitle("Check Out");
            newWindow.setScene(secondScene);
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.initOwner(stage);

            newWindow.setX(stage.getX() + 0);
            newWindow.setY(stage.getY() + 100);
            newWindow.show();
            newWindow.getIcons().add(new Image("Images/icons8-hotel-check-out-48.png"));
            newWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    Parent root= null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("../View/MenuBarBorderPane.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            });

        } else if ("Quản lý phòng".equalsIgnoreCase(side)) {
            FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("../View/RoomManager.fxml"));
            newPopup("Quản lý phòng", menuLoader);
        } else if ("Quản lý khách hàng".equalsIgnoreCase(side)) {
            FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("../View/CustomerManager.fxml"));
            newPopup("Quản lý khách hàng", menuLoader);
        } else if ("Quản lý nhân viên".equalsIgnoreCase(side)) {
            FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("../View/EmployeeManager.fxml"));
            newPopup("Quản lý nhân viên", menuLoader);
        } else if ("Thông tin".equalsIgnoreCase(side)) {
            FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("../View/InforUser.fxml"));
            StackPane secondLayout = new StackPane();
            secondLayout.getChildren().add(menuLoader.load());
            VBox root = new VBox();
            Button btnLogout = new Button("Logout");
            btnLogout.setFocusTraversable(false);
            btnLogout.setPrefSize(100, 30);
            btnLogout.setStyle("-fx-font-size: 16");
            root.getChildren().add(secondLayout);
            root.getChildren().add(btnLogout);
            root.setAlignment(Pos.CENTER);
            VBox.setMargin(btnLogout, new Insets(5, 10, 10, 10));
            Scene secondScene = new Scene(root);
            Stage newWindow = new Stage();
            newWindow.setTitle("Thông tin tài khoản");
            newWindow.setScene(secondScene);
            Account inforUser = menuLoader.getController();
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.initOwner(stage);
            newWindow.setX(stage.getX() + 200);
            newWindow.setY(stage.getY() + 100);
            newWindow.getIcons().add(new Image("Images/profile.png"));
            inforUser.setInfor(labelUsername.getText());
            newWindow.show();

            btnLogout.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            stage.close();
                            Parent root = null;
                            try {
                                root = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Stage primaryStage = new Stage();
                            primaryStage.setTitle("Login Hotel");
                            primaryStage.getIcons().add(new Image("Images/login.png"));
                            primaryStage.setScene(new Scene(root));
                            primaryStage.show();
                        }
                    });
                }
            });
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("../View/CheckIn.fxml"));
        try {
            borderPane.setCenter(menuLoader.load());
            labelUsername.setVisible(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getusername(String username) {
        labelUsername.setText(username);
        if (checkUser(username)) {
            menuItemQLNV.setVisible(true);
        } else {
            menuItemQLNV.setVisible(false);
        }
    }

    private boolean checkUser(String username) {
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        String query = "SELECT isAdmin FROM [User] WHERE username = '" + username + "'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                return rs.getBoolean("isAdmin");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void newPopup(String title, FXMLLoader menuLoader){
        Stage stage = (Stage) borderPane.getScene().getWindow();
        try {
            StackPane secondLayout = new StackPane();
            secondLayout.getChildren().add(menuLoader.load());
            Scene secondScene = new Scene(secondLayout);

            Stage newWindow = new Stage();
            newWindow.setTitle(title);
            if(title.compareTo("Quản lý phòng") == 0){
                newWindow.getIcons().add(new Image("Images/living-room.png"));
            } else if(title.compareTo("Quản lý khách hàng") == 0){
                newWindow.getIcons().add(new Image("Images/value.png"));
            } else if(title.compareTo("Quản lý nhân viên") == 0){
                newWindow.getIcons().add(new Image("Images/employee.png"));
            }
            newWindow.setScene(secondScene);

            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.initOwner(stage);

            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            newWindow.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            newWindow.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
            newWindow.show();
            newWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    Parent root= null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("../View/MenuBarBorderPane.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
