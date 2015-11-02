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

import java.nio.ByteBuffer;
import java.util.BitSet;

import com.google.common.primitives.Longs;

public class BinaryReflectedGrayCode implements Comparable<BitSet>
{
    private BitSet grayCode;

    public BinaryReflectedGrayCode(BitSet grayCode)
    {
        this.grayCode = grayCode;
    }

    public int compareTo(BitSet grayCode)
    {
        long l1 = ByteBuffer.wrap(grayToBinary(this.grayCode).toByteArray()).getLong();
        long l2 = ByteBuffer.wrap(grayToBinary(grayCode).toByteArray()).getLong();

        if (l1 < l2)
        {
            return -1;
        }
        else if (l1 == l2)
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }

    private static BitSet binaryToGray(BitSet binary)
    {
        long b = ByteBuffer.wrap(binary.toByteArray()).getLong();
        long grayCode = b ^ (b >> 1);
        return BitSet.valueOf(Longs.toByteArray(grayCode));
    }

    private static BitSet grayToBinary(BitSet grayCode)
    {
        long gray = ByteBuffer.wrap(grayCode.toByteArray()).getLong();
        long binary = gray;
        long i = gray >> 1;

        while (i != 0)
        {
            binary = binary ^ i;
            i = i >> 1;
        }

        return BitSet.valueOf(Longs.toByteArray(binary));
    }
}
