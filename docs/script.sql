CREATE SCHEMA IF NOT EXISTS `softmarketdb`
  DEFAULT CHARACTER SET utf8;
USE `softmarketdb`;

CREATE TABLE IF NOT EXISTS `softmarketdb`.`usuarios` (
  `login` VARCHAR(30)     NOT NULL,
  `senha` VARCHAR(30)     NOT NULL,
  `nome`  VARCHAR(30)     NOT NULL,
  `tipo`  ENUM ('g', 'v') NOT NULL,
  PRIMARY KEY (`login`)
)
  ENGINE = INNODB;


CREATE TABLE IF NOT EXISTS `softmarketdb`.`vendas` (
  `cod`            INT                         NOT NULL AUTO_INCREMENT,
  `data`           DATETIME                    NOT NULL DEFAULT NOW(),
  `total`          DECIMAL(6, 2)               NOT NULL,
  `formaPagamento` ENUM ('Cartão', 'Dinheiro') NOT NULL,
  PRIMARY KEY (`cod`)
)
  ENGINE = INNODB;


CREATE TABLE IF NOT EXISTS `softmarketdb`.`ingredientes` (
  `cod`  VARCHAR(30)   NOT NULL,
  `nome` VARCHAR(45)   NOT NULL,
  `peso` DECIMAL(6, 3) NOT NULL,
  PRIMARY KEY (`cod`)
)
  ENGINE = INNODB;


CREATE TABLE IF NOT EXISTS `softmarketdb`.`produtos` (
  `codBarras`      VARCHAR(30)     NOT NULL,
  `pCusto`         DECIMAL(6, 2)   NOT NULL,
  `pVenda`         DECIMAL(6, 2)   NOT NULL,
  `nome`           VARCHAR(45)     NOT NULL,
  `peso`           DECIMAL(6, 3)   NOT NULL DEFAULT 0,
  `quantidade`     INT             NOT NULL,
  `pesavel`        ENUM ('s', 'n') NOT NULL,
  `codIngrediente` VARCHAR(30),
  CHECK (pVenda > pCusto),
  PRIMARY KEY (`codBarras`),
  CONSTRAINT `fk_produtos_ingredientes`
  FOREIGN KEY (`codIngrediente`)
  REFERENCES `softmarketdb`.`ingredientes` (`cod`)
    ON DELETE SET NULL
    ON UPDATE CASCADE
)
  ENGINE = INNODB;


CREATE TABLE IF NOT EXISTS `softmarketdb`.`receitas` (
  `codBarras` VARCHAR(30)   NOT NULL,
  `pCusto`    DECIMAL(6, 2) NOT NULL,
  `pVenda`    DECIMAL(6, 2) NOT NULL,
  `nome`      VARCHAR(45)   NOT NULL,
  PRIMARY KEY (`codBarras`),
  CHECK (pVenda > pCusto)
)
  ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS `softmarketdb`.`receitas_contem_ingredientes` (
  `ingredientes_cod`   VARCHAR(30)   NOT NULL,
  `receitas_codBarras` VARCHAR(30)   NOT NULL,
  `peso`               DECIMAL(6, 3) NOT NULL,
  PRIMARY KEY (`ingredientes_cod`, `receitas_codBarras`),
  CONSTRAINT `fk_ingredientes_contem_receitas_ingredientes1`
  FOREIGN KEY (`ingredientes_cod`)
  REFERENCES `softmarketdb`.`ingredientes` (`cod`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_ingredientes_contem_receitas_receitas1`
  FOREIGN KEY (`receitas_codBarras`)
  REFERENCES `softmarketdb`.`receitas` (`codBarras`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
)
  ENGINE = INNODB;


CREATE TABLE IF NOT EXISTS `softmarketdb`.`vendas_contem_receitas` (
  `vendas_cod`         INT         NOT NULL,
  `receitas_codBarras` VARCHAR(30) NOT NULL,
  `quantidade`         INT         NOT NULL,
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
    ON UPDATE CASCADE
)
  ENGINE = INNODB;


CREATE TABLE IF NOT EXISTS `softmarketdb`.`vendas_contem_produtos` (
  `vendas_cod`         INT         NOT NULL,
  `produtos_codBarras` VARCHAR(30) NOT NULL,
  `quantidade`         INT         NOT NULL,
  PRIMARY KEY (`vendas_cod`, `produtos_codBarras`),
  CONSTRAINT `fk_vendas_contem_produtos_vendas1`
  FOREIGN KEY (`vendas_cod`)
  REFERENCES `softmarketdb`.`vendas` (`cod`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_vendas_contem_produtos_produtos1`
  FOREIGN KEY (`produtos_codBarras`)
  REFERENCES `softmarketdb`.`produtos` (`codBarras`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
)
  ENGINE = INNODB;


CREATE VIEW RELATORIO_VENDAS AS
  SELECT v.cod,
         v.data,
         v.total,
         v.formaPagamento,
         p.codBarras,
         p.nome,
         p.pCusto,
         p.pVenda,
         produto.quantidade,
         ((pVenda / pCusto) - 1) * 100 AS lucro_porcento,
         pVenda - pCusto               AS lucro_real
  FROM vendas v
         JOIN vendas_contem_produtos produto on v.cod = produto.vendas_cod
         JOIN produtos p on produto.produtos_codBarras = p.codBarras

  UNION

  SELECT v1.cod,
         v1.data,
         v1.total,
         v1.formaPagamento,
         r.codBarras,
         r.nome,
         r.pCusto,
         r.pVenda,
         receita.quantidade,
         ((pVenda / pCusto) - 1) * 100 AS lucro,
         pVenda - pCusto               AS lucro_real
  FROM vendas v1
         JOIN vendas_contem_receitas receita on v1.cod = receita.vendas_cod
         JOIN receitas r on receita.receitas_codBarras = r.codBarras
  ORDER BY cod, nome;


#Triggers
CREATE TRIGGER RestricaoProduto
  BEFORE INSERT
  ON produtos
  FOR EACH ROW
  BEGIN
    IF (SELECT count(*) from receitas WHERE receitas.codBarras = NEW.codBarras > 0)
    THEN
      SIGNAL SQLSTATE '45000';
    END IF;
  END;

CREATE TRIGGER RestricaoReceita
  BEFORE INSERT
  ON receitas
  FOR EACH ROW
  BEGIN
    IF (SELECT count(*) from produtos WHERE produtos.codBarras = NEW.codBarras > 0)
    THEN
      SIGNAL SQLSTATE '45000';
    END IF;
  END;

#Populando o BD

INSERT INTO softmarketdb.usuarios (login, senha, nome, tipo)
VALUES ('gerente01', 'gerencia1', 'Guilherme', 'g');
INSERT INTO softmarketdb.usuarios (login, senha, nome, tipo)
VALUES ('vendedor01', 'vendas1', 'Italo', 'v');


INSERT INTO softmarketdb.ingredientes
VALUES ('123', 'Arroz', 5.0);
INSERT INTO softmarketdb.ingredientes
VALUES ('1234', 'Feijão', 2.0);
INSERT INTO softmarketdb.ingredientes
VALUES ('321', 'Frango', 0.2);
INSERT INTO softmarketdb.ingredientes
VALUES ('4321', 'Acém', 5.25);
INSERT INTO softmarketdb.ingredientes
VALUES ('12345', 'Picanha', 3.75);
INSERT INTO softmarketdb.ingredientes
VALUES ('54321', 'Batata palha', 1);
INSERT INTO softmarketdb.ingredientes
VALUES ('135', 'Alface', 0.8);
INSERT INTO softmarketdb.ingredientes
VALUES ('531', 'Tomate', 0.5);


INSERT INTO softmarketdb.produtos
VALUES ('1', 15.99, 19.99, 'Arroz Sepé 5KG', 5.0, 40, 'n', 123);

INSERT INTO softmarketdb.produtos
VALUES ('2', 6.99, 10.99, 'Arroz Sepé 2KG', 2.0, 30, 'n', 123);

INSERT INTO softmarketdb.produtos
VALUES ('3', 5.99, 9.99, 'Arroz Prato Fino 2KG', 2.0, 10, 'n', 123);

INSERT INTO softmarketdb.produtos
VALUES ('4', 7.99, 8.99, 'Feijão Saboroso 1KG', 1.0, 40, 'n', 1234);

INSERT INTO softmarketdb.produtos
VALUES ('5', 7.49, 8.00, 'Feijão Pereira 1KG', 1.0, 20, 'n', 1234);

INSERT INTO softmarketdb.produtos
VALUES ('6', 22.99, 29.99, 'Pacote bolas de gude c/ 100 und', DEFAULT, 40, 'n', NULL);

INSERT INTO softmarketdb.produtos
VALUES ('7', 15.99, 19.99, 'Picanha Friboi', 1.0, 1, 's', 12345);

INSERT INTO softmarketdb.produtos
VALUES ('8', 15.99, 19.99, 'Batata Palha Elma Chips 300gr', 0.3, 25, 'n', 54321);

INSERT INTO receitas
VALUES ('9', 5.99, 11.99, 'Strogonoff de Frango');

INSERT INTO receitas_contem_ingredientes (ingredientes_cod, receitas_codBarras, peso)
VALUES ('123', '9', 0.100);
INSERT INTO receitas_contem_ingredientes (ingredientes_cod, receitas_codBarras, peso)
VALUES ('321', '9', 0.150);
INSERT INTO receitas_contem_ingredientes (ingredientes_cod, receitas_codBarras, peso)
VALUES ('54321', '9', 0.050);

INSERT INTO vendas (cod, data, total, formaPagamento)
values (default, '2018-11-06 12:40:00', 30.0, 'Dinheiro');
INSERT INTO vendas (cod, data, total, formaPagamento)
values (default, '2018-11-10 12:40:00', 100.0, 'Cartão');
INSERT INTO vendas (cod, data, total, formaPagamento)
values (default, '2018-11-13 13:10:00', 300.0, 'Cartão');
INSERT INTO vendas (cod, data, total, formaPagamento)
values (default, '2018-11-18 15:30:00', 1000.0, 'Cartão');
INSERT INTO vendas (cod, data, total, formaPagamento)
values (default, '2018-11-20 10:30:00', 35.0, 'Dinheiro');
INSERT INTO vendas (cod, data, total, formaPagamento)
values (default, '2018-11-23 09:20:00', 40.0, 'Cartão');
INSERT INTO vendas (cod, data, total, formaPagamento)
values (default, '2018-11-26 07:40:00', 12.0, 'Cartão');


INSERT INTO vendas_contem_produtos (vendas_cod, produtos_codBarras, quantidade)
VALUES (1, 2, 4);
INSERT INTO vendas_contem_produtos (vendas_cod, produtos_codBarras, quantidade)
VALUES (2, 1, 4);
INSERT INTO vendas_contem_produtos (vendas_cod, produtos_codBarras, quantidade)
VALUES (2, 2, 4);
INSERT INTO vendas_contem_produtos (vendas_cod, produtos_codBarras, quantidade)
VALUES (2, 3, 4);
INSERT INTO vendas_contem_produtos (vendas_cod, produtos_codBarras, quantidade)
VALUES (3, 3, 4);
INSERT INTO vendas_contem_produtos (vendas_cod, produtos_codBarras, quantidade)
VALUES (4, 5, 4);
INSERT INTO vendas_contem_produtos (vendas_cod, produtos_codBarras, quantidade)
VALUES (4, 6, 4);

INSERT INTO vendas_contem_receitas (vendas_cod, receitas_codBarras, quantidade)
VALUES (1, '9', 1);
INSERT INTO vendas_contem_receitas (vendas_cod, receitas_codBarras, quantidade)
VALUES (2, '9', 3);
INSERT INTO vendas_contem_receitas (vendas_cod, receitas_codBarras, quantidade)
VALUES (3, '9', 2);
INSERT INTO vendas_contem_receitas (vendas_cod, receitas_codBarras, quantidade)
VALUES (4, '9', 1);
INSERT INTO vendas_contem_receitas (vendas_cod, receitas_codBarras, quantidade)
VALUES (5, '9', 1);
INSERT INTO vendas_contem_receitas (vendas_cod, receitas_codBarras, quantidade)
VALUES (6, '9', 1);
INSERT INTO vendas_contem_receitas (vendas_cod, receitas_codBarras, quantidade)
VALUES (7, '9', 2);

#Relatório diário de vendas
select codBarras,
       data,
       date_format(data, '%H:%i')   as hora,
       nome,
       sum(quantidade)              as quantidade,
       pCusto,
       pVenda,
       sum(lucro_real * quantidade) as lucro
from RELATORIO_VENDAS
where Day(data) = Day(curdate())
group by nome
order by nome;

#Relatório mensal de vendas
select codBarras, nome, sum(quantidade) as quantidade, pCusto, pVenda, sum(lucro_real * quantidade) as lucro
from RELATORIO_VENDAS
where MONTH(data) = MONTH(now()) AND YEAR(data) = YEAR(now())
group by nome
order by nome;


#Relatório anual de vendas
select codBarras, nome, sum(quantidade) as quantidade, pCusto, pVenda, sum(lucro_real * quantidade) as lucro
from RELATORIO_VENDAS
where YEAR(data) = YEAR(now())
group by nome
order by nome;