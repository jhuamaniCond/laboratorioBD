Compilar

```bash
javac --module-path ./javafx-sdk-24.0.1/lib --add-modules javafx.controls,javafx.fxml -cp ".;./mysql-connector-j-9.3.0/mysql-connector-j-9.3.0.jar" *.java
```
Ejecutar solo el main

```bash
java --module-path ./javafx-sdk-24.0.1/lib --add-modules javafx.controls,javafx.fxml -cp ".;./mysql-connector-j-9.3.0/mysql-connector-j-9.3.0.jar" Main
```