
public class RyanMonicaProblem implements Runnable {

	/*
	 * There will be ONE instance of the RyanAndMonicaProblem. That means only ONE
	 * instance of the bank account. Both threads will access this one account.
	 */
	private BankAccount account = new BankAccount();

	public static void main(String[] args) {
		// Instantiate the Runnable (job)
		RyanMonicaProblem theJob = new RyanMonicaProblem();
		/*
		 * Make two threads, giving each thread the same Runnable job. That means both
		 * threads will be accessing the one account instance variable in the Runnable
		 * class.
		 */
		Thread one = new Thread(theJob);
		Thread two = new Thread(theJob);
		one.setName("Ryan");
		two.setName("Monica");
		one.start();
		two.start();
	}

	class BankAccount {
		// The account starts with a balance of $100
		private int balance = 100;

		public int getBalance() {
			return balance;
		}

		public void withdraw(int amount) {
			balance = balance - amount;
		}
	}

	private void makeWithdrawal(int amount) {
		/*
		 * Check the account balance, and if there's not enough money, we just print a
		 * message. If there IS enough, we go to sleep, then wake up and complete the
		 * withdrawal, just like Ryan did.
		 */
		if (account.getBalance() >= amount) {
			System.out.println(Thread.currentThread().getName() + " is about to withdraw.");
			try {
				System.out.println(Thread.currentThread().getName() + " is going to sleep.");
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " woke up.");
			account.withdraw(amount);
			System.out.println(Thread.currentThread().getName() + " completes the withdrawal.");
		} else {
			System.out.println("Sorry, not enought for " + Thread.currentThread().getName());
		}

	}

	/*
	 * In the run() method, a thread loops through and tries to make a withdrawal
	 * with each iteration. After the withdrawal, it checks the balance once again
	 * to see if the account is overdrawn.
	 */
	public void run() {
		for (int x = 0; x < 10; x++) {
			makeWithdrawal(10);
			if (account.getBalance() < 0) {
				System.out.println("Overdrawn!");
			}
		}
	}
}