import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DataBd implements AutoCloseable {

    private final Connection conn;
    private final DatabaseMetaData metaData;

    public DataBd(String url, String user, String password) throws SQLException {
        this.conn     = DriverManager.getConnection(url, user, password);
        this.metaData = conn.getMetaData();
    }

    public String[] getTableNames() throws SQLException {
        List<String> tablas = new ArrayList<>();

        try (ResultSet rs = metaData.getTables(null, null, "%", new String[] { "TABLE" })) {
            while (rs.next()) {
                tablas.add(rs.getString("TABLE_NAME"));
            }
        }
        return tablas.toArray(new String[0]);
    }

    public String[] getColumnNames(String tableName) throws SQLException {
        List<String> columnas = new ArrayList<>();

        try (ResultSet rs = metaData.getColumns(null, null, tableName, "%")) {
            while (rs.next()) {
                columnas.add(rs.getString("COLUMN_NAME"));
            }
        }
        return columnas.toArray(new String[0]);
    }

    public boolean isForeignKey(String tableName, String columnName) throws SQLException {
        // getImportedKeys devuelve las FKs de la tabla.
        try (ResultSet rs = metaData.getImportedKeys(null, null, tableName)) {
            while (rs.next()) {
                String fkColumn = rs.getString("FKCOLUMN_NAME");
                if (fkColumn.equalsIgnoreCase(columnName)) {
                    return true;
                }
            }
        }
        return false;
    }
    public void insertIntoTable(String tableName, Map<String, String> data) throws SQLException {
        if (data.isEmpty()) return;
        
        String columns = String.join(", ", data.keySet());
        String placeholders = String.join(", ", data.keySet().stream().map(k -> "?").toList());

        String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int index = 1;
            for (String key : data.keySet()) {
                ps.setString(index++, data.get(key));
            }
            ps.executeUpdate();
        }
    }

    public void updateIntoTable(String tableName, Map<String, String> data) throws SQLException {
        if (data.isEmpty()) return;

        String primaryKeyColumn = getPrimaryKeyColumn(tableName);
        if (primaryKeyColumn == null || !data.containsKey(primaryKeyColumn)) {
            System.out.println("No se pudo determinar la clave primaria o no está en los datos.");
            return;
        }

        StringBuilder setClause = new StringBuilder();
        List<String> keys = new ArrayList<>(data.keySet());
        keys.remove(primaryKeyColumn); // no actualizar la PK

        for (int i = 0; i < keys.size(); i++) {
            setClause.append(keys.get(i)).append(" = ?");
            if (i < keys.size() - 1) setClause.append(", ");
        }

        String sql = "UPDATE " + tableName + " SET " + setClause + " WHERE " + primaryKeyColumn + " = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int index = 1;
            for (String key : keys) {
                ps.setString(index++, data.get(key));
            }
            ps.setString(index, data.get(primaryKeyColumn)); // condición WHERE
            ps.executeUpdate();
        }
    }
    public List<Map<String, String>> selectColumns(String tableName, String[] columns,String EstRegColum) throws SQLException {
        List<Map<String, String>> results = new ArrayList<>();
        if (columns.length == 0) return results;

        String columnList = String.join(", ", columns);
        String sql = "SELECT " + columnList + " FROM " + tableName + " WHERE "+EstRegColum+" = 'A'";

        try (PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, String> row = new java.util.HashMap<>();
                for (String col : columns) {
                    row.put(col, rs.getString(col));
                }
                results.add(row);
            }
        }
        return results;
    }
    public String getEstRegColumn(String tableName) throws SQLException {
         for (String column : getColumnNames(tableName)) {
            if (column.toLowerCase().endsWith("estreg")) {
                return column; // Retorna el nombre de la columna que termina con "EstReg"
            }
        }
        return null; // No se encontró una columna que termine con "EstReg"
    }

    public String getPrimaryKeyColumn(String tableName) throws SQLException {
        try (ResultSet rs = metaData.getPrimaryKeys(null, null, tableName)) {
            if (rs.next()) {
                return rs.getString("COLUMN_NAME");
            }
        }
        return null; // No se encontró PK
    }
    public Connection getConnection() {
        return conn;
    }
    @Override
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            System.out.println("Closing connection to the database.");
            conn.close();
        }
        
    }
}
