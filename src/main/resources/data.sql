

insert into App_User (DTYPE, USER_ID, USER_NAME, ENCRYPTED_PASSWORD, ENABLED)
values ('AppUser', 0, 'admin', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);
 
---
 
insert into app_role (ROLE_ID, ROLE_NAME)
values (1, 'ROLE_ADMIN');
 
insert into app_role (ROLE_ID, ROLE_NAME)
values (2, 'ROLE_USER');

insert into app_role (ROLE_ID, ROLE_NAME)
values (3, 'ROLE_SPECIALIST');
 
---
 
insert into user_role (ID, USER_ID, ROLE_ID)
values (1, 0, 1);