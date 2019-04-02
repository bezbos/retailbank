USE `bankcentric`;

# banks
DROP TRIGGER IF EXISTS `banks_after_insert`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER banks_after_insert
  AFTER INSERT
  ON banks
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'banks',
          NEW.bank_id,
          NEW.bank_details,
          'INSERT',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;


DROP TRIGGER IF EXISTS `banks_after_update`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER banks_after_update
  AFTER UPDATE
  ON banks
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'banks',
          NEW.bank_id,
          NEW.bank_details,
          'UPDATE',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;


DROP TRIGGER IF EXISTS `banks_after_delete`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER banks_after_delete
  AFTER DELETE
  ON banks
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'banks',
          OLD.bank_id,
          OLD.bank_details,
          'DELETE',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;
# /banks


# addresses
DROP TRIGGER IF EXISTS `addresses_after_insert`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER addresses_after_insert
  AFTER INSERT
  ON addresses
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'addresses',
          NEW.address_id,
          NEW.line_1,
          'INSERT',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;


DROP TRIGGER IF EXISTS `addresses_after_update`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER addresses_after_update
  AFTER UPDATE
  ON addresses
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'addresses',
          NEW.address_id,
          NEW.line_1,
          'UPDATE',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;


DROP TRIGGER IF EXISTS `addresses_after_delete`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER addresses_after_delete
  AFTER DELETE
  ON addresses
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'addresses',
          OLD.address_id,
          OLD.line_1,
          'DELETE',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;
# /addresses


# branches
DROP TRIGGER IF EXISTS `branches_after_insert`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER branches_after_insert
  AFTER INSERT
  ON branches
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'branches',
          NEW.branch_id,
          NEW.branch_details,
          'INSERT',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;


DROP TRIGGER IF EXISTS `branches_after_update`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER branches_after_update
  AFTER UPDATE
  ON branches
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'branches',
          NEW.branch_id,
          NEW.branch_details,
          'UPDATE',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;


DROP TRIGGER IF EXISTS `branches_after_delete`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER branches_after_delete
  AFTER DELETE
  ON branches
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'branches',
          OLD.branch_id,
          OLD.branch_details,
          'DELETE',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;
# /branches


# customers
DROP TRIGGER IF EXISTS `customers_after_insert`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER customers_after_insert
  AFTER INSERT
  ON customers
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'customers',
          NEW.customer_id,
          NEW.contact_details,
          'INSERT',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;


DROP TRIGGER IF EXISTS `customers_after_update`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER customers_after_update
  AFTER UPDATE
  ON customers
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'customers',
          NEW.customer_id,
          NEW.contact_details,
          'UPDATE',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;


DROP TRIGGER IF EXISTS `customers_after_delete`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER customers_after_delete
  AFTER DELETE
  ON customers
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'customers',
          OLD.customer_id,
          OLD.contact_details,
          'DELETE',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;
# /customers


# accounts
DROP TRIGGER IF EXISTS `accounts_after_insert`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER accounts_after_insert
  AFTER INSERT
  ON accounts
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'accounts',
          NEW.account_number,
          NEW.other_details,
          'INSERT',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;


DROP TRIGGER IF EXISTS `accounts_after_update`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER accounts_after_update
  AFTER UPDATE
  ON accounts
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'accounts',
          NEW.account_number,
          NEW.other_details,
          'UPDATE',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;


DROP TRIGGER IF EXISTS `accounts_after_delete`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER accounts_after_delete
  AFTER DELETE
  ON accounts
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'accounts',
          OLD.account_number,
          OLD.other_details,
          'DELETE',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;
# /accounts


# merchants
DROP TRIGGER IF EXISTS `merchants_after_insert`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER merchants_after_insert
  AFTER INSERT
  ON merchants
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'merchants',
          NEW.merchant_id,
          NEW.merchant_details,
          'INSERT',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;


DROP TRIGGER IF EXISTS `merchants_after_update`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER merchants_after_update
  AFTER UPDATE
  ON merchants
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'merchants',
          NEW.merchant_id,
          NEW.merchant_details,
          'UPDATE',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;


DROP TRIGGER IF EXISTS `merchants_after_delete`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER merchants_after_delete
  AFTER DELETE
  ON merchants
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'merchants',
          OLD.merchant_id,
          OLD.merchant_details,
          'DELETE',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;
# /merchants


# ref_account_status
DROP TRIGGER IF EXISTS `ref_account_status_after_insert`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER ref_account_status_after_insert
  AFTER INSERT
  ON ref_account_status
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'ref_account_status',
          NEW.account_status_id,
          NEW.account_status_code,
          'INSERT',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;


DROP TRIGGER IF EXISTS `ref_account_status_after_update`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER ref_account_status_after_update
  AFTER UPDATE
  ON ref_account_status
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'ref_account_status',
          NEW.account_status_id,
          NEW.account_status_code,
          'UPDATE',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;


DROP TRIGGER IF EXISTS `ref_account_status_after_delete`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER ref_account_status_after_delete
  AFTER DELETE
  ON ref_account_status
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'ref_account_status',
          OLD.account_status_id,
          OLD.account_status_code,
          'DELETE',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;
# /ref_account_status


# ref_account_types
DROP TRIGGER IF EXISTS `ref_account_types_after_insert`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER ref_account_types_after_insert
  AFTER INSERT
  ON ref_account_types
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'ref_account_types',
          NEW.account_type_id,
          NEW.account_type_code,
          'INSERT',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;


DROP TRIGGER IF EXISTS `ref_account_types_after_update`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER ref_account_types_after_update
  AFTER UPDATE
  ON ref_account_types
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'ref_account_types',
          NEW.account_type_id,
          NEW.account_type_code,
          'UPDATE',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;


DROP TRIGGER IF EXISTS `ref_account_types_after_delete`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER ref_account_types_after_delete
  AFTER DELETE
  ON ref_account_types
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'ref_account_types',
          OLD.account_type_id,
          OLD.account_type_code,
          'DELETE',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;
# /ref_account_types


# ref_branch_types
DROP TRIGGER IF EXISTS `ref_branch_types_after_insert`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER ref_branch_types_after_insert
  AFTER INSERT
  ON ref_branch_types
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'ref_branch_types',
          NEW.branch_type_id,
          NEW.branch_type_code,
          'INSERT',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;


DROP TRIGGER IF EXISTS `ref_branch_types_after_update`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER ref_branch_types_after_update
  AFTER UPDATE
  ON ref_branch_types
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'ref_branch_types',
          NEW.branch_type_id,
          NEW.branch_type_code,
          'UPDATE',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;


DROP TRIGGER IF EXISTS `ref_branch_types_after_delete`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER ref_branch_types_after_delete
  AFTER DELETE
  ON ref_branch_types
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'ref_branch_types',
          OLD.branch_type_id,
          OLD.branch_type_code,
          'DELETE',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;
# /ref_branch_types


# ref_transaction_types
DROP TRIGGER IF EXISTS `ref_transaction_types_after_insert`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER ref_transaction_types_after_insert
  AFTER INSERT
  ON ref_transaction_types
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'ref_transaction_types',
          NEW.transaction_type_id,
          NEW.transaction_type_code,
          'INSERT',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;


DROP TRIGGER IF EXISTS `ref_transaction_types_after_update`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER ref_transaction_types_after_update
  AFTER UPDATE
  ON ref_transaction_types
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'ref_transaction_types',
          NEW.transaction_type_id,
          NEW.transaction_type_code,
          'UPDATE',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;


DROP TRIGGER IF EXISTS `ref_transaction_types_after_delete`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER ref_transaction_types_after_delete
  AFTER DELETE
  ON ref_transaction_types
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'ref_transaction_types',
          OLD.transaction_type_id,
          OLD.transaction_type_code,
          'DELETE',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;
# /ref_transaction_types


# transactions
DROP TRIGGER IF EXISTS `transactions_after_insert`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER transactions_after_insert
  AFTER INSERT
  ON transactions
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'transactions',
          NEW.transaction_id,
          NEW.transaction_amount,
          'INSERT',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;


DROP TRIGGER IF EXISTS `transactions_after_update`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER transactions_after_update
  AFTER UPDATE
  ON transactions
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'transactions',
          NEW.transaction_id,
          NEW.transaction_amount,
          'UPDATE',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;


DROP TRIGGER IF EXISTS `transactions_after_delete`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER transactions_after_delete
  AFTER DELETE
  ON transactions
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'transactions',
          OLD.transaction_id,
          OLD.transaction_amount,
          'DELETE',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;
# /transactions


# user_account
DROP TRIGGER IF EXISTS `user_account_after_insert`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER user_account_after_insert
  AFTER INSERT
  ON user_account
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'user_account',
          NEW.id,
          NEW.email,
          'INSERT',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;


DROP TRIGGER IF EXISTS `user_account_after_update`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER user_account_after_update
  AFTER UPDATE
  ON user_account
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'user_account',
          NEW.id,
          NEW.email,
          'UPDATE',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;


DROP TRIGGER IF EXISTS `user_account_after_delete`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` TRIGGER user_account_after_delete
  AFTER DELETE
  ON user_account
  FOR EACH ROW
BEGIN
  INSERT INTO audit_log
  VALUES (DEFAULT,
          'user_account',
          OLD.id,
          OLD.email,
          'DELETE',
          NOW(),
          (SELECT SUBSTRING_INDEX(USER(), '@', -1)));
END $$
DELIMITER ;
# /user_account
