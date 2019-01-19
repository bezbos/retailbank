USE `bankcentric`;

-- BANKS

DROP procedure IF EXISTS `addBank`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addBank`(IN p_bank_details VARCHAR(255))
BEGIN
	INSERT INTO banks
    VALUES
    (
		NULL,
		p_bank_details
    );
END$$
DELIMITER ;


DROP procedure IF EXISTS `updateBank`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateBank`(IN p_bank_id BIGINT,
														 IN p_bank_details VARCHAR(255))
BEGIN
	UPDATE banks
		SET bank_details = p_bank_details
	WHERE bank_id = p_bank_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deleteBank`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteBank`(IN p_bank_id BIGINT)
BEGIN
	DELETE FROM banks WHERE bank_id = p_bank_ID LIMIT 1;
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


DROP procedure IF EXISTS `singleBank`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleBank`(IN p_bank_id BIGINT)
BEGIN
	SELECT * FROM banks WHERE bank_id = p_bank_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `singleBankByDetails`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleBankByDetails`(IN p_bank_details VARCHAR(255))
BEGIN
	SELECT * FROM banks WHERE bank_details = p_bank_details;
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

-- /banks






-- ADDRESSES

DROP procedure IF EXISTS `addAddress`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addAddress`(IN	p_line_1 VARCHAR(255),
														IN	p_line_2 VARCHAR(255),
														IN	p_town_city VARCHAR(255),
														IN	p_zip_postcode CHAR(20),
														IN	p_state_province_country VARCHAR(50),
														IN	p_country VARCHAR(50),
														IN	p_other_details VARCHAR(255))
BEGIN
	INSERT INTO addresses
    VALUES
    (
		NULL,
		p_line_1,
		p_line_2,
		p_town_city,
		p_zip_postcode,
		p_state_province_country,
		p_country,
		p_other_details
    );
END$$
DELIMITER ;


DROP procedure IF EXISTS `updateAddress`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateAddress`(IN p_address_id BIGINT,
															IN	p_line_1 VARCHAR(255),
															IN	p_line_2 VARCHAR(255),
															IN	p_town_city VARCHAR(255),
															IN	p_zip_postcode CHAR(20),
															IN	p_state_province_country VARCHAR(50),
															IN	p_country VARCHAR(50),
															IN	p_other_details VARCHAR(255))
BEGIN
	UPDATE addresses
		SET line_1 = p_line_1,
			line_2 = p_line_2,
			town_city = p_town_city,
			zip_postcode = p_zip_postcode,
			state_province_country = p_state_province_country,
			country = p_country,
			other_details = p_other_details
	WHERE address_id = p_address_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deleteAddress`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteAddress`(IN p_address_id BIGINT)
BEGIN
	DELETE FROM addresses WHERE address_id = p_address_id LIMIT 1;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deleteAddresses`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteAddresses`(IN p_address_id BIGINT)
BEGIN
	DELETE FROM addresses WHERE address_id = p_address_ID;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allAddresses`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allAddresses`()
BEGIN
	SELECT * FROM addresses;
END$$
DELIMITER ;


DROP procedure IF EXISTS `singleAddress`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleAddress`(IN p_address_id BIGINT)
BEGIN
	SELECT * FROM addresses WHERE address_id = p_address_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `singleAddressByLine1`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleAddressByLine1`(IN p_line_1 VARCHAR(255))
BEGIN
	SELECT * FROM addresses WHERE line_1 = p_line_1;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allAddressesCount`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allAddressesCount`()
BEGIN
	SELECT COUNT(*) FROM addresses;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allAddressesRange`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allAddressesRange`(IN p_skip INTEGER,
																IN p_page_size INTEGER)
BEGIN
	SELECT * FROM addresses LIMIT p_skip, p_page_size;
END$$
DELIMITER ;

-- /addresses






-- REF BRANCH TYPES

DROP procedure IF EXISTS `addRefBranchType`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addRefBranchType`( IN	p_branch_type_code CHAR(15),
																IN	p_branch_type_description VARCHAR(255),
																IN	p_large_urban CHAR(1),
																IN	p_small_rural CHAR(1),
																IN	p_medium_suburban CHAR(1))
BEGIN
	INSERT INTO ref_branch_types
    VALUES
    (
		NULL,
		p_branch_type_code,
		p_branch_type_description,
		p_large_urban,
		p_small_rural,
		p_medium_suburban
    );
END$$
DELIMITER ;


DROP procedure IF EXISTS `updateRefBranchType`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateRefBranchType`(IN p_branch_type_id BIGINT,
																  IN p_branch_type_code CHAR(15),
																  IN p_branch_type_description VARCHAR(255),
																  IN p_large_urban CHAR(1),
																  IN p_small_rural CHAR(1),
																  IN p_medium_suburban CHAR(1))
BEGIN
	UPDATE ref_branch_types
		SET branch_type_code = p_branch_type_code,
			branch_type_description = p_branch_type_description,
			large_urban = p_large_urban,
			small_rural = p_small_rural,
			medium_suburban = p_medium_suburban
	WHERE branch_type_id = p_branch_type_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deleteRefBranchType`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteRefBranchType`(IN p_branch_type_id BIGINT)
BEGIN
	DELETE FROM ref_branch_types WHERE branch_type_id = p_branch_type_id LIMIT 1;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deleteRefBranchTypes`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteRefBranchTypes`(IN p_branch_type_id BIGINT)
BEGIN
	DELETE FROM ref_branch_types WHERE branch_type_id = p_branch_type_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allRefBranchTypes`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allRefBranchTypes`()
BEGIN
	SELECT * FROM ref_branch_types;
END$$
DELIMITER ;


DROP procedure IF EXISTS `singleRefBranchType`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleRefBranchType`(IN p_branch_type_id BIGINT)
BEGIN
	SELECT * FROM ref_branch_types WHERE branch_type_id = p_branch_type_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `singleRefBranchTypeByCode`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleRefBranchTypeByCode`(IN p_branch_type_code BIGINT)
BEGIN
	SELECT * FROM ref_branch_types WHERE branch_type_code = p_branch_type_code;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allRefBranchTypesCount`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allRefBranchTypesCount`()
BEGIN
	SELECT COUNT(*) FROM ref_branch_types;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allRefBranchTypesRange`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allRefBranchTypesRange`(IN p_skip INTEGER,
																IN p_page_size INTEGER)
BEGIN
	SELECT * FROM ref_branch_types LIMIT p_skip, p_page_size;
END$$
DELIMITER ;

-- /ref branch types





-- BRANCHES

DROP procedure IF EXISTS `addBranch`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addBranch`(IN	p_address_id BIGINT,
														IN	p_bank_id BIGINT,
														IN	p_branch_type_id BIGINT,
														IN	p_branch_details VARCHAR(255))
BEGIN
	INSERT INTO branches
    VALUES
    (
		NULL,
		p_address_id,
		p_bank_id,
		p_branch_type_id,
		p_branch_details
	);
END$$
DELIMITER ;


DROP procedure IF EXISTS `updateBranch`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateBranch`(IN p_branch_id BIGINT,
                                                        IN	p_address_id BIGINT,
                                                        IN	p_bank_id BIGINT,
														IN	p_branch_type_id BIGINT,
														IN	p_branch_details VARCHAR(255))
BEGIN
	UPDATE branches
		SET bank_id = p_bank_id,
			address_id = p_address_id,
			branch_type_id = p_branch_type_id,
			branch_details = p_branch_details
	WHERE branch_id = p_branch_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deleteBranch`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteBranch`(IN p_branch_id BIGINT)
BEGIN
	DELETE FROM branches WHERE branch_id = p_branch_id LIMIT 1;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deleteBranches`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteBranches`(IN p_branch_id BIGINT)
BEGIN
	DELETE FROM branches WHERE branch_id = p_branch_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allBranches`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allBranches`()
BEGIN
	SELECT branches.branch_id,
		   branches.address_id,
		   branches.bank_id,
		   branches.branch_type_id,
		   branches.branch_details,
           addresses.line_1,
           addresses.line_2,
           addresses.town_city,
           addresses.zip_postcode,
           addresses.state_province_country,
           addresses.country,
           addresses.other_details,
           banks.bank_details,
           ref_branch_types.branch_type_code,
           ref_branch_types.branch_type_description,
           ref_branch_types.large_urban,
           ref_branch_types.small_rural,
           ref_branch_types.medium_suburban
    FROM branches
		LEFT JOIN addresses
			ON branches.address_id = addresses.address_id
		LEFT JOIN banks
			ON branches.bank_id = banks.bank_id
		LEFT JOIN ref_branch_types
			ON branches.branch_type_id = ref_branch_types.branch_type_id
	ORDER BY branches.branch_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allBranchesByAddressId`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allBranchesByAddressId`(IN p_address_id BIGINT)
BEGIN
	SELECT branches.branch_id,
		   branches.address_id,
		   branches.bank_id,
		   branches.branch_type_id,
		   branches.branch_details,
           addresses.line_1,
           addresses.line_2,
           addresses.town_city,
           addresses.zip_postcode,
           addresses.state_province_country,
           addresses.country,
           addresses.other_details,
           banks.bank_details,
           ref_branch_types.branch_type_code,
           ref_branch_types.branch_type_description,
           ref_branch_types.large_urban,
           ref_branch_types.small_rural,
           ref_branch_types.medium_suburban
    FROM branches
		LEFT JOIN addresses
			ON branches.address_id = addresses.address_id
		LEFT JOIN banks
			ON branches.bank_id = banks.bank_id
		LEFT JOIN ref_branch_types
			ON branches.branch_type_id = ref_branch_types.branch_type_id
	WHERE branches.address_id = p_address_id
	ORDER BY branches.branch_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `singleBranch`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleBranch`(IN p_branch_id BIGINT)
BEGIN
	SELECT * FROM branches
		LEFT JOIN addresses
			ON branches.address_id = addresses.address_id
		LEFT JOIN banks
			ON branches.bank_id = banks.bank_id
		LEFT JOIN ref_branch_types
			ON branches.branch_type_id = ref_branch_types.branch_type_id
	WHERE branch_id = p_branch_id ORDER BY branches.branch_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `singleBranchByDetails`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleBranchByDetails`(IN p_details VARCHAR(255))
BEGIN
	SELECT * FROM branches
		LEFT JOIN addresses
			ON branches.address_id = addresses.address_id
		LEFT JOIN banks
			ON branches.bank_id = banks.bank_id
		LEFT JOIN ref_branch_types
			ON branches.branch_type_id = ref_branch_types.branch_type_id
	WHERE details = p_details ORDER BY branches.branch_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allBranchesCount`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allBranchesCount`()
BEGIN
	SELECT COUNT(*) FROM branches;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allBranchesRange`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allBranchesRange`(IN p_skip INTEGER,
																IN p_page_size INTEGER)
BEGIN
	SELECT * FROM branches
		LEFT JOIN addresses
			ON branches.address_id = addresses.address_id
		LEFT JOIN banks
			ON branches.bank_id = banks.bank_id
		LEFT JOIN ref_branch_types
			ON branches.branch_type_id = ref_branch_types.branch_type_id
	ORDER BY branches.branch_id
    LIMIT p_skip, p_page_size;
END$$
DELIMITER ;


-- /branches






-- OTHER
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
