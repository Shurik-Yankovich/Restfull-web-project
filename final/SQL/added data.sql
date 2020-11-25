use expexchangeservice;

insert into User
values 
(1,'admin','$2a$10$Y95gYv1s5yVBDHuZo.FlYegHhB26WPfj6QLusSAAI2sE5t8FNMyRy','ADMIN');

insert into Profile
values 
(1,1,'Abraham Linkoln','Parlament USA');

insert into Section
values 
(1,'Test section');

insert into Theme
values 
(1,'Test theme 1'),
(2,'Test theme 2');

insert into Section_Themes
values 
(1,1),
(1,2);