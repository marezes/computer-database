package com.excilys.cdb.model;

public class ModelComputerShort {
	private Integer id;
	private String name;
	
	public static class ModelComputerShortBuilder {
		private Integer id;
		private String name;
		
		public ModelComputerShortBuilder(String name) {
			this.name = name;
		}
		
		public ModelComputerShortBuilder withId(Integer id) {
			this.id = id;
			return this;
		}
		
		public ModelComputerShort build() {
			return new ModelComputerShort(this);
		}
	}
	
	private ModelComputerShort(ModelComputerShortBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
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
	
	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	// Setters
	
	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
