-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema bdfinal
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema gigacontrol
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema bdclinica_lafe
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema bdclinica_lafe
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bdclinica_lafe` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `bdclinica_lafe` ;

-- -----------------------------------------------------
-- Table `bdclinica_lafe`.`sede`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdclinica_lafe`.`sede` (
  `idsede` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `coordenadas` VARCHAR(45) NOT NULL,
  `direccion` VARCHAR(100) NOT NULL,
  `foto` BLOB NULL DEFAULT NULL,
  PRIMARY KEY (`idsede`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bdclinica_lafe`.`tipodeusuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdclinica_lafe`.`tipodeusuario` (
  `idtipodeusuario` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idtipodeusuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bdclinica_lafe`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdclinica_lafe`.`usuario` (
  `idusuario` INT NOT NULL,
  `idtipodeusuario` INT NOT NULL,
  `nombres` VARCHAR(45) NOT NULL,
  `apellidos` VARCHAR(45) NOT NULL,
  `dni` CHAR(8) NOT NULL,
  `correo` VARCHAR(45) NOT NULL,
  `contrasena` VARCHAR(256) NOT NULL,
  `genero` VARCHAR(45) NOT NULL,
  `foto` BLOB NULL,
  `celular` CHAR(9) NOT NULL,
  `edad` INT NOT NULL,
  `estado` INT NOT NULL,
  PRIMARY KEY (`idusuario`),
  INDEX `fk_usuario_tipodeusuario1_idx` (`idtipodeusuario` ASC) VISIBLE,
  CONSTRAINT `fk_usuario_tipodeusuario1`
    FOREIGN KEY (`idtipodeusuario`)
    REFERENCES `bdclinica_lafe`.`tipodeusuario` (`idtipodeusuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bdclinica_lafe`.`administrador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdclinica_lafe`.`administrador` (
  `idadministrador` INT NOT NULL AUTO_INCREMENT,
  `idsede` INT NOT NULL,
  `idusuario` INT NOT NULL,
  PRIMARY KEY (`idadministrador`, `idsede`),
  INDEX `fk_administrador_sede1_idx` (`idsede` ASC) VISIBLE,
  INDEX `fk_administrador_usuario1_idx` (`idusuario` ASC) VISIBLE,
  CONSTRAINT `fk_administrador_sede1`
    FOREIGN KEY (`idsede`)
    REFERENCES `bdclinica_lafe`.`sede` (`idsede`),
  CONSTRAINT `fk_administrador_usuario1`
    FOREIGN KEY (`idusuario`)
    REFERENCES `bdclinica_lafe`.`usuario` (`idusuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bdclinica_lafe`.`especialidad`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdclinica_lafe`.`especialidad` (
  `idespecialidad` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `costo` FLOAT NOT NULL,
  PRIMARY KEY (`idespecialidad`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bdclinica_lafe`.`administrativo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdclinica_lafe`.`administrativo` (
  `idadministrativo` INT NOT NULL AUTO_INCREMENT,
  `idsede` INT NOT NULL,
  `idespecialidad` INT NOT NULL,
  `idusuario` INT NOT NULL,
  PRIMARY KEY (`idadministrativo`),
  INDEX `fk_administrativo_sede1_idx` (`idsede` ASC) VISIBLE,
  INDEX `fk_administrativo_especialidad1_idx` (`idespecialidad` ASC) VISIBLE,
  INDEX `fk_administrativo_usuario1_idx` (`idusuario` ASC) VISIBLE,
  CONSTRAINT `fk_administrativo_especialidad1`
    FOREIGN KEY (`idespecialidad`)
    REFERENCES `bdclinica_lafe`.`especialidad` (`idespecialidad`),
  CONSTRAINT `fk_administrativo_sede1`
    FOREIGN KEY (`idsede`)
    REFERENCES `bdclinica_lafe`.`sede` (`idsede`),
  CONSTRAINT `fk_administrativo_usuario1`
    FOREIGN KEY (`idusuario`)
    REFERENCES `bdclinica_lafe`.`usuario` (`idusuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bdclinica_lafe`.`estadopaciente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdclinica_lafe`.`estadopaciente` (
  `idestadopaciente` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idestadopaciente`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bdclinica_lafe`.`seguro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdclinica_lafe`.`seguro` (
  `idseguro` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `coaseguro` FLOAT NOT NULL,
  `comisiondoctor` FLOAT NOT NULL,
  PRIMARY KEY (`idseguro`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bdclinica_lafe`.`paciente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdclinica_lafe`.`paciente` (
  `idpaciente` INT NOT NULL,
  `direccion` VARCHAR(100) NOT NULL,
  `idestadopaciente` INT NOT NULL,
  `idseguro` INT NOT NULL,
  `alergias` VARCHAR(45) NULL DEFAULT NULL,
  `consentimientos` INT NOT NULL,
  `idusuario` INT NOT NULL,
  PRIMARY KEY (`idpaciente`),
  INDEX `fk_paciente_estadopaciente1_idx` (`idestadopaciente` ASC) VISIBLE,
  INDEX `fk_paciente_seguro1_idx` (`idseguro` ASC) VISIBLE,
  INDEX `fk_paciente_usuario1_idx` (`idusuario` ASC) VISIBLE,
  CONSTRAINT `fk_paciente_estadopaciente1`
    FOREIGN KEY (`idestadopaciente`)
    REFERENCES `bdclinica_lafe`.`estadopaciente` (`idestadopaciente`),
  CONSTRAINT `fk_paciente_seguro1`
    FOREIGN KEY (`idseguro`)
    REFERENCES `bdclinica_lafe`.`seguro` (`idseguro`),
  CONSTRAINT `fk_paciente_usuario1`
    FOREIGN KEY (`idusuario`)
    REFERENCES `bdclinica_lafe`.`usuario` (`idusuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bdclinica_lafe`.`bitacoradediagnostico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdclinica_lafe`.`bitacoradediagnostico` (
  `idbitacoradediagnostico` INT NOT NULL AUTO_INCREMENT,
  `descripcion` LONGTEXT NULL DEFAULT NULL,
  `fechayhora` DATETIME NULL DEFAULT NULL,
  `idpaciente` INT NOT NULL,
  PRIMARY KEY (`idbitacoradediagnostico`),
  INDEX `fk_bitacoradediagnostico_paciente1_idx` (`idpaciente` ASC) VISIBLE,
  CONSTRAINT `fk_bitacoradediagnostico_paciente1`
    FOREIGN KEY (`idpaciente`)
    REFERENCES `bdclinica_lafe`.`paciente` (`idpaciente`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bdclinica_lafe`.`doctor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdclinica_lafe`.`doctor` (
  `iddoctor` INT NOT NULL AUTO_INCREMENT,
  `idespecialidad` INT NOT NULL,
  `idsede` INT NOT NULL,
  `cmp` INT NOT NULL,
  `formacion` LONGTEXT NOT NULL,
  `rne` INT NOT NULL,
  `capacitaciones` LONGTEXT NOT NULL,
  `idusuario` INT NOT NULL,
  PRIMARY KEY (`iddoctor`),
  INDEX `fk_doctor_especialidad_idx` (`idespecialidad` ASC) VISIBLE,
  INDEX `fk_doctor_sede1_idx` (`idsede` ASC) VISIBLE,
  INDEX `fk_doctor_usuario1_idx` (`idusuario` ASC) VISIBLE,
  CONSTRAINT `fk_doctor_especialidad`
    FOREIGN KEY (`idespecialidad`)
    REFERENCES `bdclinica_lafe`.`especialidad` (`idespecialidad`),
  CONSTRAINT `fk_doctor_sede1`
    FOREIGN KEY (`idsede`)
    REFERENCES `bdclinica_lafe`.`sede` (`idsede`),
  CONSTRAINT `fk_doctor_usuario1`
    FOREIGN KEY (`idusuario`)
    REFERENCES `bdclinica_lafe`.`usuario` (`idusuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bdclinica_lafe`.`estadocita`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdclinica_lafe`.`estadocita` (
  `idestadocita` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idestadocita`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bdclinica_lafe`.`tipocita`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdclinica_lafe`.`tipocita` (
  `idtipocita` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idtipocita`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bdclinica_lafe`.`cita`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdclinica_lafe`.`cita` (
  `idcita` INT NOT NULL AUTO_INCREMENT,
  `costo` FLOAT NOT NULL,
  `idsede` INT NOT NULL,
  `idpaciente` INT NOT NULL,
  `idespecialidad` INT NOT NULL,
  `iddoctor` INT NOT NULL,
  `fecha` DATE NOT NULL,
  `horainicio` TIME NOT NULL,
  `horafinal` TIME NOT NULL,
  `duracion` INT NOT NULL,
  `idtipocita` INT NOT NULL,
  `idseguro` INT NOT NULL,
  `idestadocita` INT NOT NULL,
  PRIMARY KEY (`idcita`),
  INDEX `fk_cita_doctor1_idx` (`iddoctor` ASC) VISIBLE,
  INDEX `fk_cita_sede1_idx` (`idsede` ASC) VISIBLE,
  INDEX `fk_cita_especialidad1_idx` (`idespecialidad` ASC) VISIBLE,
  INDEX `fk_cita_paciente1_idx` (`idpaciente` ASC) VISIBLE,
  INDEX `fk_cita_tipocita1_idx` (`idtipocita` ASC) VISIBLE,
  INDEX `fk_cita_seguro1_idx` (`idseguro` ASC) VISIBLE,
  INDEX `fk_cita_estadocita1_idx` (`idestadocita` ASC) VISIBLE,
  CONSTRAINT `fk_cita_doctor1`
    FOREIGN KEY (`iddoctor`)
    REFERENCES `bdclinica_lafe`.`doctor` (`iddoctor`),
  CONSTRAINT `fk_cita_especialidad1`
    FOREIGN KEY (`idespecialidad`)
    REFERENCES `bdclinica_lafe`.`especialidad` (`idespecialidad`),
  CONSTRAINT `fk_cita_estadocita1`
    FOREIGN KEY (`idestadocita`)
    REFERENCES `bdclinica_lafe`.`estadocita` (`idestadocita`),
  CONSTRAINT `fk_cita_paciente1`
    FOREIGN KEY (`idpaciente`)
    REFERENCES `bdclinica_lafe`.`paciente` (`idpaciente`),
  CONSTRAINT `fk_cita_sede1`
    FOREIGN KEY (`idsede`)
    REFERENCES `bdclinica_lafe`.`sede` (`idsede`),
  CONSTRAINT `fk_cita_seguro1`
    FOREIGN KEY (`idseguro`)
    REFERENCES `bdclinica_lafe`.`seguro` (`idseguro`),
  CONSTRAINT `fk_cita_tipocita1`
    FOREIGN KEY (`idtipocita`)
    REFERENCES `bdclinica_lafe`.`tipocita` (`idtipocita`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bdclinica_lafe`.`boletadoctor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdclinica_lafe`.`boletadoctor` (
  `idboletadoctor` INT NOT NULL AUTO_INCREMENT,
  `idcita` INT NOT NULL,
  `idpaciente` INT NOT NULL,
  `idseguro` INT NOT NULL,
  `iddoctor` INT NOT NULL,
  `fechacita` DATE NOT NULL,
  `monto` FLOAT NOT NULL,
  PRIMARY KEY (`idboletadoctor`),
  INDEX `fk_boletadoctor_cita1_idx` (`idcita` ASC) VISIBLE,
  INDEX `fk_boletadoctor_paciente1_idx` (`idpaciente` ASC) VISIBLE,
  INDEX `fk_boletadoctor_seguro1_idx` (`idseguro` ASC) VISIBLE,
  INDEX `fk_boletadoctor_doctor1_idx` (`iddoctor` ASC) VISIBLE,
  CONSTRAINT `fk_boletadoctor_cita1`
    FOREIGN KEY (`idcita`)
    REFERENCES `bdclinica_lafe`.`cita` (`idcita`),
  CONSTRAINT `fk_boletadoctor_doctor1`
    FOREIGN KEY (`iddoctor`)
    REFERENCES `bdclinica_lafe`.`doctor` (`iddoctor`),
  CONSTRAINT `fk_boletadoctor_paciente1`
    FOREIGN KEY (`idpaciente`)
    REFERENCES `bdclinica_lafe`.`paciente` (`idpaciente`),
  CONSTRAINT `fk_boletadoctor_seguro1`
    FOREIGN KEY (`idseguro`)
    REFERENCES `bdclinica_lafe`.`seguro` (`idseguro`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bdclinica_lafe`.`boletapaciente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdclinica_lafe`.`boletapaciente` (
  `idboletapaciente` INT NOT NULL AUTO_INCREMENT,
  `idpaciente` INT NOT NULL,
  `idcita` INT NOT NULL,
  `idseguro` INT NOT NULL,
  `fechacita` DATE NOT NULL,
  `monto` FLOAT NOT NULL,
  PRIMARY KEY (`idboletapaciente`),
  INDEX `fk_boletapaciente_paciente1_idx` (`idpaciente` ASC) VISIBLE,
  INDEX `fk_boletapaciente_cita1_idx` (`idcita` ASC) VISIBLE,
  INDEX `fk_boletapaciente_seguro1_idx` (`idseguro` ASC) VISIBLE,
  CONSTRAINT `fk_boletapaciente_cita1`
    FOREIGN KEY (`idcita`)
    REFERENCES `bdclinica_lafe`.`cita` (`idcita`),
  CONSTRAINT `fk_boletapaciente_paciente1`
    FOREIGN KEY (`idpaciente`)
    REFERENCES `bdclinica_lafe`.`paciente` (`idpaciente`),
  CONSTRAINT `fk_boletapaciente_seguro1`
    FOREIGN KEY (`idseguro`)
    REFERENCES `bdclinica_lafe`.`seguro` (`idseguro`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bdclinica_lafe`.`cuestionario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdclinica_lafe`.`cuestionario` (
  `idcuestionario` INT NOT NULL,
  `descripcion` VARCHAR(45) NULL DEFAULT NULL,
  `idpaciente` INT NOT NULL,
  `iddoctor` INT NOT NULL,
  `mostrarautomatico` INT NOT NULL,
  PRIMARY KEY (`idcuestionario`),
  INDEX `fk_cuestionario_paciente1_idx` (`idpaciente` ASC) VISIBLE,
  INDEX `fk_cuestionario_doctor1_idx` (`iddoctor` ASC) VISIBLE,
  CONSTRAINT `fk_cuestionario_doctor1`
    FOREIGN KEY (`iddoctor`)
    REFERENCES `bdclinica_lafe`.`doctor` (`iddoctor`),
  CONSTRAINT `fk_cuestionario_paciente1`
    FOREIGN KEY (`idpaciente`)
    REFERENCES `bdclinica_lafe`.`paciente` (`idpaciente`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bdclinica_lafe`.`tipohoracalendariodoctor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdclinica_lafe`.`tipohoracalendariodoctor` (
  `idtipohoracalendariodoctor` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idtipohoracalendariodoctor`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bdclinica_lafe`.`eventocalendariodoctor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdclinica_lafe`.`eventocalendariodoctor` (
  `ideventocalendariodoctor` INT NOT NULL AUTO_INCREMENT,
  `idtipohoracalendariodoctor` INT NOT NULL,
  `fecha` DATE NOT NULL,
  `horainicio` TIME NOT NULL,
  `horafinal` TIME NOT NULL,
  `duracion` INT NOT NULL,
  `descripcion` VARCHAR(200) NULL DEFAULT NULL,
  `iddoctor` INT NOT NULL,
  PRIMARY KEY (`ideventocalendariodoctor`),
  INDEX `fk_eventocalendariodoctor_tipohoracalendariodoctor1_idx` (`idtipohoracalendariodoctor` ASC) VISIBLE,
  INDEX `fk_eventocalendariodoctor_doctor1_idx` (`iddoctor` ASC) VISIBLE,
  CONSTRAINT `fk_eventocalendariodoctor_doctor1`
    FOREIGN KEY (`iddoctor`)
    REFERENCES `bdclinica_lafe`.`doctor` (`iddoctor`),
  CONSTRAINT `fk_eventocalendariodoctor_tipohoracalendariodoctor1`
    FOREIGN KEY (`idtipohoracalendariodoctor`)
    REFERENCES `bdclinica_lafe`.`tipohoracalendariodoctor` (`idtipohoracalendariodoctor`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bdclinica_lafe`.`tipoformulario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdclinica_lafe`.`tipoformulario` (
  `idtipoformulario` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `idtipodeusuario` INT NOT NULL,
  PRIMARY KEY (`idtipoformulario`, `idtipodeusuario`),
  INDEX `fk_tipoformulario_tipodeusuario1_idx` (`idtipodeusuario` ASC) VISIBLE,
  CONSTRAINT `fk_tipoformulario_tipodeusuario1`
    FOREIGN KEY (`idtipodeusuario`)
    REFERENCES `bdclinica_lafe`.`tipodeusuario` (`idtipodeusuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bdclinica_lafe`.`formularioadministrador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdclinica_lafe`.`formularioadministrador` (
  `idformularioadministrador` INT NOT NULL AUTO_INCREMENT,
  `nombres` VARCHAR(45) NULL DEFAULT NULL,
  `apellidos` VARCHAR(45) NULL DEFAULT NULL,
  `celular` CHAR(9) NULL,
  `correo` VARCHAR(45) NOT NULL,
  `contrasena` VARCHAR(256) NULL,
  `idtipoformulario` INT NOT NULL,
  `dni` CHAR(8) NULL DEFAULT NULL,
  `genero` VARCHAR(45) NULL DEFAULT NULL,
  `idsede` INT NOT NULL,
  PRIMARY KEY (`idformularioadministrador`),
  INDEX `fk_formularioadministrador_tipoformulario1_idx` (`idtipoformulario` ASC) VISIBLE,
  INDEX `fk_formularioadministrador_sede1_idx` (`idsede` ASC) VISIBLE,
  CONSTRAINT `fk_formularioadministrador_tipoformulario1`
    FOREIGN KEY (`idtipoformulario`)
    REFERENCES `bdclinica_lafe`.`tipoformulario` (`idtipoformulario`),
  CONSTRAINT `fk_formularioadministrador_sede1`
    FOREIGN KEY (`idsede`)
    REFERENCES `bdclinica_lafe`.`sede` (`idsede`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bdclinica_lafe`.`formularioadministrativo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdclinica_lafe`.`formularioadministrativo` (
  `idformularioadministrativo` INT NOT NULL AUTO_INCREMENT,
  `nombres` VARCHAR(45) NULL DEFAULT NULL,
  `apellidos` VARCHAR(45) NULL DEFAULT NULL,
  `dni` CHAR(8) NULL DEFAULT NULL,
  `genero` VARCHAR(45) NULL,
  `correo` VARCHAR(45) NOT NULL,
  `contrasena` VARCHAR(256) NULL,
  `celular` CHAR(9) NULL,
  `idtipoformulario` INT NOT NULL,
  `idsede` INT NOT NULL,
  `idespecialidad` INT NOT NULL,
  PRIMARY KEY (`idformularioadministrativo`),
  INDEX `fk_formularioadministrativo_tipoformulario1_idx` (`idtipoformulario` ASC) VISIBLE,
  INDEX `fk_formularioadministrativo_sede1_idx` (`idsede` ASC) VISIBLE,
  INDEX `fk_formularioadministrativo_especialidad1_idx` (`idespecialidad` ASC) VISIBLE,
  CONSTRAINT `fk_formularioadministrativo_tipoformulario1`
    FOREIGN KEY (`idtipoformulario`)
    REFERENCES `bdclinica_lafe`.`tipoformulario` (`idtipoformulario`),
  CONSTRAINT `fk_formularioadministrativo_sede1`
    FOREIGN KEY (`idsede`)
    REFERENCES `bdclinica_lafe`.`sede` (`idsede`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_formularioadministrativo_especialidad1`
    FOREIGN KEY (`idespecialidad`)
    REFERENCES `bdclinica_lafe`.`especialidad` (`idespecialidad`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bdclinica_lafe`.`formulariodoctor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdclinica_lafe`.`formulariodoctor` (
  `idformulariodoctor` INT NOT NULL AUTO_INCREMENT,
  `nombres` VARCHAR(45) NULL DEFAULT NULL,
  `apellidos` VARCHAR(45) NULL DEFAULT NULL,
  `correo` VARCHAR(45) NOT NULL,
  `contrasena` VARCHAR(256) NULL,
  `dni` CHAR(8) NULL DEFAULT NULL,
  `genero` VARCHAR(45) NULL DEFAULT NULL,
  `celular` CHAR(9) NULL,
  `cmp` INT NULL DEFAULT NULL,
  `rne` INT NULL DEFAULT NULL,
  `formacion` LONGTEXT NULL DEFAULT NULL,
  `capacitaciones` LONGTEXT NULL DEFAULT NULL,
  `idtipoformulario` INT NOT NULL,
  `idsede` INT NOT NULL,
  `idespecialidad` INT NOT NULL,
  PRIMARY KEY (`idformulariodoctor`),
  INDEX `fk_formulariodoctor_tipoformulario1_idx` (`idtipoformulario` ASC) VISIBLE,
  INDEX `fk_formulariodoctor_sede1_idx` (`idsede` ASC) VISIBLE,
  INDEX `fk_formulariodoctor_especialidad1_idx` (`idespecialidad` ASC) VISIBLE,
  CONSTRAINT `fk_formulariodoctor_tipoformulario1`
    FOREIGN KEY (`idtipoformulario`)
    REFERENCES `bdclinica_lafe`.`tipoformulario` (`idtipoformulario`),
  CONSTRAINT `fk_formulariodoctor_sede1`
    FOREIGN KEY (`idsede`)
    REFERENCES `bdclinica_lafe`.`sede` (`idsede`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_formulariodoctor_especialidad1`
    FOREIGN KEY (`idespecialidad`)
    REFERENCES `bdclinica_lafe`.`especialidad` (`idespecialidad`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bdclinica_lafe`.`formulariopaciente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdclinica_lafe`.`formulariopaciente` (
  `idformulariopaciente` INT NOT NULL AUTO_INCREMENT,
  `nombres` VARCHAR(45) NOT NULL,
  `apellidos` VARCHAR(45) NOT NULL,
  `dni` VARCHAR(45) NOT NULL,
  `edad` INT NULL DEFAULT NULL,
  `correo` VARCHAR(45) NOT NULL,
  `contrasena` VARCHAR(256) NULL DEFAULT NULL,
  `direccion` VARCHAR(100) NULL DEFAULT NULL,
  `genero` VARCHAR(45) NULL DEFAULT NULL,
  `celular` CHAR(9) NULL DEFAULT NULL,
  `idseguro` INT NOT NULL,
  `firmadigital` BLOB NOT NULL,
  `consentimientos` INT NULL DEFAULT NULL,
  `idtipoformulario` INT NOT NULL,
  PRIMARY KEY (`idformulariopaciente`),
  INDEX `fk_formulariopaciente_seguro1_idx` (`idseguro` ASC) VISIBLE,
  INDEX `fk_formulariopaciente_tipoformulario1_idx` (`idtipoformulario` ASC) VISIBLE,
  CONSTRAINT `fk_formulariopaciente_seguro1`
    FOREIGN KEY (`idseguro`)
    REFERENCES `bdclinica_lafe`.`seguro` (`idseguro`),
  CONSTRAINT `fk_formulariopaciente_tipoformulario1`
    FOREIGN KEY (`idtipoformulario`)
    REFERENCES `bdclinica_lafe`.`tipoformulario` (`idtipoformulario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bdclinica_lafe`.`recetamedica`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdclinica_lafe`.`recetamedica` (
  `idrecetamedica` INT NOT NULL,
  `medicamentos` VARCHAR(100) NULL DEFAULT NULL,
  `dosis` VARCHAR(100) NULL DEFAULT NULL,
  `para` VARCHAR(200) NULL DEFAULT NULL,
  `idcita` INT NOT NULL,
  PRIMARY KEY (`idrecetamedica`),
  INDEX `fk_recetamedica_cita1_idx` (`idcita` ASC) VISIBLE,
  CONSTRAINT `fk_recetamedica_cita1`
    FOREIGN KEY (`idcita`)
    REFERENCES `bdclinica_lafe`.`cita` (`idcita`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bdclinica_lafe`.`reporte`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdclinica_lafe`.`reporte` (
  `idreporte` INT NOT NULL AUTO_INCREMENT,
  `descripcion` LONGTEXT NULL DEFAULT NULL,
  `fecha` DATE NOT NULL,
  `idcita` INT NOT NULL,
  PRIMARY KEY (`idreporte`),
  INDEX `fk_reporte_cita1_idx` (`idcita` ASC) VISIBLE,
  CONSTRAINT `fk_reporte_cita1`
    FOREIGN KEY (`idcita`)
    REFERENCES `bdclinica_lafe`.`cita` (`idcita`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bdclinica_lafe`.`superadmin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdclinica_lafe`.`superadmin` (
  `idsuperadmin` INT NOT NULL AUTO_INCREMENT,
  `empresa` VARCHAR(45) NOT NULL,
  `idusuario` INT NOT NULL,
  PRIMARY KEY (`idsuperadmin`),
  INDEX `fk_superadmin_usuario1_idx` (`idusuario` ASC) VISIBLE,
  CONSTRAINT `fk_superadmin_usuario1`
    FOREIGN KEY (`idusuario`)
    REFERENCES `bdclinica_lafe`.`usuario` (`idusuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bdclinica_lafe`.`ux/ui`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdclinica_lafe`.`ux/ui` (
  `idUX/UI` INT NOT NULL,
  `modo` VARCHAR(45) NOT NULL,
  `codigocolor` VARCHAR(45) NOT NULL,
  `notificaciones` INT NOT NULL,
  `imagendefondo` BLOB NULL DEFAULT NULL,
  `idsede` INT NOT NULL,
  PRIMARY KEY (`idUX/UI`),
  INDEX `fk_UX/UI_sede1_idx` (`idsede` ASC) VISIBLE,
  CONSTRAINT `fk_UX/UI_sede1`
    FOREIGN KEY (`idsede`)
    REFERENCES `bdclinica_lafe`.`sede` (`idsede`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
