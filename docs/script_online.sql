/* Testes */
USE `jpacon92_softmarketdb`;

CREATE TABLE IF NOT EXISTS `jpacon92_softmarketdb`.`usuarios` (
  `login` VARCHAR(30)     NOT NULL,
  `senha` VARCHAR(30)     NOT NULL,
  `nome`  VARCHAR(30)     NOT NULL,
  `tipo`  ENUM ('g', 'v') NOT NULL,
  PRIMARY KEY (`login`)
)
  ENGINE = INNODB;


CREATE TABLE IF NOT EXISTS `jpacon92_softmarketdb`.`vendas` (
  `cod`            INT                         NOT NULL AUTO_INCREMENT,
  `data`           DATETIME                    NOT NULL DEFAULT NOW(),
  `total`          DECIMAL(6, 2)               NOT NULL,
  `formaPagamento` ENUM ('Cartão', 'Dinheiro') NOT NULL,
  PRIMARY KEY (`cod`)
)
  ENGINE = INNODB;


CREATE TABLE IF NOT EXISTS `jpacon92_softmarketdb`.`ingredientes` (
  `cod`  VARCHAR(30)   NOT NULL,
  `nome` VARCHAR(45)   NOT NULL,
  `peso` DECIMAL(6, 3) NOT NULL,
  PRIMARY KEY (`cod`)
)
  ENGINE = INNODB;


CREATE TABLE IF NOT EXISTS `jpacon92_softmarketdb`.`produtos` (
  `codBarras`      VARCHAR(30)     NOT NULL,
  `pCusto`         DECIMAL(6, 2)   NOT NULL,
  `pVenda`         DECIMAL(6, 2)   NOT NULL,
  `nome`           VARCHAR(45)     NOT NULL,
  `peso`           DECIMAL(6, 3)   NOT NULL DEFAULT 0,
  `quantidade`     INT             NOT NULL,
  `pesavel`        ENUM ('s', 'n') NOT NULL,
  `codIngrediente` VARCHAR(30),
  PRIMARY KEY (`codBarras`),
  CONSTRAINT `fk_produtos_ingredientes`
  FOREIGN KEY (`codIngrediente`)
  REFERENCES `jpacon92_softmarketdb`.`ingredientes` (`cod`)
    ON DELETE SET NULL
    ON UPDATE CASCADE
)
  ENGINE = INNODB;


CREATE TABLE IF NOT EXISTS `jpacon92_softmarketdb`.`receitas` (
  `codBarras` VARCHAR(30)   NOT NULL,
  `pCusto`    DECIMAL(6, 2) NOT NULL,
  `pVenda`    DECIMAL(6, 2) NOT NULL,
  `nome`      VARCHAR(45)   NOT NULL,
  PRIMARY KEY (`codBarras`)
)
  ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS `jpacon92_softmarketdb`.`receitas_contem_ingredientes` (
  `ingredientes_cod`   VARCHAR(30)   NOT NULL,
  `receitas_codBarras` VARCHAR(30)   NOT NULL,
  `peso`               DECIMAL(6, 3) NOT NULL,
  PRIMARY KEY (`ingredientes_cod`, `receitas_codBarras`),
  CONSTRAINT `fk_ingredientes_contem_receitas_ingredientes1`
  FOREIGN KEY (`ingredientes_cod`)
  REFERENCES `jpacon92_softmarketdb`.`ingredientes` (`cod`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_ingredientes_contem_receitas_receitas1`
  FOREIGN KEY (`receitas_codBarras`)
  REFERENCES `jpacon92_softmarketdb`.`receitas` (`codBarras`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
)
  ENGINE = INNODB;


CREATE TABLE IF NOT EXISTS `jpacon92_softmarketdb`.`vendas_contem_receitas` (
  `vendas_cod`         INT         NOT NULL,
  `receitas_codBarras` VARCHAR(30) NOT NULL,
  `quantidade`         INT         NOT NULL,
  PRIMARY KEY (`vendas_cod`, `receitas_codBarras`),
  CONSTRAINT `fk_vendas_contem_receitas_vendas1`
  FOREIGN KEY (`vendas_cod`)
  REFERENCES `jpacon92_softmarketdb`.`vendas` (`cod`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_vendas_contem_receitas_receitas1`
  FOREIGN KEY (`receitas_codBarras`)
  REFERENCES `jpacon92_softmarketdb`.`receitas` (`codBarras`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
)
  ENGINE = INNODB;


CREATE TABLE IF NOT EXISTS `jpacon92_softmarketdb`.`vendas_contem_produtos` (
  `vendas_cod`         INT         NOT NULL,
  `produtos_codBarras` VARCHAR(30) NOT NULL,
  `quantidade`         INT         NOT NULL,
  PRIMARY KEY (`vendas_cod`, `produtos_codBarras`),
  CONSTRAINT `fk_vendas_contem_produtos_vendas1`
  FOREIGN KEY (`vendas_cod`)
  REFERENCES `jpacon92_softmarketdb`.`vendas` (`cod`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_vendas_contem_produtos_produtos1`
  FOREIGN KEY (`produtos_codBarras`)
  REFERENCES `jpacon92_softmarketdb`.`produtos` (`codBarras`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
)
  ENGINE = INNODB;
/* Inserção de usuários */
INSERT INTO jpacon92_softmarketdb.usuarios (login, senha, nome, tipo)
VALUES ('gerente01', 'gerencia1', 'Guilherme', 'g');
INSERT INTO jpacon92_softmarketdb.usuarios (login, senha, nome, tipo)
VALUES ('vendedor01', 'vendas1', 'Italo', 'v');


/* Inserção de ingredientes */
INSERT INTO jpacon92_softmarketdb.ingredientes
VALUES ('123', 'Arroz', 5.0);
INSERT INTO jpacon92_softmarketdb.ingredientes
VALUES ('1234', 'Feijão', 2.0);
INSERT INTO jpacon92_softmarketdb.ingredientes
VALUES ('321', 'Frango', 0.2);
INSERT INTO jpacon92_softmarketdb.ingredientes
VALUES ('4321', 'Acém', 5.25);
INSERT INTO jpacon92_softmarketdb.ingredientes
VALUES ('12345', 'Picanha', 3.75);
INSERT INTO jpacon92_softmarketdb.ingredientes
VALUES ('54321', 'Batata palha', 1);
INSERT INTO jpacon92_softmarketdb.ingredientes
VALUES ('135', 'Alface', 0.8);
INSERT INTO jpacon92_softmarketdb.ingredientes
VALUES ('531', 'Tomate', 0.5);


/* Inserção de produtos */
INSERT INTO jpacon92_softmarketdb.produtos
VALUES ('1', 15.99, 19.99, 'Arroz Sepé 5KG', 5.0, 40, 'n', 123);

INSERT INTO jpacon92_softmarketdb.produtos
VALUES ('2', 6.99, 10.99, 'Arroz Sepé 2KG', 2.0, 30, 'n', 123);

INSERT INTO jpacon92_softmarketdb.produtos
VALUES ('3', 5.99, 9.99, 'Arroz Prato Fino 2KG', 2.0, 10, 'n', 123);

INSERT INTO jpacon92_softmarketdb.produtos
VALUES ('4', 7.99, 8.99, 'Feijão Saboroso 1KG', 1.0, 40, 'n', 1234);

INSERT INTO jpacon92_softmarketdb.produtos
VALUES ('5', 7.49, 8.00, 'Feijão Pereira 1KG', 1.0, 20, 'n', 1234);

INSERT INTO jpacon92_softmarketdb.produtos
VALUES ('6', 22.99, 29.99, 'Pacote bolas de gude c/ 100 und', DEFAULT, 40, 'n', NULL);

INSERT INTO jpacon92_softmarketdb.produtos
VALUES ('7', 15.99, 19.99, 'Picanha Friboi', 1.0, 1, 's', 12345);

INSERT INTO jpacon92_softmarketdb.produtos
VALUES ('8', 15.99, 19.99, 'Batata Palha Elma Chips 300gr', 0.3, 25, 'n', 54321);

# Inserção receitas

INSERT INTO receitas
VALUES ('1', 5.99, 11.99, 'Strogonoff de Frango');

# Inserção receitas_contem_ingredientes

INSERT INTO receitas_contem_ingredientes (ingredientes_cod, receitas_codBarras, peso)
VALUES ('123', '1', 0.100);
INSERT INTO receitas_contem_ingredientes (ingredientes_cod, receitas_codBarras, peso)
VALUES ('321', '1', 0.150);
INSERT INTO receitas_contem_ingredientes (ingredientes_cod, receitas_codBarras, peso)
VALUES ('54321', '1', 0.050);