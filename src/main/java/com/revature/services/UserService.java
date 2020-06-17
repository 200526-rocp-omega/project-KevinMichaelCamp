package com.revature.services;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import com.revature.dao.IUserDAO;
import com.revature.dao.UserDAO;
import com.revature.models.User;
import com.revature.templates.LoginTemplate;

public class UserService {
	private IUserDAO dao = new UserDAO();

	public User insert(User u) {
		return dao.insert(u);
	}

	public List<User> listAll() {
		return dao.findAll();
	}

	public User findById(int id) {
		return dao.findById(id);
	}

	public User findByName(String username) {
		return dao.findByName(username);
	}

	public User update(int id, User u) {
		return dao.update(id, u);
	}

	public boolean delete(int id) {
		return dao.delete(id);
	}
	
	public User login(LoginTemplate lt) {
		User userFromDB = findByName(lt.getUsername());
		
		if (userFromDB == null) {
			return null;
		}
		
		if (BCrypt.checkpw(lt.getPassword(), userFromDB.getPassword())) {
			return userFromDB;
		}
//		if (userFromDB.getPassword().equals(lt.getPassword())) {
//			return userFromDB;
//		}
		return null;
	}
	
	public void logout(HttpSession session) {
		session.invalidate();
	}
}
