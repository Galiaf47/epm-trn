package com.epam.trn.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.epam.trn.dao.UserDao;
import com.epam.trn.model.User;
import com.epam.trn.model.UserRole;
import com.epam.trn.model.UsersPage;

public class UserDaoImpl extends JdbcDaoSupport implements UserDao {
	public void insert(User user) {

		String sql = "INSERT INTO USERS "
				+ "(LOGIN, PASSWORD) VALUES (?, ?, ?)";

		getJdbcTemplate().update(sql, user.getLogin(), user.getPassword());

	}

	@SuppressWarnings("unchecked")
	@Override
	public User findByLogin(String login) {
		String sql = "SELECT ID, LOGIN, PASSWORD FROM USERS  WHERE LOGIN = ?";
		User user = ((User) getJdbcTemplate().queryForObject(sql,
				new Object[] { login }, new UserRowMapper()));

		return user;
	}

	@Override
	public User findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUsers() {
		String sql = "SELECT ID, LOGIN, PASSWORD FROM USERS";
		return (List<User>) getJdbcTemplate().query(sql, new Object[] {}, new UserRowMapper());
	}

	@Override
	public UsersPage getUsersPage(String filters, Integer page, Integer rows, String sortBy, String sortDirrection) {
		String sortStatement = "ORDER BY " + sortBy + ' ' + sortDirrection;
		String countSql = "SELECT COUNT(*) FROM USERS";
		String sql = "SELECT ID, LOGIN, PASSWORD FROM USERS " + sortStatement + " LIMIT ? offset ?";
		
		UsersPage result = new UsersPage();
		int count = getJdbcTemplate().queryForInt(countSql);
		int offset = rows * (page - 1);
		int totalPages = (int)Math.ceil((double)count / rows); 
		
		result.setRows(getJdbcTemplate().query(sql, new Object[] {rows, offset}, new UserRowMapper()));
		result.setTotal(totalPages);
		result.setPage(page);
		result.setRecords(count);
		
		return result;
	}

	@Override
	public int getTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserRole> getUserRoles(Integer userId) {
		String sql = "SELECT ID, USER_ID, NAME FROM USER_ROLES  WHERE USER_ID = ?";
		List<UserRole> userRoles = (List<UserRole>) getJdbcTemplate().query(
				sql, new Object[] { userId }, new UserRoleRowMapper());
		return userRoles;
	}

	@SuppressWarnings("rawtypes")
	class UserRoleRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserRole userRole = new UserRole();
			userRole.setId(rs.getInt("ID"));
			userRole.setName(rs.getString("NAME"));
			userRole.setUserId(rs.getInt("USER_ID"));
			return userRole;
		}

	}

	@SuppressWarnings("rawtypes")
	class UserRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getInt("ID"));
			user.setLogin(rs.getString("LOGIN"));
			user.setPassword(rs.getString("PASSWORD"));
			return user;
		}

	}
}
