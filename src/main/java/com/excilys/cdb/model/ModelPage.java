package com.excilys.cdb.model;

import java.util.ArrayList;

public class ModelPage {
	private Integer pageNumber;
	private Integer numberTotalPage;
	private Integer numberOfElementsToPrint;
	private Integer numberTotalOfComputer;
	private ArrayList<ModelComputer> modelComputerList;
	
	public static class ModelPageBuilder {
		private Integer pageNumber;
		private Integer numberTotalPage;
		private Integer numberOfElementsToPrint;
		private Integer numberTotalOfComputer;
		private ArrayList<ModelComputer> modelComputerList;
		
		public ModelPageBuilder() {
			this.pageNumber = null;
			this.numberTotalPage = null;
			this.numberOfElementsToPrint = null;
			this.numberTotalOfComputer = null;
			this.modelComputerList = null;
		}
		
		public ModelPageBuilder withPageNumber(Integer pageNumber) {
			this.pageNumber = pageNumber;
			return this;
		}
		
		public ModelPageBuilder withNumberTotalPage(Integer numberTotalPage) {
			this.numberTotalPage = numberTotalPage;
			return this;
		}
		
		public ModelPageBuilder withNumberOfElementsToPrint(Integer numberOfElementsToPrint) {
			this.numberOfElementsToPrint = numberOfElementsToPrint;
			return this;
		}
		
		public ModelPageBuilder withNumberTotalOfComputer(Integer numberTotalOfComputer) {
			this.numberTotalOfComputer = numberTotalOfComputer;
			return this;
		}
		
		public ModelPageBuilder withModelComputerList(ArrayList<ModelComputer> modelComputerList) {
			this.modelComputerList = modelComputerList;
			return this;
		}
		
		public ModelPage build() {
			return new ModelPage(this);
		}
	}
	
	private ModelPage(ModelPageBuilder build) {
		this.pageNumber = build.pageNumber;
		this.numberTotalPage = build.numberTotalPage;
		this.numberOfElementsToPrint = build.numberOfElementsToPrint;
		this.numberTotalOfComputer = build.numberTotalOfComputer;
		this.modelComputerList = build.modelComputerList;
	}

	@Override
	public String toString() {
		return "ModelPage [pageNumber=" + pageNumber + ", numberTotalPage=" + numberTotalPage
				+ ", numberOfElemetsToPrint=" + numberOfElementsToPrint + ", numberTotalOfComputer="
				+ numberTotalOfComputer + ", modelComputerList=" + modelComputerList.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((modelComputerList == null) ? 0 : modelComputerList.hashCode());
		result = prime * result + ((numberOfElementsToPrint == null) ? 0 : numberOfElementsToPrint.hashCode());
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
		ModelPage other = (ModelPage) obj;
		if (modelComputerList == null) {
			if (other.modelComputerList != null)
				return false;
		} else if (!modelComputerList.equals(other.modelComputerList))
			return false;
		if (numberOfElementsToPrint == null) {
			if (other.numberOfElementsToPrint != null)
				return false;
		} else if (!numberOfElementsToPrint.equals(other.numberOfElementsToPrint))
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
		return numberOfElementsToPrint;
	}

	public Integer getNumberTotalOfComputer() {
		return numberTotalOfComputer;
	}

	public ArrayList<ModelComputer> getModelComputerList() {
		return modelComputerList;
	}
	
	// Setters

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setNumberTotalPage(Integer numberTotalPage) {
		this.numberTotalPage = numberTotalPage;
	}

	public void setNumberOfElementsToPrint(Integer numberOfElementsToPrint) {
		this.numberOfElementsToPrint = numberOfElementsToPrint;
	}

	public void setNumberTotalOfComputer(Integer numberTotalOfComputer) {
		this.numberTotalOfComputer = numberTotalOfComputer;
	}

	public void setModelComputerList(ArrayList<ModelComputer> modelComputerList) {
		this.modelComputerList = modelComputerList;
	}

}
