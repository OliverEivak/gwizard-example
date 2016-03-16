
-- Things
INSERT INTO Thing (id, name) VALUES (1, 'premade thing 1');
INSERT INTO Thing (id, name) VALUES (2, 'premade thing 2');

-- Users
INSERT INTO User (id, username, role, `password`) VALUES (1, 'john', 'USER', unhex('243261243130246a6b4b722e7364356e705565706557544d304f367a4f434a4e716575474f3565674b564c5476436830567a685531464c6e526b6a4f'));
INSERT INTO User (id, username, role, `password`) VALUES (2, 'jane', 'USER', unhex('243261243130243244334e4c35614772726a336e3052304a687a755365536a73346e51394365306a496e675a4569756b664b466442736c536568324f'));
-- user=john pw=test user=jane pw=best

-- Authentication
INSERT INTO Authentication (id, token, `user`) VALUES (1, 'asd', 1);
INSERT INTO Authentication (id, token, `user`) VALUES (2, 'qwe', 2);