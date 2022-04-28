-- create user
create user tipocambio with encrypted password '1234';
-- create database
create database dbtipocambio;
-- otorgar permisos
grant all privileges on database dbtipocambio to tipocambio;