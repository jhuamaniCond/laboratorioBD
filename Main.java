import java.sql.SQLException;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    private BorderPane rootPane;
    private DataBd db;  
    private String[] tableNames;
    private boolean panelVisible = true;
    private double originalScrollPaneWidth = 348.8;

    @Override
    public void start(Stage stage) {
        String url = "jdbc:mysql://localhost:3306/laboratoriobd";
        String user = "root";
        String pass = "root";
        rootPane = new BorderPane();

        try {
            db = new DataBd(url, user, pass); // 👈 inicializa una vez
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        VBox tableButtons = new VBox(10); // espacio vertical entre botones
        tableButtons.setPadding(new Insets(15));
        tableButtons.setStyle("-fx-background-color: #f0f4f8;");

        ScrollPane scrollPane = new ScrollPane(tableButtons);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setStyle("-fx-background: #dce3ea; -fx-border-color: #ccc;");

        
        try {
            tableNames=db.getTableNames();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        

        for (String tableName : tableNames) {
            String label = toCamelCase(tableName);
            Button tableBtn = new Button(label);

            String finalTableName = tableName; 

            tableBtn.setOnAction(e -> {
                rootPane.setCenter(null);
                try {
                    CrudBuilder builder = new CrudBuilder(db, finalTableName); 
                    
                    VBox layout = new VBox(20,
                        builder.getForm(),
                        builder.getTableWithButtons()
                    );
                    layout.setPadding(new Insets(15));
                    rootPane.setCenter(layout);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });

            tableBtn.setFont(Font.font("Arial", 14));
            tableBtn.setPrefWidth(300);
            tableBtn.setStyle("""
                -fx-background-color: #ffffff;
                -fx-border-color: #cccccc;
                -fx-border-radius: 8;
                -fx-background-radius: 8;
                -fx-padding: 10 15;
                -fx-cursor: hand;
            """);

            // Efecto hover
            tableBtn.setOnMouseEntered(e -> tableBtn.setStyle("""
                -fx-background-color: #e0f0ff;
                -fx-border-color: #3399ff;
                -fx-border-radius: 8;
                -fx-background-radius: 8;
                -fx-padding: 10 15;
                -fx-cursor: hand;
            """));
            tableBtn.setOnMouseExited(e -> tableBtn.setStyle("""
                -fx-background-color: #ffffff;
                -fx-border-color: #cccccc;
                -fx-border-radius: 8;
                -fx-background-radius: 8;
                -fx-padding: 10 15;
                -fx-cursor: hand;
            """));

            tableButtons.getChildren().add(tableBtn);
        }

        HBox mainContent = new HBox(); // contenedor horizontal
        mainContent.setSpacing(0);

        // Botón plegable
        Button toggleBtn = new Button("<");
        toggleBtn.setPrefWidth(20);
        toggleBtn.setFocusTraversable(false);
        toggleBtn.setStyle("""
            -fx-background-color: #e6e6e6;
            -fx-border-color: #cccccc;
            -fx-border-radius: 0;
            -fx-font-weight: bold;
        """);

        // Acción del botón
        toggleBtn.setOnAction(e -> {

            if (panelVisible) {
                
                
                Timeline collapse = new Timeline(
                    new KeyFrame(Duration.ZERO,
                        new KeyValue(scrollPane.prefWidthProperty(), originalScrollPaneWidth),
                        new KeyValue(scrollPane.translateXProperty(), panelVisible ? 0 : 0)
                    ),
                    new KeyFrame(Duration.seconds(0.5),
                        new KeyValue(scrollPane.prefWidthProperty(), 0),
                        new KeyValue(scrollPane.translateXProperty(), panelVisible ? -originalScrollPaneWidth : 0)
                    )
                );
                collapse.setOnFinished(ev -> {
                    scrollPane.setManaged(false);
                    scrollPane.setVisible(false);
                    toggleBtn.setText("▶");
                });
                collapse.play();
            } else {
                scrollPane.setManaged(true);
                scrollPane.setVisible(true);
                Timeline expand = new Timeline(
                    new KeyFrame(Duration.ZERO,
                        new KeyValue(scrollPane.prefWidthProperty(), 0),
                        new KeyValue(scrollPane.translateXProperty(), !panelVisible ? -originalScrollPaneWidth : 0)
                    ),
                    new KeyFrame(Duration.seconds(0.5),
                        new KeyValue(scrollPane.prefWidthProperty(), originalScrollPaneWidth),
                        new KeyValue(scrollPane.translateXProperty(), 0)
                    )
                );
                expand.setOnFinished(ev -> toggleBtn.setText("◀"));
                expand.play();
            }

            panelVisible = !panelVisible;
        });


        mainContent.getChildren().addAll(scrollPane, toggleBtn);


        rootPane.setLeft(mainContent);
        Scene scene = new Scene(rootPane, 800, 600); 
        stage.setScene(scene);
        stage.setTitle("Tablas de la Base de Datos");
        stage.show();
    }

    public static String toCamelCase(String input) {
        StringBuilder result = new StringBuilder();
        for (String part : input.split("_")) {
            if (!part.isEmpty()) {
                result.append(Character.toUpperCase(part.charAt(0)));
                result.append(part.substring(1).toLowerCase());
                result.append(" ");
            }
        }
        return result.toString().trim();
    }
    public static void main(String[] args) {
        launch(args);
        
    }
}
