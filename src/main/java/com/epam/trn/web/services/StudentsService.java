/**
 * 
 */
package com.epam.trn.web.services;

import org.springframework.stereotype.Service;

/**
 * @author galiaf
 *
 */
@Service(value = "StudentService")
public class StudentsService {

	public String getSmtn() {
		return "Smtn";
	}
}
