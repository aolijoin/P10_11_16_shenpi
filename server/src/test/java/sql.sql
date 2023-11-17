drop database if exists approval;
create database if not exists approval;
use approval;
create table oder_number
(
    id     int auto_increment
        primary key,
    code   varchar(50)  null,
    name   varchar(50)  null,
    type   varchar(50)  null,
    remark varchar(255) not null
);

INSERT INTO approval.oder_number (code, name, type, remark) VALUES ('xxx01', '电池', '物料入库', 'iphon');
INSERT INTO approval.oder_number (code, name, type, remark) VALUES ('xxx02', 'RPG火3', '报销单据', 'iphone13电池');
INSERT INTO approval.oder_number (code, name, type, remark) VALUES ('A001', 'Oppo', '物料出库', 'adob');
INSERT INTO approval.oder_number (code, name, type, remark) VALUES ('A001', '号早上', '报销单据', '水平低');

create table tb_shenpi
(
    id          int auto_increment
        primary key,
    code        varchar(50) null,
    name        varchar(50) null,
    status      int         null,
    type        varchar(50) null,
    shenpi_time datetime    null,
    remark      varchar(50) null,
    order_num   varchar(80) null
);

INSERT INTO approval.tb_shenpi (code, name, status, type, shenpi_time, remark, order_num) VALUES ('xs20230513', '售后电池故障维护电池出库申请', 0, '物料入库', '2023-05-13 10:11:13', 'iphone13售后电池故障维护电池出库申请单', '1,4');
