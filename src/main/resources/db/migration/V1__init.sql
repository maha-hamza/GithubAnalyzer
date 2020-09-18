create TABLE IF NOT EXISTS search_history
(
   id                  VARCHAR(50)    PRIMARY KEY      NOT NULL,
   added_at            TIMESTAMP                       NOT NULL,
   added_by            VARCHAR(50)                     NOT NULL,
   repo_url            VARCHAR(50)                     NOT NULL,
   num_of_commits      BIGINT                     NOT NULL,
   num_of_prs          BIGINT                    NOT NULL,
   read_me              TEXT
);