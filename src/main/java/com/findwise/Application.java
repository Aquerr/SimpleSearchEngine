package com.findwise;

import com.findwise.index.SimpleIndex;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Application
{
    private final SearchEngine searchEngine;


    public Application()
    {
        this.searchEngine = new SimpleSearchEngine(new SimpleIndex());
    }

    public void loadData()
    {
        this.searchEngine.indexDocument("Document 1", "the brown fox jumped over the brown dog");
        this.searchEngine.indexDocument("Document 2", "the lazy brown dog sat in the corner");
        this.searchEngine.indexDocument("Document 3", "the red fox bit the lazy dog");
    }

    public List<IndexEntry> search(final String term)
    {
        return this.searchEngine.search(term);
    }

    public static void main(String[] args)
    {
        if (args.length == 0)
            throw new IllegalArgumentException("You need to specify the search term.");

        if (args.length > 1)
            throw new IllegalArgumentException("Too many arguments! You can only use one search term!");

        final String searchTerm = args[0];

        final Application application = new Application();
        application.loadData();
        final List<IndexEntry> indexEntries = application.search(searchTerm);

        //Because we cannot(?) add ToString method to IndexEntry interface, we need to format the output by ourselves here.
        System.out.println(indexEntries.stream().map(IndexEntry::getId).collect(Collectors.joining(", ")));
        //Just to see what is the score...
        System.out.println(indexEntries.stream().map(x->String.valueOf(x.getScore())).collect(Collectors.joining(", ")));
    }
}
