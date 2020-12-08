create table faculty
(
    faculty_id          int(7) auto_increment,
    faculty_name        varchar(45)   not null,
    faculty_description varchar(1000) null,
    faculty_status      varchar(40)   null,
    constraint faculty_faculty_id_uindex
        unique (faculty_id)
);

alter table faculty
    add primary key (faculty_id);

create table passport
(
    passport_id                int(7) auto_increment
        primary key,
    personal_number            varchar(14) not null,
    passport_series_and_number varchar(9)  not null,
    constraint passport_passport_series_and_number_uindex
        unique (passport_series_and_number)
);

create table enrollee
(
    enrollee_id          int(7) auto_increment
        primary key,
    email                varchar(40)  not null,
    password             varchar(120) not null,
    role                 varchar(40)  not null,
    enrollee_first_name  varchar(40)  null,
    enrollee_last_name   varchar(40)  null,
    enrollee_middle_name varchar(40)  null,
    passport_id_fk       int(7)       null,
    application_status   varchar(10)  null,
    constraint enrollee_email_uindex
        unique (email),
    constraint enrollee_passport_passport_id_fk
        foreign key (passport_id_fk) references passport (passport_id)
);

create index enrollee_passport_id_fk_index
    on enrollee (passport_id_fk);

create table enrollee_faculty
(
    enrollee_id_fk int(7) null,
    faculty_id_fk  int(7) null,
    constraint enrollee_faculty_pk
        unique (enrollee_id_fk),
    constraint enrollee_faculty_enrollee_enrollee_id_fk
        foreign key (enrollee_id_fk) references enrollee (enrollee_id),
    constraint enrollee_faculty_faculty_faculty_id_fk
        foreign key (faculty_id_fk) references faculty (faculty_id)
);

create index enrollee_faculty_faculty_id_fk_index
    on enrollee_faculty (faculty_id_fk);

create index passport_passport_id_index
    on passport (passport_id);

create table specialty
(
    specialty_id     int(7) auto_increment
        primary key,
    specialty_name   varchar(120) not null,
    faculty_id_fk    int(7)       not null,
    recruitment      varchar(10)  null,
    number_of_seats  int(3)       null,
    specialty_status varchar(40)  null,
    constraint faculty_id_fk
        foreign key (faculty_id_fk) references faculty (faculty_id)
            on update cascade on delete cascade
);

create table enrollee_specialty
(
    enrollee_id_fk  int(7) null,
    specialty_id_fk int(7) null,
    constraint enrollee_specialty_enrollee_enrollee_id_fk
        foreign key (enrollee_id_fk) references enrollee (enrollee_id),
    constraint enrollee_specialty_specialty_specialty_id_fk
        foreign key (specialty_id_fk) references specialty (specialty_id)
);

create index enrollee_specialty_enrollee_id_fk_index
    on enrollee_specialty (enrollee_id_fk);

create index enrollee_specialty_specialty_id_fk_index
    on enrollee_specialty (specialty_id_fk);

create index specialty_faculty_id_fk_index
    on specialty (faculty_id_fk);

create table subject
(
    subject_id   int(7) auto_increment
        primary key,
    subject_name varchar(30) not null
);

create table mark
(
    mark_id        int(7) auto_increment
        primary key,
    enrollee_id_fk int(7) not null,
    subject_id_fk  int(7) not null,
    mark_value     int(3) not null,
    constraint mark_enrollee_enrollee_id_fk
        foreign key (enrollee_id_fk) references enrollee (enrollee_id),
    constraint mark_subject_subject_id_fk
        foreign key (subject_id_fk) references subject (subject_id)
);

create index mark_enrollee_id_fk_index
    on mark (enrollee_id_fk);

create index mark_subject_id_fk_index
    on mark (subject_id_fk);

create table subject_specialty
(
    specialty_id_fk int(7) not null,
    subject_id_fk   int(7) not null,
    constraint specialty_id_fk
        foreign key (specialty_id_fk) references specialty (specialty_id),
    constraint subject_id_fk
        foreign key (subject_id_fk) references subject (subject_id)
);

create index subject_specialty_specialty_id_fk_index
    on subject_specialty (specialty_id_fk);

create index subject_specialty_subject_id_fk_index
    on subject_specialty (subject_id_fk);


