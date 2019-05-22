package com.excilys.cdb.model;

public class ModelPage {
	private Integer pageNumber;
	private Integer numberTotalPage;
	private Integer numberOfElementsToPrint;
	private Integer numberTotalOfComputer;
	private String wordSearched;
	private String orderBy;
	private boolean isAsc;
	
	public static class ModelPageBuilder {
		private Integer pageNumber;
		private Integer numberTotalPage;
		private Integer numberOfElementsToPrint;
		private Integer numberTotalOfComputer;
		private String wordSearched;
		private String orderBy;
		private boolean isAsc;
		
		public ModelPageBuilder() {
			this.pageNumber = null;
			this.numberTotalPage = null;
			this.numberOfElementsToPrint = null;
			this.numberTotalOfComputer = null;
			this.wordSearched = null;
			this.orderBy = null;
			this.isAsc = true;
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
		
		public ModelPageBuilder withWordSearched(String wordSearched) {
			this.wordSearched = wordSearched;
			return this;
		}
		
		public ModelPageBuilder withOrderBy(String orderBy) {
			this.orderBy = orderBy;
			return this;
		}
		
		public ModelPageBuilder withisAsc(boolean isAsc) {
			this.isAsc = isAsc;
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
		this.wordSearched = build.wordSearched;
		this.orderBy = build.orderBy;
		this.isAsc = build.isAsc;
	}

	@Override
	public String toString() {
		return "ModelPage [pageNumber=" + pageNumber + ", numberTotalPage=" + numberTotalPage
				+ ", numberOfElementsToPrint=" + numberOfElementsToPrint + ", numberTotalOfComputer="
				+ numberTotalOfComputer + ", wordSearched=" + wordSearched + ", orderBy=" + orderBy + ", isAsc=" + isAsc
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isAsc ? 1231 : 1237);
		result = prime * result + ((numberOfElementsToPrint == null) ? 0 : numberOfElementsToPrint.hashCode());
		result = prime * result + ((numberTotalOfComputer == null) ? 0 : numberTotalOfComputer.hashCode());
		result = prime * result + ((numberTotalPage == null) ? 0 : numberTotalPage.hashCode());
		result = prime * result + ((orderBy == null) ? 0 : orderBy.hashCode());
		result = prime * result + ((pageNumber == null) ? 0 : pageNumber.hashCode());
		result = prime * result + ((wordSearched == null) ? 0 : wordSearched.hashCode());
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
		if (isAsc != other.isAsc)
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
		if (orderBy == null) {
			if (other.orderBy != null)
				return false;
		} else if (!orderBy.equals(other.orderBy))
			return false;
		if (pageNumber == null) {
			if (other.pageNumber != null)
				return false;
		} else if (!pageNumber.equals(other.pageNumber))
			return false;
		if (wordSearched == null) {
			if (other.wordSearched != null)
				return false;
		} else if (!wordSearched.equals(other.wordSearched))
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

	public String getWordSearched() {
		return wordSearched;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public boolean isAsc() {
		return isAsc;
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

	public void setWordSearched(String wordSearched) {
		this.wordSearched = wordSearched;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public void setisAsc(boolean isAsc) {
		this.isAsc = isAsc;
	}
}
