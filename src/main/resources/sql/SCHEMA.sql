DROP
DATABASE
bankcentric;

CREATE
DATABASE
bankcentric;

USE
bankcentric;


-- DOMAIN


CREATE TABLE banks
(
  bank_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
  bank_details VARCHAR(255)
);

CREATE TABLE addresses
(
  address_id             BIGINT AUTO_INCREMENT PRIMARY KEY,
  line_1                 VARCHAR(255) NOT NULL,
  line_2                 VARCHAR(255),
  town_city              VARCHAR(255) NOT NULL,
  zip_postcode           CHAR(20)     NOT NULL,
  state_province_country VARCHAR(50)  NOT NULL,
  country                VARCHAR(50)  NOT NULL,
  other_details          VARCHAR(255)
);

CREATE TABLE ref_branch_types
(
  branch_type_code        CHAR(15) PRIMARY KEY,
  branch_type_description VARCHAR(255),
  large_urban             CHAR(1),
  small_rural             CHAR(1),
  medium_suburban         CHAR(1)
);

CREATE TABLE branches
(
  branch_id        BIGINT AUTO_INCREMENT PRIMARY KEY,
  address_id       BIGINT   NOT NULL,
  FOREIGN KEY (address_id) REFERENCES addresses (address_id),
  bank_id          BIGINT   NOT NULL,
  FOREIGN KEY (bank_id) REFERENCES banks (bank_id),
  branch_type_code CHAR(15) NOT NULL,
  FOREIGN KEY (branch_type_code) REFERENCES ref_branch_types (branch_type_code),
  branch_details   VARCHAR(255)
);

CREATE TABLE customers
(
  customer_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
  address_id       BIGINT       NOT NULL,
  FOREIGN KEY (address_id) REFERENCES addresses (address_id),
  branch_id        BIGINT       NOT NULL,
  FOREIGN KEY (branch_id) REFERENCES branches (branch_id),
  personal_details VARCHAR(255) NOT NULL,
  contact_details  VARCHAR(255) NOT NULL
);

CREATE TABLE ref_account_types
(
  account_type_code        CHAR(15) PRIMARY KEY,
  account_type_description VARCHAR(255),
  checking                 CHAR(1),
  savings                  CHAR(1),
  certificate_of_deposit   CHAR(1),
  money_market             CHAR(1),
  individual_retirement    CHAR(1)
);

CREATE TABLE ref_account_status
(
  account_status_code        CHAR(15) PRIMARY KEY,
  account_status_description VARCHAR(255),
  active                     CHAR(1),
  closed                     CHAR(1)
);

CREATE TABLE accounts
(
  account_number      BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_status_code CHAR(15)      NOT NULL,
  FOREIGN KEY (account_status_code) REFERENCES ref_account_status (account_status_code),
  account_type_code   CHAR(15)      NOT NULL,
  FOREIGN KEY (account_type_code) REFERENCES ref_account_types (account_type_code),
  customer_id         BIGINT        NOT NULL,
  FOREIGN KEY (customer_id) REFERENCES customers (customer_id),
  current_balance     DECIMAL(65,5) NOT NULL,
  other_details       VARCHAR(255)
);

CREATE TABLE merchants
(
  merchant_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
  merchant_details VARCHAR(255)
);

CREATE TABLE ref_transaction_types
(
  transaction_type_code        CHAR(15) PRIMARY KEY,
  transaction_type_description VARCHAR(255),
  deposit                      CHAR(1),
  withdrawal                   CHAR(1)
);

CREATE TABLE transactions
(
  transaction_id        BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_number        BIGINT        NOT NULL,
  FOREIGN KEY (account_number) REFERENCES accounts (account_number),
  merchant_id           BIGINT        NOT NULL,
  FOREIGN KEY (merchant_id) REFERENCES merchants (merchant_id),
  transaction_type_code CHAR(15)      NOT NULL,
  FOREIGN KEY (transaction_type_code) REFERENCES ref_transaction_types (transaction_type_code),
  transaction_date_time DATETIME      NOT NULL,
  transaction_amount    DECIMAL(65,5) NOT NULL,
  other_details         VARCHAR(255)
);


-- SPRING SECURITY


CREATE TABLE application_logs
(
  uuid        BINARY(16) PRIMARY KEY,
  log_message VARCHAR(255),
  log_date    DATETIME
);

CREATE TABLE user_account
(
  id            BIGINT AUTO_INCREMENT PRIMARY KEY,
  first_name    VARCHAR(255),
  last_name     VARCHAR(255),
  email         VARCHAR(255),
  user_password VARCHAR(60),
  enabled       BOOLEAN,
  is_using2fa   BOOLEAN,
  secret        VARCHAR(255),
  last_login    TIMESTAMP
);

CREATE TABLE user_role
(
  id        BIGINT AUTO_INCREMENT PRIMARY KEY,
  role_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE role_privilege
(
  id             BIGINT AUTO_INCREMENT PRIMARY KEY,
  privilege_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE user_role_role_privilege
(
  user_role      BIGINT NOT NULL,
  role_privilege BIGINT NOT NULL,
  PRIMARY KEY (user_role, role_privilege),
  CONSTRAINT constr_user_role_role_privilege_user_role_fk
    FOREIGN KEY user_role_fk(user_role) REFERENCES user_role(id),
  CONSTRAINT constr_user_role_role_privilege_role_privilege_fk
    FOREIGN KEY role_privilege_fk(role_privilege) REFERENCES role_privilege(id)
);

CREATE TABLE user_account_user_role
(
  user_account BIGINT NOT NULL,
  user_role    BIGINT NOT NULL,
  PRIMARY KEY (user_account, user_role),
  CONSTRAINT constr_user_account_user_role_user_account_fk
    FOREIGN KEY user_account_fk(user_account) REFERENCES user_account(id),
  CONSTRAINT constr_user_account_user_role_user_role_fk
    FOREIGN KEY user_role_fk(user_role) REFERENCES user_role(id)
);

CREATE TABLE verification_token
(
  id          BIGINT AUTO_INCREMENT PRIMARY KEY,
  token       VARCHAR(255),
  user_id     BIGINT NOT NULL,
  CONSTRAINT constr_vt_user_id_fk FOREIGN KEY (user_id) REFERENCES user_account (id),
  expiry_date DATE
);

CREATE TABLE password_reset_token
(
  id          BIGINT AUTO_INCREMENT PRIMARY KEY,
  token       VARCHAR(255),
  user_id     BIGINT NOT NULL,
  CONSTRAINT constr_prt_user_id_fk FOREIGN KEY (user_id) REFERENCES user_account (id),
  expiry_date DATE
);