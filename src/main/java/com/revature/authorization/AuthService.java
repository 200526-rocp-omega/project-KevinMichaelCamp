package com.revature.authorization;

import java.util.Set;

import javax.servlet.http.HttpSession;

import com.revature.exceptions.NotLoggedInException;
import com.revature.exceptions.RoleNotAllowedException;
import com.revature.models.User;

public class AuthService {
	public static void guard(HttpSession session, String... roles) {
		if (session == null || session.getAttribute("currentUser") == null) {
			throw new NotLoggedInException();
		}

		User u = (User) session.getAttribute("currentUser");
		String role = u.getRole().getRole();
		boolean found = false;
		for (String allowedRole : roles) {
			if (allowedRole.equals(role)) {
				found = true;
				break;
			}
		}

		if (!found) {
			throw new RoleNotAllowedException();
		}
	}

	public static void guard(HttpSession session, int id, String... roles) {
		try {
			guard(session, roles);
		} catch (RoleNotAllowedException e) {
			User u = (User) session.getAttribute("currentUser");
			if (u.getId() != id) {
				throw e;
			}
		}
	}

	public static void guard(HttpSession session, Set<Integer> ids, String... roles) {
		try {
			guard(session, roles);
		} catch (RoleNotAllowedException e) {
			User u = (User) session.getAttribute("currentUser");
			for (int id : ids) {
				if (u.getId() == id) {
					return;
				}
			}
			throw e;
		}
	}
}
