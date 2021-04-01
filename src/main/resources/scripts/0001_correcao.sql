--liquibase formatted sql

--changeset Leonardo Rocha:01
update animal set usuario = 1;

--changeset Leonardo Rocha:02
delete from usuario where id > 1;

--changeset Leonardo Rocha:03
ALTER TABLE public.usuario ADD CONSTRAINT usuario_un UNIQUE (sub);
