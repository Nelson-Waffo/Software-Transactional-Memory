package race_conditions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * a class used to expose race conditions: race conditions refer to the situation wherein the final result of an
 * operation depends on which thread gets executed when. 
 * @author Nelson
 *
 */
public class AccountTransfers {

	/**
	 * A method which aims to show the final state of shared data when there is no control on read and write operations on it. 
	 * in this method, three accounts are created with an initial balance, all three accounts simultaneous interact
	 * with each other, and the final state of their balance, the shared data, is observed after execution
	 * @param args
	 * @return none
	 */
	public static void main(String[] args) {
		
		// first of all, the 3 accounts are created
		Account account1 = new Account(10000, "Account_1");
		Account account2 = new Account(20000, "Account_2");
		Account account3 = new Account(30000, "Account_3");
		// display the initial balance in the 3 accounts
		System.out.println("Balances of the three accounts before the three simultaneous transfers");
		System.out.println();
		System.out.println("Account_1: "+account1);
		System.out.println("Account_2: "+ account2);
		System.out.println("Account_3: "+account3);
		System.out.println();
		System.out.println("Operations: Bank transfers");
		// now create an executor object to manage a pool of threads: thread re-use
		ExecutorService service = Executors.newCachedThreadPool();
		
        // three tasks where 3 accounts transfer money to each other
		
		service.execute(new AccountTasks(account1, account2, 5000));
		service.execute(new AccountTasks(account1, account3, 5000));
		service.execute(new AccountTasks(account3, account2, 1000));
	
		// tell the executor not to accept any more tasks
		service.shutdown();
		
		// wait a minute at most for the tasks to finish and then show the current balance in the shared account
		try {
			boolean waitEnd = service.awaitTermination(1, TimeUnit.MINUTES);
			
			if(waitEnd) {
				// display the current balance in the shared account. the toString method gets automatically called
				System.out.println();
				System.out.println("Balances of the three accounts after the three simultaneous transfers");
				System.out.println();
				System.out.println("Account_1: "+account1);
				System.out.println("Account_2: "+account2);
				System.out.println("Account_3: "+account3);
			}
			else {
				System.out.println("the execution required more than 1 minute.\nA DEADLOCK occured. so it could not complete.");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
