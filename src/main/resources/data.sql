--Eメールは「example@example.com」、パスワードは「password」
INSERT INTO user_info VALUES(NULL ,'John', 'example@example.com', '$2a$08$dSbk7S1tzk9I8CDB0mr6CO7ndTkTdC9/nojADBiukhWVBgHqR/g46',true, 'something','temp');
INSERT INTO task_type VALUES(1, 'Main', 'This is a main task');
INSERT INTO task_type VALUES(2, 'Sub', 'This is a sub task');
INSERT INTO tasks VALUES(NULL, 1, 1, 'DoIt', 'I will do it', '2019-07-07');