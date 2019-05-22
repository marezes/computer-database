package com.excilys.cdb.dto;

public class DTOPage {
	private Integer pageNumber;
	private Integer numberTotalPage;
	private Integer NumberOfElementsToPrint;
	private Integer numberTotalOfComputer;
	private String wordSearched;
	private String orderBy;
	private boolean isAsc;
	
	public static class DTOPageBuilder {
		private Integer pageNumber;
		private Integer numberTotalPage;
		private Integer numberOfElementsToPrint;
		private Integer numberTotalOfComputer;
		private String wordSearched;
		private String orderBy;
		private boolean isAsc;
		
		public DTOPageBuilder() {
			this.pageNumber = null;
			this.numberTotalPage = null;
			this.numberOfElementsToPrint = null;
			this.numberTotalOfComputer = null;
			this.wordSearched = null;
			this.orderBy = null;
			this.isAsc = true;
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
		
		public DTOPageBuilder withWordSearched(String wordSearched) {
			this.wordSearched = wordSearched;
			return this;
		}
		
		public DTOPageBuilder withOrderBy(String orderBy) {
			this.orderBy = orderBy;
			return this;
		}
		
		public DTOPageBuilder withisAsc(boolean isAsc) {
			this.isAsc = isAsc;
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
		this.wordSearched = build.wordSearched;
		this.orderBy = build.orderBy;
		this.isAsc = build.isAsc;
	}

	@Override
	public String toString() {
		return "DTOPage [pageNumber=" + pageNumber + ", numberTotalPage=" + numberTotalPage
				+ ", NumberOfElementsToPrint=" + NumberOfElementsToPrint + ", numberTotalOfComputer="
				+ numberTotalOfComputer + ", wordSearched=" + wordSearched + ", orderBy=" + orderBy + ", isAsc=" + isAsc
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((NumberOfElementsToPrint == null) ? 0 : NumberOfElementsToPrint.hashCode());
		result = prime * result + (isAsc ? 1231 : 1237);
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
		DTOPage other = (DTOPage) obj;
		if (NumberOfElementsToPrint == null) {
			if (other.NumberOfElementsToPrint != null)
				return false;
		} else if (!NumberOfElementsToPrint.equals(other.NumberOfElementsToPrint))
			return false;
		if (isAsc != other.isAsc)
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
		return NumberOfElementsToPrint;
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
		NumberOfElementsToPrint = numberOfElementsToPrint;
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
