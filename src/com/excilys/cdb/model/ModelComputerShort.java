package com.excilys.cdb.model;

public class ModelComputerShort {
	private int id;
	private String name;
	
	public ModelComputerShort(int id, String name) {
		this.setId(id);
		this.setName(name);
	}
	
	@Override
	public String toString() {
		return "ModelComputerShort [id=" + id + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModelComputerShort other = (ModelComputerShort) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	// Getters
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	// Setters
	
	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
