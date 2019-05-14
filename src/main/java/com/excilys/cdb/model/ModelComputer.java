package com.excilys.cdb.model;

import java.time.LocalDate;

public class ModelComputer {
	private Integer id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private ModelCompany modelCompany;
	
	public static class ModelComputerBuilder {
		private Integer id;
		private String name;
		private LocalDate introduced;
		private LocalDate discontinued;
		private ModelCompany modelCompany;
		
		public ModelComputerBuilder(String name) {
			this.name = name;
		}
		
		public ModelComputerBuilder withId(Integer id) {
			this.id = id;
			return this;
		}
		
		public ModelComputerBuilder withIntroduced(LocalDate introduced) {
			this.introduced = introduced;
			return this;
		}
		
		public ModelComputerBuilder withDiscontinued(LocalDate discontinued) {
			this.discontinued = discontinued;
			return this;
		}
		
		public ModelComputerBuilder withModelCompany(ModelCompany modelCompany) {
			this.modelCompany = modelCompany;
			return this;
		}
		
		public ModelComputer build() {
			return new ModelComputer(this);
		}
	}
	
	private ModelComputer(ModelComputerBuilder build) {
		this.id = build.id;
		this.name = build.name;
		this.introduced = build.introduced;
		this.discontinued = build.discontinued;
		this.modelCompany = build.modelCompany;
	}
	
	@Override
	public String toString() {
		return "ModelComputer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
				+ discontinued + ", modelCompany=" + modelCompany.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((modelCompany == null) ? 0 : modelCompany.hashCode());
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
		ModelComputer other = (ModelComputer) obj;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (modelCompany == null) {
			if (other.modelCompany != null)
				return false;
		} else if (!modelCompany.equals(other.modelCompany))
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

	public LocalDate getIntroduced() {
		return introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public ModelCompany getModelCompany() {
		return modelCompany;
	}

	// Setters
	
	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public void setModelCompany(ModelCompany modelCompany) {
		this.modelCompany = modelCompany;
	}
}
