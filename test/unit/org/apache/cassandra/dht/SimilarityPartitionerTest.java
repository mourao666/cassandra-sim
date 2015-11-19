package org.apache.cassandra.dht;

public class SimilarityPartitionerTest extends PartitionerTestCase
{
    public void initPartitioner()
    {
        partitioner = SimilarityPartitioner.instance;
    }
}