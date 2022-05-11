package com.good.platform.response;

import java.util.ArrayList;
import java.util.List;

public class PaginatedResponse<T> {

    private long totalElements;

    private long totalPages;

    private long pageSize;

    private long pageNumber;

    private long numberOfElements;

    private List<T> data;

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public long getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(long numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public List<T> getData() {
        if (data == null) {
            data = new ArrayList<>();
        }
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * @return the pageSize
     */
    public long getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize
     *            the pageSize to set
     */
    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the pageNumber
     */
    public long getPageNumber() {
        return pageNumber;
    }

    /**
     * @param pageNumber
     *            the pageNumber to set
     */
    public void setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public PaginatedResponse(long totalitems, long pages, long pSize, long pNumber, long numElements, List<T> pData) {
        this.totalElements = totalitems;
        this.totalPages = pages;
        this.pageSize = pSize;
        this.pageNumber = pNumber;
        this.numberOfElements = numElements;
        this.data = pData;
    }

    public PaginatedResponse() {
    }


}
