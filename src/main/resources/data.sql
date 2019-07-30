INSERT INTO USUARIO(nome, email, senha) VALUES('Aluno', 'aluno@gmail.com','12345');

INSERT INTO CURSO(nome, categoria) VALUES('Spring boot', 'Programação');
INSERT INTO CURSO(nome, categoria) VALUES('HTML 5', 'Front-end');

  INSERT INTO TOPICO(titulo, mensagem, data_criacao, status, curso_id, usuario_id) VALUES('Duvida1', 'Erro ao criar projeto', '2019-02-19 18:00','NAO_RESPONDIDO',1,1);
  INSERT INTO TOPICO(titulo, mensagem, data_criacao, status, curso_id, usuario_id) VALUES('Duvida2', 'Projeto nao compila', '2019-02-19 18:00','NAO_RESPONDIDO',1,1);
  INSERT INTO TOPICO(titulo, mensagem, data_criacao, status, curso_id, usuario_id) VALUES('Duvida3', 'Tag HTML', '2019-02-19 18:00' ,'NAO_RESPONDIDO',2,1);