use projetoclinica;

CREATE TABLE usuarios
(
usu_cod INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
usu_nome VARCHAR(45),
usu_senha VARCHAR(20),
usu_tipo VARCHAR(30)
);

INSERT INTO usuarios(usu_nome,usu_senha,usu_tipo) VALUES ('Matheus Souto','paunogato123','administrador');

CREATE TABLE medicos(
cod_medico INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
nome_medico VARCHAR(50) NOT NULL,
especialidade_medico VARCHAR(50) NOT NULL,
crm_medico INTEGER NOT NULL
)

CREATE TABLE bairro(
