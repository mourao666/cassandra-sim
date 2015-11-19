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

import org.apache.cassandra.utils.ByteBufferUtil;

public class BinarySerializer implements TypeSerializer<BitSet>
{
    public static final BinarySerializer instance = new BinarySerializer();

    public BitSet deserialize(ByteBuffer bytes)
    {
        return bytes.hasRemaining() ? BitSet.valueOf(bytes) : null;
    }

    public ByteBuffer serialize(BitSet value)
    {
        return value == null ? ByteBufferUtil.EMPTY_BYTE_BUFFER : ByteBuffer.wrap(value.toByteArray());
    }

    public void validate(ByteBuffer bytes) throws MarshalException
    {
        // all binary are legal.
    }

    public String toString(BitSet value)
    {
        return value == null ? "" : bitsToBinaryString(value);
    }

    private String bitsToBinaryString(BitSet value)
    {
        StringBuilder builder = new StringBuilder(value.length());

        for (int i = 0; i< value.length(); i++)
        {
            if (value.get(i))
            {
                builder.append("1");
            }
            else {
                builder.append("0");
            }
        }

        return builder.toString();
    }

    public Class<BitSet> getType()
    {
        return BitSet.class;
    }
}
