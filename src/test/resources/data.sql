-- user_list
INSERT INTO user_list (user_name, account, password, role, enable, created_date, last_modified_date)
VALUES ('管理員', 'admin', '$2a$10$ImVdIzxYdYmwlFbM9s1yuuc6HRRQfEJRuh3BnhOV5d1xDJ0ZpO9rW',
        'admin', true, '2022-12-29 16:08:05','2022-12-29 16:08:05');

INSERT INTO user_list (user_name, account, password, role, enable, created_date, last_modified_date)
VALUES ('員工', 'staff', '$2a$10$c/So5rcv8en7B/TOoC8HReIxhRm2CXkj74HzH//O1LGkHUtMu3gxC',
        'normal', true, '2022-12-29 16:09:05','2022-12-29 16:09:05');

-- order_list
INSERT INTO order_list (bill_status, order_no, order_date, product_id, product_name, src_qty, est_date, created_date,
                        last_modified_date)
VALUES (0, '20221025001', 20221025, 'A2**', '(半成品-分級)棒腿-多品', '0', 20221025, '2022-10-25 16:08:05',
        '2022-10-25 16:08:05');

-- info_list
INSERT INTO info_list (order_no, row_no, nct_product_id, nct_product_name, qj_set_weight, qj_upp_weight, qj_low_weight,
                       def_valid_day, created_date, last_modified_date)
VALUES ('20221025001', 1, 'A2-01', '醬燒大排10片-6kg/箱-白1', '0', '0', '0', '365', '2022-10-25 16:14:07',
        '2022-10-25 16:14:07');
INSERT INTO info_list (order_no, row_no, nct_product_id, nct_product_name, qj_set_weight, qj_upp_weight, qj_low_weight,
                       def_valid_day, created_date, last_modified_date)
VALUES ('20221025001', 2, 'A2-02', '醬燒大排10片-6kg/箱-白2', '0', '0', '0', '365', '2022-10-25 16:15:07',
        '2022-10-25 16:15:07');
INSERT INTO info_list (order_no, row_no, nct_product_id, nct_product_name, qj_set_weight, qj_upp_weight, qj_low_weight,
                       def_valid_day, created_date, last_modified_date)
VALUES ('20221025001', 3, 'A2-03', '醬燒大排10片-6kg/箱-白3', '0', '0', '0', '365', '2022-10-25 16:16:07',
        '2022-10-25 16:16:07');
