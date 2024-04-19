create table student (
                         id bigint default nextval('student_id_seq'::regclass) not null primary key,
                         age integer,
                         phone integer,
                         student_name varchar(255),
                         student_surname varchar(255),
                         group_number bigint references grouppa
);

alter table student owner to postgres;
