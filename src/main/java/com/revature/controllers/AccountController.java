package com.revature.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.revature.authorization.AuthService;
import com.revature.models.Account;
import com.revature.models.AccountDisplay;
import com.revature.models.User;
import com.revature.models.UserAccount;
import com.revature.services.AccountService;
import com.revature.services.UserAccountService;
import com.revature.templates.AccountTemplate;
import com.revature.templates.JointTemplate;
import com.revature.templates.TimeTemplate;
import com.revature.templates.TransactionTemplate;
import com.revature.templates.TransferTemplate;

public class AccountController {
	private final AccountService acctService = new AccountService();
	private final UserAccountService uaService = new UserAccountService();

	public List<AccountDisplay> findAllAccounts() {
		return acctService.findAll();
	}
	
	public Account findById(int id) {
		return acctService.findById(id);
	}
	
	public List<AccountDisplay> findAccountByID(HttpSession session, int id) {
		List<User> acctUsers = uaService.findUsersByAcct(id);
		Set<Integer> userIds = new HashSet<>();
		for (User u : acctUsers) {
			userIds.add(u.getId());
		}
		AuthService.guard(session, userIds, "Admin");
		return uaService.findByAcct(id);
	}
	
	public List<AccountDisplay> findAccountsByUser(HttpSession session, int id) {
		return uaService.findAcctsByUser(id);
	}

	public List<AccountDisplay> findAccountsByStatus(int status_id) {
		return acctService.findByStatus(status_id);
	}

	public List<AccountDisplay> findAccountsByType(int type_id) {
		return acctService.findByTypeDisplay(type_id);
	}

	public UserAccount create(AccountTemplate at) {
		Account createdAcct = acctService.insert(at.getAccount());
		uaService.createJoin(at.getUser_id(), createdAcct.getId());
		return uaService.findByPK(at.getUser_id(), createdAcct.getId());
	}

	public Account update(int id, Account a) {
		return acctService.update(id, a);
	}

	public List<AccountDisplay> addJoint(HttpSession session, JointTemplate jt) {
		User u = (User) session.getAttribute("currentUser");
		if (uaService.findUsersByAcct(jt.getAccountId()).get(0).getId() != u.getId()) {
			return null;
		}
		if (uaService.createJoin(jt.getUserId(), jt.getAccountId())) {
			return uaService.findByAcct(jt.getAccountId());
		}
		return null;
	}

	public boolean withdraw(HttpSession session, TransactionTemplate tt) {
		List<User> acctUsers = uaService.findUsersByAcct(tt.getAccountId());
		Set<Integer> userIds = new HashSet<>();
		for (User u : acctUsers) {
			userIds.add(u.getId());
		}
		AuthService.guard(session, userIds, "Admin");
		Account acct = acctService.findById(tt.getAccountId());
		if (acct.getBalance() >= tt.getAmount()) {
			Account updateAcct = new Account(acct.getId(), acct.getBalance() - tt.getAmount(), acct.getStatus(),
					acct.getType());
			acctService.update(tt.getAccountId(), updateAcct);
			return true;
		} else {
			return false;
		}
	}

	public boolean deposit(HttpSession session, TransactionTemplate tt) {
		List<User> acctUsers = uaService.findUsersByAcct(tt.getAccountId());
		Set<Integer> userIds = new HashSet<>();
		for (User u : acctUsers) {
			userIds.add(u.getId());
		}
		AuthService.guard(session, userIds, "Admin");
		Account acct = acctService.findById(tt.getAccountId());
		Account updateAcct = new Account(acct.getId(), acct.getBalance() + tt.getAmount(), acct.getStatus(),
				acct.getType());
		acctService.update(tt.getAccountId(), updateAcct);
		return true;
	}

	public boolean transfer(HttpSession session, TransferTemplate tt) {
		List<User> acctUsers = uaService.findUsersByAcct(tt.getSourceAccountId());
		Set<Integer> userIds = new HashSet<>();
		for (User u : acctUsers) {
			userIds.add(u.getId());
		}
		AuthService.guard(session, userIds, "Admin");
		Account source = acctService.findById(tt.getSourceAccountId());
		Account target = acctService.findById(tt.getTargetAccountId());
		if (source.getBalance() >= tt.getAmount()) {
			Account withdrawAcct = new Account(source.getId(), source.getBalance() - tt.getAmount(), source.getStatus(),
					source.getType());
			Account depositAcct = new Account(target.getId(), target.getBalance() + tt.getAmount(), target.getStatus(),
					target.getType());
			acctService.update(tt.getSourceAccountId(), withdrawAcct);
			acctService.update(tt.getTargetAccountId(), depositAcct);
			return true;
		} else {
			return false;
		}
	}

	public void accrueInterest(TimeTemplate tt) {
		List<Account> savingsAccts = acctService.findByType(2);
		for (Account a : savingsAccts) {
			double interest = tt.getNumOfMonths() * (a.getBalance() * .001);
			Account addInterest = new Account(a.getId(), a.getBalance() + interest, a.getStatus(), a.getType());
			acctService.update(a.getId(), addInterest);
		}
	}

	public boolean delete(HttpSession session, int id) {
		List<User> acctUsers = uaService.findUsersByAcct(id);
		Set<Integer> userIds = new HashSet<>();
		for (User u : acctUsers) {
			userIds.add(u.getId());
		}
		AuthService.guard(session, userIds, "Employee", "Admin");
		return acctService.delete(id);
	}

}
