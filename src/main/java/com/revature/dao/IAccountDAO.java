package com.revature.dao;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.AccountDisplay;

public interface IAccountDAO {
	public Account insert(Account a);

	public List<AccountDisplay> findAll();

	public List<AccountDisplay> findByStatus(int status_id);

	public List<AccountDisplay> findByTypeDisplay(int type_id);

	public List<Account> findByType(int type_id);

	public Account findById(int id);

	public Account update(int id, Account a);

	public boolean delete(int id);
}
