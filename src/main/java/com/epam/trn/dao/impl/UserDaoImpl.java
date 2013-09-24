package com.epam.trn.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.epam.trn.dao.UserDao;
import com.epam.trn.model.User;
import com.epam.trn.model.UserRole;
import com.epam.trn.web.grid.impl.SimpleGrid;

public class UserDaoImpl extends JdbcDaoSupport implements UserDao {
	
	@Autowired
	NamedParameterJdbcTemplate template;
	
	//TODO: move classes somewhere else
	class IdCallback implements PreparedStatementCallback<Integer> {
		@Override
		public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
			int result = 0;
			ResultSet rs = ps.executeQuery();
		    if(rs.next()) {
		    	result =  rs.getInt("ID");
		    }
		    rs.close();
		    return result;
		}
	}

	class UserRoleRowMapper implements RowMapper<UserRole> {
		public UserRole mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserRole userRole = new UserRole();
			userRole.setId(rs.getInt("ID"));
			userRole.setName(rs.getString("NAME"));
			userRole.setUserId(rs.getInt("USER_ID"));
			return userRole;
		}
	}

	class UserRowMapper implements RowMapper<User> {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getInt("ID"));
			user.setLogin(rs.getString("LOGIN"));
			user.setPassword(rs.getString("PASSWORD"));
			user.setFirstName(rs.getString("FIRSTNAME"));
			user.setLastName(rs.getString("LASTNAME"));
			user.setAddress(rs.getString("ADDRESS"));
			user.setPhone(rs.getString("PHONE"));
			user.setIsActive(rs.getBoolean("ACTIVE"));
			user.setEmail(rs.getString("EMAIL"));
			return user;
		}
	}

	@Override
	public void insert(User user) {
		String sql = "INSERT INTO USERS "
				+ "(LOGIN, PASSWORD, FIRSTNAME, LASTNAME, ADDRESS, PHONE, ACTIVE, EMAIL) VALUES "
				+ "(:login, :password, :firstname, :lastname, :address, :phone, :active, :email) "
				+ "RETURNING ID";
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("login", user.getLogin());
		parameters.addValue("password", user.getPassword());
		parameters.addValue("firstname", user.getFirstName());
		parameters.addValue("lastname", user.getLastName());
		parameters.addValue("address", user.getAddress());
		parameters.addValue("phone", user.getPhone());
		parameters.addValue("active", user.getIsActive());
		parameters.addValue("email", user.getEmail());

		user.setId(template.execute(sql, parameters, new IdCallback()));
	}

	@Override
	public User findByLogin(String login) {
		String sql = "SELECT ID, LOGIN, PASSWORD, FIRSTNAME, LASTNAME, ADDRESS, PHONE, ACTIVE, EMAIL FROM USERS  WHERE LOGIN = ?";
		User user = ((User) getJdbcTemplate().queryForObject(sql, new Object[] { login }, new UserRowMapper()));

		return user;
	}

	@Override
	public User findById(long id) {
		String sql = "SELECT ID, LOGIN, PASSWORD, FIRSTNAME, LASTNAME, ADDRESS, PHONE, ACTIVE, EMAIL FROM USERS WHERE ID = ?";
		return (User)getJdbcTemplate().queryForObject(sql, new Object[] {id}, new UserRowMapper());
	}

	@Override
	public List<User> getUsers() {
		String sql = "SELECT ID, LOGIN, PASSWORD, FIRSTNAME, LASTNAME, ADDRESS, PHONE, ACTIVE, EMAIL FROM USERS";
		return (List<User>) getJdbcTemplate().query(sql, new Object[] {}, new UserRowMapper());
	}

	@Override
	public SimpleGrid<User> getUsersPage(String filters, Integer page, Integer rows, String sortBy, String sortDirrection) {
		//TODO: 
		String sortStatement = "ORDER BY " + sortBy + ' ' + sortDirrection;
		String countSql = "SELECT COUNT(*) FROM USERS";
		String sql = "SELECT ID, LOGIN, PASSWORD, FIRSTNAME, LASTNAME, ADDRESS, PHONE, ACTIVE, EMAIL FROM USERS " + sortStatement + " LIMIT ? offset ?";
		
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

	@Override
	public List<UserRole> getUserRoles(Integer userId) {
		String sql = "SELECT ID, USER_ID, NAME FROM USER_ROLES WHERE USER_ID = ?";
		List<UserRole> userRoles = (List<UserRole>) getJdbcTemplate().query(
				sql, new Object[] { userId }, new UserRoleRowMapper());
		return userRoles;
	}

	@Override
	public Boolean deleteUser(long id) {
		String sql = "DELETE FROM USERS WHERE ID = ?";
		int result = getJdbcTemplate().update(sql, new Object[]{id});
		return result > 0;
	}

	@Override
	public Boolean deleteUsers(ArrayList<Long> ids) {
		String sqlRoles = "DELETE FROM USER_ROLES WHERE USER_ID IN (:ids)";
		String sqlUsers = "DELETE FROM USERS WHERE ID IN (:ids)";
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);
		
		template.update(sqlRoles, parameters);
		return template.update(sqlUsers, parameters) > 0;
	}
	
	@Override
	public Boolean updateUser(User user) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", user.getId());
		String fields = "";
		if(user.getEmail() != null) {
			fields += "EMAIL = :email, ";
			parameters.addValue("email", user.getEmail());
		}
		if(user.getLogin() != null) {
			fields += "LOGIN = :login, ";
			parameters.addValue("login", user.getLogin());
		}
		if(user.getFirstName() != null) {
			fields += "FIRSTNAME = :firstname, ";
			parameters.addValue("firstname", user.getFirstName());
		}
		if(user.getLastName() != null) {
			fields += "LASTNAME = :lastname, ";
			parameters.addValue("lastname", user.getLastName());
		}
		if(user.getAddress() != null) {
			fields += "ADDRESS = :address, ";
			parameters.addValue("address", user.getAddress());
		}
		if(user.getPhone() != null) {
			fields += "PHONE = :phone, ";
			parameters.addValue("phone", user.getPhone());
		}
		if(user.getIsActive() != null) {
			fields += "ACTIVE = :active, ";
			parameters.addValue("active", user.getIsActive());
		}
		
		fields = fields.replaceAll(", $", "");
		String sql = "UPDATE USERS SET " + fields + " WHERE ID = :id";
		return template.update(sql, parameters) > 0;
	}

	@Override
	public void insertUserRoles(User user) {
		String sql = "INSERT INTO USER_ROLES (USER_ID, NAME) VALUES (:user_id, :name)";
		
		List<UserRole> existingsRolesList = getUserRoles(user.getId());
		user.getRoles().removeAll(existingsRolesList);
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("user_id", user.getId());
		
		//TODO: batch insert or something like that
		for(UserRole role: user.getRoles()) {
			parameters.addValue("name", role.getName());
			template.update(sql, parameters);
		}
		//TODO: return roles to user instance if required
	}
}
