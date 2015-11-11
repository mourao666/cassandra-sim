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

package org.apache.cassandra.utils;

import java.util.BitSet;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BinaryReflectedGrayCodeUtilTest
{
    private static final BitSet MIN   = BitSet.valueOf(new long[]{Long.MIN_VALUE});
    private static final BitSet B_MIN   = BitSet.valueOf(new long[]{-1L});
    private static final BitSet G_MIN   = BitSet.valueOf(new long[]{-4611686018427387904L});
    private static final BitSet ZERO  = BitSet.valueOf(new long[]{0L});
    private static final BitSet ONE   = BitSet.valueOf(new long[]{1L});
    private static final BitSet TWO   = BitSet.valueOf(new long[]{2L});
    private static final BitSet TREE  = BitSet.valueOf(new long[]{3L});
    private static final BitSet FOUR  = BitSet.valueOf(new long[]{4L});
    private static final BitSet FIVE  = BitSet.valueOf(new long[]{5L});
    private static final BitSet SIX   = BitSet.valueOf(new long[]{6L});
    private static final BitSet SEVEN = BitSet.valueOf(new long[]{7L});
    private static final BitSet MAX   = BitSet.valueOf(new long[]{Long.MAX_VALUE});
    private static final BitSet B_MAX   = BitSet.valueOf(new long[]{6148914691236517205L});
    private static final BitSet G_MAX   = BitSet.valueOf(new long[]{4611686018427387904L});

    @Test
    public void testBinaryToGray() throws Exception
    {
        assertEquals(G_MIN, BinaryReflectedGrayCodeUtil.binaryToGray(MIN));
        assertEquals(ZERO, BinaryReflectedGrayCodeUtil.binaryToGray(ZERO));
        assertEquals(ONE, BinaryReflectedGrayCodeUtil.binaryToGray(ONE));
        assertEquals(TREE, BinaryReflectedGrayCodeUtil.binaryToGray(TWO));
        assertEquals(TWO, BinaryReflectedGrayCodeUtil.binaryToGray(TREE));
        assertEquals(SIX, BinaryReflectedGrayCodeUtil.binaryToGray(FOUR));
        assertEquals(SEVEN, BinaryReflectedGrayCodeUtil.binaryToGray(FIVE));
        assertEquals(FIVE, BinaryReflectedGrayCodeUtil.binaryToGray(SIX));
        assertEquals(FOUR, BinaryReflectedGrayCodeUtil.binaryToGray(SEVEN));
        assertEquals(G_MAX, BinaryReflectedGrayCodeUtil.binaryToGray(MAX));
    }

    @Test
    public void testGrayToBinary() throws Exception
    {
        assertEquals(B_MIN, BinaryReflectedGrayCodeUtil.grayToBinary(MIN));
        assertEquals(ZERO, BinaryReflectedGrayCodeUtil.grayToBinary(ZERO));
        assertEquals(ONE, BinaryReflectedGrayCodeUtil.grayToBinary(ONE));
        assertEquals(TREE, BinaryReflectedGrayCodeUtil.grayToBinary(TWO));
        assertEquals(TWO, BinaryReflectedGrayCodeUtil.grayToBinary(TREE));
        assertEquals(SEVEN, BinaryReflectedGrayCodeUtil.grayToBinary(FOUR));
        assertEquals(SIX, BinaryReflectedGrayCodeUtil.grayToBinary(FIVE));
        assertEquals(FOUR, BinaryReflectedGrayCodeUtil.grayToBinary(SIX));
        assertEquals(FIVE, BinaryReflectedGrayCodeUtil.grayToBinary(SEVEN));
        assertEquals(B_MAX, BinaryReflectedGrayCodeUtil.grayToBinary(MAX));
    }
}