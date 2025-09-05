--liquibase formatted sql

--changeset DanielK:1


CREATE TABLE IF NOT EXISTS interviews (
    id SERIAL PRIMARY KEY,
    job_title TEXT,
    result TEXT,
    conversation_id INTEGER,
    user_id INTEGER,
    properties TEXT,
    updated TIMESTAMP,
    created TIMESTAMP DEFAULT current_timestamp
    );

CREATE TABLE IF NOT EXISTS vacancy (
                                       id SERIAL PRIMARY KEY,
                                       title TEXT,
                                       source_url TEXT,
                                       user_id INTEGER,
                                       updated TIMESTAMP,
                                       created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS skills (
                                      id SERIAL PRIMARY KEY,
                                      name TEXT,
                                      category TEXT,
                                      updated TIMESTAMP,
                                      created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS vacancy_skill (
                                             id SERIAL PRIMARY KEY,
                                             vacancy_id INTEGER NOT NULL,
                                             skill_id INTEGER NOT NULL,
                                             created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                             updated TIMESTAMP,

                                             CONSTRAINT fk_vacancy
                                             FOREIGN KEY (vacancy_id)
    REFERENCES vacancy(id)
    ON DELETE CASCADE,

    CONSTRAINT fk_vacancy_skill
    FOREIGN KEY (skill_id)
    REFERENCES skills(id)
    ON DELETE CASCADE,

    CONSTRAINT unique_vacancy_skill UNIQUE (vacancy_id, skill_id)
    );

CREATE TABLE IF NOT EXISTS topics (
                                      id SERIAL PRIMARY KEY,
                                      name TEXT,
                                      updated TIMESTAMP,
                                      created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      skill_id INTEGER,
                                      FOREIGN KEY (skill_id) REFERENCES skills(id) ON DELETE SET NULL
    );

CREATE TABLE topic_progress (
                                id SERIAL PRIMARY KEY,
                                user_id INT NOT NULL,
                                topic_id INT NOT NULL REFERENCES topics(id) ON DELETE CASCADE,

                                confidence_level INT CHECK (confidence_level BETWEEN 0 AND 100),
                                is_weak_area BOOLEAN DEFAULT FALSE,
                                last_reviewed TIMESTAMP,

                                created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                updated TIMESTAMP,

                                UNIQUE (user_id, topic_id)
);
