-- 데이터베이스 생성 및 사용
CREATE DATABASE IF NOT EXISTS test CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE test;

-- 1) Payment Event -----------------------------------------------------------
CREATE TABLE payment_events (
    id               BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,           -- PK
    buyer_id         BIGINT UNSIGNED                NOT NULL,              -- FK to buyer table (별도 테이블이 있다면)
    is_payment_done  BOOLEAN                        NOT NULL DEFAULT FALSE,
    payment_key      VARCHAR(255)                   UNIQUE,                -- PSP 고유 키
    order_id         VARCHAR(255)                   UNIQUE,                -- 서비스 내부 주문 ID
    type             ENUM('NORMAL')                 NOT NULL,              -- 결제 유형(추가 가능)
    order_name       VARCHAR(255)                   NOT NULL,
    method           ENUM('EASY_PAY')               NOT NULL,              -- 결제 수단(추가 가능)
    psp_raw_data     JSON                           NULL,                  -- PSP 원본 응답
    created_at       DATETIME                       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       DATETIME                       NOT NULL DEFAULT CURRENT_TIMESTAMP
                                                             ON UPDATE CURRENT_TIMESTAMP,
    approved_at      DATETIME                       NULL,

    UNIQUE KEY uk_payment_key (payment_key),
    UNIQUE KEY uk_order_id    (order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2) Payment Order -----------------------------------------------------------
CREATE TABLE payment_orders (
    id                   BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,       -- PK
    payment_event_id     BIGINT UNSIGNED                NOT NULL,          -- FK → payment_event.id
    seller_id            BIGINT UNSIGNED                NOT NULL,
    product_id           BIGINT UNSIGNED                NOT NULL,
    order_id             VARCHAR(255)                   NOT NULL,          -- 조회 시 Join을 피하기 위한 컬럼 (order_id는 변경이 없기 때문에 동기화 필요 x)
    amount               DECIMAL(15,2)                  NOT NULL,          -- 필요시 Scale 조정
    payment_order_status ENUM('NOT_STARTED','EXECUTING','SUCCESS','FAILURE', 'UNKNOWN')
                                                    NOT NULL DEFAULT 'NOT_STARTED',
    ledger_updated       BOOLEAN                        NOT NULL DEFAULT FALSE,
    wallet_updated       BOOLEAN                        NOT NULL DEFAULT FALSE,
    failed_count         TINYINT UNSIGNED               NOT NULL DEFAULT 0,
    threshold            TINYINT UNSIGNED               NOT NULL DEFAULT 5,
    created_at           DATETIME                       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at           DATETIME                       NOT NULL DEFAULT CURRENT_TIMESTAMP
                                                             ON UPDATE CURRENT_TIMESTAMP,

    INDEX idx_payment_event_id (payment_event_id),
    INDEX idx_seller_id (seller_id),
    INDEX idx_product_id (product_id),
    INDEX idx_order_id (order_id),
    FOREIGN KEY (payment_event_id) REFERENCES payment_events(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3) Payment Order History ----------------------------------------------------
CREATE TABLE payment_order_histories (
    id              BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,            -- PK
    payment_order_id BIGINT UNSIGNED                NOT NULL,              -- FK → payment_order.id
    previous_status ENUM('NOT_STARTED','EXECUTING','SUCCESS','FAILURE','UNKNOWN') NOT NULL,
    new_status      ENUM('NOT_STARTED','EXECUTING','SUCCESS','FAILURE','UNKNOWN') NOT NULL,
    created_at      DATETIME                       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    changed_by      VARCHAR(255)                   NOT NULL,
    reason          VARCHAR(255)                   NULL,

    INDEX idx_payment_order_id (payment_order_id),
    INDEX idx_created_at (created_at),
    FOREIGN KEY (payment_order_id) REFERENCES payment_orders(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci; 