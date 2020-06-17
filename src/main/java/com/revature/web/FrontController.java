package com.revature.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.authorization.AuthService;
import com.revature.controllers.AccountController;
import com.revature.controllers.UserController;
import com.revature.exceptions.AuthorizationException;
import com.revature.models.Account;
import com.revature.models.AccountDisplay;
import com.revature.models.User;
import com.revature.models.UserAccount;
import com.revature.templates.AccountTemplate;
import com.revature.templates.JointTemplate;
import com.revature.templates.LoginTemplate;
import com.revature.templates.MessageTemplate;
import com.revature.templates.TimeTemplate;
import com.revature.templates.TransactionTemplate;
import com.revature.templates.TransferTemplate;

public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 8133760009821727775L;
	private static final UserController userController = new UserController();
	private static final AccountController accountController = new AccountController();
	private static final ObjectMapper om = new ObjectMapper();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		final String URI = req.getRequestURI().replace("/rocp-project", "").replaceFirst("/", "");
		String[] portions = URI.split("/");
		HttpSession session = req.getSession(false);
		res.setContentType("application/json");

		try {
			switch (portions[0]) {
			case "logout":
				AuthService.guard(session, "Standard", "Premium", "Employee", "Admin");
				userController.logout(session);
				res.setStatus(200);
				MessageTemplate message = new MessageTemplate("You have been successfully logged out");
				res.getWriter().println(om.writeValueAsString(message));
				break;
			case "users":
				if (portions.length > 1) {
					int id = Integer.parseInt(portions[1]);
					AuthService.guard(session, id, "Employee", "Admin");
					User user = userController.findUserById(id);
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(user));
				} else {
					AuthService.guard(session, "Employee", "Admin");
					List<User> users = userController.listAllUsers();
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(users));
				}
				break;
			case "accounts":
				if (portions.length == 3) {
					int id = Integer.parseInt(portions[2]);
					if (portions[1].equals("status")) {
						AuthService.guard(session, "Employee", "Admin");
						List<AccountDisplay> accts = accountController.findAccountsByStatus(id);
						res.setStatus(200);
						res.getWriter().println(om.writeValueAsString(accts));
					} else if (portions[1].equals("type")) {
						AuthService.guard(session, "Employee", "Admin");
						List<AccountDisplay> accts = accountController.findAccountsByType(id);
						res.setStatus(200);
						res.getWriter().println(om.writeValueAsString(accts));
					} else if (portions[1].equals("owner")) {
						AuthService.guard(session, id, "Employee", "Admin");
						List<AccountDisplay> accts = accountController.findAccountsByUser(session, id);
						res.setStatus(200);
						res.getWriter().println(om.writeValueAsString(accts));
					} else {
						res.setStatus(400);
						message = new MessageTemplate("Invalid URL");
						res.getWriter().println(om.writeValueAsString(message));
					}
				} else if (portions.length == 2) {
					int id = Integer.parseInt(portions[1]);
					List<AccountDisplay> account = accountController.findAccountByID(session, id);
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(account));
				} else {
					AuthService.guard(session, "Employee", "Admin");
					List<AccountDisplay> accounts = accountController.findAllAccounts();
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(accounts));
				}
				break;
			default:
				res.setStatus(404);
				message = new MessageTemplate("Invalid URL");
				res.getWriter().println(om.writeValueAsString(message));
			}
		} catch (AuthorizationException e) {
			res.setStatus(401);
			MessageTemplate message = new MessageTemplate("The incoming token has expired");
			res.getWriter().println(om.writeValueAsString(message));
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		final String URI = req.getRequestURI().replace("/rocp-project", "").replaceFirst("/", "");
		String[] portions = URI.split("/");
		res.setContentType("application/json");

		try {
			switch (portions[0]) {
			case "login":
				LoginTemplate lt = om.readValue(req.getReader(), LoginTemplate.class);
				User user = userController.login(lt);
				if (user == null) {
					res.setStatus(400);
					MessageTemplate message = new MessageTemplate("Invalid Credentials");
					res.getWriter().println(om.writeValueAsString(message));
				} else {
					res.setStatus(200);
					req.getSession().setAttribute("currentUser", user);
					res.getWriter().println(om.writeValueAsString(user));
				}
				break;
			case "users":
				User newUser = om.readValue(req.getReader(), User.class);
				User createdUser = userController.create(newUser);
				if (createdUser == null) {
					res.setStatus(400);
					MessageTemplate message = new MessageTemplate("Error creating user");
					res.getWriter().println(om.writeValueAsString(message));
				}
				res.setStatus(201);
				MessageTemplate message = new MessageTemplate("New user created");
				res.getWriter().println(om.writeValueAsString(message));
				break;
			case "accounts":
				if (portions.length == 1) {
					AccountTemplate newAcct = om.readValue(req.getReader(), AccountTemplate.class);
					AuthService.guard(req.getSession(false), newAcct.getUser_id(), "Admin");
					UserAccount createdAcct = accountController.create(newAcct);
					if (createdAcct == null) {
						res.setStatus(400);
						message = new MessageTemplate("Error creating account");
						res.getWriter().println(om.writeValueAsString(message));
					}
					res.setStatus(201);
					res.getWriter().println(om.writeValueAsString(createdAcct));
				} else {
					switch (portions[1]) {
					case "withdraw":
						TransactionTemplate ttW = om.readValue(req.getReader(), TransactionTemplate.class);
						AuthService.acctGuard(accountController.findById(ttW.getAccountId()));
						if (accountController.withdraw(req.getSession(false), ttW)) {
							res.setStatus(200);
							message = new MessageTemplate(
									"$" + ttW.getAmount() + " has been withdrawn from Account #" + ttW.getAccountId());
							res.getWriter().println(om.writeValueAsString(message));
						} else {
							res.setStatus(400);
							message = new MessageTemplate("Insufficient Funds");
							res.getWriter().println(om.writeValueAsString(message));
						}
						break;
					case "deposit":
						TransactionTemplate ttD = om.readValue(req.getReader(), TransactionTemplate.class);
						AuthService.acctGuard(accountController.findById(ttD.getAccountId()));
						if (accountController.deposit(req.getSession(false), ttD)) {
							res.setStatus(200);
							message = new MessageTemplate(
									"$" + ttD.getAmount() + " has been deposited to Account #" + ttD.getAccountId());
							res.getWriter().println(om.writeValueAsString(message));
						} else {
							res.setStatus(400);
							message = new MessageTemplate("Insufficient Funds");
							res.getWriter().println(om.writeValueAsString(message));
						}
						break;
					case "transfer":
						TransferTemplate ttt = om.readValue(req.getReader(), TransferTemplate.class);
						AuthService.acctGuard(accountController.findById(ttt.getSourceAccountId()));
						AuthService.acctGuard(accountController.findById(ttt.getTargetAccountId()));
						if (accountController.transfer(req.getSession(false), ttt)) {
							res.setStatus(202);
							message = new MessageTemplate("$" + ttt.getAmount() + " has been transferred from Account #"
									+ ttt.getSourceAccountId() + " to Account #" + ttt.getTargetAccountId());
							res.getWriter().println(om.writeValueAsString(message));
						} else {
							res.setStatus(400);
							message = new MessageTemplate("Insufficient Funds");
							res.getWriter().println(om.writeValueAsString(message));
						}
						break;
					case "addJoint":
						HttpSession session = req.getSession(false);
						AuthService.guard(session, "Premium");
						JointTemplate jt = om.readValue(req.getReader(), JointTemplate.class);
						AuthService.acctGuard(accountController.findById(jt.getAccountId()));
						List<AccountDisplay> accounts = accountController.addJoint(session, jt);
						if (accounts == null) {
							res.setStatus(400);
							message = new MessageTemplate("Error adding joint account");
							res.getWriter().println(om.writeValueAsString(message));
						}
						res.setStatus(201);
						res.getWriter().println(om.writeValueAsString(accounts));
						break;
					case "passTime":
						AuthService.guard(req.getSession(false), "Admin");
						TimeTemplate tt = om.readValue(req.getReader(), TimeTemplate.class);
						accountController.accrueInterest(tt);
						res.setStatus(202);
						message = new MessageTemplate(
								tt.getNumOfMonths() + " months of interest has been accrued for all Savings Accounts");
						res.getWriter().println(om.writeValueAsString(message));
						break;
					default:
						res.setStatus(404);
						message = new MessageTemplate("Invalid URL");
						res.getWriter().println(om.writeValueAsString(message));
					}
				}
				break;
			default:
				res.setStatus(404);
				message = new MessageTemplate("Invalid URL");
				res.getWriter().println(om.writeValueAsString(message));
			}
		} catch (AuthorizationException e) {
			res.setStatus(401);
			MessageTemplate message = new MessageTemplate("The incoming token has expired");
			res.getWriter().println(om.writeValueAsString(message));
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		final String URI = req.getRequestURI().replace("/rocp-project", "").replaceFirst("/", "");
		String[] portions = URI.split("/");
		HttpSession session = req.getSession();
		res.setContentType("application/json");

		try {
			switch (portions[0]) {
			case "users":
				User updateUser = om.readValue(req.getReader(), User.class);
				AuthService.guard(session, updateUser.getId(), "Admin");

				User returnUser = userController.update(updateUser.getId(), updateUser);
				if (returnUser == null) {
					res.setStatus(400);
					MessageTemplate message = new MessageTemplate("Error updating user");
					res.getWriter().println(om.writeValueAsString(message));
				}
				res.setStatus(202);
				res.getWriter().println(om.writeValueAsString(updateUser));
				break;
			case "accounts":
				AuthService.guard(session, "Admin");
				Account updateAcct = om.readValue(req.getReader(), Account.class);
				Account returnAcct = accountController.update(updateAcct.getId(), updateAcct);
				if (returnAcct == null) {
					res.setStatus(400);
					MessageTemplate message = new MessageTemplate("Error updating account");
					res.getWriter().println(om.writeValueAsString(message));
				}
				res.setStatus(202);
				res.getWriter().println(om.writeValueAsString(returnAcct));
				break;
			default:
				res.setStatus(404);
				MessageTemplate message = new MessageTemplate("Invalid URL");
				res.getWriter().println(om.writeValueAsString(message));
			}
		} catch (AuthorizationException e) {
			res.setStatus(401);
			MessageTemplate message = new MessageTemplate("The incoming token has expired");
			res.getWriter().println(om.writeValueAsString(message));
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		final String URI = req.getRequestURI().replace("/rocp-project", "").replaceFirst("/", "");
		String[] portions = URI.split("/");
		int id = Integer.parseInt(portions[1]);
		HttpSession session = req.getSession();
		res.setContentType("application/json");

		try {
			switch (portions[0]) {
			case "users":
				AuthService.guard(session, id, "Admin");
				userController.delete(id);
				res.setStatus(202);
				MessageTemplate message = new MessageTemplate("User deleted");
				res.getWriter().println(om.writeValueAsString(message));
				break;
			case "accounts":
				accountController.delete(session, id);
				res.setStatus(202);
				message = new MessageTemplate("Account deleted");
				res.getWriter().println(om.writeValueAsString(message));
				break;
			default:
				res.setStatus(404);
				message = new MessageTemplate("Invalid URL");
				res.getWriter().println(om.writeValueAsString(message));
			}
		} catch (AuthorizationException e) {
			res.setStatus(401);
			MessageTemplate message = new MessageTemplate("The incoming token has expired");
			res.getWriter().println(om.writeValueAsString(message));
		}
	}

}
