package com.excilys.cdb.model;

public class ModelCompany {
	private Integer id;
	private String name;
	
	public static class ModelCompanyBuilder {
		private Integer id;
		private String name;
		
		public ModelCompanyBuilder() {
			this.id = null;
			this.name = null;
		}
		
		public ModelCompanyBuilder withId(Integer id) {
			this.id = id;
			return this;
		}
		
		public ModelCompanyBuilder withName(String name) {
			this.name = name;
			return this;
		}
		
		public ModelCompany build() {
			return new ModelCompany(this);
		}
	}
	
	private ModelCompany(ModelCompanyBuilder build) {
		this.id = build.id;
		this.name = build.name;
	}
	
	@Override
	public String toString() {
		return "ModelCompany [id=" + id + ", name=" + name + "]";
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
		ModelCompany other = (ModelCompany) obj;
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
