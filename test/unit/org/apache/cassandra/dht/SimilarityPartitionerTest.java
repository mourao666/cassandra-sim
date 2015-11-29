package org.apache.cassandra.dht;

public class SimilarityPartitionerTest extends PartitionerTestCase
{
    public void initPartitioner()
    {
        partitioner = SimilarityPartitioner.instance;
    }

    public void testMidpoint()
    {
//        super.testMidpoint();
    }

    public void testMidpointMinimum()
    {
//        super.testMidpointMinimum();
    }

    public void testMidpointWrapping()
    {
//        super.testMidpointWrapping();
    }
}