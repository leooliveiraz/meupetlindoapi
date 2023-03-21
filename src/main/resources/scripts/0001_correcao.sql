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


--changeset Leonardo Rocha:07
ALTER TABLE vermifugo RENAME CONSTRAINT fkhn46xh8byrd1cpi99jjvu9nrj TO vermifugo_fk;
ALTER TABLE vacina RENAME CONSTRAINT fk6ee6rl85beh82u706xuwnvwur TO vacina_fk;
ALTER TABLE peso RENAME CONSTRAINT fk4sli8balxum9c62j9lvsg58a9 TO peso_fk;
ALTER TABLE medicamento RENAME CONSTRAINT fkoyk21mwmg32a6dg75addppid9 TO medicamento_fk;
ALTER TABLE inscricao RENAME CONSTRAINT fkrdtdb2b0yku7j13qrjfxt2eap TO inscricao_fk;
ALTER TABLE exame RENAME CONSTRAINT fkhvu1lyuqny3fgvkoeonjabhhr TO exame_fk;
ALTER TABLE compartilhamento_animal RENAME CONSTRAINT fkgbwqyhhday73j3cbtdfyrkn17 TO compartilhamento_usuario_fk;
ALTER TABLE compartilhamento_animal RENAME CONSTRAINT fktff5fn5l2si1vni4b8kos33c2 TO compartilhamento_animal_fk;
ALTER TABLE arquivo RENAME CONSTRAINT fk86dtqveb43h1i3e529xybm5ax TO arquivo_fk;
ALTER TABLE anti_pulga RENAME CONSTRAINT fkr2avwltce8kq7kbnayyud3iy8 TO antipulga_fk;
ALTER TABLE animal RENAME CONSTRAINT fk7g0lbe4bj4fgcmbvr8e41groy TO animal_usuario_fk;
ALTER TABLE animal RENAME CONSTRAINT fkkqwgbxb1aecjw8mqagtlfifi2 TO animal_imagem_fk;

--changeset Leonardo Rocha:08
ALTER TABLE consulta DROP CONSTRAINT consulta_fk;
ALTER TABLE consulta ADD CONSTRAINT consulta_fk FOREIGN KEY (animal) REFERENCES animal(id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE compartilhamento_animal DROP CONSTRAINT compartilhamento_animal_fk;
ALTER TABLE compartilhamento_animal ADD CONSTRAINT compartilhamento_animal_fk FOREIGN KEY (animal) REFERENCES animal(id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE vermifugo DROP CONSTRAINT vermifugo_fk;
ALTER TABLE vermifugo ADD CONSTRAINT vermifugo_fk FOREIGN KEY (animal) REFERENCES animal(id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE vacina DROP CONSTRAINT vacina_fk;
ALTER TABLE vacina ADD CONSTRAINT vacina_fk FOREIGN KEY (animal) REFERENCES animal(id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE peso DROP CONSTRAINT peso_fk;
ALTER TABLE peso ADD CONSTRAINT peso_fk FOREIGN KEY (animal) REFERENCES animal(id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE medicamento DROP CONSTRAINT medicamento_fk;
ALTER TABLE medicamento ADD CONSTRAINT medicamento_fk FOREIGN KEY (animal) REFERENCES animal(id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE exame DROP CONSTRAINT exame_fk;
ALTER TABLE exame ADD CONSTRAINT exame_fk FOREIGN KEY (animal) REFERENCES animal(id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE anti_pulga DROP CONSTRAINT antipulga_fk;
ALTER TABLE anti_pulga ADD CONSTRAINT antipulga_fk FOREIGN KEY (animal) REFERENCES animal(id) ON DELETE CASCADE ON UPDATE CASCADE;
