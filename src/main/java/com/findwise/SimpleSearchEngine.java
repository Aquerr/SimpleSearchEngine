package com.findwise;

import com.findwise.index.Index;

import java.util.*;

public class SimpleSearchEngine implements SearchEngine
{
    private final Index index;

    public SimpleSearchEngine(final Index index)
    {
        this.index = index;
    }

    @Override
    public void indexDocument(final String id, final String content)
    {
        this.index.indexDocument(id.toLowerCase(), content.toLowerCase());
    }

    @Override
    public List<IndexEntry> search(final String term)
    {
        final Map<String, IndexEntry> indexEntries = this.index.getIndex().get(term.toLowerCase());
        if (indexEntries == null)
            return Collections.emptyList();

        final List<IndexEntry> searchResult = new ArrayList<>(indexEntries.values());
        searchResult.sort(Comparator.comparingDouble(IndexEntry::getScore).reversed());
        return searchResult;
    }
}
