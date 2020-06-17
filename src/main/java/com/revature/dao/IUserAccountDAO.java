package com.revature.dao;

import java.util.List;

import com.revature.models.AccountDisplay;
import com.revature.models.User;
import com.revature.models.UserAccount;

public interface IUserAccountDAO {
	public boolean createJoin(int user_id, int account_id);

	public List<UserAccount> findAll();

	public List<AccountDisplay> findAcctsByUser(int user_id);

	public List<User> findUsersByAcct(int account_id);

	public UserAccount findByPK(int user_id, int account_id);

	public List<AccountDisplay> findByAcct(int account_id);

	public boolean deleteJoin(int user_id, int account_id);
}
