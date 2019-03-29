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


DROP procedure IF EXISTS `allBanksByDetails`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allBanksByDetails`(IN p_bank_details VARCHAR(255))
BEGIN
	SELECT * FROM banks WHERE bank_details LIKE CONCAT("%", p_bank_details, "%")
    ORDER BY bank_id ASC 
    LIMIT 10; 
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

DROP procedure IF EXISTS `allBanksRangeCount`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allBanksRangeCount`(IN p_skip INTEGER,
								  IN p_page_size INTEGER)
BEGIN
	SELECT COUNT FROM banks LIMIT p_skip, p_page_size; 
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


DROP procedure IF EXISTS `allAddressesByLine1`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allAddressesByLine1`(IN p_address_line1 VARCHAR(255))
BEGIN
	SELECT * FROM addresses WHERE line_1 LIKE CONCAT("%", p_address_line1, "%")
    ORDER BY address_id ASC 
    LIMIT 10; 
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
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleRefBranchTypeByCode`(IN p_branch_type_code CHAR(15))
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


DROP procedure IF EXISTS `allBranchesByDetails`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allBranchesByDetails`(IN p_details VARCHAR(255))
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
	WHERE branches.branch_details LIKE CONCAT("%", p_details, "%")
    ORDER BY branches.branch_id ASC 
    LIMIT 10;
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
	WHERE branches.branch_details = p_details ORDER BY branches.branch_id; 
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



-- CUSTOMERS

DROP procedure IF EXISTS `addCustomer`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addCustomer`(IN	p_address_id BIGINT, 
														IN	p_branch_id BIGINT, 
														IN	p_personal_details VARCHAR(255), 
														IN	p_contact_details VARCHAR(255))
BEGIN
	INSERT INTO customers
    VALUES
    (
		NULL, 
		p_address_id, 
		p_branch_id, 
		p_personal_details, 
		p_contact_details
	); 
END$$
DELIMITER ;


DROP procedure IF EXISTS `updateCustomer`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateCustomer`(IN p_customer_id BIGINT,	
                                                        IN	p_address_id BIGINT,
                                                        IN	p_branch_id BIGINT,
														IN	p_personal_details VARCHAR(255), 
														IN	p_contact_details VARCHAR(255))
BEGIN
	UPDATE customers 
		SET address_id = p_address_id, 
			branch_id = p_branch_id,
			personal_details = p_personal_details,
            contact_details = p_contact_details
	WHERE customers.customer_id = p_customer_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deleteCustomer`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteCustomer`(IN p_customer_id BIGINT)
BEGIN
	DELETE FROM customers WHERE customer_id = p_customer_id LIMIT 1;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deleteCustomers`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteCustomers`(IN p_customer_id BIGINT)
BEGIN
	DELETE FROM customers WHERE customer_id = p_customer_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allCustomers`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allCustomers`()
BEGIN
	SELECT customers.customer_id,
		   customers.address_id,
		   customers.branch_id,
		   customers.personal_details,
           customers.contact_details,
           addresses.line_1,
           branches.branch_details
    FROM customers
		LEFT JOIN addresses
			ON customers.address_id = addresses.address_id
		LEFT JOIN branches
			ON customers.branch_id = branches.branch_id
	ORDER BY customers.customer_id; 
END$$
DELIMITER ;


DROP procedure IF EXISTS `allCustomersByPersonalDetails`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allCustomersByPersonalDetails`( IN p_customer_details VARCHAR(255))
BEGIN
	SELECT customers.customer_id,
		   customers.address_id,
		   customers.branch_id,
		   customers.personal_details,
           customers.contact_details,
           addresses.line_1,
           branches.branch_details
    FROM customers
		LEFT JOIN addresses
			ON customers.address_id = addresses.address_id
		LEFT JOIN branches
			ON customers.branch_id = branches.branch_id
	WHERE customers.personal_details LIKE CONCAT("%", p_customer_details, "%")
	ORDER BY customers.customer_id
    LIMIT 10; 
END$$
DELIMITER ;


DROP procedure IF EXISTS `allCustomersById`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allCustomersById`( IN p_customer_id BIGINT)
BEGIN
	SELECT customers.customer_id,
		   customers.address_id,
		   customers.branch_id,
		   customers.personal_details,
           customers.contact_details,
           addresses.line_1,
           branches.branch_details
    FROM customers
		LEFT JOIN addresses
			ON customers.address_id = addresses.address_id
		LEFT JOIN branches
			ON customers.branch_id = branches.branch_id
	WHERE customers.customer_id = p_customer_id
	ORDER BY customers.customer_id; 
END$$
DELIMITER ;


DROP procedure IF EXISTS `singleCustomer`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleCustomer`(IN p_customer_id BIGINT)
BEGIN
	SELECT customers.customer_id,
		   customers.address_id,
		   customers.branch_id,
		   customers.personal_details,
           customers.contact_details,
           addresses.line_1,
           branches.branch_details
    FROM customers
		LEFT JOIN addresses
			ON customers.address_id = addresses.address_id
		LEFT JOIN branches
			ON customers.branch_id = branches.branch_id
	WHERE customers.customer_id = p_customer_id
    ORDER BY customers.customer_id;
END$$
DELIMITER ;

DROP procedure IF EXISTS `singleCustomerByPersonalDetails`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleCustomerByPersonalDetails`(IN p_customer_details VARCHAR(255))
BEGIN
	SELECT customers.customer_id,
		   customers.address_id,
		   customers.branch_id,
		   customers.personal_details,
           customers.contact_details,
           addresses.line_1,
           branches.branch_details
    FROM customers
		LEFT JOIN addresses
			ON customers.address_id = addresses.address_id
		LEFT JOIN branches
			ON customers.branch_id = branches.branch_id
	WHERE customers.personal_details = p_customer_details
    ORDER BY customers.customer_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allCustomersCount`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allCustomersCount`()
BEGIN
	SELECT COUNT(*) FROM customers; 
END$$
DELIMITER ;


DROP procedure IF EXISTS `allCustomersRange`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allCustomersRange`(IN p_skip INTEGER,
																IN p_page_size INTEGER)
BEGIN
	SELECT customers.customer_id,
		   customers.address_id,
		   customers.branch_id,
		   customers.personal_details,
           customers.contact_details,
           addresses.line_1,
           branches.branch_details
    FROM customers
		LEFT JOIN addresses
			ON customers.address_id = addresses.address_id
		LEFT JOIN branches
			ON customers.branch_id = branches.branch_id
    ORDER BY customers.customer_id
    LIMIT p_skip, p_page_size; 
END$$
DELIMITER ;

-- /customers



-- REF ACCOUNT TYPES

DROP procedure IF EXISTS `addRefAccountType`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addRefAccountType`(IN p_account_type_code CHAR(15),
																IN p_account_type_description VARCHAR(255),
																IN p_checking CHAR(1),
																IN p_savings CHAR(1),
																IN p_certificate_of_deposit CHAR(1),
																IN p_money_market CHAR(1),
																IN p_individual_retirement CHAR(1))
BEGIN
	INSERT INTO ref_account_types
    VALUES
    (
		NULL, 
		p_account_type_code,
		p_account_type_description,
		p_checking,
		p_savings,
		p_certificate_of_deposit,
		p_money_market,
		p_individual_retirement
	); 
END$$
DELIMITER ;


DROP procedure IF EXISTS `updateRefAccountType`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateRefAccountType`(IN p_account_type_id BIGINT,	
																IN p_account_type_code CHAR(15),
																IN p_account_type_description VARCHAR(255),
																IN p_checking CHAR(1),
																IN p_savings CHAR(1),
																IN p_certificate_of_deposit CHAR(1),
																IN p_money_market CHAR(1),
																IN p_individual_retirement CHAR(1))
BEGIN
	UPDATE ref_account_types 
		SET account_type_code = p_account_type_code,
			account_type_description = p_account_type_description,
			checking = p_checking,
			savings = p_savings,
			certificate_of_deposit = p_certificate_of_deposit,
			money_market = p_money_market,
			individual_retirement = p_individual_retirement
	WHERE account_type_id = p_account_type_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deleteRefAccountType`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteRefAccountType`(IN p_account_type_id BIGINT)
BEGIN
	DELETE FROM ref_account_types WHERE account_type_id = p_account_type_id LIMIT 1;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deleteRefAccountTypes`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteRefAccountTypes`(IN p_account_type_id BIGINT)
BEGIN
	DELETE FROM ref_account_types WHERE account_type_id = p_account_type_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allRefAccountTypes`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allRefAccountTypes`()
BEGIN
	SELECT * FROM ref_account_types; 
END$$
DELIMITER ;


DROP procedure IF EXISTS `allRefAccountTypesById`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allRefAccountTypesById`(IN p_account_type_id BIGINT)
BEGIN
	SELECT * FROM ref_account_types
    WHERE account_type_id = p_account_type_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `singleRefAccountType`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleRefAccountType`(IN p_account_type_id BIGINT)
BEGIN
	SELECT * FROM ref_account_types WHERE account_type_id = p_account_type_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `singleRefAccountTypeByCode`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleRefAccountTypeByCode`(IN p_account_type_code VARCHAR(255))
BEGIN
	SELECT * FROM ref_account_types WHERE account_type_code = p_account_type_code;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allRefAccountTypesCount`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allRefAccountTypesCount`()
BEGIN
	SELECT COUNT(*) FROM ref_account_types; 
END$$
DELIMITER ;


DROP procedure IF EXISTS `allRefAccountTypesRange`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allRefAccountTypesRange`(IN p_skip INTEGER,
																IN p_page_size INTEGER)
BEGIN
	SELECT * FROM ref_account_types LIMIT p_skip, p_page_size; 
END$$
DELIMITER ;

-- /refAccountTypes



-- REF ACCOUNT STATUS

DROP procedure IF EXISTS `addRefAccountStatus`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addRefAccountStatus`(IN p_account_status_code CHAR(15),
																	IN p_account_status_description VARCHAR(255),
																	IN p_active CHAR(1),
																	IN p_closed CHAR(1))
BEGIN
	INSERT INTO ref_account_status
    VALUES
    (
		NULL, 
		p_account_status_code,
		p_account_status_description,
		p_active,
		p_closed
	); 
END$$
DELIMITER ;


DROP procedure IF EXISTS `updateRefAccountStatus`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateRefAccountStatus`(IN p_account_status_id BIGINT,	
																	IN p_account_status_code CHAR(15),
																	IN p_account_status_description VARCHAR(255),
																	IN p_active CHAR(1),
																	IN p_closed CHAR(1))
BEGIN
	UPDATE ref_account_status
		SET account_status_code = p_account_status_code,
			account_status_description = p_account_status_description,
			active = p_active,
			closed = p_closed
	WHERE account_status_id = p_account_status_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deleteRefAccountStatus`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteRefAccountStatus`(IN p_account_status_id BIGINT)
BEGIN
	DELETE FROM ref_account_status WHERE account_status_id = p_status_type_id LIMIT 1;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deleteRefAccountStatus`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteRefAccountStatus`(IN p_account_status_id BIGINT)
BEGIN
	DELETE FROM ref_account_status WHERE account_status_id = p_account_status_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allRefAccountStatus`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allRefAccountStatus`()
BEGIN
	SELECT * FROM ref_account_status; 
END$$
DELIMITER ;


DROP procedure IF EXISTS `allRefAccountStatusById`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allRefAccountStatusById`(IN p_account_status_id BIGINT)
BEGIN
	SELECT * FROM ref_account_status
    WHERE account_status_id = p_account_status_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `singleRefAccountStatusByCode`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleRefAccountStatusByCode`(IN p_account_status_code CHAR(15))
BEGIN
	SELECT * FROM ref_account_status WHERE account_status_code = p_account_status_code;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allRefAccountStatusCount`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allRefAccountStatusCount`()
BEGIN
	SELECT COUNT(*) FROM ref_account_status; 
END$$
DELIMITER ;


DROP procedure IF EXISTS `allRefAccountStatusRange`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allRefAccountStatusRange`(IN p_skip INTEGER,
																IN p_page_size INTEGER)
BEGIN
	SELECT * FROM ref_account_status LIMIT p_skip, p_page_size; 
END$$
DELIMITER ;

-- /refAccountStatus


-- ACCOUNTS

DROP procedure IF EXISTS `addAccount`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addAccount`(IN p_account_status_id BIGINT,
														IN p_account_type_id BIGINT,
														IN p_customer_id BIGINT,
														IN p_current_balance DECIMAL(65,5),
														IN p_other_details VARCHAR(255))
BEGIN
	INSERT INTO accounts
    VALUES
    (
		NULL, 
		p_account_status_id,
		p_account_type_id,
		p_customer_id,
		p_current_balance,
		p_other_details
	); 
END$$
DELIMITER ;


DROP procedure IF EXISTS `updateAccount`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateAccount`(IN p_account_number BIGINT,	
															IN p_account_status_id BIGINT,
															IN p_account_type_id BIGINT,
															IN p_customer_id BIGINT,
															IN p_current_balance DECIMAL(65,5),
															IN p_other_details VARCHAR(255))
BEGIN
	UPDATE accounts 
		SET account_status_id = p_account_status_id, 
			account_status_id = p_account_status_id,
			account_type_id = p_account_type_id,
            customer_id = p_customer_id,
            current_balance = p_current_balance,
            other_details = p_other_details
	WHERE account_number = p_account_number;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deleteAccount`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteAccount`(IN p_account_number BIGINT)
BEGIN
	DELETE FROM accounts WHERE account_number = p_account_number LIMIT 1;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deleteAccounts`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteAccounts`(IN p_account_number BIGINT)
BEGIN
	DELETE FROM accounts WHERE account_number = p_account_number;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allAccounts`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allAccounts`()
BEGIN
	SELECT accounts.account_number,
		   accounts.account_status_id,
           accounts.account_type_id,
           accounts.customer_id,
           accounts.current_balance,
           accounts.other_details,
           ref_account_status.account_status_code,
           ref_account_types.account_type_code,
           customers.personal_details
    FROM accounts
		LEFT JOIN ref_account_status
			ON accounts.account_status_id = ref_account_status.account_status_id
		LEFT JOIN ref_account_types
			ON accounts.account_type_id = ref_account_types.account_type_id
		LEFT JOIN customers
			ON accounts.customer_id = customers.customer_id
	ORDER BY accounts.account_number;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allAccountsById`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allAccountsById`( IN p_account_number BIGINT)
BEGIN
	SELECT accounts.account_number,
		   accounts.account_status_id,
           accounts.account_type_id,
           accounts.customer_id,
           accounts.current_balance,
           accounts.other_details,
           ref_account_status.account_status_code,
           ref_account_types.account_type_code,
           customers.personal_details
    FROM accounts
		LEFT JOIN ref_account_status
			ON accounts.account_status_id = ref_account_status.account_status_id
		LEFT JOIN ref_account_types
			ON accounts.account_type_id = ref_account_types.account_type_id
		LEFT JOIN customers
			ON accounts.customer_id = customers.customer_id
	WHERE accounts.account_number = p_account_number
	ORDER BY accounts.account_number;
END$$
DELIMITER ;


DROP procedure IF EXISTS `singleAccount`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleAccount`(IN p_account_number BIGINT)
BEGIN
	SELECT accounts.account_number,
		   accounts.account_status_id,
           accounts.account_type_id,
           accounts.customer_id,
           accounts.current_balance,
           accounts.other_details,
           ref_account_status.account_status_code,
           ref_account_types.account_type_code,
           customers.personal_details
    FROM accounts
		LEFT JOIN ref_account_status
			ON accounts.account_status_id = ref_account_status.account_status_id
		LEFT JOIN ref_account_types
			ON accounts.account_type_id = ref_account_types.account_type_id
		LEFT JOIN customers
			ON accounts.customer_id = customers.customer_id
	WHERE accounts.account_number = p_account_number
	ORDER BY accounts.account_number;
END$$
DELIMITER ;

DROP procedure IF EXISTS `singleBankAccountByDetails`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleBankAccountByDetails`(IN p_account_details VARCHAR(255))
BEGIN
	SELECT accounts.account_number,
		   accounts.account_status_id,
           accounts.account_type_id,
           accounts.customer_id,
           accounts.current_balance,
           accounts.other_details,
           ref_account_status.account_status_code,
           ref_account_types.account_type_code,
           customers.personal_details
    FROM accounts
		LEFT JOIN ref_account_status
			ON accounts.account_status_id = ref_account_status.account_status_id
		LEFT JOIN ref_account_types
			ON accounts.account_type_id = ref_account_types.account_type_id
		LEFT JOIN customers
			ON accounts.customer_id = customers.customer_id
	WHERE accounts.other_details = p_account_details
	ORDER BY accounts.account_number;
END$$
DELIMITER ;

DROP procedure IF EXISTS `allAccountsCount`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allAccountsCount`()
BEGIN
	SELECT COUNT(*) FROM accounts; 
END$$
DELIMITER ;


DROP procedure IF EXISTS `allAccountsRange`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allAccountsRange`(IN p_skip INTEGER,
																IN p_page_size INTEGER)
BEGIN
	SELECT accounts.account_number,
		   accounts.account_status_id,
           accounts.account_type_id,
           accounts.customer_id,
           accounts.current_balance,
           accounts.other_details,
           ref_account_status.account_status_code,
           ref_account_types.account_type_code,
           customers.personal_details
    FROM accounts
		LEFT JOIN ref_account_status
			ON accounts.account_status_id = ref_account_status.account_status_id
		LEFT JOIN ref_account_types
			ON accounts.account_type_id = ref_account_types.account_type_id
		LEFT JOIN customers
			ON accounts.customer_id = customers.customer_id
	ORDER BY accounts.account_number
    LIMIT p_skip, p_page_size; 
END$$
DELIMITER ;

-- /accounts



-- MERCHANTS

DROP procedure IF EXISTS `addMerchant`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addMerchant`(IN p_merchant_details VARCHAR(255))
BEGIN
	INSERT INTO merchants
    VALUES
    (
		NULL, 
		p_merchant_details
	); 
END$$
DELIMITER ;


DROP procedure IF EXISTS `updateMerchant`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateMerchant`(IN p_merchant_id BIGINT,
															 IN p_merchant_details VARCHAR(255))
BEGIN
	UPDATE merchants 
		SET merchant_details = p_merchant_details
	WHERE merchant_id = p_merchant_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deleteMerchant`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteMerchant`(IN p_merchant_id BIGINT)
BEGIN
	DELETE FROM merchants WHERE merchant_id = p_merchant_id LIMIT 1;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deleteMerchants`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteMerchants`(IN p_merchant_id BIGINT)
BEGIN
	DELETE FROM merchants WHERE merchant_id = p_merchant_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allMerchants`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allMerchants`()
BEGIN
	SELECT * FROM merchants;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allMerchantsByDetails`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allMerchantsByDetails`( IN p_merchant_details VARCHAR(255))
BEGIN
	SELECT * FROM merchants 
    WHERE merchant_details LIKE CONCAT("%", p_merchant_details, "%")
    ORDER BY merchant_id
    LIMIT 10;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allMerchantsById`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allMerchantsById`( IN p_merchant_id BIGINT)
BEGIN
	SELECT * FROM merchants WHERE merchant_id = p_merchant_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `singleMerchant`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleMerchant`(IN p_merchant_id BIGINT)
BEGIN
	SELECT * FROM merchants WHERE merchant_id = p_merchant_id;
END$$
DELIMITER ;

DROP procedure IF EXISTS `singleMerchantByDetails`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleMerchantByDetails`(IN p_merchant_details VARCHAR(255))
BEGIN
	SELECT * FROM merchants WHERE merchant_details = p_merchant_details 
    ORDER BY merchants.merchant_id DESC;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allMerchantsCount`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allMerchantsCount`()
BEGIN
	SELECT COUNT(*) FROM merchants; 
END$$
DELIMITER ;


DROP procedure IF EXISTS `allMerchantsRange`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allMerchantsRange`(IN p_skip INTEGER,
																IN p_page_size INTEGER)
BEGIN
	SELECT * FROM merchants
    LIMIT p_skip, p_page_size;
END$$
DELIMITER ;

-- /merchants




-- REF TRANSACTION TYPES

DROP procedure IF EXISTS `addRefTransactionType`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addRefTransactionType`(IN p_transaction_type_code CHAR(15),
																IN p_transaction_type_description VARCHAR(255),
																IN p_deposit CHAR(1),
																IN p_withdrawal CHAR(1))
BEGIN
	INSERT INTO ref_transaction_types
    VALUES
    (
		NULL, 
		p_transaction_type_code,
		p_transaction_type_description,
		p_deposit,
		p_withdrawal
	); 
END$$
DELIMITER ;


DROP procedure IF EXISTS `updateRefTransactionType`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateRefTransactionType`(IN p_transaction_type_id BIGINT,	
																		IN p_transaction_type_code CHAR(15),
																		IN p_transaction_type_description VARCHAR(255),
																		IN p_deposit CHAR(1),
																		IN p_withdrawal CHAR(1))
BEGIN
	UPDATE ref_transaction_types 
		SET transaction_type_code = p_transaction_type_code,
			transaction_type_description = p_transaction_type_description,
			deposit = p_deposit,
			withdrawal = p_withdrawal
	WHERE Transaction_type_id = p_Transaction_type_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deleteRefTransactionType`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteRefTransactionType`(IN p_transaction_type_id BIGINT)
BEGIN
	DELETE FROM ref_transaction_types WHERE transaction_type_id = p_transaction_type_id LIMIT 1;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deleteRefTransactionTypes`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteRefTransactionTypes`(IN p_transaction_type_id BIGINT)
BEGIN
	DELETE FROM ref_transaction_types WHERE transaction_type_id = p_transaction_type_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allRefTransactionTypes`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allRefTransactionTypes`()
BEGIN
	SELECT * FROM ref_transaction_types; 
END$$
DELIMITER ;


DROP procedure IF EXISTS `allRefTransactionTypesById`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allRefTransactionTypesById`(IN p_transaction_type_id BIGINT)
BEGIN
	SELECT * FROM ref_transaction_types
    WHERE transaction_type_id = p_transaction_type_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `singleRefTransactionType`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleRefTransactionType`(IN p_transaction_type_id BIGINT)
BEGIN
	SELECT * FROM ref_transaction_types WHERE transaction_type_id = p_transaction_type_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `singleRefTransactionTypeByCode`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleRefTransactionTypeByCode`(IN p_transaction_type_code CHAR(15))
BEGIN
	SELECT * FROM ref_transaction_types WHERE transaction_type_code = p_transaction_type_code;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allRefTransactionTypesCount`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allRefTransactionTypesCount`()
BEGIN
	SELECT COUNT(*) FROM ref_transaction_types; 
END$$
DELIMITER ;


DROP procedure IF EXISTS `allRefTransactionTypesRange`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allRefTransactionTypesRange`(IN p_skip INTEGER,
																		  IN p_page_size INTEGER)
BEGIN
	SELECT * FROM ref_transaction_types LIMIT p_skip, p_page_size; 
END$$
DELIMITER ;

-- /refTransactionTypes



-- TRANSACTIONS

DROP procedure IF EXISTS `addTransaction`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addTransaction`(IN p_account_number BIGINT,
															IN p_merchant_id BIGINT,
															IN p_transaction_type_id BIGINT,
															IN p_transaction_date_time DATETIME,
															IN p_transaction_amount DECIMAL(65,5),
															IN p_other_details VARCHAR(255))
BEGIN
	INSERT INTO transactions
    VALUES
    (
		NULL, 
		p_account_number,
        p_merchant_id,
        p_transaction_type_id,
        p_transaction_date_time,
        p_transaction_amount,
        p_other_details
	); 
END$$
DELIMITER ;


DROP procedure IF EXISTS `updateTransaction`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateTransaction`(IN p_transaction_id BIGINT,
															IN p_account_number BIGINT,
															IN p_merchant_id BIGINT,
															IN p_transaction_type_id BIGINT,
															IN p_transaction_date_time DATETIME,
															IN p_transaction_amount DECIMAL(65,5),
															IN p_other_details VARCHAR(255))
BEGIN
	UPDATE transactions 
		SET account_number = p_account_number,
			merchant_id = p_merchant_id,
            transaction_type_id = p_transaction_type_id,
            transaction_date_time = p_transaction_date_time,
            transaction_amount = p_transaction_amount,
            other_details = p_other_details
	WHERE transaction_id = p_transaction_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deleteTransaction`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteTransaction`(IN p_transaction_id BIGINT)
BEGIN
	DELETE FROM transactions WHERE transaction_id = p_transaction_id LIMIT 1;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deleteTransactions`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteTransactions`(IN p_transaction_id BIGINT)
BEGIN
	DELETE FROM transactions WHERE transaction_id = p_transaction_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allTransactions`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allTransactions`()
BEGIN
	SELECT transactions.transaction_id,
		   transactions.account_number,
		   transactions.merchant_id,
	       transactions.transaction_type_id,
		   transactions.transaction_date_time,
           transactions.transaction_amount,
           transactions.other_details,
           accounts.current_balance,
           accounts.other_details,
           merchants.merchant_details,
           ref_transaction_types.transaction_type_id,
           ref_transaction_types.transaction_type_code
    FROM transactions
		LEFT JOIN accounts
			ON transactions.account_number = accounts.account_number
		LEFT JOIN merchants
			ON transactions.merchant_id = merchants.merchant_id
		LEFT JOIN ref_transaction_types
			ON transactions.transaction_type_id = ref_transaction_types.transaction_type_id
	ORDER BY transactions.transaction_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allTransactionsById`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allTransactionsById`( IN p_transaction_id BIGINT)
BEGIN
	SELECT transactions.transaction_id,
		   transactions.account_number,
		   transactions.merchant_id,
	       transactions.transaction_type_id,
		   transactions.transaction_date_time,
           transactions.transaction_amount,
           transactions.other_details,
           accounts.current_balance,
           accounts.other_details,
           merchants.merchant_details,
           ref_transaction_types.transaction_type_id,
           ref_transaction_types.transaction_type_code
    FROM transactions
		LEFT JOIN accounts
			ON transactions.account_number = accounts.account_number
		LEFT JOIN merchants
			ON transactions.merchant_id = merchants.merchant_id
		LEFT JOIN ref_transaction_types
			ON transactions.transaction_type_id = ref_transaction_types.transaction_type_id
	WHERE transaction_id = p_transaction_id
	ORDER BY transactions.transaction_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `singleTransaction`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleTransaction`(IN p_transaction_id BIGINT)
BEGIN
	SELECT transactions.transaction_id,
		   transactions.account_number,
		   transactions.merchant_id,
	       transactions.transaction_type_id,
		   transactions.transaction_date_time,
           transactions.transaction_amount,
           transactions.other_details,
           accounts.current_balance,
           accounts.other_details,
           merchants.merchant_details,
           ref_transaction_types.transaction_type_id,
           ref_transaction_types.transaction_type_code
    FROM transactions
		LEFT JOIN accounts
			ON transactions.account_number = accounts.account_number
		LEFT JOIN merchants
			ON transactions.merchant_id = merchants.merchant_id
		LEFT JOIN ref_transaction_types
			ON transactions.transaction_type_id = ref_transaction_types.transaction_type_id
	WHERE transaction_id = p_transaction_id
	ORDER BY transactions.transaction_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `singleTransactionByDetails`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleTransactionByDetails`(IN p_transaction_details VARCHAR(255))
BEGIN
	SELECT transactions.transaction_id,
		   transactions.account_number,
		   transactions.merchant_id,
	       transactions.transaction_type_id,
		   transactions.transaction_date_time,
           transactions.transaction_amount,
           transactions.other_details,
           accounts.current_balance,
           accounts.other_details,
           merchants.merchant_details,
           ref_transaction_types.transaction_type_id,
           ref_transaction_types.transaction_type_code
    FROM transactions
		LEFT JOIN accounts
			ON transactions.account_number = accounts.account_number
		LEFT JOIN merchants
			ON transactions.merchant_id = merchants.merchant_id
		LEFT JOIN ref_transaction_types
			ON transactions.transaction_type_id = ref_transaction_types.transaction_type_id
	WHERE transactions.other_details = p_transaction_details
	ORDER BY transactions.transaction_id DESC;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allTransactionsCount`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allTransactionsCount`()
BEGIN
	SELECT COUNT(*) FROM transactions; 
END$$
DELIMITER ;


DROP procedure IF EXISTS `allTransactionsRange`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allTransactionsRange`(IN p_skip INTEGER,
																IN p_page_size INTEGER)
BEGIN
	SELECT transactions.transaction_id,
		   transactions.account_number,
		   transactions.merchant_id,
	       transactions.transaction_type_id,
		   transactions.transaction_date_time,
           transactions.transaction_amount,
           transactions.other_details,
           accounts.current_balance,
           accounts.other_details,
           merchants.merchant_details,
           ref_transaction_types.transaction_type_id,
           ref_transaction_types.transaction_type_code
    FROM transactions
		LEFT JOIN accounts
			ON transactions.account_number = accounts.account_number
		LEFT JOIN merchants
			ON transactions.merchant_id = merchants.merchant_id
		LEFT JOIN ref_transaction_types
			ON transactions.transaction_type_id = ref_transaction_types.transaction_type_id
	ORDER BY transactions.transaction_id
    LIMIT p_skip, p_page_size;
END$$
DELIMITER ;

-- /Transactions



-- USER ACCOUNT

DROP procedure IF EXISTS `getUserRoles`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getUserRoles`(IN p_user_account_id BIGINT)
BEGIN
	SELECT id, role_name FROM user_role
		LEFT JOIN user_account_user_role
			ON user_role.id = user_account_user_role.user_role
	WHERE user_account_user_role.user_account = p_user_account_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `getRolePrivileges`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getRolePrivileges`(IN p_user_role_id BIGINT)
BEGIN
	SELECT id, privilege_name FROM role_privilege
		LEFT JOIN user_role_role_privilege
			ON role_privilege.id = user_role_role_privilege.role_privilege
	WHERE user_role_role_privilege.user_role = p_user_role_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `setRoleToUser`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `setRoleToUser`(IN p_user_id BIGINT,
														  IN p_role_id BIGINT)
BEGIN
	INSERT INTO user_account_user_role
		VALUE
        (
			p_user_id,
			p_role_id
		);
END$$
DELIMITER ;


DROP procedure IF EXISTS `deleteRoleFromUser`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteRoleFromUser`(IN p_user_id BIGINT,
																IN p_role_id BIGINT)
BEGIN
	DELETE FROM user_account_user_role 
    WHERE user_account = p_user_id AND
		  user_role = p_role_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deleteAllRolesFromUser`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteAllRolesFromUser`(IN p_user_id BIGINT)
BEGIN
	DELETE FROM user_account_user_role 
    WHERE user_account = p_user_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `addUserRole`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addUserRole`(IN p_role_name VARCHAR(255))
BEGIN
	INSERT INTO user_role
		VALUE
        (
			NULL,
			p_role_name
		);
END$$
DELIMITER ;


DROP procedure IF EXISTS `updateUserRole`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateUserRole`(IN p_role_id BIGINT,
																  IN p_role_name VARCHAR(255))
BEGIN
	UPDATE user_role
		SET role_name = p_role_name
    WHERE user_role.id = p_role_id 
    LIMIT 1;
END$$
DELIMITER ;


DROP procedure IF EXISTS `addRolePrivilege`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addRolePrivilege`(IN p_privilege_name VARCHAR(255))
BEGIN
	INSERT INTO role_privilege
		VALUE
        (
			NULL,
			p_privilege_name
		);
END$$
DELIMITER ;


DROP procedure IF EXISTS `updateRolePrivilege`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateRolePrivilege`(IN p_privilege_id BIGINT,
																  IN p_privilege_name VARCHAR(255))
BEGIN
	UPDATE role_privilege
		SET privilege_name = p_privilege_name
    WHERE role_privilege.id = p_privilege_id 
    LIMIT 1;
END$$
DELIMITER ;


DROP procedure IF EXISTS `addUserAccount`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addUserAccount`(IN p_first_name VARCHAR(255),
																IN p_last_name VARCHAR(255),
																IN p_email VARCHAR(255),
																IN p_user_password VARCHAR(60),
																IN p_enabled BOOLEAN,
																IN p_is_using2fa BOOLEAN,
																IN p_secret VARCHAR(255),
																IN p_last_login TIMESTAMP,
                                                                IN p_auth_provider VARCHAR(255),
                                                                IN p_auth_provider_id VARCHAR(255))
BEGIN
	INSERT INTO user_account
		VALUE
        (
			NULL,
			p_first_name,
            p_last_name,
			p_email,
			p_user_password,
			p_enabled,
			p_is_using2fa,
			p_secret,
			p_last_login,
            p_auth_provider,
            p_auth_provider_id
		);
END$$
DELIMITER ;


DROP procedure IF EXISTS `updateUserAccount`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateUserAccount`(IN p_user_account_id BIGINT ,
																IN p_first_name VARCHAR(255),
																IN p_last_name VARCHAR(255),
																IN p_email VARCHAR(255),
																IN p_user_password VARCHAR(60),
																IN p_enabled BOOLEAN,
																IN p_is_using2fa BOOLEAN,
																IN p_secret VARCHAR(255),
																IN p_last_login TIMESTAMP,
                                                                IN p_auth_provider VARCHAR(255),
                                                                IN p_auth_provider_id VARCHAR(255))
BEGIN
	UPDATE user_account
		SET first_name = p_first_name,
			last_name = p_last_name,
			email = p_email,
			user_password = p_user_password,
			enabled = p_enabled,
			is_using2fa = p_is_using2fa,
			secret = p_secret,
			last_login = p_last_login,
            auth_provider = p_auth_provider,
            auth_provider_id = p_auth_provider_id
	WHERE user_account.id = p_user_account_id 
    LIMIT 1;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deleteUserAccount`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteUserAccount`(IN p_user_account_id BIGINT)
BEGIN
	DELETE FROM user_account WHERE user_account.id = p_user_account_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `singleUserAccount`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleUserAccount`(IN p_user_account_id BIGINT)
BEGIN
	SELECT * FROM user_account WHERE user_account.id = p_user_account_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `singleUserAccountByUsername`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleUserAccountByUsername`(IN p_user_account_username VARCHAR(255))
BEGIN
	SELECT * FROM user_account 
    WHERE user_account.email = p_user_account_username;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allUserAccounts`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allUserAccounts`()
BEGIN
	SELECT * FROM user_account;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allUserAccountsCount`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allUserAccountsCount`()
BEGIN
	SELECT COUNT(*) FROM user_account; 
END$$
DELIMITER ;


DROP procedure IF EXISTS `allUserAccountsRange`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allUserAccountsRange`(IN p_skip INTEGER,
																   IN p_page_size INTEGER)
BEGIN
	SELECT * FROM users ORDER BY user_account.id
    LIMIT p_skip, p_page_size;
END$$
DELIMITER ;


DROP procedure IF EXISTS `getUserAccountVerificationToken`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getUserAccountVerificationToken`(IN p_user_account_id BIGINT)
BEGIN
	SELECT * FROM verification_token
		LEFT JOIN user_account
			ON verification_token.user_id = user_account.id
	WHERE verification_token.user_id = p_user_account_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `getUserAccountPasswordResetToken`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getUserAccountPasswordResetToken`(IN p_user_account_id BIGINT)
BEGIN
	SELECT * FROM password_reset_token
		LEFT JOIN user_account
			ON password_reset_token.user_id = user_account.id
	WHERE password_reset_token.user_id = p_user_account_id;
END$$
DELIMITER ;

-- /user account



-- USER ROLE

DROP procedure IF EXISTS `addRole`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addRole`(IN p_role_name VARCHAR(255))
BEGIN
	INSERT INTO user_role
    VALUES
    (
		NULL, 
		p_role_name
	); 
END$$
DELIMITER ;


DROP procedure IF EXISTS `updateRole`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateRole`(IN p_role_id BIGINT,
															 IN p_role_name VARCHAR(255))
BEGIN
	UPDATE user_role 
		SET role_name = p_role_name
	WHERE user_role.id = p_role_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deleteRole`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteRole`(IN p_role_id BIGINT)
BEGIN
	DELETE FROM user_role WHERE user_role.id = p_role_id LIMIT 1;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deleteRoles`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteRoles`(IN p_role_id BIGINT)
BEGIN
	DELETE FROM user_role WHERE user_role.id = p_role_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allRoles`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allRoles`()
BEGIN
	SELECT * FROM user_role;
END$$
DELIMITER ;


DROP procedure IF EXISTS `singleRole`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleRole`(IN p_role_id BIGINT)
BEGIN
	SELECT * FROM user_role WHERE user_role.id = p_role_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `singleRoleByName`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleRoleByName`(IN p_role_name CHAR(15))
BEGIN
	SELECT * FROM user_role WHERE user_role.role_name = p_role_name;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allRolesCount`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allRolesCount`()
BEGIN
	SELECT COUNT(*) FROM user_role; 
END$$
DELIMITER ;


DROP procedure IF EXISTS `allRolesRange`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allRolesRange`(IN p_skip INTEGER,
															IN p_page_size INTEGER)
BEGIN
	SELECT * FROM user_role
    LIMIT p_skip, p_page_size;
END$$
DELIMITER ;

-- /user role




-- VERIFICATION TOKEN

DROP procedure IF EXISTS `addVerificationToken`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addVerificationToken`(IN p_token VARCHAR(255),
																	IN p_user_id BIGINT ,
																	IN p_expiry_date DATE)
BEGIN
	INSERT INTO verification_token
    VALUES
    (
		NULL, 
		p_token,
        p_user_id,
        p_expiry_date
	); 
END$$
DELIMITER ;


DROP procedure IF EXISTS `updateVerificationToken`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateVerificationToken`(IN p_token_id BIGINT,
																		IN p_token VARCHAR(255),
																		IN p_user_id BIGINT ,
																		IN p_expiry_date DATE)
BEGIN
	UPDATE verification_token 
		SET token = p_token,
			user_id = p_user_id,
			expiry_date = p_expiry_date
	WHERE verification_token.id = p_token_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deleteVerificationToken`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteVerificationToken`(IN p_token_id BIGINT)
BEGIN
	DELETE FROM verification_token WHERE verification_token.id = p_token_id LIMIT 1;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deleteVerificationTokens`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteVerificationTokens`(IN p_token_id BIGINT)
BEGIN
	DELETE FROM verification_token WHERE verification_token.id = p_token_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allVerificationTokens`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allVerificationTokens`()
BEGIN
	SELECT * FROM verification_token
		LEFT JOIN user_account
			ON verification_token.user_id = user_account.id
    ORDER BY verification_token.id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `singleVerificationToken`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleVerificationToken`(IN p_token_id BIGINT)
BEGIN
	SELECT * FROM verification_token
    LEFT JOIN user_account
			ON verification_token.user_id = user_account.id    
    WHERE verification_token.id = p_token_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `singleVerificationTokenByEmail`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleVerificationTokenByEmail`(IN p_email VARCHAR(255))
BEGIN
	SELECT * FROM verification_token
		LEFT JOIN user_account
			ON verification_token.user_id = user_account.id
    WHERE user_account.email = p_email;
END$$
DELIMITER ;


DROP procedure IF EXISTS `singleVerificationTokenByToken`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singleVerificationTokenByToken`(IN p_token VARCHAR(255))
BEGIN
	SELECT * FROM verification_token
		LEFT JOIN user_account
			ON verification_token.user_id = user_account.id
    WHERE verification_token.token = p_token;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allVerificationTokensCount`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allVerificationTokensCount`()
BEGIN
	SELECT COUNT(*) FROM verification_token; 
END$$
DELIMITER ;


DROP procedure IF EXISTS `allVerificationTokensRange`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allVerificationTokensRange`(IN p_skip INTEGER,
																		 IN p_page_size INTEGER)
BEGIN
	SELECT * FROM verification_token
		LEFT JOIN user_account
			ON verification_token.user_id = user_account.id
    ORDER BY verification_token.id
    LIMIT p_skip, p_page_size;
END$$
DELIMITER ;


-- /verification token




-- PASSWORD RESET TOKEN

DROP procedure IF EXISTS `addPasswordResetToken`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addPasswordResetToken`(IN p_token VARCHAR(255),
																	IN p_user_id BIGINT ,
																	IN p_expiry_date DATE)
BEGIN
	INSERT INTO password_reset_token
    VALUES
    (
		NULL, 
		p_token,
        p_user_id,
        p_expiry_date
	); 
END$$
DELIMITER ;


DROP procedure IF EXISTS `updatePasswordResetToken`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updatePasswordResetToken`(IN p_token_id BIGINT,
																		IN p_token VARCHAR(255),
																		IN p_user_id BIGINT ,
																		IN p_expiry_date DATE)
BEGIN
	UPDATE password_reset_token 
		SET token = p_token,
			user_id = p_user_id,
			expiry_date = p_expiry_date
	WHERE verification_token.id = p_token_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deletePasswordResetToken`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deletePasswordResetToken`(IN p_token_id BIGINT)
BEGIN
	DELETE FROM password_reset_token WHERE password_reset_token.id = p_token_id LIMIT 1;
END$$
DELIMITER ;


DROP procedure IF EXISTS `deletePasswordResetTokens`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deletePasswordResetTokens`(IN p_token_id BIGINT)
BEGIN
	DELETE FROM password_reset_token WHERE password_reset_token.id = p_token_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allPasswordResetTokens`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allPasswordResetTokens`()
BEGIN
	SELECT * FROM password_reset_token
		LEFT JOIN user_account
			ON password_reset_token.user_id = user_account.id
    ORDER BY password_reset_token.id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `singlePasswordResetToken`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singlePasswordResetToken`(IN p_token_id BIGINT)
BEGIN
	SELECT * FROM password_reset_token
    LEFT JOIN user_account
			ON password_reset_token.user_id = user_account.id    
    WHERE password_reset_token.id = p_token_id;
END$$
DELIMITER ;


DROP procedure IF EXISTS `singlePasswordResetTokenByEmail`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singlePasswordResetTokenByEmail`(IN p_email VARCHAR(255))
BEGIN
	SELECT * FROM password_reset_token
		LEFT JOIN user_account
			ON password_reset_token.user_id = user_account.id
    WHERE password_reset_token.email = p_email;
END$$
DELIMITER ;


DROP procedure IF EXISTS `singlePasswordResetTokenByToken`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `singlePasswordResetTokenByToken`(IN p_token VARCHAR(255))
BEGIN
	SELECT * FROM password_reset_token
		LEFT JOIN user_account
			ON password_reset_token.user_id = user_account.id
    WHERE password_reset_token.token = p_token;
END$$
DELIMITER ;


DROP procedure IF EXISTS `allPasswordResetTokensCount`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allPasswordResetTokensCount`()
BEGIN
	SELECT COUNT(*) FROM password_reset_token; 
END$$
DELIMITER ;


DROP procedure IF EXISTS `allPasswordResetTokensRange`;
DELIMITER $$
USE `bankcentric`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `allPasswordResetTokensRange`(IN p_skip INTEGER,
																		 IN p_page_size INTEGER)
BEGIN
	SELECT * FROM password_reset_token
		LEFT JOIN user_account
			ON password_reset_token.user_id = user_account.id
    ORDER BY password_reset_token.id
    LIMIT p_skip, p_page_size;
END$$
DELIMITER ;


-- /password reset token



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
