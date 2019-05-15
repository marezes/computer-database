package com.excilys.cdb.dto;

import java.util.ArrayList;

public class DTOPage {
	private Integer pageNumber;
	private Integer numberTotalPage;
	private Integer NumberOfElemetsToPrint;
	private Integer numberTotalOfComputer;
	private ArrayList<DTOComputer> modelComputerList;
	
	public static class DTOPageBuilder {
		private Integer pageNumber;
		private Integer numberTotalPage;
		private Integer numberOfElemetsToPrint;
		private Integer numberTotalOfComputer;
		private ArrayList<DTOComputer> modelComputerList;
		
		public DTOPageBuilder() {
		}
		
		public DTOPageBuilder withPageNumber(Integer pageNumber) {
			this.pageNumber = pageNumber;
			return this;
		}
		
		public DTOPageBuilder withNumberTotalPage(Integer numberTotalPage) {
			this.numberTotalPage = numberTotalPage;
			return this;
		}
		
		public DTOPageBuilder withNumberOfElemetsToPrint(Integer numberOfElemetsToPrint) {
			this.numberOfElemetsToPrint = numberOfElemetsToPrint;
			return this;
		}
		
		public DTOPageBuilder withNumberTotalOfComputer(Integer numberTotalOfComputer) {
			this.numberTotalOfComputer = numberTotalOfComputer;
			return this;
		}
		
		public DTOPageBuilder withModelComputerList(ArrayList<DTOComputer> modelComputerList) {
			this.modelComputerList = modelComputerList;
			return this;
		}
		
		public DTOPage build() {
			return new DTOPage(this);
		}
	}
	
	private DTOPage(DTOPageBuilder build) {
		this.pageNumber = build.pageNumber;
		this.numberTotalPage = build.numberTotalPage;
		this.NumberOfElemetsToPrint = build.numberOfElemetsToPrint;
		this.numberTotalOfComputer = build.numberTotalOfComputer;
		this.modelComputerList = build.modelComputerList;
	}

	@Override
	public String toString() {
		return "DTOPage [pageNumber=" + pageNumber + ", numberTotalPage=" + numberTotalPage
				+ ", NumberOfElemetsToPrint=" + NumberOfElemetsToPrint + ", numberTotalOfComputer="
				+ numberTotalOfComputer + ", modelComputerList=" + modelComputerList.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((NumberOfElemetsToPrint == null) ? 0 : NumberOfElemetsToPrint.hashCode());
		result = prime * result + ((modelComputerList == null) ? 0 : modelComputerList.hashCode());
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
		if (NumberOfElemetsToPrint == null) {
			if (other.NumberOfElemetsToPrint != null)
				return false;
		} else if (!NumberOfElemetsToPrint.equals(other.NumberOfElemetsToPrint))
			return false;
		if (modelComputerList == null) {
			if (other.modelComputerList != null)
				return false;
		} else if (!modelComputerList.equals(other.modelComputerList))
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

	public Integer getNumberOfElemetsToPrint() {
		return NumberOfElemetsToPrint;
	}

	public Integer getNumberTotalOfComputer() {
		return numberTotalOfComputer;
	}

	public ArrayList<DTOComputer> getModelComputerList() {
		return modelComputerList;
	}
	
	// Setters

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setNumberTotalPage(Integer numberTotalPage) {
		this.numberTotalPage = numberTotalPage;
	}

	public void setNumberOfElemetsToPrint(Integer numberOfElemetsToPrint) {
		NumberOfElemetsToPrint = numberOfElemetsToPrint;
	}

	public void setNumberTotalOfComputer(Integer numberTotalOfComputer) {
		this.numberTotalOfComputer = numberTotalOfComputer;
	}

	public void setModelComputerList(ArrayList<DTOComputer> modelComputerList) {
		this.modelComputerList = modelComputerList;
	}

}
