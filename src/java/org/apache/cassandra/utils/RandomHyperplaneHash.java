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
import java.nio.DoubleBuffer;
import java.util.BitSet;
import java.util.Random;

import org.apache.cassandra.config.DatabaseDescriptor;

public class RandomHyperplaneHash
{
    public BitSet rhh(ByteBuffer key)
    {
        return rhh(key, DatabaseDescriptor.getIdentifierLength(), DatabaseDescriptor.getVectors());
    }

    private static BitSet rhh(ByteBuffer key, int bits, double[][] vectors)
    {
        int dimension = key.capacity() / 8;

        double[] vectorKey = getVectorKey(key, dimension);

        BitSet hash = new BitSet(bits);

        double sum;

        int i = 0;
        int j = 0;

        while (i < vectors.length)
        {
            sum = scalarProduct(vectorKey, vectors[i]);
            if (sum >= 0.0)
            {
                hash.set(j, true);
            }
            else
            {
                hash.set(j, false);
            }
            i++;
            j++;
        }

        return hash;
    }

    public BitSet rhh()
    {
        return rhh(DatabaseDescriptor.getIdentifierLength(), DatabaseDescriptor.getVectors());
    }

    private static BitSet rhh(int bits, double[][] vectors)
    {
        Random seed = new Random();
        // TODO get size of byte array dynamically, size 6
        byte[] bytes = new byte[6 * 8];
        seed.nextBytes(bytes);
        ByteBuffer key = ByteBuffer.wrap(bytes);

        return rhh(key, bits, vectors);
    }

    private static double[] getVectorKey(ByteBuffer key, int dimension)
    {
        DoubleBuffer buffer = ((ByteBuffer) key.rewind()).asDoubleBuffer();
        double[] vector = new double[dimension];

        int i = 0;
        while (buffer.hasRemaining())
        {
            vector[i] = buffer.get();
            i++;
        }

        return vector;
    }

    private static double scalarProduct(double[] v1, double[] v2)
    {
        double scalarProduct = 0;

        for (int i = 0; i < v1.length; i++)
        {
            scalarProduct = scalarProduct + (v1[i] * v2[i]);
        }

        return scalarProduct;
    }

    /*private static double[][] getVectors(int bits, int dimension)
    {
        double[][] vectors = new double[bits][dimension];

        Random seed = new Random();

        for (int i = 0; i < bits; i++)
        {
            for (int j = 0; j < dimension; j++)
            {
                vectors[i][j] = seed.nextGaussian();
            }
        }

        return vectors;
    }*/
}
