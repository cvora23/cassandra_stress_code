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

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.cassandra.db.marshal.AbstractType;
import org.apache.cassandra.db.marshal.TupleType;

/**
 * Created by root on 8/20/17.
 */
public class Tuples extends Generator<ByteBuffer>
{
    final Generator tupleType1;
    final Generator tupleType2;

    public Tuples(String name,Generator tupleType1,Generator tupleType2,GeneratorConfig config){
        super(getTupleTypeInstance(tupleType1,tupleType2),config,name,ByteBuffer.class);
        this.tupleType1 = tupleType1;
        this.tupleType2 = tupleType2;
    }

    private static TupleType getTupleTypeInstance(Generator tupleType1,Generator tupleType2){
        List<AbstractType<?>> abstractTypeList = new ArrayList<AbstractType<?>>();
        abstractTypeList.add(tupleType1.type);
        abstractTypeList.add(tupleType2.type);
        TupleType tupleType = new TupleType(abstractTypeList);
        return  tupleType;
    }

    public ByteBuffer generate()
    {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.BYTES + Long.BYTES);
        Integer integer = (int)identityDistribution.next();
        Long bigInteger = identityDistribution.next();
        byteBuffer.putInt(0,integer);
        byteBuffer.putLong(4,bigInteger);
        return byteBuffer;
    }
}
