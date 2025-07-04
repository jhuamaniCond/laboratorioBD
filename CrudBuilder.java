import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.sql.*;
import java.util.*;

public class CrudBuilder {

    private final DataBd db;
    private final String tableName;
    private final VBox formVBox = new VBox(10);
    private final TableView<Map<String, String>> table = new TableView<>();
    private final Map<String, TextField> fieldInputs = new LinkedHashMap<>();

    private final Button btnAdd = new Button("Adicionar");
    private final Button btnMod = new Button("Modificar");
    private final Button btnDel = new Button("Eliminar");
    private final Button btnInc = new Button("Inactivar");
    private final Button btnRea = new Button("Reactivar");
    private final Button btnAct = new Button("Actualizar");
    private final Button btnCan = new Button("Cancelar");
    private final Button btnSalir = new Button("Salir");

    private Diccionario diccionario = new Diccionario();

    private int flagAction = 0;

    public CrudBuilder(DataBd db, String tableName) throws SQLException {
        this.db = db;
        this.tableName = tableName;
        buildForm();
        buildTable();
        setupButtonActions();
    }

    private void buildForm() throws SQLException {
        formVBox.getChildren().clear();
        fieldInputs.clear();

        GridPane formGrid = new GridPane();
        formGrid.setHgap(20);
        formGrid.setVgap(10);

        int row = 0;
        int col = 0;

        for (String column : db.getColumnNames(tableName)) {

            String traduccionAtributo= diccionario.getTraduccion(tableName,column);

            Label label = new Label(traduccionAtributo);
            TextField textField = new TextField();
            textField.setPromptText(traduccionAtributo);
            if (column.toLowerCase().endsWith("estreg")) {
                textField.setText("A");
                textField.setPrefColumnCount(1);
                textField.setMaxWidth(25); // espacio para un carácter
                textField.setDisable(true);
            }
            
            HBox fieldBox = new HBox(10, label, textField);

            formGrid.add(fieldBox, col, row);
            fieldInputs.put(column, textField);

            row++;
            if (row == 4) {
                row = 0;
                col++;
            }
        }

        TitledPane tpForm = new TitledPane("Registro de " + tableName, formGrid);
        tpForm.setCollapsible(false);

        formVBox.getChildren().add(tpForm);
    }

    private void buildTable() throws SQLException {
        table.getColumns().clear();
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.setPrefHeight(400);

        String[] columns = db.getColumnNames(tableName);
        int columnCount = columns.length;

        for (String column : columns) {
            TableColumn<Map<String, String>, String> col = new TableColumn<>(column);
            col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(column)));

            if (columnCount <= 4) {
                col.prefWidthProperty().bind(table.widthProperty().divide(columnCount));
            } else {
                col.setMinWidth(150);
            }

            table.getColumns().add(col);
        }

        loadData();
    }
    private void loadData() {
        ObservableList<Map<String, String>> rows = FXCollections.observableArrayList();
        String sql = "SELECT * FROM " + tableName;
        try {
            Connection conn = db.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            ResultSetMetaData rsMeta = rs.getMetaData();
            while (rs.next()) {
                Map<String, String> row = new LinkedHashMap<>();
                for (int i = 1; i <= rsMeta.getColumnCount(); i++) {
                    row.put(rsMeta.getColumnName(i), rs.getString(i));
                }
                rows.add(row);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.setItems(rows);
    }

    private VBox buildActionButtons() {
        HBox row1 = new HBox(10, btnAdd, btnMod, btnDel, btnCan);
        row1.setAlignment(Pos.CENTER);
        HBox row2 = new HBox(10, btnInc, btnRea, btnAct, btnSalir);
        row2.setAlignment(Pos.CENTER);

        VBox buttons = new VBox(10, row1, row2);
        buttons.setPadding(new Insets(10));
        return buttons;
    }
    private String getEstadoColumnName() {
        return fieldInputs.keySet()
                .stream()
                .filter(name -> name.toLowerCase().endsWith("estreg"))
                .findFirst()
                .orElse(null);
    }
    private void setupButtonActions() {
        btnAdd.setOnAction(e -> prepareAdd());
        btnMod.setOnAction(e -> prepareModify());
        btnDel.setOnAction(e -> prepareState('*'));
        btnInc.setOnAction(e -> prepareState('I'));
        btnRea.setOnAction(e -> prepareState('A'));
        btnAct.setOnAction(e -> doUpdate());
        btnCan.setOnAction(e -> cancelOp());
        btnSalir.setOnAction(e -> System.exit(0));
    }
    
    private void prepareAdd() {

        flagAction = 1;
        clearForm();

        setFormEnabled(true, Arrays.asList("estreg"));;
        setButtons(false, true);
    }

    private void prepareModify() {
        try {
            if (table.getSelectionModel().getSelectedItem() == null) {
                showAlert("Por favor, seleccione un registro para modificar.","selección requerida");
                return;
            }
        } catch (Exception e) {
            System.out.println("Error al seleccionar el registro: " + e.getMessage());
            return;
        }

        flagAction = 1;
        String primaryKey;
        try{
            primaryKey = db.getPrimaryKeyColumn(tableName);
        } catch (SQLException e) {
            System.out.println("Error al obtener la clave primaria: " + e.getMessage());
            primaryKey="cod";
            return;
        }
        
        setFormEnabled(true, Arrays.asList("estreg",primaryKey));;
        moveAtributesToForm();
        setButtons(false, true);

    }
    private void moveAtributesToForm() {
        Map<String, String> sel = table.getSelectionModel().getSelectedItem();
        if (sel == null) return;

        for (Map.Entry<String, TextField> entry : fieldInputs.entrySet()) {
            String column = entry.getKey();
            TextField field = entry.getValue();
            String value = sel.get(column);
            field.setText(value != null ? value : "");
        }
    }

    private void prepareState(char st) {
        Map<String, String> sel = table.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        flagAction = 1;
        setFormEnabled(false,new ArrayList<>());//todos se inhabilitan
        moveAtributesToForm();
        String estadoColumn = getEstadoColumnName();
        
        if (fieldInputs.containsKey(estadoColumn)) {
            fieldInputs.get(estadoColumn).setText(String.valueOf(st));
            fieldInputs.get(estadoColumn).setDisable(true);
        }
        setButtons(false, true);
    }

    private void doUpdate() {
        if (flagAction != 1) return;
        boolean hayVacios = fieldInputs.values().stream()
        .anyMatch(tf -> tf.getText().trim().isEmpty());

        if (hayVacios) {
            showAlert("Por favor, complete todos los campos antes de agregar un nuevo registro.","información incompleta");
            return; 
        }
        try {
            // Recolectar los datos del formulario
            Map<String, String> data = new LinkedHashMap<>();
            for (Map.Entry<String, TextField> entry : fieldInputs.entrySet()) {
                data.put(entry.getKey(), entry.getValue().getText());
            }

            String primaryKey = db.getPrimaryKeyColumn(tableName);

            if (primaryKey == null) {
                System.out.println("No se pudo detectar la clave primaria de la tabla " + tableName);
                return;
            }

            if (fieldInputs.get(primaryKey).isDisabled()) {
                // UPDATE
                db.updateIntoTable(tableName, data);
            } else {
                // INSERT
                db.insertIntoTable(tableName, data);
            }

        } catch (Exception ex) {
            System.out.println("Error al insertar/actualizar: " + ex.getMessage());
        }

        flagAction = 0;
        resetState();
        loadData();
    }

    private void cancelOp() {
        flagAction = 0;
        resetState();
    }

    private void resetState() {
        setFormEnabled(true, Arrays.asList("estreg"));
        clearForm();
        setButtons(true, false);
    }

    private void clearForm(){
        for (Map.Entry<String, TextField> entry : fieldInputs.entrySet()) {
            String col = entry.getKey();
            TextField field = entry.getValue();
            
            if (col.toLowerCase().endsWith("estreg")) {
                field.setText("A"); // siempre con "A" para activo
            } else {
                field.clear();
            }
        }
    }
    private void setFormEnabled(boolean enabled, List<String> alwaysDisabledSuffixes) {
        for (Map.Entry<String, TextField> entry : fieldInputs.entrySet()) {
            String col = entry.getKey();
            TextField field = entry.getValue();

            boolean forceDisable = false;
            for (String suffix : alwaysDisabledSuffixes) {
                if (col.toLowerCase().endsWith(suffix.toLowerCase())) {
                    forceDisable = true;
                    break;
                }
            }

            if (forceDisable) {
                field.setDisable(true);
            } else {
                field.setDisable(!enabled);
            }
        }
    }

    private void setButtons(boolean ops, boolean upd) {
        btnAdd.setDisable(!ops);
        btnMod.setDisable(!ops);
        btnDel.setDisable(!ops);
        btnInc.setDisable(!ops);
        btnRea.setDisable(!ops);
        btnSalir.setDisable(upd);
        btnAct.setDisable(!upd);
        btnCan.setDisable(!upd);
    }
    public VBox getForm() {
        return formVBox;
    }

    public VBox getTableWithButtons() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TitledPane tpTable = new TitledPane("Tabla " + tableName, table);
        tpTable.setCollapsible(false);

        layout.getChildren().addAll(tpTable, buildActionButtons());
        return layout;
    }
    private void showAlert(String message,String title) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setHeaderText(title);
        a.setContentText(message);
        a.showAndWait();
    }
    public Map<String, TextField> getFieldInputs() {
        return fieldInputs;
    }
} 
