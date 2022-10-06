package facade.dtos;

import java.io.Serializable;
import java.util.Date;

public class BeginEndDTO implements Serializable{

	private Date begin;
	private Date end;
	
	public BeginEndDTO(Date begin, Date end) {
		this.begin = begin;
		this.end = end;
	}
	
	public Date getBegin() {
		return this.begin;
	}
	
	public Date getEnd() {
		return this.end;
	}
}