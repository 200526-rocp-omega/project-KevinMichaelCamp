package com.revature.dao;

import java.util.List;

import com.revature.models.User;

public interface IUserDAO {
	public User insert(User u);

	public List<User> findAll();

	public User findById(int id);

	public User findByName(String username);

	public User update(int id, User u);

	public boolean delete(int id);
}
