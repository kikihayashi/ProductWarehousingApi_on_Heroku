CREATE TABLE IF NOT EXISTS order_list
(
    order_id           INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    bill_status        INT          NOT NULL,
    order_no           VARCHAR(32)  NOT NULL,
    order_date         INT          NOT NULL,
    product_id         VARCHAR(128) NOT NULL,
    product_name       VARCHAR(128) NOT NULL,
    src_qty            VARCHAR(32)  NOT NULL,
    est_date           INT          NOT NULL,
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
);

CREATE TABLE IF NOT EXISTS info_list
(
    info_id            INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    order_no           VARCHAR(32)  NOT NULL,
    row_no             INT          NOT NULL,
    nct_product_id     VARCHAR(128) NOT NULL,
    nct_product_name   VARCHAR(128) NOT NULL,
    qj_set_weight      VARCHAR(32)  NOT NULL,
    qj_upp_weight      VARCHAR(32)  NOT NULL,
    qj_low_weight      VARCHAR(32)  NOT NULL,
    def_valid_day      VARCHAR(32)  NOT NULL,
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
);

CREATE TABLE IF NOT EXISTS barcode_list
(
    barcode_id         INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    order_no           VARCHAR(32)  NOT NULL,
    product_id         VARCHAR(32)  NOT NULL,
    product_name       VARCHAR(128) NOT NULL,
    lot_no             VARCHAR(32)  NOT NULL,
    lot_name           VARCHAR(128) NOT NULL,
    serial_no          VARCHAR(32)  NOT NULL,
    qrcode             VARCHAR(128) NOT NULL,
    valid_day          VARCHAR(32)  NOT NULL,
    weight_max         VARCHAR(32)  NOT NULL,
    weight_min         VARCHAR(32)  NOT NULL,
    pallet_no          VARCHAR(32)  NOT NULL,
    pallet_status      VARCHAR(32)  NOT NULL,
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
);

CREATE TABLE IF NOT EXISTS pallet_list
(
    pallet_id          INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    pallet_no          VARCHAR(32) NOT NULL,
    weight_set         VARCHAR(32) NOT NULL,
    make_date          VARCHAR(32) NOT NULL,
    created_date       TIMESTAMP   NOT NULL,
    last_modified_date TIMESTAMP   NOT NULL
);

CREATE TABLE IF NOT EXISTS serial_list
(
    serial_id          INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    warehouse_no       VARCHAR(32)  NOT NULL,
    order_no           VARCHAR(32)  NOT NULL,
    prod_id            VARCHAR(32)  NOT NULL,
    prod_name          VARCHAR(128) NOT NULL,
    batch_id           VARCHAR(32)  NOT NULL,
    serial_no          VARCHAR(32)  NOT NULL,
    pallet_no          VARCHAR(32)  NOT NULL,
    valid_date         VARCHAR(32)  NOT NULL,
    est_date           INT          NOT NULL,
    qj_set_weight      VARCHAR(32)  NOT NULL,
    pda_id             VARCHAR(32)  NOT NULL,
    ware_in_class      VARCHAR(32)  NOT NULL,
    ware_id            VARCHAR(32)  NOT NULL,
    storage_id         VARCHAR(32)  NOT NULL,
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
);

CREATE TABLE IF NOT EXISTS warehouse_list
(
    warehouse_id       INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    warehouse_no       VARCHAR(32)  NOT NULL,
    bill_date          VARCHAR(32)  NOT NULL,
    order_no           VARCHAR(32)  NOT NULL,
    order_date         VARCHAR(32)  NOT NULL,
    work_time          VARCHAR(32)  NOT NULL,
    product_id         VARCHAR(32)  NOT NULL,
    product_name       VARCHAR(128) NOT NULL,
    total_quantity     VARCHAR(32)  NOT NULL,
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
);

