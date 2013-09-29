package com.epam.trn.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.epam.trn.dao.UserDao;
import com.epam.trn.dao.utils.IdCallback;
import com.epam.trn.dao.utils.UserResultExtractor;
import com.epam.trn.dao.utils.UserRowMapper;
import com.epam.trn.model.User;
import com.epam.trn.model.UserRole;
import com.epam.trn.web.grid.impl.SimpleGrid;

public class UserDaoImpl extends JdbcDaoSupport implements UserDao {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private final String USER_UPDATE_FIELDS = "email, login, firstname, lastname, address, phone, active";
	private final String USER_UPDATE_VALUES = ":email, :login, :firstname, :lastname, :address, :phone, :active";
	private final String USER_INSERT_FIELDS = USER_UPDATE_FIELDS + ", password"; 
	private final String USER_INSERT_VALUES = USER_UPDATE_VALUES + ", :password";
	private final String USER_SELECT_FIELDS = "id, " + USER_INSERT_FIELDS;
	private final String USER_WITH_ROLES_QUERY = "SELECT u.id, u.email, u.login, u.password, u.firstname, u.lastname, u.address, u.phone, u.active, roles.name AS role_name FROM users AS u LEFT OUTER JOIN (user_roles JOIN roles ON user_roles.role_id = roles.Id) ON user_roles.user_id = u.id ";
	
	//TODO: move classes somewhere else
	//TODO: delete
	class UserRoleRowMapper implements RowMapper<UserRole> {
		public UserRole mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserRole userRole = new UserRole();
			userRole.setId(rs.getInt("ID"));
			userRole.setName(rs.getString("NAME"));
			return userRole;
		}
	}

	@Override
	public void insert(User user) {
		String sql = "INSERT INTO USERS (" + USER_INSERT_FIELDS + ") VALUES (" + USER_INSERT_VALUES + ") RETURNING ID";
//TODO: util		
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
		String sql = USER_WITH_ROLES_QUERY + " WHERE login = :login";

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("login", login);
		
		List<User> result = template.query(sql, parameters, new UserResultExtractor());
		return result.isEmpty() ? null : result.get(0);
	}
	
	@Override
	public User findById(long id) {
		String sql = USER_WITH_ROLES_QUERY + " WHERE id = :id";

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", id);

		List<User> result = template.query(sql, parameters, new UserResultExtractor());
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public List<User> getUsers() {
		//TODO: deletes
		String sql = "SELECT " + USER_SELECT_FIELDS + " FROM USERS";
		return (List<User>) getJdbcTemplate().query(sql, new Object[] {}, new UserRowMapper());
	}

	@Override
	public SimpleGrid<User> getUsersPage(String filters, Integer page, Integer rows, String sortBy, String sortDirrection) {
		//TODO: prepared sortStatement
		String sortStatement = "ORDER BY " + sortBy + ' ' + sortDirrection;
		String countSql = "SELECT COUNT(*) FROM USERS";
		String sql = USER_WITH_ROLES_QUERY + sortStatement + " LIMIT :limit OFFSET :offset";
		
		int count = getJdbcTemplate().queryForInt(countSql);
		int offset = rows * (page - 1);
		int totalPages = (int)Math.ceil((double)count / rows); 

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("limit", rows);
		parameters.addValue("offset", offset);
		SimpleGrid<User> result = new SimpleGrid<User>(template.query(sql, parameters, new UserResultExtractor()));
	
		result.setTotal(totalPages);
		result.setPage(page);
		result.setRecords(count);
		
		return result;
	}

	@Override
	public int getTotal() {
		// TODO delete
		return 0;
	}

	@Override
	//TODO: delete
	public List<UserRole> getUserRoles(Long userId) {
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
		//TODO: cascade
		String sqlRoles = "DELETE FROM USER_ROLES WHERE USER_ID IN (:ids)";
		String sqlUsers = "DELETE FROM USERS WHERE ID IN (:ids)";
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);
		
		template.update(sqlRoles, parameters);
		return template.update(sqlUsers, parameters) > 0;
	}
	
	@Override
	public Boolean updateUser(User user) {
		String sql = "UPDATE USERS SET (" + USER_UPDATE_FIELDS + ") = (" + USER_UPDATE_VALUES + ") WHERE ID = :id";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", user.getId());
		parameters.addValue("login", user.getLogin());
		parameters.addValue("firstname", user.getFirstName());
		parameters.addValue("lastname", user.getLastName());
		parameters.addValue("address", user.getAddress());
		parameters.addValue("phone", user.getPhone());
		parameters.addValue("active", user.getIsActive());
		parameters.addValue("email", user.getEmail());

		return template.update(sql, parameters) > 0;
	}

	@Override
	//TODO: change to roles table
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
