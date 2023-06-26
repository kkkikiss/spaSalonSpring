CREATE TABLE spa_salons
(
    id         bigserial PRIMARY KEY,
    mark       character varying(20) NOT NULL,
    name_salon character varying(20) NOT NULL,
    cost       integer,
    id_profile bigint                NOT NULL,

    CONSTRAINT fk_spa_salons_to_profile FOREIGN KEY (id_profile)
        REFERENCES profile (id)
);