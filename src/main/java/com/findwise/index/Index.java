package com.findwise.index;

import com.findwise.IndexEntry;

import java.util.Map;

public interface Index
{
    void indexDocument(String id, String content);

    Map<String, Map<String, IndexEntry>> getIndex();
}
