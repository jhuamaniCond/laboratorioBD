
CREATE TABLE `EMPRESA_CLIENTE` (
  `EmpCod` CHAR(3) PRIMARY KEY,
  `EmpRUC` CHAR(11),
  `EmpNom` varchar(255),
  `EmpEstReg` CHAR(1) CHECK (EmpEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `CLIENTE` (
  `CliCod` CHAR(4) PRIMARY KEY,
  `CliEmpCod` CHAR(3),
  `CliNom` varchar(255),
  `CliApePat` varchar(255),
  `CliApeMat` varchar(255),
  `CliDir` varchar(255),
  `CliTel` varchar(255),
  `CliCor` varchar(255),
  `CliComCliCod` CHAR(2),
  `CliUsuCod` CHAR(4),
  `CliEstReg` CHAR(1) CHECK (CliEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `CATEGORIA_CLIENTE` (
  `CatCod` CHAR(2) PRIMARY KEY,
  `CatNom` varchar(255),
  `CatEstReg` CHAR(1) CHECK (CatEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `COMPRAS_CLIENTE` (
  `ComCod` CHAR(4) PRIMARY KEY,
  `ComCliCod` CHAR(4),
  `ComAño` int,
  `ComMes` int,
  `ComTot` decimal,
  `ComTotAcu` decimal,
  `ComEscCreCod` CHAR(2),
  `ComDeu` decimal,
  `ComEstReg` CHAR(1) CHECK (ComEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `OFICINA` (
  `OfiCod` CHAR(2) PRIMARY KEY,
  `OfiCiuCod` CHAR(2),
  `OfiDir` varchar(255),
  `OfiProCod` CHAR(4),
  `OfiEstReg` CHAR(1) CHECK (OfiEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `USUARIO` (
  `UsuCod` CHAR(4) PRIMARY KEY,
  `UsuNom` varchar(255),
  `UsuCon` varchar(255),
  `UsuRol` varchar(255),
  `UsuEstReg` CHAR(1) CHECK (UsuEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `CARGO` (
  `CarCod` CHAR(2) PRIMARY KEY,
  `CarDes` varchar(255),
  `CarEstReg` CHAR(1) CHECK (CarEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `AUDITORIA` (
  `AudCod` CHAR(4) PRIMARY KEY,
  `AudUsu` CHAR(4),
  `AudEstReg` CHAR(1) CHECK (AudEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `AUDITORIA_ACCION` (
  `AudAccCod` CHAR(4) PRIMARY KEY,
  `AudCod` CHAR(4),
  `AudAccDesc` varchar(255),
  `AudAccAño` int,
  `AudAccMes` int,
  `AudAccDia` int,
  `AudAccHor` int,
  `AudAccMin` int,
  `AudAccSeg` int,
  `AudAccEstReg` CHAR(1) CHECK (AudAccEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `REPRESENTANTE_VENTA` (
  `RepCod` CHAR(2) PRIMARY KEY,
  `RepNom` varchar(255),
  `RepEdad` int,
  `RepSue` double,
  `RepCarCod` CHAR(2),
  `RepConAño` int,
  `RepConMes` int,
  `RepConDia` int,
  `RepProCod` CHAR(4),
  `RepVenOfiCod` CHAR(2),
  `RepUsuCod` CHAR(4),
  `RepEstReg` CHAR(1) CHECK (RepEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `PROVECCION_VENTAS` (
  `ProVenCod` CHAR(4) PRIMARY KEY,
  `ProVenObj` decimal,
  `ProVenCon` decimal,
  `ProVenAño` decimal,
  `ProVenMes` decimal,
  `ProEstReg` CHAR(1) CHECK (ProEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `VENTAS` (
  `VenCod` CHAR(4) PRIMARY KEY,
  `VenAcuTot` int,
  `VenEscBonCod` CHAR(2),
  `VenRepCod` CHAR(2),
  `VenAño` int,
  `VenMes` int,
  `VenEstReg` CHAR(1) CHECK (VenEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `BONIFICACION_VENTAS` (
  `BonVenCod` CHAR(2) PRIMARY KEY,
  `EscBonCod` CHAR(2),
  `BonCan` decimal,
  `BonEstReg` CHAR(1) CHECK (BonEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `FACTURA_CABECERA` (
  `FacCod` CHAR(4) PRIMARY KEY,
  `FacAño` int,
  `FacMes` int,
  `FacDia` int,
  `FacPedCod` CHAR(4),
  `FacTipPagCod` CHAR(2),
  `FacEstReg` CHAR(1) CHECK (FacEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `PEDIDO` (
  `PedCod` CHAR(4) PRIMARY KEY,
  `PedCli` CHAR(4),
  `PedRep` CHAR(2),
  `PedAñoCre` int,
  `PedMesCre` int,
  `PedDiaCre` int,
  `PedEnvCod` CHAR(4),
  `PedEstReg` CHAR(1) CHECK (PedEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `ENVIO` (
  `EnvCod` CHAR(4) PRIMARY KEY,
  `EnvTipCod` CHAR(2),
  `EnvCos` decimal,
  `EnvEst` varchar(255),
  `EnvCiuCod` CHAR(2),
  `EnvAñoEnt` int,
  `EnvMesEnt` int,
  `EnvDiaEnt` int,
  `EnvEstReg` CHAR(1) CHECK (EnvEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `TIPO_ENVIO` (
  `TipCod` CHAR(2) PRIMARY KEY,
  `TipDes` varchar(255),
  `TipEstReg` CHAR(1) CHECK (TipEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `PRODUCTO` (
  `ProCod` CHAR(4) PRIMARY KEY,
  `ProFabCod` CHAR(3),
  `ProDes` varchar(255),
  `ProPre` decimal,
  `ProClaProCod` CHAR(2),
  `ProUniMedCod` CHAR(2),
  `ProAlmCod` CHAR(2),
  `ProEstReg` CHAR(1) CHECK (ProEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `FABRICANTE` (
  `FabCod` CHAR(3) PRIMARY KEY,
  `FabNom` varchar(255),
  `FabDir` varchar(255),
  `FabEstReg` CHAR(1) CHECK (FabEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `FACTURA_DETALLE` (
  `FacDetCod` CHAR(5) PRIMARY KEY,
  `FacCod` CHAR(5),
  `DetProCod` CHAR(4),
  `DetCan` int,
  `DetPre` decimal,
  `DetEstReg` CHAR(1) CHECK (DetEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `ALMACEN` (
  `AlmCod` CHAR(2) PRIMARY KEY,
  `AlmNom` varchar(255),
  `AlmCosMes` decimal,
  `AlmEstReg` CHAR(1) CHECK (AlmEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `TIPO_PAGO` (
  `TipPagCod` CHAR(2) PRIMARY KEY,
  `TipPagNom` varchar(255),
  `TipPagEstReg` CHAR(1) CHECK (TipPagEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `UNIDAD_MEDIDA` (
  `UniMedCod` CHAR(2) PRIMARY KEY,
  `UniMedDesc` varchar(255),
  `UniMedEstReg` CHAR(1) CHECK (UniMedEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `PRODUCTO_STOCK` (
  `ProStoCod` CHAR(4) PRIMARY KEY,
  `ProCod` CHAR(4),
  `ProStoAct` int,
  `ProStoMin` int,
  `ProStoMax` int,
  `ProStoEstReg` CHAR(1) CHECK (ProStoEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `CLASIFICACION_PRODUCTO` (
  `ClaProCod` CHAR(2) PRIMARY KEY,
  `ClaProDes` varchar(255),
  `ClaProEstReg` CHAR(1) CHECK (ClaProEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `CIUDADES` (
  `CiuCod` CHAR(2) PRIMARY KEY,
  `CiuRegCod` CHAR(2),
  `CiuNom` varchar(255),
  `CiuEstReg` CHAR(1) CHECK (CiuEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `REGIONES` (
  `RegCod` CHAR(2) PRIMARY KEY,
  `RegNom` varchar(255),
  `RegEstReg` CHAR(1) CHECK (RegEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `LIMITE_COMPRAS` (
  `LimComCod` CHAR(2) PRIMARY KEY,
  `LimCom` double,
  `LimComEstReg` CHAR(1) CHECK (LimComEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `COMPORTAMIENTO_CLIENTE` (
  `ComCliCod` CHAR(2) PRIMARY KEY,
  `ComCli` varchar(255),
  `ComCliVol` varchar(255),
  `ComCliEscCatCod` CHAR(2),
  `ComCliEstReg` CHAR(1) CHECK (ComCliEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `ESCALA_CREDITOS` (
  `EscCreCod` CHAR(2) PRIMARY KEY,
  `EscCreMinAcu` double,
  `EscCreMaxAcu` double,
  `EscCreLimComCod` CHAR(2),
  `EscCredEstReg` CHAR(1) CHECK (EscCredEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `ESCALA_CATEGORIA` (
  `EscCatCod` CHAR(2) PRIMARY KEY,
  `EscCatPun` CHAR(2),
  `EscCatEstReg` CHAR(1) CHECK (EscCatEstReg IN ('A', 'I', '*'))
);

CREATE TABLE `ESCALA_BONIFICACION` (
  `EscBonCod` CHAR(2) PRIMARY KEY,
  `EscBonVenMaxAcu` double,
  `EscBonVenMinAcu` double,
  `EscBonEstReg` CHAR(1) CHECK (EscBonEstReg IN ('A', 'I', '*'))
);


ALTER TABLE `CLIENTE` ADD FOREIGN KEY (`CliEmpCod`) REFERENCES `EMPRESA_CLIENTE` (`EmpCod`);

ALTER TABLE `PEDIDO` ADD FOREIGN KEY (`PedCli`) REFERENCES `CLIENTE` (`CliCod`);

ALTER TABLE `COMPRAS_CLIENTE` ADD FOREIGN KEY (`ComCliCod`) REFERENCES `CLIENTE` (`CliCod`);

ALTER TABLE `PEDIDO` ADD FOREIGN KEY (`PedRep`) REFERENCES `REPRESENTANTE_VENTA` (`RepCod`);

ALTER TABLE `PEDIDO` ADD FOREIGN KEY (`PedEnvCod`) REFERENCES `ENVIO` (`EnvCod`);

ALTER TABLE `ENVIO` ADD FOREIGN KEY (`EnvTipCod`) REFERENCES `TIPO_ENVIO` (`TipCod`);

ALTER TABLE `FACTURA_CABECERA` ADD FOREIGN KEY (`FacPedCod`) REFERENCES `PEDIDO` (`PedCod`);

ALTER TABLE `FACTURA_DETALLE` ADD FOREIGN KEY (`FacCod`) REFERENCES `FACTURA_CABECERA` (`FacCod`);

ALTER TABLE `FACTURA_DETALLE` ADD FOREIGN KEY (`DetProCod`) REFERENCES `PRODUCTO` (`ProCod`);

ALTER TABLE `PRODUCTO` ADD FOREIGN KEY (`ProFabCod`) REFERENCES `FABRICANTE` (`FabCod`);

ALTER TABLE `PRODUCTO` ADD FOREIGN KEY (`ProAlmCod`) REFERENCES `ALMACEN` (`AlmCod`);

ALTER TABLE `REPRESENTANTE_VENTA` ADD FOREIGN KEY (`RepProCod`) REFERENCES `PROVECCION_VENTAS` (`ProVenCod`);

ALTER TABLE `OFICINA` ADD FOREIGN KEY (`OfiProCod`) REFERENCES `PROVECCION_VENTAS` (`ProVenCod`);

ALTER TABLE `AUDITORIA` ADD FOREIGN KEY (`AudUsu`) REFERENCES `USUARIO` (`UsuCod`);

ALTER TABLE `REPRESENTANTE_VENTA` ADD FOREIGN KEY (`RepCarCod`) REFERENCES `CARGO` (`CarCod`);

ALTER TABLE `REPRESENTANTE_VENTA` ADD FOREIGN KEY (`RepVenOfiCod`) REFERENCES `OFICINA` (`OfiCod`);

ALTER TABLE `REPRESENTANTE_VENTA` ADD FOREIGN KEY (`RepUsuCod`) REFERENCES `USUARIO` (`UsuCod`);

ALTER TABLE `CLIENTE` ADD FOREIGN KEY (`CliUsuCod`) REFERENCES `USUARIO` (`UsuCod`);

ALTER TABLE `FACTURA_CABECERA` ADD FOREIGN KEY (`FacTipPagCod`) REFERENCES `TIPO_PAGO` (`TipPagCod`);

ALTER TABLE `PRODUCTO` ADD FOREIGN KEY (`ProUniMedCod`) REFERENCES `UNIDAD_MEDIDA` (`UniMedCod`);

ALTER TABLE `PRODUCTO_STOCK` ADD FOREIGN KEY (`ProCod`) REFERENCES `PRODUCTO` (`ProCod`);

ALTER TABLE `VENTAS` ADD FOREIGN KEY (`VenRepCod`) REFERENCES `REPRESENTANTE_VENTA` (`RepCod`);

ALTER TABLE `PRODUCTO` ADD FOREIGN KEY (`ProClaProCod`) REFERENCES `CLASIFICACION_PRODUCTO` (`ClaProCod`);

ALTER TABLE `CIUDADES` ADD FOREIGN KEY (`CiuRegCod`) REFERENCES `REGIONES` (`RegCod`);

ALTER TABLE `ENVIO` ADD FOREIGN KEY (`EnvCiuCod`) REFERENCES `CIUDADES` (`CiuCod`);

ALTER TABLE `CLIENTE` ADD FOREIGN KEY (`CliComCliCod`) REFERENCES `COMPORTAMIENTO_CLIENTE` (`ComCliCod`);

ALTER TABLE `COMPRAS_CLIENTE` ADD FOREIGN KEY (`ComEscCreCod`) REFERENCES `ESCALA_CREDITOS` (`EscCreCod`);

ALTER TABLE `ESCALA_CREDITOS` ADD FOREIGN KEY (`EscCreLimComCod`) REFERENCES `LIMITE_COMPRAS` (`LimComCod`);

ALTER TABLE `COMPORTAMIENTO_CLIENTE` ADD FOREIGN KEY (`ComCliEscCatCod`) REFERENCES `ESCALA_CATEGORIA` (`EscCatCod`);

ALTER TABLE `ESCALA_CATEGORIA` ADD FOREIGN KEY (`EscCatPun`) REFERENCES `CATEGORIA_CLIENTE` (`CatCod`);

ALTER TABLE `VENTAS` ADD FOREIGN KEY (`VenEscBonCod`) REFERENCES `ESCALA_BONIFICACION` (`EscBonCod`);

ALTER TABLE `BONIFICACION_VENTAS` ADD FOREIGN KEY (`EscBonCod`) REFERENCES `ESCALA_BONIFICACION` (`EscBonCod`);

ALTER TABLE `AUDITORIA_ACCION` ADD FOREIGN KEY (`AudCod`) REFERENCES `AUDITORIA` (`AudCod`);

ALTER TABLE `OFICINA` ADD FOREIGN KEY (`OfiCiuCod`) REFERENCES `CIUDADES` (`CiuCod`);

