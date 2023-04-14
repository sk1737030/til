-- 주문 테이블 생성
CREATE TABLE `spring_batch`.`orders`
(
    `id`         INT NOT NULL AUTO_INCREMENT,
    `order_item` VARCHAR(45) NULL,
    `price`      INT NULL,
    `order_date` DATE NULL,
    PRIMARY KEY (`id`)
)
;

-- 정산 테이블 생성
CREATE TABLE `spring_batch`.`accounts`
(
    `id`           INT NOT NULL AUTO_INCREMENT,
    `order_item`   VARCHAR(45) NULL,
    `price`        INT NULL,
    `order_date`   DATE NULL,
    `account_date` DATE NULL,
    PRIMARY KEY (`id`)
)
;

select *
from spring_batch.orders
;

select *
from spring_batch.accounts
;