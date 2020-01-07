package lock_based_synchronization;

import java.security.SecureRandom;
import java.util.Date;


/**
 * A class which shows how race conditions can be solved using lock-based synchronisation. the idea is to place regions
 * of code encompassing shared data in synchronised statements. this is basically achieved using the keyword synchronized.
 * now for a method to be a executed, the thread must first acquire the lock on the calling object, and because each 
 * object in java has a unique lock, only one thread at a time can execute a synchronized method.
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
	 * @param name
	 * 
	 */
	public Account(int b, String name) {
		balance = b;
		date = new Date();
		accountName = name;
	}

	/** operations on the account*/

	/**
	 *  A method to deposit money in the account
	 * @param amount
	 * @return boolean
	 */
	public synchronized void deposit(int amount) {
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
	public synchronized void transfer(Account account, int amount) {
		// withdraw from this account
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
		// deposit money into the remote account. this method is defined above
		account.deposit(amount);
	}

	/**
	 *  a method to display the current balance of an account
	 *  @param none
	 *  @return String
	 */
	public String toString() {
		return "your current balance is: "+balance+"€ on "+date.toString();
	}
	
	/**
	 * A method to return the name of the account
	 * @return String
	 */
	public String getAccountName() {
		return accountName;
	}

}

