create table grouppa_students_list (
                                       group_id bigint references grouppa,
                                       students_list_id bigint unique references student
);

alter table grouppa_students_list owner to postgres;
