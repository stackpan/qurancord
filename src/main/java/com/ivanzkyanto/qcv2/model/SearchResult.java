package com.ivanzkyanto.qcv2.model;

import lombok.Data;

import java.util.List;

@Data
public class SearchResult {

    private Integer count;

    private List<SearchMatch> matches;

}
