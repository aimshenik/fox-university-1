-- CREATE  Students

CREATE TABLE public."Students"
(
    id integer NOT NULL,
    "firstName" "char"[] NOT NULL,
    "lastName" "char"[] NOT NULL,
    CONSTRAINT "Students_id_pkey" PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."Students"
    OWNER to andrey;
    
-- CREATE  Groups    

CREATE TABLE public."Groups"
(
    id integer NOT NULL,
    name "char"[] NOT NULL,
    CONSTRAINT "Groups_id_pkey" PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."Groups"
    OWNER to andrey;
    
-- CREATE  GroupStudents
 
CREATE TABLE public."GroupStudents"
(
    id integer NOT NULL,
    student_id integer NOT NULL,
    group_id integer NOT NULL,
    CONSTRAINT "GroupStudent_id_pkey" PRIMARY KEY (id),
    CONSTRAINT "Group_fkey" FOREIGN KEY (group_id)
        REFERENCES public."Groups" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "Student_fkey" FOREIGN KEY (student_id)
        REFERENCES public."Students" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."GroupStudents"
    OWNER to andrey;
    
    