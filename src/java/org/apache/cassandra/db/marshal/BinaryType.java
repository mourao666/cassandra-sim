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

import org.apache.cassandra.cql3.CQL3Type;
import org.apache.cassandra.cql3.Constants;
import org.apache.cassandra.cql3.Term;
import org.apache.cassandra.serializers.BinarySerializer;
import org.apache.cassandra.serializers.MarshalException;
import org.apache.cassandra.serializers.TypeSerializer;
import org.apache.cassandra.utils.ByteBufferUtil;

public class BinaryType extends AbstractType<BitSet>
{
    public static final BinaryType instance = new BinaryType();

    BinaryType() {} // singleton

    public int compare(ByteBuffer o1, ByteBuffer o2)
    {
        return ByteBufferUtil.compareUnsigned(o1, o2);
    }

    /**
     * get a byte representation of the given string.
     *
     * @param source
     */
    public ByteBuffer fromString(String source) throws MarshalException
    {
        // Return an empty ByteBuffer for an empty string.
        if (source.isEmpty())
            return ByteBufferUtil.EMPTY_BYTE_BUFFER;

        BitSet binaryType;

        try
        {
            binaryType = BitSet.valueOf(ByteBufferUtil.bytes(source));
        }
        catch (Exception e)
        {
            throw new MarshalException(String.format("Unable to make binary from '%s'", source), e);
        }

        return decompose(binaryType);
    }

    /**
     * Given a parsed JSON string, return a byte representation of the object.
     *
     * @param parsed the result of parsing a json string
     **/
    public Term fromJSONObject(Object parsed) throws MarshalException
    {
        try
        {
            return new Constants.Value(fromString((String) parsed));
        }
        catch (ClassCastException exc)
        {
            throw new MarshalException(String.format(
                    "Expected a binary string, but got a %s: %s", parsed.getClass().getSimpleName(), parsed));
        }
    }

    /**
     * Converts a value to a JSON string.
     *
     * @param buffer
     * @param protocolVersion
     */
    public String toJSONString(ByteBuffer buffer, int protocolVersion)
    {
        return super.toJSONString(buffer, protocolVersion);
    }

    /**
     * Needed to handle ReversedType in value-compatibility checks.  Subclasses should implement this instead of
     * isValueCompatibleWith().
     *
     * @param otherType
     */
    public boolean isValueCompatibleWithInternal(AbstractType<?> otherType)
    {
        return super.isValueCompatibleWithInternal(otherType);
    }

    public CQL3Type asCQL3Type()
    {
        return CQL3Type.Native.BINARY;
    }

    public TypeSerializer<BitSet> getSerializer()
    {
        return BinarySerializer.instance;
    }
}
