
-- Things
INSERT INTO Thing (id, name) VALUES (1, 'premade thing 1');
INSERT INTO Thing (id, name) VALUES (2, 'premade thing 2');

-- Users
INSERT INTO User (id, username, role) VALUES (1, 'john', 'USER');
INSERT INTO User (id, username, role) VALUES (2, 'jane', 'USER');

-- Sessions
INSERT INTO Authentication (id, token, user) VALUES (1, 'asd', 1);
INSERT INTO Authentication (id, token, user) VALUES (2, 'qwe', 2);
