CREATE TABLE requestlog
(
    id SERIAL PRIMARY KEY,
    login text,
    requestCount numeric,
    createdAt timestamp without time zone NOT NULL,
    modifiedAt timestamp without time zone
);