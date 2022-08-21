package org.jetbrains.java.decompiler.main.collectors;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BytecodeLocalValueMapper {
    public Map<String, BytecodeLocalValueMapperObject> map = new HashMap<>();

    public BytecodeLocalValueMapper(){
    }

    public BytecodeLocalValueMapperObject getDescriptionObject(String methodSignature){
        if (map.containsKey(methodSignature)){
            return map.get(methodSignature);
        }else {
            BytecodeLocalValueMapperObject object = new BytecodeLocalValueMapperObject();
            map.put(methodSignature,object);
            return object;
        }
    }
}
