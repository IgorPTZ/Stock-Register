
// SEQUENCES

CREATE SEQUENCE public.serialproduto
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 3223372036854775807
  START 14
  CACHE 1;
ALTER TABLE public.serialproduto
  OWNER TO postgres;

CREATE SEQUENCE public.serialusuario
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 3223372036854775807
  START 48
  CACHE 1;
ALTER TABLE public.serialusuario
  OWNER TO postgres;
  
CREATE SEQUENCE public.serialusuariotelefone
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 3223372036854775807
  START 8
  CACHE 1;
ALTER TABLE public.serialusuariotelefone
  OWNER TO postgres;
  
  
// TABLES  
  
CREATE TABLE public.produto
(
  id bigint NOT NULL DEFAULT nextval('serialproduto'::regclass),
  nome character varying,
  quantidade double precision,
  valor double precision,
  CONSTRAINT produto_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE public.usuario
(
  login character varying,
  senha character varying,
  id bigint NOT NULL DEFAULT nextval('serialusuario'::regclass),
  nome character varying,
  telefone character varying,
  cep character varying,
  rua character varying,
  bairro character varying,
  cidade character varying,
  uf character varying,
  ibge character varying,
  imagem text,
  tipo_imagem text,
  documento text,
  tipo_documento text,
  CONSTRAINT usuario_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE public.usuario_telefone
(
  id bigint NOT NULL DEFAULT nextval('serialusuariotelefone'::regclass),
  numero character varying,
  tipo character varying,
  usuario bigint NOT NULL,
  CONSTRAINT usuario_telefone_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);