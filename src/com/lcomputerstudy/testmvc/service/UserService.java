package com.lcomputerstudy.testmvc.service;

import java.util.List;

import com.lcomputerstudy.testmvc.dao.*;
import com.lcomputerstudy.testmvc.vo.Board;
import com.lcomputerstudy.testmvc.vo.Pagination;
import com.lcomputerstudy.testmvc.vo.User;

public class UserService {	
	private static UserService service = null;
	private static UserDAO dao = null;
	
	private UserService() {
		
	}
	
	public static UserService getInstance() {
		if(service == null) {
			service = new UserService();
			dao = UserDAO.getInstance();
		}
		return service;
	}
	
	public List<User> getUsers(Pagination pagination) {
		return dao.getUsers(pagination);
	}
	
	public void insertUser(User user) {
		dao.insertUser(user);
	}
	public void levelInsert(User user) {
		dao.levelInsert(user);
	}
	public void levelRemove(User user) {
		dao.levelRemove(user);
	}
	public int getUsersCount() {
		return dao.getUsersCount();
	}
	
	public User loginUser(String idx, String pw) {
		return dao.loginUser(idx, pw);
	}
	
	public User getUsers(User user) {
		return dao.getUsers(user);
	}
	
	public void editUser(User user) {
		dao.editUser(user);
	}
	
	public void deleteUser(User user) {
		dao.deleteUser(user);
	}
}
