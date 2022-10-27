insert into regiones (nombre) values ('Europa');
insert into regiones (nombre) values ('Asia');
insert into regiones (nombre) values ('NorteAmerica');
insert into regiones (nombre) values ('Sudamerica');
insert into regiones (nombre) values ('Africa');
insert into regiones (nombre) values ('Oceania');


insert into clientes (nombre,apellido,email,telefono,created_at,id_region) values('Jose','Perez','jp@email.com',6321545,'2022-10-24',1);
insert into clientes (nombre,apellido,email,telefono,created_at,id_region) values('Manuel','Perez','jp@email.com',6321545,'2022-10-24',2);
insert into clientes (nombre,apellido,email,telefono,created_at,id_region) values('Pedro','Perez','jp@email.com',6321545,'2022-10-24',3);
insert into clientes (nombre,apellido,email,telefono,created_at,id_region) values('Luis','Perez','jp@email.com',6321545,'2022-10-24',4);
insert into clientes (nombre,apellido,email,telefono,created_at,id_region) values('Antonio','Perez','jp@email.com',6321545,'2022-10-24',5);
insert into clientes (nombre,apellido,email,telefono,created_at,id_region) values('Laura','Perez','jp@email.com',6321545,'2022-10-24',6);

insert into roles (nombre) values ('ROLE_USER');
insert into roles (nombre) values ('ROLE_ADMIN');

insert into usuarios (username,password,enabled) values ('manuel','$2a$10$MioWi1FlXmSJuvYBpumBa.h9ZuJh8CAxMeYqJxdRhEMw6k9GSjJ5a',1);
insert into usuarios (username,password,enabled) values ('mada','$2a$10$Uiu5e4s1NVf2Eq9e5e8hYunpYg02KEArSCbv/IWDgnV6BOgZFyL3W',0);

insert into usuario_roles (usuario_id,role_id) values (1,1);