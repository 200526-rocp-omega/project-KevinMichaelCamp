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
import com.revature.models.UserDisplay;
import com.revature.util.ConnectionUtil;

public class AccountDAO implements IAccountDAO {

	@Override
	public Account insert(Account a) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO ACCOUNTS (balance, status_id, type_id) VALUES (?,?,?)";
			String columnNames[] = new String[] { "id" };
			PreparedStatement stmt = conn.prepareStatement(sql, columnNames);
			stmt.setDouble(1, a.getBalance());
			stmt.setInt(2, a.getStatus().getId());
			stmt.setInt(3, a.getType().getId());

			if (stmt.executeUpdate() == 0)
				return new Account();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				return findById(id);
			} else {
				return new Account();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new Account();
		}
	}

	@Override
	public List<AccountDisplay> findAll() {
		List<AccountDisplay> allAccounts = new ArrayList<>();

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ACCOUNTS\r\n"
					+ "INNER JOIN ACCOUNT_STATUS ON ACCOUNTS.status_id = ACCOUNT_STATUS.id\r\n"
					+ "INNER JOIN ACCOUNT_TYPE ON ACCOUNTS.type_id = ACCOUNT_TYPE.id\r\n"
					+ "INNER JOIN USERS_ACCOUNTS ON ACCOUNTS.id = USERS_ACCOUNTS.account_id\r\n"
					+ "INNER JOIN USERS ON user_id = USERS.id\r\n" + "INNER JOIN ROLES ON role_id = ROLES.id\r\n"
					+ "ORDER BY ACCOUNTS.id";
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
				String role = rs.getString("role");
				double balance = rs.getDouble("balance");
				String status = rs.getString("status");
				String type = rs.getString("type");

				UserDisplay u = new UserDisplay(user_id, username, password, firstName, lastName, email, role);
				AccountDisplay a = new AccountDisplay(account_id, balance, status, type, u);
				allAccounts.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
		return allAccounts;
	}

	@Override
	public List<AccountDisplay> findByStatus(int status_id) {
		List<AccountDisplay> accounts = new ArrayList<>();

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ACCOUNTS\r\n"
					+ "INNER JOIN ACCOUNT_STATUS ON ACCOUNTS.status_id = ACCOUNT_STATUS.id\r\n"
					+ "INNER JOIN ACCOUNT_TYPE ON ACCOUNTS.type_id = ACCOUNT_TYPE.id\r\n"
					+ "INNER JOIN USERS_ACCOUNTS ON ACCOUNTS.id = USERS_ACCOUNTS.account_id\r\n"
					+ "INNER JOIN USERS ON user_id = USERS.id\r\n" + "INNER JOIN ROLES ON role_id = ROLES.id\r\n"
					+ "WHERE status_id = ? ORDER BY ACCOUNTS.id";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, status_id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int user_id = rs.getInt("user_id");
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
	public List<AccountDisplay> findByTypeDisplay(int type_id) {
		List<AccountDisplay> accounts = new ArrayList<>();

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ACCOUNTS\r\n"
					+ "INNER JOIN ACCOUNT_STATUS ON ACCOUNTS.status_id = ACCOUNT_STATUS.id\r\n"
					+ "INNER JOIN ACCOUNT_TYPE ON ACCOUNTS.type_id = ACCOUNT_TYPE.id\r\n"
					+ "INNER JOIN USERS_ACCOUNTS ON ACCOUNTS.id = USERS_ACCOUNTS.account_id\r\n"
					+ "INNER JOIN USERS ON user_id = USERS.id\r\n" + "INNER JOIN ROLES ON role_id = ROLES.id\r\n"
					+ "WHERE type_id = ? ORDER BY ACCOUNTS.id";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, type_id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int user_id = rs.getInt("user_id");
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
	public List<Account> findByType(int type_id) {
		List<Account> accounts = new ArrayList<>();

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ACCOUNTS\r\n" + "INNER JOIN ACCOUNT_STATUS ON status_id = ACCOUNT_STATUS.id\r\n"
					+ "INNER JOIN ACCOUNT_TYPE ON type_id = ACCOUNT_TYPE.id\r\n"
					+ "WHERE type_id = ? ORDER BY ACCOUNTS.id";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, type_id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				double balance = rs.getDouble("balance");
				String status = rs.getString("status");
				int status_id = rs.getInt("status_id");
				String type = rs.getString("type");

				AccountStatus as = new AccountStatus(status_id, status);
				AccountType at = new AccountType(type_id, type);
				Account a = new Account(id, balance, as, at);
				accounts.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
		return accounts;
	}

	@Override
	public Account findById(int id) {
		Account account = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ACCOUNTS INNER JOIN ACCOUNT_STATUS ON ACCOUNTS.status_id = ACCOUNT_STATUS.id INNER JOIN ACCOUNT_TYPE ON ACCOUNTS.type_id = ACCOUNT_TYPE.id WHERE ACCOUNTS.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				int account_id = rs.getInt("id");
				double balance = rs.getDouble("balance");
				int status_id = rs.getInt("status_id");
				String status = rs.getString("status");
				int type_id = rs.getInt("type_id");
				String type = rs.getString("type");

				AccountStatus as = new AccountStatus(status_id, status);
				AccountType at = new AccountType(type_id, type);
				account = new Account(account_id, balance, as, at);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new Account();
		}
		return account;
	}

	@Override
	public Account update(int id, Account a) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "UPDATE ACCOUNTS SET balance = ?, status_id = ?, type_id = ? WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, a.getBalance());
			stmt.setInt(2, a.getStatus().getId());
			stmt.setInt(3, a.getType().getId());
			stmt.setInt(4, id);

			if (stmt.executeUpdate() != 0) {
				return findById(id);
			} else {
				return new Account();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new Account();
		}
	}

	@Override
	public boolean delete(int id) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "DELETE FROM ACCOUNTS WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);

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
