CREATE TABLE IF NOT EXISTS social_network.photos
(
    user_id    uuid,
    photo_id   uuid,
    photo_link text,
    date       date,
    image_name text,

    PRIMARY KEY ((user_id), photo_id)
)