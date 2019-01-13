USE `bankcentric`;


DROP procedure IF EXISTS `addBank`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addBank`(IN p_bank_details VARCHAR(255))
BEGIN
	INSERT INTO banks
    VALUES(NULL, p_bank_details); 
END$$
DELIMITER ;


DROP procedure IF EXISTS `allBanks`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allBanks`()
BEGIN
	SELECT * FROM banks; 
END$$
DELIMITER ;


DROP procedure IF EXISTS `allBanksCount`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allBanksCount`()
BEGIN
	SELECT COUNT(*) FROM banks; 
END$$
DELIMITER ;


DROP procedure IF EXISTS `allBanksRange`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allBanksRange`(IN p_skip INTEGER,
								  IN p_page_size INTEGER)
BEGIN
	SELECT * FROM banks LIMIT p_skip, p_page_size; 
END$$
DELIMITER ;


DROP procedure IF EXISTS `depositToAccount`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `depositToAccount`(IN p_account_number BIGINT,
                                        
                                        IN p_merchant_id BIGINT,
                                        IN p_transaction_amount DECIMAL,
                                        IN p_other_details VARCHAR(255))
BEGIN
	UPDATE accounts 
	SET current_balance = current_balance + p_transaction_amount 
	WHERE account_number = p_account_number;
 
	INSERT INTO transactions(account_number, 
							 merchant_id, 
							 transaction_type_id, 
							 transaction_date_time, 
							 transaction_amount, 
							 other_details) 
	VALUE(p_account_number,
		  p_merchant_id,
		  2,
		  NOW(),
	      p_transaction_amount,
		  p_other_details); 
END$$
DELIMITER ;


DROP procedure IF EXISTS `withdrawFromAccount`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `withdrawFromAccount`(IN p_account_number BIGINT,
                                        
                                        IN p_merchant_id BIGINT,
                                        IN p_transaction_amount DECIMAL,
                                        IN p_other_details VARCHAR(255))
BEGIN
	UPDATE accounts 
	SET current_balance = current_balance - p_transaction_amount 
	WHERE account_number = p_account_number;
 
	INSERT INTO transactions(account_number, 
							 merchant_id, 
							 transaction_type_id, 
							 transaction_date_time, 
							 transaction_amount, 
							 other_details) 
	VALUE(p_account_number,
		  p_merchant_id,
		  1,
		  NOW(),
	      p_transaction_amount,
		  p_other_details); 
END$$
DELIMITER ;
