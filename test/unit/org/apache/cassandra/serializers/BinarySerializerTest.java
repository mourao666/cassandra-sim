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

package org.apache.cassandra.serializers;

import java.nio.ByteBuffer;
import java.util.BitSet;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class BinarySerializerTest
{
    private static BinarySerializer serializer = BinarySerializer.instance;

    private static BitSet value = BitSet.valueOf(new long[]{15L});
    private static ByteBuffer bytes = ByteBuffer.wrap(value.toByteArray());

    @Test
    public void testDeserialize() throws Exception
    {
        assertNull(serializer.deserialize(ByteBuffer.wrap(new byte[0])));
        assertEquals(value, serializer.deserialize(bytes));
    }

    @Test
    public void testSerialize() throws Exception
    {
        assertEquals(ByteBuffer.wrap(new byte[0]), serializer.serialize(null));
        assertEquals(bytes, serializer.serialize(value));
    }

    @Test
    public void testToString() throws Exception
    {
        assertTrue(serializer.toString(null).isEmpty());
        assertEquals("1111", serializer.toString(value));
    }

    @Test
    public void testGetType() throws Exception
    {
        assertEquals(BitSet.class, serializer.getType());
    }
}