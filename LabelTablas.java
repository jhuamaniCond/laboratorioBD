import java.util.HashMap;
import java.util.Map;

public class LabelTablas {
    private final Map<String, String> labels;

    public LabelTablas() {
        labels = new HashMap<>();

        // Agrega aquí los mappings tabla → atributo label
        labels.put("usuarios", "nombre");
        labels.put("productos", "nombre_producto");
        labels.put("categorias", "descripcion");
        labels.put("empleados", "apellido");

    }

    // Método para obtener el label de una tabla
    public String getLabelAttribute(String tableName) {
        return labels.get(tableName);
    }

    // También puedes exponer todo el mapa si lo necesitas
    public Map<String, String> getLabels() {
        return labels;
    }
}
