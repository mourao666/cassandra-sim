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
import java.util.BitSet;
import java.util.Random;

public class RandomHyperplaneHash
{
    // TODO implement validation for bits number
    private static final int BITS = 8;

    private RandomHyperplaneHash()
    {

    }

    public static BitSet rhh(ByteBuffer key)
    {
        int dimension = key.remaining() / 8;

        double[] vectorKey = getVectorKey(key, dimension);
        double[][] vectors = getVectors(dimension);

        BitSet hash = new BitSet(BITS);

        double sum;

        for (int i = 0; i < vectors.length; i++)
        {
            sum = scalarProduct(vectorKey, vectors[i]);

            if (sum >= 0)
            {
                hash.set(i, true);
            }
            else
            {
                hash.set(i, false);
            }
        }

        return hash;
    }

    public static BitSet rhh()
    {
        Random seed = new Random();
        byte[] bytes = new byte[(seed.nextInt(Integer.MAX_VALUE) + 1) * 8];
        seed.nextBytes(bytes);
        ByteBuffer key = ByteBuffer.wrap(bytes);

        return rhh(key);
    }

    private static double[] getVectorKey(ByteBuffer key, int dimension)
    {
        double[] vector = new double[dimension];

        for (int i = 0; i < dimension; i++)
        {
            vector[i] = key.getDouble();
        }

        return vector;
    }

    private static double[][] getVectors(int dimension)
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
}
