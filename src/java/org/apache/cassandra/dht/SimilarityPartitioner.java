/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.cassandra.dht;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.List;
import java.util.Map;

import org.apache.cassandra.db.BufferDecoratedKey;
import org.apache.cassandra.db.DecoratedKey;
import org.apache.cassandra.db.marshal.AbstractType;
import org.apache.cassandra.db.marshal.BinaryType;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.cassandra.utils.ByteBufferUtil;
import org.apache.cassandra.utils.FBUtilities;
import org.apache.cassandra.utils.ObjectSizes;
import org.apache.cassandra.utils.Pair;
import org.apache.cassandra.utils.RandomHyperplaneHash;

public class SimilarityPartitioner implements IPartitioner
{
    public static final BinaryToken MINIMUM = new BinaryToken(new BitSet());

    public static final BigInteger BYTE_MASK = new BigInteger("255");

    private static final long EMPTY_SIZE = ObjectSizes.measure(MINIMUM);

    public static final SimilarityPartitioner instance = new SimilarityPartitioner();

    /**
     * Transform key to object representation of the on-disk format.
     *
     * @param key the raw, client-facing key
     * @return decorated version of key
     */
    public DecoratedKey decorateKey(ByteBuffer key)
    {
        return new BufferDecoratedKey(getToken(key), key);
    }

    /**
     * Calculate a Token representing the approximate "middle" of the given
     * range.
     *
     * @param lToken
     * @param rToken
     * @return The approximate midpoint between left and right.
     */
    public Token midpoint(Token lToken, Token rToken)
    {
        BinaryToken lt = (BinaryToken) lToken;
        BinaryToken rt = (BinaryToken) rToken;

        int sigbytes = Math.max(lt.token.toByteArray().length, rt.token.toByteArray().length);
        BigInteger left = bigForBinary(lt.token, sigbytes);
        BigInteger right = bigForBinary(rt.token, sigbytes);

        Pair<BigInteger,Boolean> midpair = FBUtilities.midpoint(left, right, 8*sigbytes);
        return new BinaryToken(binaryForBig(midpair.left, sigbytes, midpair.right));
    }

    private BigInteger bigForBinary(BitSet binary, int sigbytes)
    {
        byte[] b, bytes = binary.toByteArray();
        if (sigbytes != bytes.length)
        {
            b = new byte[sigbytes];
            System.arraycopy(bytes, 0, b, 0, bytes.length);
        } else
            b = bytes;
        return new BigInteger(1, b);
    }

    private BitSet binaryForBig(BigInteger big, int sigbytes, Boolean remainder)
    {
        byte[] bytes = new byte[sigbytes + (remainder ? 1 : 0)];
        if (remainder)
        {
            // remaining bit is the most significant in the last byte
            bytes[sigbytes] |= 0x80;
        }
        // bitmask for a single byte
        for (int i = 0; i < sigbytes; i++)
        {
            int maskpos = 8 * (sigbytes - (i + 1));
            // apply bitmask and get byte value
            bytes[i] = (byte)(big.and(BYTE_MASK.shiftLeft(maskpos)).shiftRight(maskpos).intValue() & 0xFF);
        }
        return BitSet.valueOf(bytes);
    }

    /**
     * @return A Token smaller than all others in the range that is being partitioned.
     * Not legal to assign to a node or key.  (But legal to use in range scans.)
     */
    public Token getMinimumToken()
    {
        return MINIMUM;
    }

    /**
     * @param key
     * @return a Token that can be used to route a given key
     * (This is NOT a method to create a Token from its string representation;
     * for that, use TokenFactory.fromString.)
     */
    public Token getToken(ByteBuffer key)
    {
        if (key.remaining() == 0)
            return MINIMUM;
        return new BinaryToken(RandomHyperplaneHash.rhh(key));
    }

    /**
     * @return a randomly generated token
     */
    public Token getRandomToken()
    {
        return new BinaryToken(RandomHyperplaneHash.rhh());
    }

    private final Token.TokenFactory tokenFactory = new Token.TokenFactory()
    {
        public ByteBuffer toByteArray(Token token)
        {
            BinaryToken binaryToken = (BinaryToken) token;
            return ByteBuffer.wrap(binaryToken.token.toByteArray());
        }

        public Token fromByteArray(ByteBuffer bytes)
        {
            return new BinaryToken(BitSet.valueOf(bytes));
        }

        public String toString(Token token)
        {
            BinaryToken binaryToken = (BinaryToken) token;
            return binaryToken.toString();
        }

        public void validate(String token) throws ConfigurationException
        {
            try
            {
                BitSet.valueOf(ByteBufferUtil.bytes(token));
            }
            catch (Exception e)
            {
                throw new ConfigurationException(e.getMessage());
            }
        }

        public Token fromString(String string)
        {
            return new BinaryToken(BitSet.valueOf(ByteBufferUtil.bytes(string)));
        }
    };

    public Token.TokenFactory getTokenFactory()
    {
        return tokenFactory;
    }

    public static class BinaryToken extends Token
    {
        final BitSet token;

        public BinaryToken(BitSet token)
        {
            this.token = token;
        }

        public int compareTo(Token token)
        {
            BinaryToken o = (BinaryToken) token;
            return new BinaryReflectedGrayCode(this.token).compareTo(o.token);
        }

        @Override
        public IPartitioner getPartitioner()
        {
            return instance;
        }

        @Override
        public long getHeapSize()
        {
            return EMPTY_SIZE + ObjectSizes.sizeOfArray(token.toByteArray());
        }

        @Override
        public Object getTokenValue()
        {
            return token;
        }
    }

    /**
     * @return True if the implementing class preserves key order in the Tokens
     * it generates.
     */
    public boolean preservesOrder()
    {
        return true;
    }

    /**
     * Calculate the deltas between tokens in the ring in order to compare
     * relative sizes.
     *
     * @param sortedTokens a sorted List of Tokens
     * @return the mapping from 'token' to 'percentage of the ring owned by that token'.
     */
    public Map<Token, Float> describeOwnership(List<Token> sortedTokens)
    {
        return null;
    }

    public AbstractType<?> getTokenValidator()
    {
        return BinaryType.instance;
    }
}
