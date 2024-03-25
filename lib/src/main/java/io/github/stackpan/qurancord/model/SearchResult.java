package io.github.stackpan.qurancord.model;

import lombok.Data;

import java.util.List;

@Data
public class SearchResult {

    private Integer count;

    private List<SearchMatch> matches;

}
