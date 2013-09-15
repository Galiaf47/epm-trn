/**
 * 
 */
package com.epam.trn.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epam.trn.dao.UserDao;
import com.epam.trn.model.User;

/**
 * @author Siarhei Klimuts
 *
 */
@Controller
public class StudentsService {
	private static Integer asd = 0;
	@Autowired
	private UserDao userDao;

	public class UsersList implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -1588030058563583602L;
		private Integer records;
		private Integer total;
		private Integer page;
		private List<User> rows;

		public Integer getRecords() {
			return records;
		}

		public void setRecords(Integer records) {
			this.records = records;
		}

		public Integer getTotal() {
			return total;
		}

		public void setTotal(Integer total) {
			this.total = total;
		}

		public Integer getPage() {
			return page;
		}

		public void setPage(Integer page) {
			this.page = page;
		}

		public List<User> getRows() {
			return rows;
		}

		public void setRows(List<User> rows) {
			this.rows = rows;
		}
	}	
	
	@RequestMapping(method=RequestMethod.GET, value="/students", headers="Accept=application/json")
	public @ResponseBody UsersList getStudents() {
		List<User> students = userDao.getUsers();
		UsersList result = new UsersList();
		
		result.setRows(students);
		result.setTotal(50);
		result.setPage(2);
		result.setRecords(students.size());

		if(asd == 0){
			return result;
		} else {
			asd = 0;
			return new UsersList();
		}
	}
	
	@RequestMapping(method=RequestMethod.POST/*DELETE*/, value="/students")
	public void getStudent() {
		asd = 1;
	}
}
