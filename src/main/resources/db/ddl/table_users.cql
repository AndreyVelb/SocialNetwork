CREATE TABLE IF NOT EXISTS social_network.users
(
    id              uuid,
    last_name       text,
    first_name      text,
    user_name       text,
    birth_date      date,
    main_photo_link text,
    description     text,

    PRIMARY KEY ((id), last_name, first_name)
)