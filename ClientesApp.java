import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.sql.*;
import java.util.Optional;

public class ClientesApp extends Application {
    // Configuración de conexión a BD
    private static final String URL = "jdbc:mysql://localhost:3306/empresa_distribucion?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "";

    // Componentes de UI
    private TableView<Cliente> table;
    private TextField txtCliEstReg, txtCliCod, txtCliNom, txtCliApePat, txtCliApeMat, txtCliDir, txtCliTel, txtCliCor;
    private TextField txtBusqueda;
    private ComboBox<String> cbEmpresa, cbComportamiento;
    private Button btnAdd, btnMod, btnDel, btnInc, btnRea, btnAct, btnCan, btnSalir, btnBuscar;
    private int flagOp = 0; // 0 = inactivo, 1 = operación pendiente
    private char estadoPendiente = 'A'; // Para manejar cambios de estado

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // ----------------------------
        // 1. Configuración del Formulario
        // ----------------------------
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(15));

        // Campos del formulario
        txtCliCod = new TextField();
        txtCliCod.setPromptText("Código Cliente");
        txtCliNom = new TextField();
        txtCliNom.setPromptText("Nombres");
        txtCliApePat = new TextField();
        txtCliApePat.setPromptText("Apellido Paterno");
        txtCliApeMat = new TextField();
        txtCliApeMat.setPromptText("Apellido Materno");
        txtCliDir = new TextField();
        txtCliDir.setPromptText("Dirección");
        txtCliTel = new TextField();
        txtCliTel.setPromptText("Teléfono");
        txtCliCor = new TextField();
        txtCliCor.setPromptText("Correo");
        
        // Campo Estado
        txtCliEstReg = new TextField("A");
        txtCliEstReg.setPromptText("Estado");
        txtCliEstReg.setDisable(true);
        txtCliEstReg.setPrefWidth(50);

        // Combobox para relaciones
        cbEmpresa = new ComboBox<>();
        cbEmpresa.setPromptText("Seleccione empresa");
        cbComportamiento = new ComboBox<>();
        cbComportamiento.setPromptText("Comportamiento");

        // Campo de búsqueda
        txtBusqueda = new TextField();
        txtBusqueda.setPromptText("Buscar por nombre o código");
        btnBuscar = new Button("Buscar");
        btnBuscar.setOnAction(e -> buscarClientes());

        // Ubicación de los elementos en el grid
        form.add(new Label("Código:"), 0, 0);
        form.add(txtCliCod, 1, 0);
        form.add(new Label("Estado:"), 2, 0);
        form.add(txtCliEstReg, 3, 0);
        
        form.add(new Label("Nombres:"), 0, 1);
        form.add(txtCliNom, 1, 1);
        form.add(new Label("Apellido Paterno:"), 2, 1);
        form.add(txtCliApePat, 3, 1);
        
        form.add(new Label("Apellido Materno:"), 0, 2);
        form.add(txtCliApeMat, 1, 2);
        form.add(new Label("Empresa:"), 2, 2);
        form.add(cbEmpresa, 3, 2);
        
        form.add(new Label("Dirección:"), 0, 3);
        form.add(txtCliDir, 1, 3, 3, 1);
        
        form.add(new Label("Teléfono:"), 0, 4);
        form.add(txtCliTel, 1, 4);
        form.add(new Label("Correo:"), 2, 4);
        form.add(txtCliCor, 3, 4);
        
        form.add(new Label("Comportamiento:"), 0, 5);
        form.add(cbComportamiento, 1, 5);

        // ----------------------------
        // 2. Configuración de la Tabla
        // ----------------------------
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Columnas de la tabla
        TableColumn<Cliente, String> colCod = new TableColumn<>("Código");
        colCod.setCellValueFactory(new PropertyValueFactory<>("cliCod"));

        TableColumn<Cliente, String> colNombre = new TableColumn<>("Nombre Completo");
        colNombre.setCellValueFactory(cell -> 
            cell.getValue().nombreCompletoProperty());

        TableColumn<Cliente, String> colEmpresa = new TableColumn<>("Empresa");
        colEmpresa.setCellValueFactory(new PropertyValueFactory<>("empresa"));

        TableColumn<Cliente, String> colComportamiento = new TableColumn<>("Comportamiento");
        colComportamiento.setCellValueFactory(new PropertyValueFactory<>("comportamiento"));

        TableColumn<Cliente, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("cliEstReg"));

        table.getColumns().addAll(colCod, colNombre, colEmpresa, colComportamiento, colEstado);
        table.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> mostrarClienteSeleccionado(newVal));

        // ----------------------------
        // 3. Configuración de Botones
        // ----------------------------
        btnAdd = new Button("Adicionar");
        btnMod = new Button("Modificar");
        btnDel = new Button("Eliminar");
        btnInc = new Button("Inactivar");
        btnRea = new Button("Reactivar");
        btnAct = new Button("Actualizar");
        btnCan = new Button("Cancelar");
        btnSalir = new Button("Salir");

        // Asignación de eventos
        btnAdd.setOnAction(e -> prepararAdicion());
        btnMod.setOnAction(e -> prepararModificacion());
        btnDel.setOnAction(e -> prepararEliminacion());
        btnInc.setOnAction(e -> prepararEstado('I'));
        btnRea.setOnAction(e -> prepararEstado('A'));
        btnAct.setOnAction(e -> actualizarCliente());
        btnCan.setOnAction(e -> cancelarOperacion());
        btnSalir.setOnAction(e -> stage.close());

        // Organización de botones
        HBox botonesSuperiores = new HBox(10, btnAdd, btnMod, btnDel, btnCan);
        HBox botonesInferiores = new HBox(10, btnInc, btnRea, btnAct, btnSalir);
        botonesSuperiores.setAlignment(Pos.CENTER);
        botonesInferiores.setAlignment(Pos.CENTER);
        VBox panelBotones = new VBox(10, botonesSuperiores, botonesInferiores);
        panelBotones.setPadding(new Insets(10));

        // Panel de búsqueda
        HBox panelBusqueda = new HBox(10, txtBusqueda, btnBuscar);
        panelBusqueda.setAlignment(Pos.CENTER_RIGHT);
        panelBusqueda.setPadding(new Insets(0, 10, 10, 10));

        // ----------------------------
        // 4. Configuración Final de la Ventana
        // ----------------------------
        VBox panelCentral = new VBox(10, form, table);
        panelCentral.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(panelBusqueda);
        root.setCenter(panelCentral);
        root.setBottom(panelBotones);

        Scene scene = new Scene(root, 1000, 650);
        stage.setScene(scene);
        stage.setTitle("Gestión de Clientes");
        stage.show();

        // Cargar datos iniciales
        cargarComboboxes();
        cargarClientes();
        resetearEstado();
    }

    // ----------------------------
    // Métodos de Negocio
    // ----------------------------

    private void cargarComboboxes() {
        // Cargar empresas
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT EmpCod, EmpNom FROM EMPRESA_CLIENTE WHERE EmpEstReg = 'A'")) {
            
            cbEmpresa.getItems().clear();
            while (rs.next()) {
                cbEmpresa.getItems().add(rs.getString("EmpCod") + " - " + rs.getString("EmpNom"));
            }
        } catch (SQLException e) {
            mostrarError("Error al cargar empresas: " + e.getMessage());
        }

        // Cargar comportamientos
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT ComCliCod, ComCliDes FROM COMPORTAMIENTO_CLIENTE WHERE ComCliEstReg = 'A'")) {
            
            cbComportamiento.getItems().clear();
            while (rs.next()) {
                cbComportamiento.getItems().add(rs.getString("ComCliCod") + " - " + rs.getString("ComCliDes"));
            }
        } catch (SQLException e) {
            mostrarError("Error al cargar comportamientos: " + e.getMessage());
        }
    }

    private void cargarClientes() {
        ObservableList<Cliente> clientes = FXCollections.observableArrayList();
        String sql = "SELECT c.*, e.EmpNom, com.ComCliDes FROM CLIENTE c " +
                     "LEFT JOIN EMPRESA_CLIENTE e ON c.CliEmpCod = e.EmpCod " +
                     "LEFT JOIN COMPORTAMIENTO_CLIENTE com ON c.CliComCliCod = com.ComCliCod " +
                     "WHERE c.CliEstReg != '*' " +
                     "ORDER BY c.CliApePat, c.CliApeMat, c.CliNom";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                clientes.add(new Cliente(
                    rs.getString("CliCod"),
                    rs.getString("CliNom"),
                    rs.getString("CliApePat"),
                    rs.getString("CliApeMat"),
                    rs.getString("CliDir"),
                    rs.getString("CliTel"),
                    rs.getString("CliCor"),
                    rs.getString("CliEmpCod"),
                    rs.getString("EmpNom"),
                    rs.getString("CliComCliCod"),
                    rs.getString("ComCliDes"),
                    rs.getString("CliEstReg")
                ));
            }
        } catch (SQLException e) {
            mostrarError("Error al cargar clientes: " + e.getMessage());
        }

        table.setItems(clientes);
    }

    private void buscarClientes() {
        String criterio = txtBusqueda.getText().trim();
        if (criterio.isEmpty()) {
            cargarClientes();
            return;
        }

        ObservableList<Cliente> clientes = FXCollections.observableArrayList();
        String sql = "SELECT c.*, e.EmpNom, com.ComCliDes FROM CLIENTE c " +
                     "LEFT JOIN EMPRESA_CLIENTE e ON c.CliEmpCod = e.EmpCod " +
                     "LEFT JOIN COMPORTAMIENTO_CLIENTE com ON c.CliComCliCod = com.ComCliCod " +
                     "WHERE c.CliEstReg != '*' AND " +
                     "(c.CliCod LIKE ? OR c.CliNom LIKE ? OR c.CliApePat LIKE ? OR c.CliApeMat LIKE ?) " +
                     "ORDER BY c.CliApePat, c.CliApeMat, c.CliNom";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String parametroBusqueda = "%" + criterio + "%";
            for (int i = 1; i <= 4; i++) {
                stmt.setString(i, parametroBusqueda);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                clientes.add(new Cliente(
                    rs.getString("CliCod"),
                    rs.getString("CliNom"),
                    rs.getString("CliApePat"),
                    rs.getString("CliApeMat"),
                    rs.getString("CliDir"),
                    rs.getString("CliTel"),
                    rs.getString("CliCor"),
                    rs.getString("CliEmpCod"),
                    rs.getString("EmpNom"),
                    rs.getString("CliComCliCod"),
                    rs.getString("ComCliDes"),
                    rs.getString("CliEstReg")
                ));
            }
        } catch (SQLException e) {
            mostrarError("Error al buscar clientes: " + e.getMessage());
        }

        table.setItems(clientes);
    }

    private void prepararAdicion() {
        flagOp = 1;
        estadoPendiente = 'A';
        limpiarFormulario();
        txtCliCod.setDisable(false);
        txtCliCod.requestFocus();
        configurarBotones(false, true);
    }

    private void prepararEstado(char nuevoEstado) {
        Cliente seleccionado = table.getSelectionModel().getSelectedItem();
        
        if (seleccionado == null) {
            mostrarError("Debe seleccionar un cliente de la tabla");
            return;
        }

        String mensajeConfirmacion = "";
        switch (nuevoEstado) {
            case 'I':
                mensajeConfirmacion = "¿Está seguro de inactivar este cliente?";
                break;
            case '*':
                mensajeConfirmacion = "¿Está seguro de eliminar lógicamente este cliente?";
                break;
            case 'A':
                mensajeConfirmacion = "¿Está seguro de reactivar este cliente?";
                break;
        }

        if (!confirmarAccion(mensajeConfirmacion)) {
            return;
        }

        // Configurar estado
        estadoPendiente = nuevoEstado;
        flagOp = 1;
        
        // Bloquear campos para cambio de estado
        bloquearCamposFormulario(true);
        txtCliEstReg.setText(String.valueOf(nuevoEstado));
        
        String mensaje = "";
        switch (nuevoEstado) {
            case 'I':
                mensaje = "Cliente marcado para inactivar";
                break;
            case '*':
                mensaje = "Cliente marcado para eliminación lógica";
                break;
            case 'A':
                mensaje = "Cliente marcado para reactivación";
                break;
        }
        
        mostrarMensaje(mensaje + " (presione Actualizar para confirmar)");
        configurarBotones(false, true);
    }

    private void prepararModificacion() {
        Cliente seleccionado = table.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarError("Seleccione un cliente para modificar");
            return;
        }
        
        flagOp = 1;
        estadoPendiente = 'M'; // Modificación
        txtCliCod.setDisable(true);
        bloquearCamposFormulario(false);
        txtCliEstReg.setDisable(true);
        configurarBotones(false, true);
    }

    private void prepararEliminacion() {
        prepararEstado('*');
    }

    private void actualizarCliente() {
        if (flagOp != 1) return;

        // Validar campos obligatorios
        if (!validarCampos()) {
            return;
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            String sql;
            boolean esNuevo = !txtCliCod.isDisabled() && estadoPendiente == 'A';

            if (esNuevo) {
                // Verificar que no exista el código
                if (existeCliente(txtCliCod.getText())) {
                    mostrarError("Ya existe un cliente con este código");
                    return;
                }
                
                sql = "INSERT INTO CLIENTE (CliCod, CliNom, CliApePat, CliApeMat, CliEmpCod, " +
                      "CliDir, CliTel, CliCor, CliComCliCod, CliEstReg) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'A')";
            } else {
                // Actualización o cambio de estado
                sql = "UPDATE CLIENTE SET CliNom=?, CliApePat=?, CliApeMat=?, CliEmpCod=?, " +
                      "CliDir=?, CliTel=?, CliCor=?, CliComCliCod=?, CliEstReg=? WHERE CliCod=?";
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                String codEmpresa = extraerCodigo(cbEmpresa.getValue());
                String codComportamiento = extraerCodigo(cbComportamiento.getValue());

                if (esNuevo) {
                    stmt.setString(1, txtCliCod.getText().trim());
                    stmt.setString(2, txtCliNom.getText().trim());
                    stmt.setString(3, txtCliApePat.getText().trim());
                    stmt.setString(4, txtCliApeMat.getText().trim());
                    stmt.setString(5, codEmpresa);
                    stmt.setString(6, txtCliDir.getText().trim());
                    stmt.setString(7, txtCliTel.getText().trim());
                    stmt.setString(8, txtCliCor.getText().trim());
                    stmt.setString(9, codComportamiento);
                } else {
                    stmt.setString(1, txtCliNom.getText().trim());
                    stmt.setString(2, txtCliApePat.getText().trim());
                    stmt.setString(3, txtCliApeMat.getText().trim());
                    stmt.setString(4, codEmpresa);
                    stmt.setString(5, txtCliDir.getText().trim());
                    stmt.setString(6, txtCliTel.getText().trim());
                    stmt.setString(7, txtCliCor.getText().trim());
                    stmt.setString(8, codComportamiento);
                    // Aplicar el estado pendiente
                    stmt.setString(9, String.valueOf(estadoPendiente == 'M' ? 'A' : estadoPendiente));
                    stmt.setString(10, txtCliCod.getText().trim());
                }

                int filasAfectadas = stmt.executeUpdate();
                if (filasAfectadas > 0) {
                    mostrarMensaje("Cliente " + (esNuevo ? "agregado" : "actualizado") + " exitosamente");
                    cargarClientes();
                    resetearEstado();
                } else {
                    mostrarError("No se pudo realizar la operación");
                }
            }
        } catch (SQLException e) {
            mostrarError("Error al guardar cliente: " + e.getMessage());
        }
    }

    private boolean validarCampos() {
        if (txtCliCod.getText().trim().isEmpty()) {
            mostrarError("El código del cliente es obligatorio");
            txtCliCod.requestFocus();
            return false;
        }
        
        if (txtCliNom.getText().trim().isEmpty()) {
            mostrarError("El nombre es obligatorio");
            txtCliNom.requestFocus();
            return false;
        }
        
        if (txtCliApePat.getText().trim().isEmpty()) {
            mostrarError("El apellido paterno es obligatorio");
            txtCliApePat.requestFocus();
            return false;
        }
        
        if (cbEmpresa.getValue() == null) {
            mostrarError("Debe seleccionar una empresa");
            cbEmpresa.requestFocus();
            return false;
        }
        
        if (cbComportamiento.getValue() == null) {
            mostrarError("Debe seleccionar un comportamiento");
            cbComportamiento.requestFocus();
            return false;
        }
        
        // Validar email si se proporciona
        if (!txtCliCor.getText().trim().isEmpty() && !esEmailValido(txtCliCor.getText().trim())) {
            mostrarError("El formato del correo electrónico no es válido");
            txtCliCor.requestFocus();
            return false;
        }
        
        return true;
    }

    private boolean existeCliente(String codigo) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM CLIENTE WHERE CliCod = ?")) {
            
            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            mostrarError("Error al verificar cliente: " + e.getMessage());
            return true; // En caso de error, asumimos que existe para evitar duplicados
        }
    }

    private boolean esEmailValido(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$");
    }

    private String extraerCodigo(String comboValue) {
        if (comboValue == null || comboValue.isEmpty()) {
            return "";
        }
        String[] partes = comboValue.split(" - ");
        return partes.length > 0 ? partes[0] : "";
    }

    private void mostrarClienteSeleccionado(Cliente cliente) {
        if (cliente != null && flagOp == 0) {
            txtCliCod.setText(cliente.getCliCod());
            txtCliNom.setText(cliente.getCliNom());
            txtCliApePat.setText(cliente.getCliApePat());
            txtCliApeMat.setText(cliente.getCliApeMat());
            txtCliDir.setText(cliente.getCliDir());
            txtCliTel.setText(cliente.getCliTel());
            txtCliCor.setText(cliente.getCliCor());
            txtCliEstReg.setText(cliente.getCliEstReg());
            
            // Seleccionar en comboboxes
            seleccionarEnCombo(cbEmpresa, cliente.getCliEmpCod());
            seleccionarEnCombo(cbComportamiento, cliente.getCliComCliCod());
        }
    }

    private void seleccionarEnCombo(ComboBox<String> combo, String codigo) {
        if (codigo != null && !codigo.isEmpty()) {
            for (String item : combo.getItems()) {
                if (item.startsWith(codigo + " - ")) {
                    combo.setValue(item);
                    break;
                }
            }
        }
    }

    private void cancelarOperacion() {
        flagOp = 0;
        estadoPendiente = 'A';
        limpiarFormulario();
        bloquearCamposFormulario(false);
        configurarBotones(true, false);
    }

    private void resetearEstado() {
        flagOp = 0;
        estadoPendiente = 'A';
        limpiarFormulario();
        bloquearCamposFormulario(false);
        configurarBotones(true, false);
        txtCliCod.setDisable(false);
    }

    private void bloquearCamposFormulario(boolean bloquear) {
        txtCliNom.setDisable(bloquear);
        txtCliApePat.setDisable(bloquear);
        txtCliApeMat.setDisable(bloquear);
        txtCliDir.setDisable(bloquear);
        txtCliTel.setDisable(bloquear);
        txtCliCor.setDisable(bloquear);
        cbEmpresa.setDisable(bloquear);
        cbComportamiento.setDisable(bloquear);
    }

    private void limpiarFormulario() {
        txtCliCod.clear();
        txtCliNom.clear();
        txtCliApePat.clear();
        txtCliApeMat.clear();
        txtCliDir.clear();
        txtCliTel.clear();
        txtCliCor.clear();
        cbEmpresa.getSelectionModel().clearSelection();
        cbComportamiento.getSelectionModel().clearSelection();
        txtCliEstReg.setText("A");
        txtCliEstReg.setDisable(true);
    }

    private void configurarBotones(boolean operaciones, boolean actualizacion) {
        btnAdd.setDisable(!operaciones);
        btnMod.setDisable(!operaciones);
        btnDel.setDisable(!operaciones);
        btnInc.setDisable(!operaciones);
        btnRea.setDisable(!operaciones);
        btnSalir.setDisable(actualizacion);
        btnAct.setDisable(!actualizacion);
        btnCan.setDisable(!actualizacion);
    }

    private boolean confirmarAccion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar acción");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        
        Optional<ButtonType> resultado = alert.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private boolean confirmar(String mensaje) {
        return confirmarAccion(mensaje);
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    

    // ----------------------------
    // Clase Modelo Cliente (Actualizada)
    // ----------------------------
    public static class Cliente {
        private final String cliCod;
        private final String cliNom;
        private final String cliApePat;
        private final String cliApeMat;
        private final String cliDir;
        private final String cliTel;
        private final String cliCor;
        private final String cliEmpCod;
        private final String empresa;
        private final String cliComCliCod;
        private final String comportamiento;
        private final String cliEstReg;

        public Cliente(String cliCod, String cliNom, String cliApePat, String cliApeMat,
                      String cliDir, String cliTel, String cliCor, String cliEmpCod, 
                      String empresa, String cliComCliCod, String comportamiento, String cliEstReg) {
            this.cliCod = cliCod;
            this.cliNom = cliNom;
            this.cliApePat = cliApePat;
            this.cliApeMat = cliApeMat;
            this.cliDir = cliDir;
            this.cliTel = cliTel;
            this.cliCor = cliCor;
            this.cliEmpCod = cliEmpCod;
            this.empresa = empresa;
            this.cliComCliCod = cliComCliCod;
            this.comportamiento = comportamiento;
            this.cliEstReg = cliEstReg;
        }

        // Getters
        public String getCliCod() { return cliCod; }
        public String getCliNom() { return cliNom; }
        public String getCliApePat() { return cliApePat; }
        public String getCliApeMat() { return cliApeMat; }
        public String getCliDir() { return cliDir; }
        public String getCliTel() { return cliTel; }
        public String getCliCor() { return cliCor; }
        public String getCliEmpCod() { return cliEmpCod; }
        public String getEmpresa() { return empresa; }
        public String getCliComCliCod() { return cliComCliCod; }
        public String getComportamiento() { return comportamiento; }
        public String getCliEstReg() { return cliEstReg; }

        // Propiedad calculada para la tabla
        public StringProperty nombreCompletoProperty() {
            String nombreCompleto = (cliNom != null ? cliNom : "") + " " + 
                                   (cliApePat != null ? cliApePat : "") + " " + 
                                   (cliApeMat != null ? cliApeMat : "");
            return new SimpleStringProperty(nombreCompleto.trim());
        }
    }
}
