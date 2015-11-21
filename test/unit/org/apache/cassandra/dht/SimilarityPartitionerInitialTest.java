package org.apache.cassandra.dht;

import java.nio.ByteBuffer;
import java.util.BitSet;

import org.junit.Before;
import org.junit.Test;

import org.apache.cassandra.db.marshal.BinaryType;
import org.apache.cassandra.utils.ObjectSizes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SimilarityPartitionerInitialTest
{
    private IPartitioner partitioner;
    private ByteBuffer key;
    private BitSet keyHash;

    @Before
    public void init()
    {
        initPartitioner();
        initKey();
        initKeyHash();
    }

    private void initPartitioner()
    {
        partitioner = SimilarityPartitioner.instance;
    }

    private void initKey()
    {
        double[] object = {10.0, 5.0, 6.0, 1.0, 0.0, 2.0};
        key = ByteBuffer.allocate(8 * object.length);
        for (int i = 0; i < object.length; i++) {
            key.putDouble(object[i]);
        }
        key.rewind();
    }

    private void initKeyHash()
    {
        keyHash = new BitSet(8);
        keyHash.set(0, true);
        keyHash.set(1, false);
        keyHash.set(2, true);
        keyHash.set(3, false);
        keyHash.set(4, true);
        keyHash.set(5, true);
        keyHash.set(6, true);
        keyHash.set(7, false);
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
        assertTrue(partitioner.getMinimumToken().isMinimum());
    }

    @Test
    public void testGetToken() throws Exception
    {
        assertTrue(partitioner.getToken(ByteBuffer.wrap(new byte[]{})).isMinimum());
        testToken(partitioner.getToken(key));
    }

    private void testToken(Token token)
    {
        // compareTo
        assertEquals(-1, partitioner.getMinimumToken().compareTo(new SimilarityPartitioner.BinaryToken(keyHash)));
        assertEquals(0, token.compareTo(new SimilarityPartitioner.BinaryToken(keyHash)));
        assertEquals(1, token.compareTo(partitioner.getMinimumToken()));
        // getPartitioner
        assertEquals(partitioner, token.getPartitioner());
        // getHeapSize
        assertEquals(ObjectSizes.measure(new SimilarityPartitioner.BinaryToken(new BitSet())) +
                     ObjectSizes.sizeOfArray(keyHash.toByteArray()), token.getHeapSize());
        // getTokenValue
        assertEquals(keyHash, token.getTokenValue());
        // toString
//        assertEquals("10101110", token.toString());
    }

    @Test
    public void testGetRandomToken() throws Exception
    {
        assertNotNull(partitioner.getRandomToken());
    }

    @Test
    public void testGetTokenFactory() throws Exception
    {
        Token.TokenFactory factory = partitioner.getTokenFactory();
        testTokenFactory(factory);
    }

    private void testTokenFactory(Token.TokenFactory factory)
    {
        // toByteArray
        assertEquals(ByteBuffer.wrap(keyHash.toByteArray()), factory.toByteArray(partitioner.getToken(key)));
        // fromByteArray
        assertEquals(partitioner.getToken(key).getTokenValue(), factory.fromByteArray(ByteBuffer.wrap(keyHash.toByteArray())).getTokenValue());
        // toString
//        assertEquals("10101110", factory.toString(partitioner.getToken(key)));
        // validate
        // fromString
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