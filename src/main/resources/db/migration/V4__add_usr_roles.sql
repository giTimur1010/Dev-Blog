--create tables

create table public.role
(
    id   bigint       NOT NULL GENERATED ALWAYS AS IDENTITY,
    name varchar(100) not null,
    CONSTRAINT usr_role_pk PRIMARY KEY (id)
);

ALTER TABLE public.role
    OWNER TO postgres;

create table public.usr_roles
(
    id_role bigint NOT NULL,
    id_usr  bigint NOT NULL,
    CONSTRAINT many_usr_has_many_role PRIMARY KEY (id_role, id_usr)
);

alter table public.usr_roles
    owner to postgres;

--fill in data

insert into public.role (name)
values ('USER'),
       ('ADMIN')