INSERT INTO roles (role_name) VALUES('ADMIN');
INSERT INTO roles (role_name) VALUES('CASHIER');

INSERT INTO users (user_name, password, user_enabled) VALUES('admin', '$2a$10$Rb8k4GlbNkq4Y52ZjkCNAOherPqcoJYDG1lg5i2j9n4KCgJtAyOma', 1);
INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
