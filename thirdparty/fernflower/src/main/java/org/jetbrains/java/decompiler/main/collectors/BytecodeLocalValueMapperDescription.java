package org.jetbrains.java.decompiler.main.collectors;

import org.jetbrains.java.decompiler.struct.attr.StructLocalVariableTableAttribute;

public class BytecodeLocalValueMapperDescription {
    public String name;
    public String desc;
    public String signature;
    public int start;
    public int end;
    public int index;
    public BytecodeLocalValueMapperDescription(){
        this.name = "";
        this.desc = "";
        this.signature = "";
        this.start = -1;
        this.end = -1;
        this.index = -1;
    }

    public BytecodeLocalValueMapperDescription setValue(String name, String desc, String signature, int start, int end, int index){
        this.name = name;
        this.desc = desc;
        this.signature = signature;
        this.start = start;
        this.end = end;
        this.index = index;
        return this;
    }

    public BytecodeLocalValueMapperDescription fromStructLocalVariableTableAttributeLocalVariable (StructLocalVariableTableAttribute.LocalVariable localVariable){
        this.name = localVariable.name;
        this.desc = localVariable.descriptor;
        this.start = localVariable.start_pc;
        this.end = localVariable.length;
        this.index = localVariable.index;

        return this;
    }

}
