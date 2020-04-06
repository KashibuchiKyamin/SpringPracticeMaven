-- 会議室
INSERT INTO meeting_room (room_name) VALUES ('新木場');
INSERT INTO meeting_room (room_name) VALUES ('月島');
INSERT INTO meeting_room (room_name) VALUES ('豊洲');
INSERT INTO meeting_room (room_name) VALUES ('辰巳');
INSERT INTO meeting_room (room_name) VALUES ('銀座一丁目');
INSERT INTO meeting_room (room_name) VALUES ('新富町');
INSERT INTO meeting_room (room_name) VALUES ('汐留');
INSERT INTO meeting_room (room_name) VALUES ('有楽町');


-- 会議室の予約可能日
-- room_id=1(新木場)
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE, 1);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE + 1, 1);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE - 1, 1);

INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE, 2);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE + 1, 2);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE - 1, 2);

INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE, 3);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE + 1, 3);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE - 1, 3);

INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE, 4);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE + 1, 4);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE - 1, 4);

INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE, 5);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE + 1, 5);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE - 1, 5);

INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE, 6);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE + 1, 6);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE - 1, 6);

INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE, 7);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE + 1, 7);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE - 1, 7);

-- room_id=8(有楽町)
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE, 8);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE + 1, 8);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE - 1, 8);


-- ダミーユーザ(password = demo)
INSERT INTO usr (user_id, first_name, last_name, password, role_name)
VALUES ('taro-yamada', '太郎', '山田', '$2a$10$oxSJI.keBwxmsMLkcT9lPeAIxfNTPNQxpeywMrF7A3kVszwUTqfTK', 'USER');
INSERT INTO usr (user_id, first_name, last_name, password, role_name)
VALUES ('aaaa', 'aaa太郎', 'aaa', '$2a$10$oxSJI.keBwxmsMLkcT9lPeAIxfNTPNQxpeywMrF7A3kVszwUTqfTK', 'USER');

INSERT INTO usr (user_id, first_name, last_name, password, role_name)
VALUES ('Admin', 'Admin', 'Admin', '$2a$10$oxSJI.keBwxmsMLkcT9lPeAIxfNTPNQxpeywMrF7A3kVszwUTqfTK', 'ADMIN');
