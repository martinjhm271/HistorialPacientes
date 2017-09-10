

-- ----------------------------
-- Table structure for eps
-- ----------------------------
DROP TABLE IF EXISTS paciente;
DROP TABLE IF EXISTS consulta;
DROP TABLE IF EXISTS eps;

CREATE TABLE eps (
"id" SERIAL NOT NULL PRIMARY KEY,
"nombre" varchar(100)  NOT NULL,
"nit" varchar(100)  NOT NULL
);

-- ----------------------------
-- Records of eps
-- ----------------------------
INSERT INTO eps VALUES (DEFAULT,'Compensar', 'COM');
INSERT INTO eps VALUES (DEFAULT,'Sanitas', 'SAM');
INSERT INTO eps VALUES (DEFAULT,'Sura', 'SUR');
INSERT INTO eps VALUES (DEFAULT,'Coomeva', 'COO');
INSERT INTO eps VALUES (DEFAULT,'Medimas', 'MED');
INSERT INTO eps VALUES (DEFAULT,'SaludTotal', 'SAL');

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Uniques structure for table eps
-- ----------------------------
ALTER TABLE eps ADD UNIQUE ("nombre");




-- ----------------------------
-- Table structure for paciente
-- ----------------------------


CREATE TABLE paciente (
"id" SERIAL NOT NULL PRIMARY KEY,
"tipo_id" varchar(2)  NOT NULL,
"nombre" varchar(100)NOT NULL,
"fechanacimiento" date NOT NULL,
"eps_id" int4 NOT NULL
);


-- ----------------------------
-- Records of paciente
-- ----------------------------
INSERT INTO paciente VALUES (DEFAULT,'CC', 'Juan Perez', '01-01-02', '1');
INSERT INTO paciente VALUES (DEFAULT,'CC', 'Maria Rodriguez', '01-01-02', '2');
INSERT INTO paciente VALUES (DEFAULT,'CC', 'Pedro Martinez', '01-01-02', '3');
INSERT INTO paciente VALUES (DEFAULT,'CC', 'Martin Hernandez', '01-01-02', '4');
INSERT INTO paciente VALUES (DEFAULT,'CC', 'Cristian Pinzon', '01-01-02', '4');
INSERT INTO paciente VALUES (DEFAULT,'CC', 'Daniel Beltran', '56-01-01', '5');
INSERT INTO paciente VALUES (DEFAULT,'CC', 'Ricardo Pinto', '56-01-01', '6');

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Uniques structure for table paciente
-- ----------------------------
ALTER TABLE paciente ADD UNIQUE ("nombre");


-- ----------------------------
-- Foreign Key structure for table paciente
-- ----------------------------
ALTER TABLE paciente ADD FOREIGN KEY ("eps_id") REFERENCES eps ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;





-- ----------------------------
-- Table structure for consulta
-- ----------------------------


CREATE TABLE consulta (
"id" SERIAL NOT NULL PRIMARY KEY,
"fechayhora" date NOT NULL,
"resumen" varchar(100)  NOT NULL,
"costo" int8 NOT NULL,
"paciente_id" int4 NOT NULL
);


-- ----------------------------
-- Records of consulta
-- ----------------------------
INSERT INTO consulta VALUES (DEFAULT,'01-01-02', 'Dolor de cabeza', '454', '1');
INSERT INTO consulta VALUES (DEFAULT,'01-01-02', 'Dolor de estomago', '271', '1');
INSERT INTO consulta VALUES (DEFAULT, '01-01-02', 'Dolor de garganta', '222', '1');
INSERT INTO consulta VALUES (DEFAULT, '01-01-02', 'Gripa', '54', '4');
INSERT INTO consulta VALUES (DEFAULT, '01-01-02', 'Fiebre', '71', '5');
INSERT INTO consulta VALUES (DEFAULT,'01-01-02', 'Malestar y mareo', '322', '5');
INSERT INTO consulta VALUES (DEFAULT, '01-01-02', 'Vomito', '322', '6');
INSERT INTO consulta VALUES (DEFAULT, '01-01-02', 'Alergia', '322', '7');
INSERT INTO consulta VALUES (DEFAULT, '01-01-02', 'Resfriado', '322', '3');

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Foreign Key structure for table consulta
-- ----------------------------
ALTER TABLE consulta ADD FOREIGN KEY ("paciente_id") REFERENCES paciente ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

