package demo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

	String createEmployee(String firstName, String lastName);

	Iterable<Employee> getAllEmployees();

	double getHoursById(long id);
	
	void requestTimeOff(Employee em, double timeRequested);

	Iterable<Double> getEmployeeRequests(long id);



}
