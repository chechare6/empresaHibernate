INSERT IGNORE INTO hib_departamento (nombre, jefe) VALUES ('Finanzas', NULL), ('Linux', NULL), ('Ventas', NULL);
INSERT IGNORE INTO hib_empleado (nombre, salario, departamento) VALUES ('Ana Arias', NULL, 1), ('Luis Leiva', '1234.56', 3), ('Milena Mira', NULL, 3);
INSERT IGNORE INTO hib_proyecto (nombre) VALUES ('Proyecto1'), ('Proyecto2');