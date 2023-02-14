CREATE TABLE IF NOT EXISTS social_network.posts
(
    author_id uuid,
    post_id   uuid,
    content   text,
    time      time,
    date      date,

    PRIMARY KEY ((author_id), post_id)
)    