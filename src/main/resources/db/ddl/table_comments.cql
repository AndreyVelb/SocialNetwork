CREATE TABLE IF NOT EXISTS social_network.comments
(
    post_id          uuid,
    author_id        uuid,
    comment_id       uuid,
    content          text,
    author_full_name text,
    time             time,
    date             date,

    PRIMARY KEY ((post_id), author_id, comment_id)
)