import java.util.HashMap;
import java.util.Map;

public class Diccionario {
    private final Map<String, Map<String, String>> etiquetas;

    public Diccionario() {
        etiquetas = new HashMap<>();

        // EMPRESA_CLIENTE
        Map<String, String> empresa_cliente = new HashMap<>();
        empresa_cliente.put("EmpCod", "Código");
        empresa_cliente.put("EmpRUC", "RUC");
        empresa_cliente.put("EmpNom", "Nombre");
        empresa_cliente.put("EmpEstReg", "Estado Registro");
        etiquetas.put("EMPRESA_CLIENTE", empresa_cliente);
        
        // CLIENTE
        Map<String, String> cliente = new HashMap<>();
        cliente.put("CliCod", "Código");
        cliente.put("CliEmpCod", "Empresa");
        cliente.put("CliNom", "Nombre");
        cliente.put("CliApePat", "Apellido Paterno");
        cliente.put("CliApeMat", "Apellido Materno");
        cliente.put("CliDir", "Dirección");
        cliente.put("CliTel", "Teléfono");
        cliente.put("CliCor", "Correo");
        cliente.put("CliComCliCod", "Comportamiento Cliente");
        cliente.put("CliUsuCod", "Usuario");
        cliente.put("CliEstReg", "Estado Registro");
        etiquetas.put("CLIENTE", cliente);

        // CATEGORIA_CLIENTE
        Map<String, String> categoria_cliente = new HashMap<>();
        categoria_cliente.put("CatCod", "Código");
        categoria_cliente.put("CatNom", "Nombre");
        categoria_cliente.put("CatEstReg", "Estado Registro");
        etiquetas.put("CATEGORIA_CLIENTE", categoria_cliente);

        // COMPRAS_CLIENTE
        Map<String, String> compras_cliente = new HashMap<>();
        compras_cliente.put("ComCod", "Código");
        compras_cliente.put("ComCliCod", "Cliente");
        compras_cliente.put("ComAño", "Año");
        compras_cliente.put("ComMes", "Mes");
        compras_cliente.put("ComTot", "Total");
        compras_cliente.put("ComTotAcu", "Total Acumulado");
        compras_cliente.put("ComEscCreCod", "Escala Crédito");
        compras_cliente.put("ComDeu", "Deuda");
        compras_cliente.put("ComEstReg", "Estado Registro");
        etiquetas.put("COMPRAS_CLIENTE", compras_cliente);

        // OFICINA
        Map<String, String> oficina = new HashMap<>();
        oficina.put("OfiCod", "Código");
        oficina.put("OfiCiuCod", "Ciudad");
        oficina.put("OfiDir", "Dirección");
        oficina.put("OfiProCod", "Producto");
        oficina.put("OfiEstReg", "Estado Registro");
        etiquetas.put("OFICINA", oficina);

        // USUARIO
        Map<String, String> usuario = new HashMap<>();
        usuario.put("UsuCod", "Código");
        usuario.put("UsuNom", "Nombre");
        usuario.put("UsuCon", "Contraseña");
        usuario.put("UsuRol", "Rol");
        usuario.put("UsuEstReg", "Estado Registro");
        etiquetas.put("USUARIO", usuario);

        // CARGO
        Map<String, String> cargo = new HashMap<>();
        cargo.put("CarCod", "Código");
        cargo.put("CarDes", "Descripción");
        cargo.put("CarEstReg", "Estado Registro");
        etiquetas.put("CARGO", cargo);

        // AUDITORIA
        Map<String, String> auditoria = new HashMap<>();
        auditoria.put("AudCod", "Código");
        auditoria.put("AudUsu", "Usuario");
        auditoria.put("AudEstReg", "Estado Registro");
        etiquetas.put("AUDITORIA", auditoria);

        // AUDITORIA_ACCION
        Map<String, String> auditoria_accion = new HashMap<>();
        auditoria_accion.put("AudAccCod", "Código");
        auditoria_accion.put("AudCod", "Auditoría");
        auditoria_accion.put("AudAccDesc", "Descripción Acción");
        auditoria_accion.put("AudAccAño", "Año");
        auditoria_accion.put("AudAccMes", "Mes");
        auditoria_accion.put("AudAccDia", "Día");
        auditoria_accion.put("AudAccHor", "Hora");
        auditoria_accion.put("AudAccMin", "Minuto");
        auditoria_accion.put("AudAccSeg", "Segundo");
        auditoria_accion.put("AudAccEstReg", "Estado Registro");
        etiquetas.put("AUDITORIA_ACCION", auditoria_accion);

        // REPRESENTANTE_VENTA
        Map<String, String> representante_venta = new HashMap<>();
        representante_venta.put("RepCod", "Código");
        representante_venta.put("RepNom", "Nombre");
        representante_venta.put("RepEdad", "Edad");
        representante_venta.put("RepSue", "Sueldo");
        representante_venta.put("RepCarCod", "Cargo");
        representante_venta.put("RepConAño", "Año Contrato");
        representante_venta.put("RepConMes", "Mes Contrato");
        representante_venta.put("RepConDia", "Día Contrato");
        representante_venta.put("RepProCod", "Proyeccion Ventas");
        representante_venta.put("RepVenOfiCod", "Oficina");
        representante_venta.put("RepUsuCod", "Usuario");
        representante_venta.put("RepEstReg", "Estado Registro");
        etiquetas.put("REPRESENTANTE_VENTA", representante_venta);

        // PROVECCION_VENTAS
        Map<String, String> proveccion_ventas = new HashMap<>();
        proveccion_ventas.put("ProVenCod", "Código");
        proveccion_ventas.put("ProVenObj", "Objetivo");
        proveccion_ventas.put("ProVenCon", "Conseguido");
        proveccion_ventas.put("ProVenAño", "Año");
        proveccion_ventas.put("ProVenMes", "Mes");
        proveccion_ventas.put("ProEstReg", "Estado Registro");
        etiquetas.put("PROVECCION_VENTAS", proveccion_ventas);

        // VENTAS
        Map<String, String> ventas = new HashMap<>();
        ventas.put("VenCod", "Código");
        ventas.put("VenAcuTot", "Total Acumulado");
        ventas.put("VenEscBonCod", "Escala Bonificación");
        ventas.put("VenRepCod", "Representante");
        ventas.put("VenAño", "Año");
        ventas.put("VenMes", "Mes");
        ventas.put("VenEstReg", "Estado Registro");
        etiquetas.put("VENTAS", ventas);

        // BONIFICACION_VENTAS
        Map<String, String> bonificacion_ventas = new HashMap<>();
        bonificacion_ventas.put("BonVenCod", "Código");
        bonificacion_ventas.put("EscBonCod", "Escala Bonificación");
        bonificacion_ventas.put("BonCan", "Cantidad");
        bonificacion_ventas.put("BonEstReg", "Estado Registro");
        etiquetas.put("BONIFICACION_VENTAS", bonificacion_ventas);

        // FACTURA_CABECERA
        Map<String, String> factura_cabecera = new HashMap<>();
        factura_cabecera.put("FacCod", "Código");
        factura_cabecera.put("FacAño", "Año");
        factura_cabecera.put("FacMes", "Mes");
        factura_cabecera.put("FacDia", "Día");
        factura_cabecera.put("FacPedCod", "Pedido");
        factura_cabecera.put("FacTipPagCod", "Tipo Pago");
        factura_cabecera.put("FacEstReg", "Estado Registro");
        etiquetas.put("FACTURA_CABECERA", factura_cabecera);

        // PEDIDO
        Map<String, String> pedido = new HashMap<>();
        pedido.put("PedCod", "Código");
        pedido.put("PedCli", "Cliente");
        pedido.put("PedRep", "Representante");
        pedido.put("PedAñoCre", "Año Creación");
        pedido.put("PedMesCre", "Mes Creación");
        pedido.put("PedDiaCre", "Día Creación");
        pedido.put("PedEnvCod", "Envío");
        pedido.put("PedEstReg", "Estado Registro");
        etiquetas.put("PEDIDO", pedido);

        // ENVIO
        Map<String, String> envio = new HashMap<>();
        envio.put("EnvCod", "Código");
        envio.put("EnvTipCod", "Tipo Envío");
        envio.put("EnvCos", "Costo");
        envio.put("EnvEst", "Estado Envío");
        envio.put("EnvCiuCod", "Ciudad");
        envio.put("EnvAñoEnt", "Año Entrega");
        envio.put("EnvMesEnt", "Mes Entrega");
        envio.put("EnvDiaEnt", "Día Entrega");
        envio.put("EnvEstReg", "Estado Registro");
        etiquetas.put("ENVIO", envio);

        // TIPO_ENVIO
        Map<String, String> tipo_envio = new HashMap<>();
        tipo_envio.put("TipCod", "Código");
        tipo_envio.put("TipDes", "Descripción");
        tipo_envio.put("TipEstReg", "Estado Registro");
        etiquetas.put("TIPO_ENVIO", tipo_envio);

        // PRODUCTO
        Map<String, String> producto = new HashMap<>();
        producto.put("ProCod", "Código");
        producto.put("ProFabCod", "Fabricante");
        producto.put("ProDes", "Descripción");
        producto.put("ProPre", "Precio");
        producto.put("ProClaProCod", "Clasificación Producto");
        producto.put("ProUniMedCod", "Unidad Medida");
        producto.put("ProAlmCod", "Almacén");
        producto.put("ProEstReg", "Estado Registro");
        etiquetas.put("PRODUCTO", producto);

        // FABRICANTE
        Map<String, String> fabricante = new HashMap<>();
        fabricante.put("FabCod", "Código");
        fabricante.put("FabNom", "Nombre");
        fabricante.put("FabDir", "Dirección");
        fabricante.put("FabEstReg", "Estado Registro");
        etiquetas.put("FABRICANTE", fabricante);

        // FACTURA_DETALLE
        Map<String, String> factura_detalle = new HashMap<>();
        factura_detalle.put("FacDetCod", "Código");
        factura_detalle.put("FacCod", "Factura");
        factura_detalle.put("DetProCod", "Producto");
        factura_detalle.put("DetCan", "Cantidad");
        factura_detalle.put("DetPre", "Precio");
        factura_detalle.put("DetEstReg", "Estado Registro");
        etiquetas.put("FACTURA_DETALLE", factura_detalle);

        // ALMACEN
        Map<String, String> almacen = new HashMap<>();
        almacen.put("AlmCod", "Código");
        almacen.put("AlmNom", "Nombre");
        almacen.put("AlmCosMes", "Costo Mensual");
        almacen.put("AlmEstReg", "Estado Registro");
        etiquetas.put("ALMACEN", almacen);

        // TIPO_PAGO
        Map<String, String> tipo_pago = new HashMap<>();
        tipo_pago.put("TipPagCod", "Código");
        tipo_pago.put("TipPagNom", "Nombre");
        tipo_pago.put("TipPagEstReg", "Estado Registro");
        etiquetas.put("TIPO_PAGO", tipo_pago);

        // UNIDAD_MEDIDA
        Map<String, String> unidad_medida = new HashMap<>();
        unidad_medida.put("UniMedCod", "Código");
        unidad_medida.put("UniMedDesc", "Descripción");
        unidad_medida.put("UniMedEstReg", "Estado Registro");
        etiquetas.put("UNIDAD_MEDIDA", unidad_medida);

        // PRODUCTO_STOCK
        Map<String, String> producto_stock = new HashMap<>();
        producto_stock.put("ProStoCod", "Código");
        producto_stock.put("ProCod", "Producto");
        producto_stock.put("ProStoAct", "Stock Actual");
        producto_stock.put("ProStoMin", "Stock Mínimo");
        producto_stock.put("ProStoMax", "Stock Máximo");
        producto_stock.put("ProStoEstReg", "Estado Registro");
        etiquetas.put("PRODUCTO_STOCK", producto_stock);

        // CLASIFICACION_PRODUCTO
        Map<String, String> clasificacion_producto = new HashMap<>();
        clasificacion_producto.put("ClaProCod", "Código");
        clasificacion_producto.put("ClaProDes", "Descripción");
        clasificacion_producto.put("ClaProEstReg", "Estado Registro");
        etiquetas.put("CLASIFICACION_PRODUCTO", clasificacion_producto);

        // CIUDADES
        Map<String, String> ciudades = new HashMap<>();
        ciudades.put("CiuCod", "Código");
        ciudades.put("CiuRegCod", "Región");
        ciudades.put("CiuNom", "Nombre");
        ciudades.put("CiuEstReg", "Estado Registro");
        etiquetas.put("CIUDADES", ciudades);

        // REGIONES
        Map<String, String> regiones = new HashMap<>();
        regiones.put("RegCod", "Código");
        regiones.put("RegNom", "Nombre");
        regiones.put("RegEstReg", "Estado Registro");
        etiquetas.put("REGIONES", regiones);

        // LIMITE_COMPRAS
        Map<String, String> limite_compras = new HashMap<>();
        limite_compras.put("LimComCod", "Código");
        limite_compras.put("LimCom", "Límite Compra");
        limite_compras.put("LimComEstReg", "Estado Registro");
        etiquetas.put("LIMITE_COMPRAS", limite_compras);

        // COMPORTAMIENTO_CLIENTE
        Map<String, String> comportamiento_cliente = new HashMap<>();
        comportamiento_cliente.put("ComCliCod", "Código");
        comportamiento_cliente.put("ComCli", "Comportamiento");
        comportamiento_cliente.put("ComCliVol", "Voluntad");
        comportamiento_cliente.put("ComCliEscCatCod", "Escala Categoría");
        comportamiento_cliente.put("ComCliEstReg", "Estado Registro");
        etiquetas.put("COMPORTAMIENTO_CLIENTE", comportamiento_cliente);

        // ESCALA_CREDITOS
        Map<String, String> escala_creditos = new HashMap<>();
        escala_creditos.put("EscCreCod", "Código");
        escala_creditos.put("EscCreMinAcu", "Min Acumulado");
        escala_creditos.put("EscCreMaxAcu", "Max Acumulado");
        escala_creditos.put("EscCreLimComCod", "Límite Compra");
        escala_creditos.put("EscCredEstReg", "Estado Registro");
        etiquetas.put("ESCALA_CREDITOS", escala_creditos);

        // ESCALA_CATEGORIA
        Map<String, String> escala_categoria = new HashMap<>();
        escala_categoria.put("EscCatCod", "Código");
        escala_categoria.put("EscCatPun", "Puntaje");
        escala_categoria.put("EscCatEstReg", "Estado Registro");
        etiquetas.put("ESCALA_CATEGORIA", escala_categoria);

        // ESCALA_BONIFICACION
        Map<String, String> escala_bonificacion = new HashMap<>();
        escala_bonificacion.put("EscBonCod", "Código");
        escala_bonificacion.put("EscBonVenMaxAcu", "Venta Máxima Acumulada");
        escala_bonificacion.put("EscBonVenMinAcu", "Venta Mínima Acumulada");
        escala_bonificacion.put("EscBonEstReg", "Estado Registro");
        etiquetas.put("ESCALA_BONIFICACION", escala_bonificacion);
    }

    public String getTraduccion(String tabla, String atributo) {
        Map<String, String> tablaEtiquetas = etiquetas.get(tabla.toUpperCase());
        if (tablaEtiquetas != null && tablaEtiquetas.containsKey(atributo)) {
            return tablaEtiquetas.get(atributo);
        }
        return atributo; // Si no hay traducción, devuelve el nombre original
    }

    public Map<String, String> getEtiquetasTabla(String tabla) {
        return etiquetas.getOrDefault(tabla.toUpperCase(), new HashMap<>());
    }
}
