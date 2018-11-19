/* Testes */
CREATE SCHEMA IF NOT EXISTS `softmarketdb` DEFAULT CHARACTER SET utf8 ;
USE `softmarketdb` ;

CREATE TABLE IF NOT EXISTS `softmarketdb`.`usuarios` (
  `login` VARCHAR(30) NOT NULL,
  `senha` VARCHAR(30) NOT NULL,
  `nome` VARCHAR(30) NOT NULL,
  `tipo` ENUM('g','v') NOT NULL,
  PRIMARY KEY (`login`))
  ENGINE = INNODB;


CREATE TABLE IF NOT EXISTS `softmarketdb`.`vendas` (
  `cod` INT NOT NULL AUTO_INCREMENT,
  `data` DATETIME NOT NULL DEFAULT NOW(),
  `total` DECIMAL(6,2) NOT NULL,
  `formaPagamento` ENUM('Cartão', 'Dinheiro') NOT NULL,
  PRIMARY KEY (`cod`))
  ENGINE = INNODB;


CREATE TABLE IF NOT EXISTS `softmarketdb`.`ingredientes` (
  `cod` INT NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `quantidade` INT NOT NULL,
  PRIMARY KEY (`cod`))
  ENGINE = INNODB;


CREATE TABLE IF NOT EXISTS `softmarketdb`.`produtos` (
  `codBarras` VARCHAR(30) NOT NULL,
  `pCusto` DECIMAL(6,2) NOT NULL,
  `pVenda` DECIMAL(6,2) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `peso` INT NOT NULL,
  `quantidade` INT(4) NOT NULL,
  `codIngrediente` INT,
  PRIMARY KEY (`codBarras`),
  CONSTRAINT `fk_produtos_ingredientes`
  FOREIGN KEY (`codIngrediente`)
  REFERENCES `softmarketdb`.`ingredientes` (`cod`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = INNODB;


CREATE TABLE IF NOT EXISTS `softmarketdb`.`receitas` (
  `codBarras` VARCHAR(30) NOT NULL,
  `pCusto` DECIMAL(6,2) NOT NULL,
  `pVenda` DECIMAL(6,2) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`codBarras`))
  ENGINE = INNODB;


CREATE TABLE IF NOT EXISTS `softmarketdb`.`receitas_contem_ingredientes` (
  `ingredientes_cod` INT NOT NULL,
  `receitas_codBarras` VARCHAR(30) NOT NULL,
  `peso` INT NOT NULL,
  PRIMARY KEY (`ingredientes_cod`, `receitas_codBarras`),
  CONSTRAINT `fk_ingredientes_contem_receitas_ingredientes1`
  FOREIGN KEY (`ingredientes_cod`)
  REFERENCES `softmarketdb`.`ingredientes` (`cod`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE ,
  CONSTRAINT `fk_ingredientes_contem_receitas_receitas1`
  FOREIGN KEY (`receitas_codBarras`)
  REFERENCES `softmarketdb`.`receitas` (`codBarras`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE )
  ENGINE = INNODB;


CREATE TABLE IF NOT EXISTS `softmarketdb`.`vendas_contem_receitas` (
  `vendas_cod` INT NOT NULL,
  `receitas_codBarras` VARCHAR(30) NOT NULL,
  `quantidade` INT NOT NULL,
  PRIMARY KEY (`vendas_cod`, `receitas_codBarras`),
  CONSTRAINT `fk_vendas_contem_receitas_vendas1`
  FOREIGN KEY (`vendas_cod`)
  REFERENCES `softmarketdb`.`vendas` (`cod`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_vendas_contem_receitas_receitas1`
  FOREIGN KEY (`receitas_codBarras`)
  REFERENCES `softmarketdb`.`receitas` (`codBarras`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE )
  ENGINE = INNODB;


CREATE TABLE IF NOT EXISTS `softmarketdb`.`vendas_contem_produtos` (
  `vendas_cod` INT NOT NULL,
  `produtos_codBarras` VARCHAR(30) NOT NULL,
  `quantidade` INT NOT NULL,
  PRIMARY KEY (`vendas_cod`, `produtos_codBarras`),
  CONSTRAINT `fk_vendas_contem_produtos_vendas1`
  FOREIGN KEY (`vendas_cod`)
  REFERENCES `softmarketdb`.`vendas` (`cod`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE ,
  CONSTRAINT `fk_vendas_contem_produtos_produtos1`
  FOREIGN KEY (`produtos_codBarras`)
  REFERENCES `softmarketdb`.`produtos` (`codBarras`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE )
  ENGINE = INNODB;

INSERT INTO usuarios (login, senha, nome, tipo) VALUES('gerente01', 'gerencia1', 'Guilherme', 'g');
INSERT INTO usuarios (login, senha, nome, tipo) VALUES('vendedor01', 'vendas1', 'Italo', 'v');