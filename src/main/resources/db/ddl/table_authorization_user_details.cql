CREATE TABLE IF NOT EXISTS social_network.authorization_user_details
(
    mail       text,
    id         uuid,
    password   text,
    role       text,
    last_name  text,
    first_name text,

    PRIMARY KEY ((mail), id)
)