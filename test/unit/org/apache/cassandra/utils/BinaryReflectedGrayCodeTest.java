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

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BinaryReflectedGrayCodeTest
{
    private BinaryReflectedGrayCode binaryReflectedGrayCode;

    private static final BitSet MIN   = BitSet.valueOf(new long[]{-4611686018427387904L});
    private static final BitSet ZERO  = BitSet.valueOf(new long[]{0L});
    private static final BitSet MAX   = BitSet.valueOf(new long[]{4611686018427387904L});

    @Before
    public void init()
    {
        binaryReflectedGrayCode = new BinaryReflectedGrayCode();
    }

    @Test
    public void testCompareTo() throws Exception
    {
        assertEquals(-1, binaryReflectedGrayCode.compare(MIN, ZERO));
        assertEquals(0, binaryReflectedGrayCode.compare(ZERO, ZERO));
        assertEquals(1, binaryReflectedGrayCode.compare(MAX, ZERO));
    }
}