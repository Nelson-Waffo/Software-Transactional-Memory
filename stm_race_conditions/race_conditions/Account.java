package race_conditions;

import java.security.SecureRandom;
import java.util.Date;

/**
 * An account class used to illustrate the occurrence of race conditions in some instances
 * @author Nelson
 *
 */
public class Account {

	private static final SecureRandom randomTime = new SecureRandom();
	private int balance;
	private Date date;
	private String accountName;

	/**
	 *  A constructor to create account objects	
	 * @param b
	 */
	public Account(int b, String name) {
		balance = b;
		date = new Date();
		accountName = name;
	}

	/** operations on the account*/

	/**
	 *  A method to deposit money in an account
	 * @param amount
	 * @return boolean
	 */
	public void deposit(int amount) {
		int bal = balance;
		// the thread sleep for a random amount of time
		try {
			Thread.sleep(randomTime.nextInt(400));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(amount > 0) {
			bal += amount;
			// update the balance
			balance = bal;
			// update the date
			date = new Date();
			// notify
			System.out.println(getAccountName()+" just received "+amount+"€ from a remote account");
		}
		else {
			throw new IllegalArgumentException("Invalid amount");
		}
	}

	/**
	 * A method to transfer money from one account to another account
	 * @param account
	 * @param amount
	 */
	public void transfer(Account account, int amount) {
		// money is transferred from one account to another account
		// withdraw from this account
		/**withdrawal*/
		int bal = balance;
		try {
			Thread.sleep(randomTime.nextInt(400));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(amount < 0) {
			throw new IllegalArgumentException("invalid amount");
		}
		if(bal >= amount) {
			bal -= amount;
			// update the balance
			balance = bal;
			// update the date
			date = new Date();
			// notify
			System.out.println(getAccountName()+" just transferred "+amount + "€ to a remote account");
		}else {
			throw new IllegalArgumentException("Insuffiient balance");
		}
		/** deposit money into the remote account. this method is defined above */
		account.deposit(amount);
	}

	/**
	 *  a method to display the current balance of an account
	 *  @param none
	 *  @return String
	 */
	public String toString() {
		return "the current balance is: "+balance+"€ on "+date.toString();
	}
	
	/**
	 * A method to return the name of the account
	 * @return String
	 */
	public String getAccountName() {
		return accountName;
	}

}

