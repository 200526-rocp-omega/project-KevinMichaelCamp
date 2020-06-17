package com.revature.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.revature.models.AccountDisplay;
import com.revature.models.User;
import com.revature.services.AccountService;
import com.revature.services.UserAccountService;
import com.revature.services.UserService;
import com.revature.templates.LoginTemplate;

public class UserController {
	private final UserService service = new UserService();
	private final UserAccountService uaService = new UserAccountService();
	private final AccountService acctService = new AccountService();

	public User login(LoginTemplate lt) {
		return service.login(lt);
	}

	public void logout(HttpSession session) {
		service.logout(session);
	}

	public User findUserById(int id) {
		return service.findById(id);
	}

	public List<User> listAllUsers() {
		return service.listAll();
	}

	public User create(User u) {
		return service.insert(u);
	}

	public User update(int id, User u) {
		return service.update(id, u);
	}

	public boolean delete(int id) {
		List<AccountDisplay> accounts = uaService.findAcctsByUser(id);
		for (AccountDisplay acct : accounts) {
			uaService.deleteJoin(id, acct.getId());
			if (uaService.findUsersByAcct(acct.getId()).size() == 0) {
				acctService.delete(acct.getId());
			}
		}
		return service.delete(id);
	}
}
