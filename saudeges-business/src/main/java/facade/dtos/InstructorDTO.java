package facade.dtos;

import java.io.Serializable;
import business.instructor.Instructor;

public class InstructorDTO implements Serializable{

	private int id;
	private String name;
	
	public InstructorDTO(int id, String name) {
		
		this.id = id;
		this.name = name;
	}
	
	public InstructorDTO(Instructor i) {
		this.id = i.getId();
		this.name = i.getName();
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
}
