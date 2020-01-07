package race_conditions;

/**
 * A class which defines the task perform on an account object
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
	 * the method which defines the actual task: to transfer money from one account to another account
	 * @param none
	 * @author Nelson
	 */
	public void run() {	
		// transfer money from account1 to account account2
		account1.transfer(account2, amount);
	}
}

