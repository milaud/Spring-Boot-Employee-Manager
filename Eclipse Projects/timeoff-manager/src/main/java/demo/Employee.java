package demo;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

@Entity
@Table
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private long id;

	@Column
	private String firstName;
	
	@Column
	private String lastName;

	final double MAX_TIME = 112; // two-weeks vacation
	
	@Column
	private double timeRemaining;

	private ArrayList<Double> allTimes = new ArrayList<Double>();

	public Employee(long id, String firstName, String lastName, double timeRemaining) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.timeRemaining = MAX_TIME;
		this.allTimes = new ArrayList<Double>();
	}

	public Employee() {
		this.allTimes = new ArrayList<Double>();
	}

	public long getId() {
		return id;
	}

	public void setFirstName(String name) {
		this.firstName = name;
	}

	public void setLastName(String name) {
		this.lastName = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public String getFullName() {
		return firstName + " " + lastName;
	}

	public double getRemainingHours() {
		return timeRemaining;
	}
	
	public void setTimeBound() {
		this.timeRemaining = MAX_TIME;
	}
	
	public void setTime(double hours) {
		this.timeRemaining = hours;
	}

	public boolean requestTimeOff(long id, double hours) {
		if (hours > 0 && timeRemaining >= hours) {
			double time = this.timeRemaining - hours;
			setTime(time);
			this.allTimes.add(hours);
			return true;
		}
		else return false;
		
	}
	
	public Collection<Double> getEmployeeRequests(long id) {
		return allTimes;
	}

}
