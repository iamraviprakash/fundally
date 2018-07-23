package in.iiits.fundally.procurement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name="InstituteAccountType")
public class InstituteAccountType {
	
	@Id
	@Column(name="Id")
	private String id;
	
	@Column(name="Name")
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "InstituteAccountType [id=" + id + ", name=" + name + "]";
	}

	

}
