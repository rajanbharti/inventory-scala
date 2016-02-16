create database if not exists inventory;

use inventory;

create table if not exists inventory_info (id int auto_increment primary key, inv_name varchar(15), inv_location varchar(15));

create table if not exists orders (id int auto_increment primary key,item varchar(15),qty int);

create table if not exists inventory(inv_id int, item varchar(15), qty int);

create table if not exists items (item_id int auto_increment primary key, item_name varchar(15));