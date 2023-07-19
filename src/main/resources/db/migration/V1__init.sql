CREATE TABLE public.usr (
                            id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ,
                            avatar varchar(50),
                            description varchar(250),
                            username varchar(100) NOT NULL,
                            full_name varchar(250),
                            email varchar(250) NOT NULL,
                            password varchar(200) NOT NULL,
                            birth_date date,
                            CONSTRAINT userid_pk PRIMARY KEY (id)
);

ALTER TABLE public.usr OWNER TO postgres;

CREATE TABLE public.article (
                                id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ,
                                id_user bigint,
                                title varchar(200) NOT NULL DEFAULT 'Без темы',
                                content text NOT NULL,
                                likes_number smallint DEFAULT 0,
                                thumbnail varchar(50),
                                created_date timestamp DEFAULT NOW(),
                                CONSTRAINT articleid_pk PRIMARY KEY (id)
);

ALTER TABLE public.article OWNER TO postgres;

CREATE TABLE public.comment (
                                id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ,
                                id_article bigint,
                                id_user bigint,
                                content varchar(240) NOT NULL,
                                likes_number smallint DEFAULT 0,
                                created_date timestamp DEFAULT NOW(),
                                number bigint NOT NULL DEFAULT 1,
                                CONSTRAINT commentpk PRIMARY KEY (id)
);

ALTER TABLE public.comment OWNER TO postgres;

CREATE TABLE public.tag (
                            id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ,
                            parent_id bigint,
                            name varchar(100) NOT NULL,
                            CONSTRAINT tag_pk PRIMARY KEY (id) );

ALTER TABLE public.tag OWNER TO postgres;

ALTER TABLE public.article ADD CONSTRAINT user_fk FOREIGN KEY (id_user)
    REFERENCES public.usr (id) MATCH FULL
    ON DELETE SET NULL ON UPDATE CASCADE;

CREATE TABLE public.article_tag (
                                    id_article bigint NOT NULL,
                                    id_tag bigint NOT NULL,
                                    CONSTRAINT many_article_has_many_tag_pk PRIMARY KEY (id_article,id_tag)
);

ALTER TABLE public.article_tag ADD CONSTRAINT article_fk FOREIGN KEY (id_article)
    REFERENCES public.article (id) MATCH FULL
    ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE public.article_tag ADD CONSTRAINT tag_fk FOREIGN KEY (id_tag)
    REFERENCES public.tag (id) MATCH FULL
    ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE public.comment ADD CONSTRAINT article_fk FOREIGN KEY (id_article)
    REFERENCES public.article (id) MATCH FULL
    ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE public.comment ADD CONSTRAINT user_fk FOREIGN KEY (id_user)
    REFERENCES public.usr (id) MATCH FULL
    ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE public.tag ADD CONSTRAINT parent_tag_pk FOREIGN KEY (parent_id)
    REFERENCES public.tag (id) MATCH SIMPLE
    ON DELETE NO ACTION ON UPDATE NO ACTION;