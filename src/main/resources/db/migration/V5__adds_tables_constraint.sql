alter table if exists reservation
       add constraint FKrea93581tgkq61mdl13hehami
       foreign key (user_id)
       references users;

alter table if exists room_reservation
       add constraint FKk5ue6e363dj4dx91gjmk279wj
       foreign key (reservation_id)
       references reservation;

alter table if exists room_reservation
       add constraint FK19p6c3un3mbs7b7bxkcxk8xn2
       foreign key (room_id)
       references room;