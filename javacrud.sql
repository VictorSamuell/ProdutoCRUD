CREATE DATABASE IF NOT EXISTS javacrud;

USE javacrud;

CREATE TABLE IF NOT EXISTS product_tbl (
    id INT PRIMARY KEY AUTO_INCREMENT,
    pname VARCHAR(255) NOT NULL,
    price VARCHAR(20) NOT NULL,
    qty VARCHAR(20) NOT NULL
);

INSERT INTO product_tbl (pname, price, qty) VALUES
('Pepsi', '5.50', '100'),
('Nescau', '8.5', '120'),
('Coca-Cola', '6.25', '80');


CREATE TABLE IF NOT EXISTS category_tbl (
    id INT PRIMARY KEY AUTO_INCREMENT,
    category VARCHAR(255) NOT NULL,
    product_id INT,
    FOREIGN KEY (product_id) REFERENCES product_tbl(id)
);


INSERT INTO category_tbl (category, product_id) VALUES
('Refrigerante', 1),
('Achocolatado', 2),
('Refrigerante', 3);


CREATE TABLE IF NOT EXISTS reclama_tbl (
    id INT PRIMARY KEY AUTO_INCREMENT,
    descricao varchar(255) NOT NULL
    
);

CREATE TABLE IF NOT EXISTS cliente_tbl (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome_cliente VARCHAR(255) NOT NULL
);




