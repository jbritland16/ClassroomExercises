package dtos;

import com.jbritland.entities.BankAccount;

public class BankAccountTransactionDetailsDTO {

	private BankAccount withdrawAccount;
	private BankAccount depositAccount;
	private double amount;
	private String transactionDetails;
	
	public BankAccountTransactionDetailsDTO() {
		super();
	}

	public BankAccountTransactionDetailsDTO(BankAccount outAccount, double amount, String transactionDetails) {
		super();
		this.withdrawAccount = outAccount;
		this.amount = amount;
		this.transactionDetails = transactionDetails;
	}

	public BankAccountTransactionDetailsDTO(BankAccount outAccount, BankAccount inAccount, double amount,
			String transactionDetails) {
		super();
		this.withdrawAccount = outAccount;
		this.depositAccount = inAccount;
		this.amount = amount;
		this.transactionDetails = transactionDetails;
	}

	public BankAccount getWithdrawAccount() {
		return withdrawAccount;
	}

	public BankAccount getDepositAccount() {
		return depositAccount;
	}

	public double getAmount() {
		return amount;
	}

	public String getTransactionDetails() {
		return transactionDetails;
	}

	@Override
	public String toString() {
		return "BankAccountTransactionDetailsDTO [outAccount=" + withdrawAccount + ", inAccount=" + depositAccount + ", amount="
				+ amount + ", transactionDetails=" + transactionDetails + "]";
	}
	
	
	
}
