package com.excilys.cdb.dto;

public class DTOComputerShort {
	private Integer id; // un id
	private String name; // Nom de l'entreprise
	
	public static class DTOComputerShortBuilder {
		private Integer id;
		private String name;
		
		public DTOComputerShortBuilder(String name) {
			this.name = name;
			this.id = null;
		}
		
		public DTOComputerShortBuilder withId(Integer id) {
			this.id = id;
			return this;
		}
		
		public DTOComputerShort build() {
			return new DTOComputerShort(this);
		}
	}
	
	private DTOComputerShort(DTOComputerShortBuilder build) {
		this.id = build.id;
		this.name = build.name;
	}
	
	@Override
	public String toString() {
		return "id = " + id + ", name = " + name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		DTOComputerShort other = (DTOComputerShort) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	// Getters
	
	public String getName() {
		return name;
	}

	public Integer getId() {
		return id;
	}

	// Setters
	
	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
