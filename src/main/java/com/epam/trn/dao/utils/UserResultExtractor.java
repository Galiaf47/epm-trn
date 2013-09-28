/**
 * 
 */
package com.epam.trn.dao.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.epam.trn.model.User;
import com.epam.trn.model.UserRole;

import static com.epam.trn.dao.utils.UserRowMapper.*;

/**
 * @author Siarhei Klimuts
 *
 */
public class UserResultExtractor implements ResultSetExtractor<List<User>>{

	@Override
	public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
		HashMap<Long, User> resultMap = new HashMap<Long, User>();

		while(rs.next()) {
			HashMap<String, Object> columns = new HashMap<String, Object>();
			ResultSetMetaData meta = rs.getMetaData();
			for(int i = 1; i < meta.getColumnCount() + 1; i++) {
				String label = meta.getColumnLabel(i);
				columns.put(label, rs.getObject(label));
			}
			
			User user = resultMap.get(columns.get(USER_ID));
			if(user == null) {
				user = new User();

				user.setId((Long)columns.get(USER_ID));
				user.setEmail((String)columns.get(USER_EMAIL));
				user.setLogin((String)columns.get(USER_LOGIN));
				user.setPassword((String)columns.get(USER_PASSWORD));
				user.setFirstName((String)columns.get(USER_FIRST_NAME));
				user.setLastName((String)columns.get(USER_LAST_NAME));
				user.setAddress((String)columns.get(USER_ADDRESS));
				user.setPhone((String)columns.get(USER_PHONE));
				user.setIsActive((Boolean)columns.get(USER_ACTIVE));
				
				resultMap.put(user.getId(), user);
			}
			
			String roleName = (String)columns.get(ROLE_NAME);
			if(roleName != null) {
				UserRole role = new UserRole();
				role.setName(roleName);
				user.addRole(role);
			}
		}
		
		return new ArrayList<User>(resultMap.values());
	}
}
