delete from ComentarioEntity;
delete from ActividadEntity;
delete from UsuarioEntity;
delete from CoordinadorEntity;
delete from AdministradorEntity;
delete from AnfitrionEntity;

insert into COMENTARIOENTITY (titulo, texto, fecha, id) values ('Networked', 'velit vivamus vel nulla eget eros', '2019-03-21 00:00:00', 1);
insert into COMENTARIOENTITY (titulo, texto, fecha, id) values ('model', 'iaculis congue vivamus metus arcu adipiscing molestie', '2019-03-21 00:00:00', 2);
insert into COMENTARIOENTITY (titulo, texto, fecha, id) values ('Team-oriented', 'aenean lectus pellentesque eget nunc donec quis', '2019-05-01 00:00:00', 3);
insert into COMENTARIOENTITY (titulo, texto, fecha, id) values ('responsive', 'lobortis ligula sit amet eleifend', '2019-04-29 00:00:00', 4);



Select * from ActividadEntity;
Select * from ComentarioEntity;
SELECT ID, FECHA, TEXTO, TITULO, ACTIVIDAD_ID, AUTOR_ID, CALIFICACIONES_ID FROM COMENTARIOENTITY;


