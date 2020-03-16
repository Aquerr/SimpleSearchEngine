package com.findwise.index;

import com.findwise.IndexEntry;

/**
 * Simple implementation of IndexEntry.
 */
public class SimpleIndexEntry implements IndexEntry
{
    private String id;
    private double score;

    public SimpleIndexEntry(final String id, final double score)
    {
        this.id = id;
        this.score = score;
    }

    @Override
    public String getId()
    {
        return this.id;
    }

    @Override
    public void setId(String id)
    {
        this.id = id;
    }

    @Override
    public double getScore()
    {
        return this.score;
    }

    @Override
    public void setScore(double score)
    {
        this.score = score;
    }
}
