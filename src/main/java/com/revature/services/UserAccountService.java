package com.revature.services;

import java.util.List;

import com.revature.dao.IUserAccountDAO;
import com.revature.dao.UserAccountDAO;
import com.revature.models.AccountDisplay;
import com.revature.models.User;
import com.revature.models.UserAccount;

public class UserAccountService {
	private IUserAccountDAO uaDao = new UserAccountDAO();
	
	public boolean createJoin(int user_id, int account_id) {
		return uaDao.createJoin(user_id, account_id);
	}

	public List<UserAccount> findAll(){
		return uaDao.findAll();
	}

	public List<AccountDisplay> findAcctsByUser(int user_id){
		return uaDao.findAcctsByUser(user_id);
	}

	public List<User> findUsersByAcct(int account_id){
		return uaDao.findUsersByAcct(account_id);
	}
	
	public UserAccount findByPK(int user_id, int account_id) {
		return uaDao.findByPK(user_id, account_id);
	}
	
	public List<AccountDisplay> findByAcct(int account_id){
		return uaDao.findByAcct(account_id);
	}
	
	public boolean deleteJoin(int user_id, int account_id) {
		return uaDao.deleteJoin(user_id, account_id);
	}
}
