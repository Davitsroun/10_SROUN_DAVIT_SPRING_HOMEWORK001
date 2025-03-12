package org.example._10_sroun_davit_spring_homework001.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pagination {
    private int totalElements;
    private int currentPage;
    private int pageSize;
    private int totalPages;
}
