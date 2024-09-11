# ATM
ATM project for Java class with an excess of functionality, this code does not currently completely run, but still has many features and methods which I am proud of.

Following are the requirements for the program: 

Write the following class files (.java) to create a bank account, deposit and withdraw to an already existing bank account. In order to open/create a new bank account a user will either provide {name, date of birth(dob)} or {name, dob, initial amount}. Your program should check whether same person (check both name and dob) has already an account or not.  If an account already exist, the user will not be able to create another account.  
Whenever an account is created the following information will be generated by your program: a bank account number of eight digits and a password of eight characters randomly selected from the following list of characters: 

String alphabets="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890#_!%^&@";

Account information (account id, name, dob, and password) will be stored in a file named accountInfo.txt and all the transactions (i.e., deposit and withdraw amount of all the accounts in the bank) are stored in another file named balanceSheet.txt. 

Your program must have the following class (.java) files:

NewAccount: This java file will be used to create a new account and contains the following methods: generatePassword(), createAccount() and other overloaded/overridden methods/constructors.

AccessAccount: Contains all the required methods to check the existence of an account, deposit an amount to the account and withdraw an amount from the account.

Account: This class will use the other classes and methods to either create an account or deposit/withdraw operations.

AccountDriver: This class (.java) has been provided here (see below) such that you can follow to create other classes and/or required methods to maintain and manage bank accounts.

Your code should include constructor overloading, static variable for the account number, and method overloading.  
