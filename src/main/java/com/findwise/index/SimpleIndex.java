package com.findwise.index;

import com.findwise.IndexEntry;

import java.util.*;

public class SimpleIndex implements Index
{
    private Map<String, Map<String, IndexEntry>> invertedIndex = new TreeMap<>();
    private Map<String, String> docs = new HashMap<>();

    @Override
    public void indexDocument(String id, String content)
    {
        final String[] words = content.split("\\W+");
        for (final String word : words)
        {
            //Lowercase and without punctuation marks. Not sure if intended.
            final String actualWord = word.replaceAll("[.!?\\\\-]", "");

            Map<String, IndexEntry> documents = invertedIndex.computeIfAbsent(actualWord, k -> new TreeMap<>());
            documents.put(id, new SimpleIndexEntry(id, calculateTermFrequency(content, word)));
        }

        docs.put(id, content);

        //Calculate inverse term frequency in all documents
        for (final Map.Entry<String, Map<String, IndexEntry>> indexEntryMap : invertedIndex.entrySet())
        {
            final String word = indexEntryMap.getKey();
            final double inverseTermFrequency = calculateInverseTermFrequencyInAllDocuments(docs.values(), word);
            for (final IndexEntry entry : indexEntryMap.getValue().values())
            {
                entry.setScore(entry.getScore() * inverseTermFrequency);
            }
        }
    }

    @Override
    public Map<String, Map<String, IndexEntry>> getIndex()
    {
        return invertedIndex;
    }

    private double calculateTermFrequency(final String content, final String term)
    {
        double weight = 0;
        final String[] words = content.split("\\s+");
        for (final String word : words)
        {
            //Remove all punctuation marks, transform to lowercase and then compare words.
            if (word.replaceAll("[.!?\\\\-]", "").equals(term))
                weight++;
        }
        return weight / content.length();
    }

    private double calculateInverseTermFrequencyInAllDocuments(final Collection<String> docs, final String term)
    {
        double count = 0;
        for (final String doc : docs)
        {
            final String[] words = doc.split("\\s+");
            for (final String word : words)
            {
                //Remove all punctuation marks and then compare.
                if (word.replaceAll("[.!?\\\\-]", "").equals(term))
                {
                    count++;
                    break;
                }
            }
        }
        return Math.log(1 + (docs.size() / count));
    }
}
