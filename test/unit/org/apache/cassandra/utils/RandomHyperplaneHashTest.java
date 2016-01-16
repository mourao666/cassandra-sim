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

import java.nio.ByteBuffer;
import java.util.BitSet;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RandomHyperplaneHashTest
{
    private static final double[] object = {10.0, 5.0, 6.0, 1.0, 0.0, 2.0};

    private static ByteBuffer key;
    private static BitSet hash;

    @BeforeClass
    public static void init()
    {
        key = ByteBuffer.allocate(8 * object.length);
        for (int i = 0; i < object.length; i++) {
            key.putDouble(object[i]);
        }

        hash = new BitSet(8);
        hash.set(7, true);
        hash.set(6, false);
        hash.set(5, true);
        hash.set(4, false);
        hash.set(3, true);
        hash.set(2, true);
        hash.set(1, true);
        hash.set(0, false);
    }

    @Test
    public void testRhhWithKey() throws Exception
    {
        BitSet rhh = new RandomHyperplaneHash().rhh(key);
        assertEquals(hash, rhh);
    }

    @Test
    public void testRhhWithoutKey() throws Exception
    {
        BitSet rhh = new RandomHyperplaneHash().rhh();
        assertNotNull(rhh);
    }
}