/**
 * 
 */
package com.epam.trn.service;

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
