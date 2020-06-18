package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class UserDAO implements IUserDAO {

	@Override
	public User insert(User u) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String hashed = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());
			String sql = "INSERT INTO USERS (username, password, first_name, last_name, email, role_id) VALUES (?,?,?,?,?,?)";
			String columnNames[] = new String[] { "id" };
			PreparedStatement stmt = conn.prepareStatement(sql, columnNames);
			stmt.setString(1, u.getUsername());
			stmt.setString(2, hashed);
			stmt.setString(3, u.getFirstName());
			stmt.setString(4, u.getLastName());
			stmt.setString(5, u.getEmail());
			stmt.setInt(6, u.getRole().getId());

			if (stmt.executeUpdate() == 0)
				return new User();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				return findById(id);
			} else {
				return new User();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new User();
		}
	}

	@Override
	public List<User> findAll() {
		List<User> allUsers = new ArrayList<>();

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM USERS INNER JOIN ROLES ON USERS.role_id = ROLES.id ORDER BY USERS.id";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				int role_id = rs.getInt("role_id");
				String role = rs.getString("role");

				Role r = new Role(role_id, role);
				User u = new User(id, username, password, firstName, lastName, email, r);
				allUsers.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
		return allUsers;
	}

	@Override
	public User findById(int id) {
		User user = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM USERS INNER JOIN ROLES ON USERS.role_id = ROLES.id WHERE USERS.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				int user_id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				int role_id = rs.getInt("role_id");
				String role = rs.getString("role");

				Role r = new Role(role_id, role);
				user = new User(user_id, username, password, firstName, lastName, email, r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new User();
		}
		return user;
	}

	@Override
	public User findByName(String username) {
		User user = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM USERS INNER JOIN ROLES ON USERS.role_id = ROLES.id WHERE username = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				int user_id = rs.getInt("id");
				String uname = rs.getString("username");
				String password = rs.getString("password");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				int role_id = rs.getInt("role_id");
				String role = rs.getString("role");

				Role r = new Role(role_id, role);
				user = new User(user_id, uname, password, firstName, lastName, email, r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new User();
		}
		return user;
	}

	@Override
	public User update(int id, User u) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String hashed = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());
			String sql = "UPDATE USERS SET username = ?, password = ?, first_name = ?, last_name = ?, email = ? WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, u.getUsername());
			stmt.setString(2, hashed);
			stmt.setString(3, u.getFirstName());
			stmt.setString(4, u.getLastName());
			stmt.setString(5, u.getEmail());
			stmt.setInt(6, id);

			if (stmt.executeUpdate() != 0)
				return findById(id);
			else
				return new User();

		} catch (SQLException e) {
			e.printStackTrace();
			return new User();
		}
	}

	@Override
	public boolean delete(int id) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "DELETE FROM USERS WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);

			if (stmt.executeUpdate() != 0)
				return true;
			else
				return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
