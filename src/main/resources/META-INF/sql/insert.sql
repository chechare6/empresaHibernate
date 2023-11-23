INSERT INTO hib_departamento (nombre, jefe) VALUES ('Finanzas', NULL), ('Linux', NULL), ('Ventas', NULL);
INSERT INTO hib_empleado (nombre, salario, nacimiento, departamento) VALUES ('Ana Arias', '900','1983-03-21', 1), ('Luis Leiva', '1234.56','1992-07-12', 3), ('Milena Mira', '1134.34','1968-12-12', 3) ON DUPLICATE KEY UPDATE nombre = nombre;
UPDATE hib_departamento SET jefe = 1 WHERE id = 1;
UPDATE hib_departamento SET jefe = 3 WHERE id = 3;
INSERT INTO hib_proyecto (nombre) VALUES ('Proyecto1'), ('Proyecto2') ON DUPLICATE KEY UPDATE nombre = nombre;