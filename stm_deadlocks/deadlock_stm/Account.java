package deadlock_stm;

import java.security.SecureRandom;
import java.util.Date;

import org.multiverse.api.StmUtils;
import org.multiverse.api.Txn;
import org.multiverse.api.callables.TxnCallable;
import org.multiverse.api.references.TxnInteger;
import org.multiverse.api.references.TxnRef;

/**
 * This class uses the concept of software transactional memory to control access to shared data. Put simply,
 * the regions of code which encompass shared data are made atomic. This approach never suffers from deadlocks.
 * @author Nelson
 *
 */
public class Account {

	private static final SecureRandom randomTime = new SecureRandom();
	private TxnInteger balance;
	private TxnRef<Date> date;
	private TxnRef<String> name;

	/**
	 *  A constructor to create account objects	
	 * @param b
	 */
	public Account(int b, String n) {
		balance = StmUtils.newTxnInteger(b);
		date = StmUtils.newTxnRef(new Date());
		name = StmUtils.newTxnRef(n);
	}

	/** operations on the account*/

	/**
	 *  A method to deposit money in an account
	 * @param amount
	 * @return boolean
	 */
	public void deposit(int amount) {
		// place the code in a transaction
		StmUtils.atomic(new Runnable() {
			@Override
			public void run() {
				TxnInteger bal = balance;
				try {
					Thread.sleep(randomTime.nextInt(400));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(amount > 0) {
					bal.increment(amount);
					// update the balance
					balance = bal;
					// update the date
					date.set(new Date());
					// notify
					System.out.println(getAccountName()+" just received "+amount + "€ from a remote account");
				}else {
					throw new IllegalArgumentException("Invalid amount");
				}
			}
		});
	}
	
	/**
	 * A method to transfer money from one account to another account
	 * @param account
	 * @param amount
	 */
	public void transfer(Account account, int amount) {
		// place the code in a transaction
		StmUtils.atomic(new Runnable() {
			public void run() {
				// money is transferred from one account to another account
				/**withdrawal from this account*/
				StmUtils.atomic(new Runnable() {
					public void run() {
						TxnInteger bal = balance;
						try {
							Thread.sleep(randomTime.nextInt(400));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if(amount < 0) {
							throw new IllegalArgumentException("invalid amount");
						}
						bal.decrement(amount);
						// update the balance
						balance = bal;
						// update the date
						date.set(new Date());
						if(balance.get() < 0) {
							throw new IllegalArgumentException("not enough balance in the account for this withdrawal");
						}
						// notify
						System.out.println(getAccountName()+" just transferred "+amount + "€ to a remote account");
					}
				});
				/** deposit money into the remote account */
				account.deposit(amount);
			}
		});
	}

	/**
	 *  a method to display the current balance of an account
	 *  @param none
	 *  @return String
	 */
	public String toString() {
		return StmUtils.atomic(new TxnCallable<String>() {
			@Override
			public String call(final Txn txn) throws Exception {
				return String.format("the balance is: %d€ on %tF %<tT", balance.get(txn), date.get(txn));
			}
		});
	}	
	/**
	 * A method to return the name of the account
	 * @return String
	 */
	
	public String getAccountName() {
		return StmUtils.atomic(new TxnCallable<String>() {
			@Override
			public String call(final Txn txn) throws Exception {
				return String.format("%s", name.get(txn));
			}
		});
	}
}

