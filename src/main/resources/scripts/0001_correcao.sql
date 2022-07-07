--liquibase formatted sql

--changeset Leonardo Rocha:01
update animal set usuario = 1;

--changeset Leonardo Rocha:02
delete from usuario where id > 1;

--changeset Leonardo Rocha:03
ALTER TABLE public.usuario ADD CONSTRAINT usuario_un UNIQUE (sub);


--changeset Leonardo Rocha:04
update medicamento set tipo_medicamento = 'medicamento';

--changeset Leonardo Rocha:05
INSERT INTO medicamento (data_medicamento, data_proxima, nome, tipo_medicamento, animal)
select ap.data_anti_pulga , ap.data_proxima , ap.nome , 'antipulga' , ap.animal from anti_pulga ap ;

--changeset Leonardo Rocha:06
INSERT INTO medicamento (data_medicamento, data_proxima, nome, tipo_medicamento, animal)
select v.data_vermifugo , v.data_proximo_vermifugo , v.nome , 'vermifugo', v.animal from vermifugo v ;
