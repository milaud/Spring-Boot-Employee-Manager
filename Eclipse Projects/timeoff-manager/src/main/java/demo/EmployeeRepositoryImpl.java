package demo;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeRepositoryImpl {

	@Autowired
	private EmployeeRepository employee;
	
	public Employee findById(long id){
        return employee.findOne(id);
    }
	
	public boolean exists(long id) {
		return employee.exists(id);
	}

    public void createEmployee(String firstName, String lastName){
    	Employee em = new Employee();
		em.setFirstName(firstName);
		em.setLastName(lastName);
		em.setTimeBound();
		employee.save(em);
    }

    public void deleteEmployee(Employee em){
    	employee.delete(em);
    }
	
	public double getHoursById(long id) {
		Employee em = employee.findOne(id);
		return em.getRemainingHours();
	}

	public boolean requestTimeOff(Employee em, double timeRequested) {
		if (em.requestTimeOff(em.getId(), timeRequested)) {
			employee.save(em);
			return true;
		}
		else return false;
	}

	public Collection<Double> getEmployeeRequests(long id) {
		Employee em = employee.findOne(id);
		return em.getEmployeeRequests(id);
	}

	public Iterable<Employee> getAllEmployees() {
		return this.employee.findAll();
	}

	
	
}
