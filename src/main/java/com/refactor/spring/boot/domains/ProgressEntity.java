package com.refactor.spring.boot.domains;

import lombok.Data;

@Data
public class ProgressEntity {
    private Long pBytesRead;
    private Long pContentLength;
    private Integer pItems;
}
