package lock_based_synchronization;

/**
 * A class which contains the task: to transfer money between two accounts
 * @author Nelson
 *
 */
public class AccountTasks implements Runnable {
 
	private final Account account1;
	private final Account account2;
	private final int amount;
	
	public AccountTasks(Account a1, Account a2, int a) {
		account1 = a1;
		account2 = a2;
		amount = a;
	}
	
	/**
	 * The task
	 * A method where account transactions are made. in this example, money is transferred from one account to another
	 * @param none
	 * @author Nelson
	 */
	public void run() {	
		// transfer money from this account1 to account account2
		account1.transfer(account2, amount);
	}
}
