CREATE TABLE users(
 id BIGSERIAL PRIMARY KEY,
 name VARCHAR(60) NOT NULL,
 email VARCHAR(254) UNIQUE NOT NULL,
 password VARCHAR(255) NOT NULL
);

CREATE TABLE links(
 id BIGSERIAL PRIMARY KEY,
 url_original TEXT NOT NULL,
 short_code VARCHAR(10) UNIQUE NOT NULL,

 user_id BIGINT,
    CONSTRAINT fk_links_users
                  FOREIGN KEY (user_id)
                  REFERENCES users(id)
                  ON DELETE CASCADE
);