USE bankcentric;

SET SQL_SAFE_UPDATES = 1; -- Just in case...


-- DOMAIN


INSERT INTO
  banks(bank_details)
VALUES
('Industrial & Commercial Bank of China - ICBC'),
('China Construction Bank Corp.'),
('Agricultural Bank of China'),
('Bank of China'),
('HSBC Holdings'),
('JPMorgan Chase & Co.'),
('BNP Paribas'),
('Mitsubishi UFJ Financial Group'),
('Bank of America'),
('Credit Agricole Group');

INSERT INTO
  addresses (line_1, line_2, town_city, zip_postcode, state_province_country, country, other_details)
VALUES
('691 Quincy Place', '4941 Westport Pass', 'El Paso', '79911', 'Texas', 'United States', 'Advanced intermediate time-frame'),
('06033 Sutherland Park', '032 Hoffman Parkway', 'Philadelphia', '19131', 'Pennsylvania', 'United States', 'Face to face intermediate function'),
('71 Dennis Terrace', '55888 Brickson Park Crossing', 'Columbus', '43231', 'Ohio', 'United States', 'Fully-configurable multi-tasking data-warehouse'),
('2 Linden Court', '84090 Moose Crossing', 'Miami', '33111', 'Florida', 'United States', 'Total 4th generation contingency'),
('41 Summit Hill', '05 Esch Court', 'Fresno', '93740', 'California', 'United States', 'Open-source tangible data-warehouse'),
('270 Johnson Avenue', '43 Magdeline Circle', 'Washington', '20062', 'District of Columbia', 'United States', 'Realigned intangible support'),
('089 Maple Crossing', '94 Fuller Street', 'Hollywood', '33023', 'Florida', 'United States', 'Up-sized stable algorithm'),
('45210 Shasta Parkway', '778 Helena Hill', 'Phoenix', '85030', 'Arizona', 'United States', 'Organic global algorithm'),
('9 Carey Hill', '2106 Hoffman Avenue', 'North Little Rock', '72199', 'Arkansas', 'United States', 'Networked human-resource time-frame'),
('1 Farragut Avenue', '07 Union Center', 'Shreveport', '71130', 'Louisiana', 'United States', 'Organized bi-directional emulation'),
('5294 Dorton Court', '08135 Veith Court', 'Denton', '76210', 'Texas', 'United States', 'Diverse scalable access'),
('83 Lyons Court', '8535 Lindbergh Way', 'Memphis', '38181', 'Tennessee', 'United States', 'Balanced didactic flexibility'),
('69329 Barnett Hill', '5869 Daystar Point', 'Sacramento', '95838', 'California', 'United States', 'Optional mission-critical application'),
('20459 Shasta Terrace', '5 Tomscot Junction', 'Chicago', '60609', 'Illinois', 'United States', 'Focused national approach'),
('25 Sutteridge Alley', '1 Barnett Drive', 'New York City', '10039', 'New York', 'United States', 'Quality-focused tangible installation'),
('412 Messerschmidt Parkway', '1610 Bluejay Hill', 'Miami', '33164', 'Florida', 'United States', 'Public-key disintermediate workforce'),
('3613 Corscot Hill', '8 Esch Center', 'Oklahoma City', '73109', 'Oklahoma', 'United States', 'Pre-emptive multi-state system engine'),
('968 Hansons Street', '87 Harper Trail', 'Rochester', '14646', 'New York', 'United States', 'Managed 24/7 open architecture'),
('37 Spaight Terrace', '1291 Shasta Place', 'El Paso', '79968', 'Texas', 'United States', 'Persistent tangible core'),
('92417 Annamark Drive', '63483 Almo Parkway', 'Albuquerque', '87190', 'New Mexico', 'United States', 'Seamless scalable ability');

INSERT INTO
  ref_branch_types (branch_type_code, branch_type_description,
                    large_urban, -- 'Y' or 'N'
                    small_rural, -- 'Y' or 'N'
                    medium_suburban -- 'Y' or 'N'
)
VALUES
('01-URB', 'Represents a large urban branch.', 'Y', 'N', 'N'),
('02-RUL', 'Represents a small rural branch', 'N', 'Y', 'N'),
('03-SUB', 'Represents a medium sub urban branch', 'N', 'N', 'Y');

INSERT INTO
  branches(address_id, bank_id, branch_type_code, branch_details)
VALUES
(1, 1, '01-URB', 'Lorem ipsum dolor sit amet.'),
(2, 3, '01-URB', 'Lorem ipsum dolor sit amet.'),
(3, 4, '01-URB', 'Lorem ipsum dolor sit amet.'),
(4, 9, '01-URB', 'Lorem ipsum dolor sit amet.'),
(5, 8, '03-SUB', 'Lorem ipsum dolor sit amet.'),
(6, 7, '03-SUB', 'Lorem ipsum dolor sit amet.'),
(7, 6, '02-RUL', 'Lorem ipsum dolor sit amet.');

INSERT INTO
  customers(address_id, branch_id, personal_details, contact_details)
VALUES
(1, 1, 'Wyndham Fausch, wfausch0@blogspot.com, Male', 'nibh ligula nec sem duis aliquam convallis nunc proin at turpis a'),
(2, 2, 'Clayson MacRinn, cmacrinn1@craigslist.org, Male', 'blandit ultrices enim lorem ipsum dolor sit amet consectetuer adipiscing elit proin interdum mauris non ligula'),
(3, 7, 'Berenice Mitchener, bmitchener2@bluehost.com, Female', 'nulla tellus in sagittis dui vel nisl duis ac nibh fusce'),
(4, 6, 'Waylan Juza, wjuza4@salon.com, Male', 'molestie hendrerit at vulputate vitae nisl aenean lectus pellentesque eget nunc donec quis orci eget orci vehicula'),
(5, 5, 'Agretha Jakov, ajakov8@linkedin.com, Female', 'suscipit a feugiat et eros vestibulum ac est lacinia nisi venenatis tristique fusce congue'),
(6, 3, 'Eulalie Featley, efeatley7@aol.com, Female', 'quisque ut erat curabitur gravida nisi at nibh in hac habitasse platea dictumst aliquam');

INSERT INTO
  ref_account_types(account_type_code, account_type_description,
                    checking, -- 'Y' or 'N'
                    savings, -- 'Y' or 'N'
                    certificate_of_deposit, -- 'Y' or 'N'
                    money_market,-- 'Y' or 'N'
                    individual_retirement -- 'Y' or 'N'
)
VALUES
('01-CHK', ' A checking account offers easy access to your money for your daily transactional needs and helps keep your cash secure. Customers can use a debit card or checks to make purchases or pay bills.', 'Y', 'N', 'N', 'N', 'N'),
('02-SAV', 'A savings account allows you to accumulate interest on funds you’ve saved for future needs. Interest rates can be compounded on a daily, weekly, monthly, or annual basis.', 'N', 'Y', 'N', 'N', 'N'),
('03-COD', 'Certificates of deposit, or CDs, allow you to invest your money at a set interest rate for a pre-set period of time.', 'N', 'N', 'Y', 'N', 'N'),
('04-MMA', 'Money market accounts are similar to savings accounts, but they require you to maintain a higher balance to avoid a monthly service fee.', 'N', 'N', 'N', 'Y', 'N'),
('05-IRA', 'IRAs, or individual retirement accounts, allow you to save independently for your retirement.', 'N', 'N', 'N', 'N', 'Y');

INSERT INTO
  ref_account_status(account_status_code, account_status_description,
                     active, -- 'Y' or 'N'
                     closed -- 'Y' or 'N'
)
VALUES
('01-ACT', 'The account is active and can be withdrawn from and receive deposits.', 'Y', 'N'),
('02-CLS', 'The account is closed and cannot be withdrawn from or receive deposits.', 'N', 'Y');

INSERT INTO
  accounts(account_status_code, account_type_code, customer_id, current_balance, other_details)
VALUES
('01-ACT', '01-CHK', 1, 50000, 'No additional details.'),
('02-CLS', '04-MMA', 2, 0, 'Account closed due to being inactive for 2 years.'),
('01-ACT', '02-SAV', 3, 9000, 'No additional details.'),
('01-ACT', '03-COD', 4, 23500, 'No additional details.'),
('01-ACT', '05-IRA', 5, 100000, 'No additional details.'),
('02-CLS', '01-CHK', 6, 9000000, 'Account closed under the suspicion of money laundering. Balance is frozen until the investigation is over.');

INSERT INTO
  merchants(merchant_details)
VALUES
('Barings Bank'),
('Berenberg Bank'),
('Greenhill & Co.'),
('Hill Samuel'),
('Kuhn, Loeb & Co.');

INSERT INTO
  ref_transaction_types(transaction_type_code, transaction_type_description,
                        deposit,  -- 'Y' or 'N'
                        withdrawal -- 'Y' or 'N'
)
VALUES
('01-WTH', 'Represents a withdrawal transaction.', 'N', 'Y'),
('02-DEP', 'Represents a deposit transaction.', 'Y', 'N');


INSERT INTO
  transactions(account_number, merchant_id, transaction_type_code, transaction_date_time, transaction_amount, other_details)
VALUES
(1, 1, '01-WTH', NOW(), 250, 'No additional details.'),
(3, 3, '01-WTH', NOW(), 2500, 'No additional details.'),
(4, 4, '02-DEP', NOW(), 555, 'No additional details.'),
(5, 1, '01-WTH', NOW(), 25000, 'No additional details.');


-- SPRING SECURITY


INSERT INTO
  user_role(role_name)
VALUES
('ROLE_ADMIN'),
('ROLE_USER');

INSERT INTO
  role_privilege(privilege_name)
VALUES
('READ_PRIVILEGE'),
('WRITE_PRIVILEGE'),
('CHANGE_PASSWORD_PRIVILEGE');

INSERT INTO
  user_role_role_privilege(user_role, role_privilege)
VALUES
(1,1), -- (ROLE_ADMIN, READ_PRIVILEGE)
(1,2), -- (ROLE_ADMIN, WRITE_PRIVILEGE)
(1,3), -- (ROLE_ADMIN, CHANGE_PASSWORD_PRIVILEGE)
(2,1), -- (ROLE_USER, READ_PRIVILEGE)
(2,3); -- (ROLE_USER, CHANGE_PASSWORD_PRIVILEGE)

INSERT INTO
  user_account(first_name, last_name, email, user_password, enabled, is_using2fa, secret)
VALUES
('admin', -- last_name
 'admin', -- first_name
 'admin@gmail.com', -- email
 '$2a$11$Jte0g5qCiNIu0VQQZPWeDuFjryXj/b/YSv4RMde0GS.9ktweN91k.', -- BCRYPT(11 cycles)
 true, -- enabled(by default new accounts are disabled until verified by email)
 false, -- is_using2fa(disabled by default)
 'C5J6UUBED5TQ8WK1EHQQ4' -- secret(by default receives a random Base32 value)
),
('test', -- last_name
 'test', -- first_name
 'test@gmail.com', -- email
 '$2a$11$Jte0g5qCiNIu0VQQZPWeDuFjryXj/b/YSv4RMde0GS.9ktweN91k.', -- BCRYPT(11 cycles)
 true, -- enabled(by default new accounts are disabled until verified by email)
 false, -- is_using2fa(disabled by default)
 'EAOQNUFDCX4FVH4P' -- secret(by default receives a random Base32 value)
);

INSERT INTO
  user_account_user_role(user_account, user_role)
VALUES
(1, 1), -- admin, ROLE_ADMIN
(1, 2),	-- admin, ROLE_USER
(2, 2);	-- test, ROLE_USER