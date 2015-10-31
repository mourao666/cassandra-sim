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

public class BinarySerializer implements TypeSerializer<BitSet>
{
    public static final BinarySerializer instance = new BinarySerializer();

    public ByteBuffer serialize(BitSet value)
    {
        return null;
    }

    public BitSet deserialize(ByteBuffer bytes)
    {
        return null;
    }

    public void validate(ByteBuffer bytes) throws MarshalException
    {

    }

    public String toString(BitSet value)
    {
        return null;
    }

    public Class<BitSet> getType()
    {
        return null;
    }
}
