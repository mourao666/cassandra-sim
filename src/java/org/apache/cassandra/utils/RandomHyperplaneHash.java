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

public class RandomHyperplaneHash
{
    // TODO implement validation for bits number
    private static final int BITS = 8;

    // TODO get vectors dynamically
    private static final double[][] VECTORS = {
    { -0.1, -0.9, -0.6, 0.5, 0.5, -0.8 },
    { 0.8, 0.7, -0.6, -0.5, 0, -0.8 },
    { 0, 0.1, 0.6, -0.9, -0.7, -0.3 },
    { 0.4, -0.5, 0.1, -0.8, 0.2, 0 },
    { -0.7, -0.8, -0.7, 0, 0.2, -0.9 },
    { 0.1, 0.5, 0.4, -0.1, -0.7, 0.6 },
    { -0.5, 0.5, 0.1, -0.7, 0.4, 0.3 },
    { 0.4, 0.3, -0.2, 0, 0.1, -0.5 }
    };

    private RandomHyperplaneHash()
    {

    }

    public static BitSet rhh(ByteBuffer key)
    {
        int dimension = key.capacity() / 8;

        double[] vectorKey = getVectorKey(key, dimension);

        BitSet hash = new BitSet(BITS);

        double sum;

        int i = 0;
        int j = 0;

        while (i < VECTORS.length)
        {
            sum = scalarProduct(vectorKey, VECTORS[i]);
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

    public static BitSet rhh()
    {
        Random seed = new Random();
        // TODO get size of byte array dynamically
        byte[] bytes = new byte[6 * 8];
        seed.nextBytes(bytes);
        ByteBuffer key = ByteBuffer.wrap(bytes);

        return rhh(key);
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

    /*private static double[][] getVectors(int dimension)
    {
        double[][] vectors = new double[BITS][dimension];

        Random seed = new Random();

        for (int i = 0; i < BITS; i++)
        {
            for (int j = 0; j < dimension; j++)
            {
                vectors[i][j] = seed.nextGaussian();
            }
        }

        return vectors;
    }*/
}
