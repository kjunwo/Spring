-- 권한
INSERT INTO 권한 (authority_name) VALUES ('ADMIN');
INSERT INTO 권한 (authority_name) VALUES ('USER');
INSERT INTO 권한 (authority_name) VALUES ('INSTRUCTOR');

-- 사용자
INSERT INTO 사용자 (user_name, password, email, real_name, user_birthdate, user_authority, createdAt, point)
VALUES
    ('pengsoo', '$2b$12$A0kgVpplgbH3ZZ1E89441eacUXljTTt7nP8I3RdLtW0P6/CXdEnCm', 'pengsoo@email.com', '백병열' , '1999-09-09', 'ADMIN', NOW(), '10000')
    ('totoro', '$2b$12$A0kgVpplgbH3ZZ1E89441eacUXljTTt7nP8I3RdLtW0P6/CXdEnCm', 'totoro@email.com', '강준우',  '1999-05-24', 'USER', NOW(), '20000')
    ('fourbie', '$2b$12$A0kgVpplgbH3ZZ1E89441eacUXljTTt7nP8I3RdLtW0P6/CXdEnCm', 'fourbie@email.com', '정길수', '1999-06-06', 'INSTRUCTOR', NOW(), '30000')

-- 수강신청
INSERT INTO 수강신청 (user_name, subject_id, purchase_time)
VALUES  