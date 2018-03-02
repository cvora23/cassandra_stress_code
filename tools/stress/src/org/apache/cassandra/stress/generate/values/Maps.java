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

package org.apache.cassandra.stress.generate.values;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cassandra.db.marshal.ListType;
import org.apache.cassandra.db.marshal.MapType;

/**
 * Created by root on 5/25/17.
 */
public class Maps<K,V> extends Generator<Map<K,V>>
{

    final Generator<K> keyType;
    final Generator<V> valueType;

    final K[] keyBuffer;
    final V[] valueBuffer;

    public Maps(String name,Generator<K> keyType,Generator<V> valueType,GeneratorConfig config){
        super(MapType.getInstance(keyType.type,valueType.type, true), config, name, Map.class);
        this.keyType = keyType;
        this.valueType = valueType;
        keyBuffer = (K[]) new Object[(int) sizeDistribution.maxValue()];
        valueBuffer = (V[]) new Object[(int) sizeDistribution.maxValue()];
    }

    public void setSeed(long seed){
        super.setSeed(seed);
        keyType.setSeed(seed * 31);
        valueType.setSeed(seed * 31);
    }

    public Map<K, V> generate()
    {
        final Map<K, V> map = new HashMap<K,V>();
        int size = (int) sizeDistribution.next();
        for (int i = 0 ; i < size ; i++){
            keyBuffer[i] = keyType.generate();
            valueBuffer[i] = valueType.generate();
            map.put(keyBuffer[i],valueBuffer[i]);
        }
        return map;
    }
}
