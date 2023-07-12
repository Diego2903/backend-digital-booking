package com.digital.api.digital_booking.models;

import java.util.List;

@lombok.Data

public class PageResponse<T> {
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    private List<T> content;

    public PageResponse(long totalElements, int totalPages, int currentPage, int pageSize, List<T> content) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.content = content;
    }
}
