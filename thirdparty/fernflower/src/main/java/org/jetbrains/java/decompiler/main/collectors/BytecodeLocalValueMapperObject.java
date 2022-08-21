package org.jetbrains.java.decompiler.main.collectors;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.function.Consumer;

public class BytecodeLocalValueMapperObject {
    private final HashSet<Integer> indexSet = new HashSet<>();
    private final LinkedList<BytecodeLocalValueMapperDescription> bytecodeLocalValueMapperDescriptions = new LinkedList<>();

    public BytecodeLocalValueMapperObject(){

    }

    private boolean checkIndexExist(int index){
        if (indexSet.contains(index)){
            return true;
        }else {
            indexSet.add(index);
            return false;
        }
    }

    public void add(BytecodeLocalValueMapperDescription description){
//        if(checkIndexExist(description.index)) return;
        bytecodeLocalValueMapperDescriptions.add(description);
    }

    public void travel(Consumer<BytecodeLocalValueMapperDescription> action){
        bytecodeLocalValueMapperDescriptions.forEach(action);
    }
}
