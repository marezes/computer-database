package com.excilys.cdb.dto;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

public class DTOComputer {
	@Nullable
	@Min(1)
	private Integer id; // un id
	@NotNull
	@Size(min=1)
	private String name; // Nom de l'ordinateur
	@DateTimeFormat(pattern= "yyyy-mm-dd")
	private LocalDate introduced; // date introduced
	@DateTimeFormat(pattern= "yyyy-mm-dd")
	private LocalDate discontinued; // date discontinued
	@Nullable
	@Min(1)
	private Integer companyId; // l'id d'une entreprise
	@Nullable
	@Size(min=1)
	private String companyName; // le nom de l'entreprise
	
	public static class DTOComputerBuilder {
		private Integer id;
		private String name;
		private LocalDate introduced;
		private LocalDate discontinued;
		private Integer companyId;
		private String companyName;
		
		public DTOComputerBuilder(String name) {
			this.name = name;
			this.companyId = null;
			this.introduced = null;
			this.discontinued = null;
			this.companyName = null;
		}
		
		public DTOComputerBuilder withId(Integer id) {
			this.id = id;
			return this;
		}
		
		public DTOComputerBuilder withIntroduced(LocalDate introduced) {
			this.introduced = introduced;
			return this;
		}
		
		public DTOComputerBuilder withDiscontinued(LocalDate discontinued) {
			this.discontinued = discontinued;
			return this;
		}
		
		public DTOComputerBuilder withCompanyId(Integer companyId) {
			this.companyId = companyId;
			return this;
		}
		
		public DTOComputerBuilder withCompanyName(String companyName) {
			this.companyName = companyName;
			return this;
		}
		
		public DTOComputer build() {
			return new DTOComputer(this);
		}
	}
	
	private DTOComputer(DTOComputerBuilder build) {
		this.id = build.id;
		this.name = build.name;
		this.introduced = build.introduced;
		this.discontinued = build.discontinued;
		this.companyId = build.companyId;
		this.companyName = build.companyName;
	}

	@Override
	public String toString() {
		return "id = " + id + ", name = " + name 
				+ ", introduced = " + (introduced == null ? "vide" : introduced) 
				+ ", discontinued = " + (discontinued == null ? "vide" : discontinued)
				+ ", Company [ id = " + (companyId == null ? "vide" : companyId) 
				+ ", name = " + (companyName == null ? "vide" : companyName) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
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
		DTOComputer other = (DTOComputer) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName))
			return false;
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

	public Integer getCompanyId() {
		return companyId;
	}

	public String getCompanyName() {
		return companyName;
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

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
