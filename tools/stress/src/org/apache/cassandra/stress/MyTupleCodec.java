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

package org.apache.cassandra.stress;
import java.nio.ByteBuffer;
import java.time.ZonedDateTime;
import java.util.List;

import com.datastax.driver.core.DataType;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.TupleType;
import com.datastax.driver.core.TypeCodec;

/**
 * Created by root on 8/20/17.
 */
public class MyTupleCodec extends TypeCodec.AbstractTupleCodec<ByteBuffer>
{
    public MyTupleCodec(TupleType tupleType)throws IllegalArgumentException{
        super(tupleType,ByteBuffer.class);
        List<DataType> types = tupleType.getComponentTypes();
        if(types.size() == 2 && types.get(0).equals(DataType.cint()) && types.get(1).equals(DataType.bigint())){
            // everything good
        }else{
            throw new IllegalArgumentException();
        }
    }

    protected ByteBuffer newInstance()
    {
        return null;
    }

    protected ByteBuffer serializeField(ByteBuffer source, int index, ProtocolVersion protocolVersion)
    {
        if(index == 0){
            int value = source.getInt(0);
            return cint().serializeNoBoxing(value,protocolVersion);
        }
        if(index == 1){
            long value = source.getLong(4);
            return bigint().serializeNoBoxing(value,protocolVersion);
        }
        throw new IndexOutOfBoundsException("Tuple index out of bounds. " + index);
    }

    protected ByteBuffer deserializeAndSetField(ByteBuffer input, ByteBuffer target, int index, ProtocolVersion protocolVersion)
    {
        if(index == 0){
            int value = cint().deserializeNoBoxing(input,protocolVersion);
            return target.putInt(0,value);
        }
        if(index == 1){
            long value = bigint().deserializeNoBoxing(input,protocolVersion);
            return target.putLong(4,value);
        }
        throw new IndexOutOfBoundsException("Tuple index out of bounds. " + index);
    }

    protected String formatField(ByteBuffer byteBuffer, int i)
    {
        return null;
    }

    protected ByteBuffer parseAndSetField(String s, ByteBuffer byteBuffer, int i)
    {
        return null;
    }
}
