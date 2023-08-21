-- create a database called MoneyPakyaw
CREATE DATABASE dbMoneyPakyaw;

-- use the database
USE dbMoneyPakyaw;

-- create a table called expense_categories
CREATE TABLE expense_categories (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  amount decimal(10,2) NOT NULL,
  date date NOT NULL,
  username varchar(20) NOT NULL,
  paid bit(1) NOT NULL,
  PRIMARY KEY (id)
);

-- create a table called income_sources
CREATE TABLE income_sources (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  amount decimal(10,2) NOT NULL,
  date date NOT NULL,
  username varchar(20) NOT NULL,
  earned bit(1) NOT NULL,
  PRIMARY KEY (id)
);

-- create a table called savings
CREATE TABLE savings (
  id int NOT NULL AUTO_INCREMENT,
  amount decimal(10,2) NOT NULL,
  date date NOT NULL,
  username varchar(20) NOT NULL,
  PRIMARY KEY (id)
);

-- create a table called theme
CREATE TABLE theme (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(50) DEFAULT NULL,
  color varchar(7) DEFAULT NULL,
  username varchar(20) NOT NULL,
  PRIMARY KEY (id)
);

-- create a table called transactions
CREATE TABLE transactions (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  amount decimal(10,2) NOT NULL,
  date date NOT NULL,
  username varchar(20) NOT NULL,
  PRIMARY KEY (id)
);

-- create a table called user_account
CREATE TABLE user_account (
  username varchar(20) NOT NULL,
  password varchar(20) NOT NULL,
  name varchar(50) NOT NULL,
  age int(11) NOT NULL,
  balance decimal(10,2) NOT NULL,
  theme_in_use varchar(50) DEFAULT NULL,
  PRIMARY KEY (username)
);

-- insert some sample data into expense_categories
INSERT INTO expense_categories (name, amount, date, username, paid)
VALUES
('Rent', 1000.00, '2021-11-01', 'anna', 0),
('Groceries', 200.00, '2021-11-02', 'anna', 0),
('Electricity', 800.00, '2021-11-05', 'anna', 0),
('Internet', 600.00, '2021-11-07', 'anna', 0),
('Gas', 300.00, '2021-11-12', 'john', 0),
('Insurance', 1000.00, '2021-11-01', 'john', 0),
('Car Loan', 500.00, '2021-11-15', 'john', 0),
('Phone Bill', 100.00, '2021-11-20', 'john', 0);

-- insert some sample data into income_sources
INSERT INTO income_sources (name, amount, date, username, earned)
VALUES
('Salary', 5000.00, '2021-11-30', 'anna', 0),
('Freelance', 3000.00, '2021-11-20', 'anna', 0),
('Interest', 200.00, '2021-11-01', 'john', 0),
('Dividend', 500.00, '2021-11-15', 'john', 0),
('Bonus', 1000.00, '2021-11-25', 'john', 0);

-- insert some sample data into savings
INSERT INTO savings (amount, date, username)
VALUES
(1500.00,'2021-11-03','anna'),
(2500.00,'2021-11-04','anna'),
(3000.00,'2021-11-05','john'),
(3500.00,'2021-11-06','john');

-- insert some sample data into theme
INSERT INTO theme (name,color ,username)
VALUES
('Blue','#0000FF','anna'),
('Red','#FF0000','john');

-- insert some sample data into transactions
INSERT INTO transactions (name ,amount ,date ,username)
VALUES
('Online Shopping' ,-1000.00 ,'2021-11-17' ,'anna'),
('Cash Deposit' ,500.00 ,'2021-11-18' ,'anna'),
('Credit Card Payment' ,-2000.00 ,'2021-11-19' ,'john'),
('Lottery Ticket' ,-100.00 ,'2021-11-20' ,'john');

-- insert some sample data into user_account
INSERT INTO user_account (username ,password ,name ,age ,balance ,theme_in_use)
VALUES
('anna' ,'5678' ,'Anna Marie' ,22 ,8000.00 ,'#0000FF'),
('john' ,'9012' ,'John Smith' ,30 ,12000.00 ,'#FF0000');