package org.apache.cassandra.dht;

import org.junit.Before;
import org.junit.Test;

import org.apache.cassandra.db.marshal.BinaryType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SimilarityPartitionerInitialTest
{
    private IPartitioner partitioner;

    @Before
    public void initPartitioner()
    {
        partitioner = SimilarityPartitioner.instance;
    }

    @Test
    public void testDecorateKey() throws Exception
    {

    }

    @Test
    public void testMidpoint() throws Exception
    {

    }

    @Test
    public void testGetMinimumToken() throws Exception
    {

    }

    @Test
    public void testGetToken() throws Exception
    {

    }

    @Test
    public void testGetRandomToken() throws Exception
    {

    }

    @Test
    public void testGetTokenFactory() throws Exception
    {

    }

    @Test
    public void testPreservesOrder() throws Exception
    {
        assertTrue(partitioner.preservesOrder());
    }

    @Test
    public void testDescribeOwnership() throws Exception
    {

    }

    @Test
    public void testGetTokenValidator() throws Exception
    {
        assertEquals(BinaryType.instance, partitioner.getTokenValidator());
    }
}