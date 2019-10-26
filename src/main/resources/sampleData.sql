insert into role(name) values ('ROOT');
insert into role(name) values ('ADMIN');
insert into role(name) values ('USER');

insert into user_profile(firstname, lastname) values('Andrew', 'Waterfall');

insert into user(email, password, user_profile_id) values('awaterfall@gmail.com', 'password', 1);

insert into user_role(user_id, role_id) values(1, 1);

insert into board(name, user_id) values ('Test board', 1);
insert into board_column(column_index, name, board_id) values (1, 'Board column', 1);
insert into board_row(content, row_index, board_column_id) values ('It is a content of the row', 1, 1);

