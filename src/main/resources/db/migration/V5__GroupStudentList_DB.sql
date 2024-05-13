create table grouppa_students_list (
                                       group_id bigint references grouppa,
                                       student_id bigint references student
);

alter table grouppa_students_list owner to postgres;
