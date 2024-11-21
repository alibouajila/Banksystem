# BankSystem

A simple Java-based banking system that integrates with an Oracle database. This project allows users to create accounts, deposit/withdraw funds, transfer money between accounts, and view account details.

## Features:
- **Account Creation**: Users can create an account with CIN, username, and account type.
- **Deposit & Withdraw**: Deposit and withdraw funds from the account.
- **Transfer Funds**: Transfer money between accounts.
- **Database Integration**: Stores account data (balance, account type) in an Oracle database.

## Setup:

### 1. Clone the repository:
git clone https://github.com/alibouajila/BankSystem.git  
cd BankSystem

### 2.Database setup and table creation.
**Ensure your Oracle Database is running and accessible at localhost:1521:XE**  
(or modify the database connection settings in the Database class if needed).  
Create the accounts table by running the following SQL in your Oracle database:  

**CREATE TABLE accounts (**  
    **account_num VARCHAR2(20) PRIMARY KEY,** 
    **cin VARCHAR2(20),**  
    **username VARCHAR2(50),**   
    **balance NUMBER(10, 2),**    
    **account_type NUMBER(1)**  
**);**   
### 3.Connect the application to your created Database: 
- **Check the Database.java class and change USERNAME and PASSWORD to your personal database information.**
- **NOTE:** you may need to change the port of the localhost to the port in use too

### 4. Maven build process to install dependencies: 
- **Install Maven if it's not already installed.**  
(Follow the instructions on the official Maven website)   
- **Build the project and install dependencies by running:**  
- "**mvn clean install**"  
### 5.Run the project:  
- "**mvn exec:java** " 

 ## Dependencies:  
-  Information about required tools (JDK, Maven, Oracle sql developper).
## License:  
- MIT License.




