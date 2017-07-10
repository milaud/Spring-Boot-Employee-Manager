package demo;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping(path = "/timeoff")
public class EmployeeController {

	@Autowired
	private EmployeeRepositoryImpl employee;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String createEmployee(@RequestParam String firstName, @RequestParam String lastName) {
		try {
			employee.createEmployee(firstName, lastName);
		} catch (Exception e) {
			return "Unable to create employee";
		}
		return "Employee created: " + firstName + " " + lastName;
	}

	@RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
	public Employee findEmployee(@PathVariable long id) {
			Employee em = employee.findById(id);
			return em;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public String deleteEmployee(@PathVariable long id) {
		Employee em = employee.findById(id);

		try {
			employee.deleteEmployee(em);
		} catch (Exception e) {
			return "Unable to delete employee.";
		}
		return "Employee " + em.getFullName() + " successfully deleted.";
	}

	@RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
	public String getHoursById(@PathVariable long id) {
		if (!employee.exists(id))
			return "Employee does not exist.";
		else {
			Employee em = employee.findById(id);
			double time = em.getRemainingHours();
			return "Employee " + em.getFullName() + " has " + time + " hours remaining.";
		}
	}

	@RequestMapping(value = "/request/{id}/{timeRequested}", method = RequestMethod.POST)
	public String requestTimeOff(@PathVariable long id, @PathVariable double timeRequested) {

		Employee em = employee.findById(id);

		if (!employee.exists(id))
			return "Employee does not exist.";
		else if (employee.requestTimeOff(em, timeRequested)) {
			return "Employee request successful. Employee has " + em.getRemainingHours() + " hours remaining.";
		} else
			return "Unable to grant time off. Employee only has " + em.getRemainingHours() + " hours remaining.";

		/*
		 * try { employee.requestTimeOff(em, timeRequested); } catch (Exception
		 * e) { return "Unable to grant time off. Employee only has " +
		 * em.getRemainingHours(); }
		 * 
		 * return "Employee request successful. Employee has " +
		 * em.getRemainingHours() + " remaining.";
		 */

	}

	@RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
	// public Iterable<Double> getEmployeeRequests(@PathVariable long id) {
	public String getEmployeeRequests(@PathVariable long id) {
		if (!employee.exists(id))
			return "Employee does not exist.";
		else
			return this.employee.getEmployeeRequests(id).toString();
		// return this.employee.getEmployeeRequests(id);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Iterable<Employee> getAllEmployees() {
		return this.employee.getAllEmployees();
	}

}
