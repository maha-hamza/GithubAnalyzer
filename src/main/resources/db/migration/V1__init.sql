create TABLE IF NOT EXISTS search_history
(
   id                  INTEGER    PRIMARY KEY      NOT NULL,
   added_at            TIMESTAMP                       NOT NULL,
   added_by            VARCHAR(50)                     NOT NULL,
   repo_url            VARCHAR(50)                     NOT NULL,
   num_of_commits      INTEGER                     NOT NULL,
   num_of_prs          INTEGER                     NOT NULL,
   readme              TEXT
);