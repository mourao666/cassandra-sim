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

package org.apache.cassandra.db.marshal;

import java.nio.ByteBuffer;
import java.util.BitSet;

import org.junit.Test;

import org.apache.cassandra.cql3.CQL3Type;
import org.apache.cassandra.serializers.BinarySerializer;

import static org.junit.Assert.assertEquals;

public class BinaryTypeTest
{
    private static BinaryType binaryType = BinaryType.instance;

    private static ByteBuffer buffer = ByteBuffer.wrap(BitSet.valueOf(new long[]{15L}).toByteArray());

    @Test
    public void testCompare() throws Exception
    {
        // ?
    }

    @Test
    public void testFromString() throws Exception
    {
        // ?
    }

    @Test
    public void testFromJSONObject() throws Exception
    {
        // ?
    }

    @Test
    public void testToJSONString() throws Exception
    {
        assertEquals("\"1111\"", binaryType.toJSONString(buffer, 0));
    }

    @Test
    public void testIsValueCompatibleWithInternal() throws Exception
    {
        // ?
    }

    @Test
    public void testAsCQL3Type() throws Exception
    {
        assertEquals(CQL3Type.Native.BINARY, binaryType.asCQL3Type());
    }

    @Test
    public void testGetSerializer() throws Exception
    {
        assertEquals(BinarySerializer.instance, binaryType.getSerializer());
    }
}