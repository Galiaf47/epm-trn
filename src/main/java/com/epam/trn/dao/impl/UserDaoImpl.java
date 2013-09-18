package com.epam.trn.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.epam.trn.dao.UserDao;
import com.epam.trn.model.User;
import com.epam.trn.model.UserRole;
import com.epam.trn.web.grid.impl.SimpleGrid;

public class UserDaoImpl extends JdbcDaoSupport implements UserDao {
	public void insert(User user) {

		String sql = "INSERT INTO USERS "
				+ "(LOGIN, PASSWORD, FIRSTNAME, LASTNAME, ADDRESS, PHONE, ACTIVE) VALUES (?, ?, ?, ?, ?, ?, ?)";

		getJdbcTemplate().update(sql, user.getLogin(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getAddress(), user.getPhone(), user.getIsActive());
	}

	@SuppressWarnings("unchecked")
	@Override
	public User findByLogin(String login) {
		String sql = "SELECT ID, LOGIN, PASSWORD, FIRSTNAME, LASTNAME, ADDRESS, PHONE, ACTIVE FROM USERS  WHERE LOGIN = ?";
		User user = ((User) getJdbcTemplate().queryForObject(sql,
				new Object[] { login }, new UserRowMapper()));

		return user;
	}

	@Override
	public User findById(long id) {
		String sql = "SELECT ID, LOGIN, PASSWORD, FIRSTNAME, LASTNAME, ADDRESS, PHONE, ACTIVE FROM USERS WHERE ID = ?";
		return (User)getJdbcTemplate().queryForObject(sql, new Object[] {id}, new UserRowMapper());
	}

	@Override
	public List<User> getUsers() {
		String sql = "SELECT ID, LOGIN, PASSWORD, FIRSTNAME, LASTNAME, ADDRESS, PHONE, ACTIVE FROM USERS";
		return (List<User>) getJdbcTemplate().query(sql, new Object[] {}, new UserRowMapper());
	}

	@Override
	public SimpleGrid<User> getUsersPage(String filters, Integer page, Integer rows, String sortBy, String sortDirrection) {
		//TODO: 
		String sortStatement = "ORDER BY " + sortBy + ' ' + sortDirrection;
		String countSql = "SELECT COUNT(*) FROM USERS";
		String sql = "SELECT ID, LOGIN, PASSWORD, FIRSTNAME, LASTNAME, ADDRESS, PHONE, ACTIVE FROM USERS " + sortStatement + " LIMIT ? offset ?";
		
		int count = getJdbcTemplate().queryForInt(countSql);
		int offset = rows * (page - 1);
		int totalPages = (int)Math.ceil((double)count / rows); 
		
		SimpleGrid<User> result = new SimpleGrid<User>(getJdbcTemplate().query(sql, new Object[] {rows, offset}, new UserRowMapper()));
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
			user.setFirstName(rs.getString("FIRSTNAME"));
			user.setLastName(rs.getString("LASTNAME"));
			user.setAddress(rs.getString("ADDRESS"));
			user.setPhone(rs.getString("PHONE"));
			user.setIsActive(rs.getBoolean("ACTIVE"));
			return user;
		}
	}

	@Override
	public Boolean deleteUser(long id) {
		String sql = "DELETE FROM USERS WHERE ID = ?";
		int result = getJdbcTemplate().update(sql, new Object[]{id});
		return result > 0;
	}

	@Override
	public Boolean deleteUsers(ArrayList<Long> ids) {
		String sql = "DELETE FROM USERS WHERE ID IN (:ids)";
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getJdbcTemplate());
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);
		int result = namedParameterJdbcTemplate.update(sql, parameters);
		return result > 0;
	}
	
	@Override
	public Boolean updateUser(User user) {
		String sql = "UPDATE USERS SET (LOGIN, PASSWORD, FIRSTNAME, LASTNAME, ADDRESS, PHONE, ACTIVE) = (?, ?, ?, ?, ?, ?, ?) WHERE ID = ?";
		return getJdbcTemplate().update(sql, new Object[]{user.getLogin(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getAddress(), user.getPhone(), user.getIsActive(), user.getId()}) > 0;
	}
}
