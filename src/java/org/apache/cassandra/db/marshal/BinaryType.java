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

import org.apache.cassandra.cql3.Term;
import org.apache.cassandra.serializers.MarshalException;
import org.apache.cassandra.serializers.TypeSerializer;

/**
 * Created by mourao666 on 25/10/15.
 */
public class BinaryType extends AbstractType<BitSet>
{
    public static final BinaryType instance = new BinaryType();

    BinaryType() {} // singleton

    /**
     * get a byte representation of the given string.
     *
     * @param source
     */
    public ByteBuffer fromString(String source) throws MarshalException
    {
        return null;
    }

    /**
     * Given a parsed JSON string, return a byte representation of the object.
     *
     * @param parsed the result of parsing a json string
     **/
    public Term fromJSONObject(Object parsed) throws MarshalException
    {
        return null;
    }

    public TypeSerializer<BitSet> getSerializer()
    {
        return null;
    }

    public int compare(ByteBuffer byteBuffer, ByteBuffer t1)
    {
        return 0;
    }
}
