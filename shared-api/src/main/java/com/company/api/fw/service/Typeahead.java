package com.company.api.fw.service;

import java.util.List;

import com.company.util.Dto;

public interface Typeahead<T> {
    
    int LIMIT = 10;
    
    String TAG_Q = "q";
    
    List<T> doTypeahead(Dto params);
}
