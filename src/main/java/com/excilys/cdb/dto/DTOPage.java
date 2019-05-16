package com.excilys.cdb.dto;

import java.util.ArrayList;

public class DTOPage {
	private Integer pageNumber;
	private Integer numberTotalPage;
	private Integer NumberOfElementsToPrint;
	private Integer numberTotalOfComputer;
	private ArrayList<DTOComputer> dtoComputerList;
	
	public static class DTOPageBuilder {
		private Integer pageNumber;
		private Integer numberTotalPage;
		private Integer numberOfElementsToPrint;
		private Integer numberTotalOfComputer;
		private ArrayList<DTOComputer> dtoComputerList;
		
		public DTOPageBuilder() {
			this.pageNumber = null;
			this.numberTotalPage = null;
			this.numberOfElementsToPrint = null;
			this.numberTotalOfComputer = null;
			this.dtoComputerList = null;
		}
		
		public DTOPageBuilder withPageNumber(Integer pageNumber) {
			this.pageNumber = pageNumber;
			return this;
		}
		
		public DTOPageBuilder withNumberTotalPage(Integer numberTotalPage) {
			this.numberTotalPage = numberTotalPage;
			return this;
		}
		
		public DTOPageBuilder withNumberOfElementsToPrint(Integer numberOfElementsToPrint) {
			this.numberOfElementsToPrint = numberOfElementsToPrint;
			return this;
		}
		
		public DTOPageBuilder withNumberTotalOfComputer(Integer numberTotalOfComputer) {
			this.numberTotalOfComputer = numberTotalOfComputer;
			return this;
		}
		
		public DTOPageBuilder withDtoComputerList(ArrayList<DTOComputer> dtoComputerList) {
			this.dtoComputerList = dtoComputerList;
			return this;
		}
		
		public DTOPage build() {
			return new DTOPage(this);
		}
	}
	
	private DTOPage(DTOPageBuilder build) {
		this.pageNumber = build.pageNumber;
		this.numberTotalPage = build.numberTotalPage;
		this.NumberOfElementsToPrint = build.numberOfElementsToPrint;
		this.numberTotalOfComputer = build.numberTotalOfComputer;
		this.dtoComputerList = build.dtoComputerList;
	}

	@Override
	public String toString() {
		return "DTOPage [pageNumber=" + pageNumber + ", numberTotalPage=" + numberTotalPage
				+ ", NumberOfElemetsToPrint=" + NumberOfElementsToPrint + ", numberTotalOfComputer="
				+ numberTotalOfComputer + ", modelComputerList=" + dtoComputerList.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((NumberOfElementsToPrint == null) ? 0 : NumberOfElementsToPrint.hashCode());
		result = prime * result + ((dtoComputerList == null) ? 0 : dtoComputerList.hashCode());
		result = prime * result + ((numberTotalOfComputer == null) ? 0 : numberTotalOfComputer.hashCode());
		result = prime * result + ((numberTotalPage == null) ? 0 : numberTotalPage.hashCode());
		result = prime * result + ((pageNumber == null) ? 0 : pageNumber.hashCode());
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
		DTOPage other = (DTOPage) obj;
		if (NumberOfElementsToPrint == null) {
			if (other.NumberOfElementsToPrint != null)
				return false;
		} else if (!NumberOfElementsToPrint.equals(other.NumberOfElementsToPrint))
			return false;
		if (dtoComputerList == null) {
			if (other.dtoComputerList != null)
				return false;
		} else if (!dtoComputerList.equals(other.dtoComputerList))
			return false;
		if (numberTotalOfComputer == null) {
			if (other.numberTotalOfComputer != null)
				return false;
		} else if (!numberTotalOfComputer.equals(other.numberTotalOfComputer))
			return false;
		if (numberTotalPage == null) {
			if (other.numberTotalPage != null)
				return false;
		} else if (!numberTotalPage.equals(other.numberTotalPage))
			return false;
		if (pageNumber == null) {
			if (other.pageNumber != null)
				return false;
		} else if (!pageNumber.equals(other.pageNumber))
			return false;
		return true;
	}
	
	// Getters

	public Integer getPageNumber() {
		return pageNumber;
	}

	public Integer getNumberTotalPage() {
		return numberTotalPage;
	}

	public Integer getNumberOfElementsToPrint() {
		return NumberOfElementsToPrint;
	}

	public Integer getNumberTotalOfComputer() {
		return numberTotalOfComputer;
	}

	public ArrayList<DTOComputer> getDtoComputerList() {
		return dtoComputerList;
	}
	
	// Setters

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setNumberTotalPage(Integer numberTotalPage) {
		this.numberTotalPage = numberTotalPage;
	}

	public void setNumberOfElementsToPrint(Integer numberOfElementsToPrint) {
		NumberOfElementsToPrint = numberOfElementsToPrint;
	}

	public void setNumberTotalOfComputer(Integer numberTotalOfComputer) {
		this.numberTotalOfComputer = numberTotalOfComputer;
	}

	public void setDtoComputerList(ArrayList<DTOComputer> dtoComputerList) {
		this.dtoComputerList = dtoComputerList;
	}

}
