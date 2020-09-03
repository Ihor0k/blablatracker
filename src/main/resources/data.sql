INSERT INTO `department` VALUES (1, 'Frontend team');
INSERT INTO `department` VALUES (2, 'Backend team');

INSERT INTO `user` VALUES (1, 'Jane Doe', 1);
INSERT INTO `user` VALUES (2, 'Tom Smith', 1);
INSERT INTO `user` VALUES (3, 'Hilary Ouse', 1);

INSERT INTO `user` VALUES (4, 'Norman Gordon', 2);
INSERT INTO `user` VALUES (5, 'Richard Tea', 2);
INSERT INTO `user` VALUES (6, 'Miles Tone', 2);
INSERT INTO `user` VALUES (7, 'Desmond Eagle', 2);

INSERT INTO `task` VALUES (1, 'Create tasks', 'Create and estimate tasks', 1, '2020-09-01 12:00:00', 1, 2);
INSERT INTO `task` VALUES (2, 'Main page', 'Create main page for the website', 1, '2020-09-02 12:00:00', 2, 0);
INSERT INTO `task` VALUES (3, 'About page', 'Create about page for the website', 1, '2020-09-01 12:00:00', 3, 1);
INSERT INTO `task` VALUES (4, 'Fix bug #42', 'Fix the bug', 2, '2020-09-02 12:00:00', 2, 2);
INSERT INTO `task` VALUES (5, 'Fix margins', 'Increase top margin on header by 2px', 3, '2020-09-01 12:00:00', 2, 2);
INSERT INTO `task` VALUES (6, 'Make demo', 'Make a demo of the last changes ', 1, '2020-09-03 12:00:00', 2, 1);

INSERT INTO `task` VALUES (7, 'REST controller', 'Implement REST controller for the website', 4, '2020-09-03 12:00:00', 5, 0);
INSERT INTO `task` VALUES (8, 'DB tables', 'Create database tables', 4, '2020-09-02 12:00:00', 6, 2);
INSERT INTO `task` VALUES (9, 'Business logic', 'Implement business logic', 4, '2020-09-01 12:00:00', 7, 1);
INSERT INTO `task` VALUES (10, 'Call with frontend team', 'Discuss APIs with the frontend team', 4, '2020-09-02 12:00:00', 4, 2);
INSERT INTO `task` VALUES (11, 'Fix NPE', 'See request in attachments', 6, '2020-09-03 12:00:00', 5, 0);
INSERT INTO `task` VALUES (12, 'Refactor HugeFile.java', 'Split it into smaller classes', 7, '2020-09-01 12:00:00', 6, 0);

INSERT INTO `comment` VALUES (1, 'When is it?', 2, '2020-09-03 18:00:00', 6);
INSERT INTO `comment` VALUES (2, 'It\'s on Wednesday', 1, '2020-09-03 18:00:00', 6);

INSERT INTO `comment` VALUES (3, 'Can\'t reproduce', 2, '2020-09-03 18:00:00', 4);

INSERT INTO `comment` VALUES (4, 'There are no any attachments', 5, '2020-09-03 18:00:00', 11);
INSERT INTO `comment` VALUES (5, 'Oh, sorry. Check it now', 6, '2020-09-03 18:00:00', 11);

INSERT INTO `comment` VALUES (6, 'Richard can help you', 7, '2020-09-03 18:00:00', 12);