interface Bank {
void deposit(int id, int val) throws InvalidAccount;
void withdraw(int id, int val) throws InvalidAccount, NotEnoughFunds;
int totalBalance(int accounts[]) throws InvalidAccount;
int totalBalance() throws InvalidAccount;
void transfer(int from, int to, int amount) throws InvalidAccount, NotEnoughFunds;
int numAccounts();

}