package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.AccountDisplay;
import com.revature.models.AccountStatus;
import com.revature.models.AccountType;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.models.UserAccount;
import com.revature.models.UserDisplay;
import com.revature.util.ConnectionUtil;

public class UserAccountDAO implements IUserAccountDAO {

	@Override
	public boolean createJoin(int user_id, int account_id) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO USERS_ACCOUNTS VALUES (?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, user_id);
			stmt.setInt(2, account_id);

			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<UserAccount> findAll() {
		List<UserAccount> useraccts = new ArrayList<>();

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM USERS_ACCOUNTS\r\n" + "INNER JOIN USERS ON user_id = USERS.id\r\n"
					+ "INNER JOIN ROLES ON USERS.role_id = ROLES.id\r\n"
					+ "INNER JOIN ACCOUNTS ON account_id = ACCOUNTS.id\r\n"
					+ "INNER JOIN ACCOUNT_STATUS ON ACCOUNTS.status_id = ACCOUNT_STATUS.id\r\n"
					+ "INNER JOIN ACCOUNT_TYPE ON ACCOUNTS.type_id = ACCOUNT_type.id";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int user_id = rs.getInt("user_id");
				int account_id = rs.getInt("account_id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				int role_id = rs.getInt("role_id");
				String role = rs.getString("role");
				double balance = rs.getDouble("balance");
				int status_id = rs.getInt("status_id");
				String status = rs.getString("status");
				int type_id = rs.getInt("type_id");
				String type = rs.getString("type");

				Role r = new Role(role_id, role);
				User u = new User(user_id, username, password, firstName, lastName, email, r);
				AccountStatus as = new AccountStatus(status_id, status);
				AccountType at = new AccountType(type_id, type);
				Account a = new Account(account_id, balance, as, at);
				UserAccount ua = new UserAccount(user_id, account_id, u, a);
				useraccts.add(ua);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
		return useraccts;
	}

	@Override
	public List<AccountDisplay> findAcctsByUser(int user_id) {
		List<AccountDisplay> accounts = new ArrayList<>();

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ACCOUNTS\r\n"
					+ "INNER JOIN ACCOUNT_STATUS ON ACCOUNTS.status_id = ACCOUNT_STATUS.id\r\n"
					+ "INNER JOIN ACCOUNT_TYPE ON ACCOUNTS.type_id = ACCOUNT_TYPE.id\r\n"
					+ "INNER JOIN USERS_ACCOUNTS ON ACCOUNTS.id = USERS_ACCOUNTS.account_id\r\n"
					+ "INNER JOIN USERS ON user_id = USERS.id\r\n" + "INNER JOIN ROLES ON role_id = ROLES.id\r\n"
					+ "WHERE USERS.id = ? ORDER BY ACCOUNTS.id";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, user_id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int account_id = rs.getInt("account_id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				String role = rs.getString("role");
				double balance = rs.getDouble("balance");
				String status = rs.getString("status");
				String type = rs.getString("type");

				UserDisplay u = new UserDisplay(user_id, username, password, firstName, lastName, email, role);
				AccountDisplay a = new AccountDisplay(account_id, balance, status, type, u);
				accounts.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
		return accounts;
	}

	@Override
	public List<User> findUsersByAcct(int account_id) {
		List<User> users = new ArrayList<>();

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM USERS_ACCOUNTS\r\n" + "INNER JOIN USERS ON user_id = USERS.id\r\n"
					+ "INNER JOIN ROLES ON USERS.role_id = ROLES.id\r\n" + "WHERE account_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, account_id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int user_id = rs.getInt("user_id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				int role_id = rs.getInt("role_id");
				String role = rs.getString("role");

				Role r = new Role(role_id, role);
				User u = new User(user_id, username, password, firstName, lastName, email, r);
				users.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
		return users;
	}

	@Override
	public UserAccount findByPK(int user_id, int account_id) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM USERS_ACCOUNTS\r\n" + "INNER JOIN USERS ON user_id = USERS.id\r\n"
					+ "INNER JOIN ROLES ON USERS.role_id = ROLES.id\r\n"
					+ "INNER JOIN ACCOUNTS ON account_id = ACCOUNTS.id\r\n"
					+ "INNER JOIN ACCOUNT_STATUS ON status_id = ACCOUNT_STATUS.id\r\n"
					+ "INNER JOIN ACCOUNT_TYPE ON type_id = ACCOUNT_TYPE.id\r\n"
					+ "WHERE user_id = ? AND account_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, user_id);
			stmt.setInt(2, account_id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				int role_id = rs.getInt("role_id");
				String role = rs.getString("role");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				int status_id = rs.getInt("status_id");
				String status = rs.getString("status");
				int type_id = rs.getInt("type_id");
				String type = rs.getString("type");
				double balance = rs.getDouble("balance");

				Role r = new Role(role_id, role);
				User u = new User(user_id, username, password, firstName, lastName, email, r);
				AccountStatus as = new AccountStatus(status_id, status);
				AccountType at = new AccountType(type_id, type);
				Account a = new Account(account_id, balance, as, at);
				UserAccount ua = new UserAccount(user_id, account_id, u, a);
				return ua;
			} else {
				return new UserAccount();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new UserAccount();
		}
	}

	@Override
	public List<AccountDisplay> findByAcct(int account_id) {
		List<AccountDisplay> accounts = new ArrayList<>();
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ACCOUNTS\r\n"
					+ "INNER JOIN ACCOUNT_STATUS ON ACCOUNTS.status_id = ACCOUNT_STATUS.id\r\n"
					+ "INNER JOIN ACCOUNT_TYPE ON ACCOUNTS.type_id = ACCOUNT_TYPE.id\r\n"
					+ "INNER JOIN USERS_ACCOUNTS ON ACCOUNTS.id = USERS_ACCOUNTS.account_id\r\n"
					+ "INNER JOIN USERS ON user_id = USERS.id\r\n" + "INNER JOIN ROLES ON role_id = ROLES.id\r\n"
					+ "WHERE ACCOUNTS.id = ? ORDER BY ACCOUNTS.id";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, account_id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int user_id = rs.getInt("user_id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				String role = rs.getString("role");
				double balance = rs.getDouble("balance");
				String status = rs.getString("status");
				String type = rs.getString("type");

				UserDisplay u = new UserDisplay(user_id, username, password, firstName, lastName, email, role);
				AccountDisplay a = new AccountDisplay(account_id, balance, status, type, u);
				accounts.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
		return accounts;
	}

	@Override
	public boolean deleteJoin(int user_id, int account_id) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "DELETE FROM USERS_ACCOUNTS WHERE user_id = ? AND account_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, user_id);
			stmt.setInt(2, account_id);

			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
