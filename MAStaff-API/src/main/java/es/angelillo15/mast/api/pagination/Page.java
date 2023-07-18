package es.angelillo15.mast.api.pagination;

import lombok.Data;

@Data
public class Page <O> {
    private int currentPage;
    private int maxPages;
    private int pageSize;
    private  O objects;
}
