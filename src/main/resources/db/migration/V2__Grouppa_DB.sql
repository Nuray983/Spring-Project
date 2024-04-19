create table grouppa (
                         id bigint default nextval('grouppa_id_seq'::regclass) not null primary key,
                         group_name varchar(255),
                         quantity integer
);

alter table grouppa owner to postgres;
