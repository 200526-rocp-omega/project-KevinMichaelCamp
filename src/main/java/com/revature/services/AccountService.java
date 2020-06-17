package com.revature.services;

import java.util.List;

import com.revature.dao.AccountDAO;
import com.revature.dao.IAccountDAO;
import com.revature.models.Account;
import com.revature.models.AccountDisplay;

public class AccountService {
	private IAccountDAO dao = new AccountDAO();

	public Account insert(Account a) {
		return dao.insert(a);
	}

	public List<AccountDisplay> findAll() {
		return dao.findAll();
	}

	public List<AccountDisplay> findByStatus(int status_id) {
		return dao.findByStatus(status_id);
	}

	public List<AccountDisplay> findByTypeDisplay(int type_id) {
		return dao.findByTypeDisplay(type_id);
	}

	public List<Account> findByType(int type_id) {
		return dao.findByType(type_id);
	}

	public Account findById(int id) {
		return dao.findById(id);
	}

	public Account update(int id, Account a) {
		return dao.update(id, a);
	}

	public boolean delete(int id) {
		return dao.delete(id);
	}
}
