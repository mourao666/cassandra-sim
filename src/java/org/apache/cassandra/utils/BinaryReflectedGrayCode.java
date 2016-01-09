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
import java.util.Comparator;

public class BinaryReflectedGrayCode implements Comparator<BitSet>
{
    public int compare(BitSet gray1, BitSet gray2)
    {
        long l1 = convert(BinaryReflectedGrayCodeUtil.grayToBinary(gray1));
        long l2 = convert(BinaryReflectedGrayCodeUtil.grayToBinary(gray2));

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

    private static long convert(BitSet bits)
    {
        long value = 0L;
        for (int i = 0; i < bits.length(); ++i)
        {
            value += bits.get(i) ? (1L << i) : 0L;
        }
        return value;
    }

    /*private static BitSet convert(long value)
    {
        BitSet bits = new BitSet();
        int index = 0;
        while (value != 0L)
        {
            if (value % 2L != 0)
            {
                bits.set(index);
            }
            ++index;
            value = value >>> 1;
        }
        return bits;
    }*/
}
