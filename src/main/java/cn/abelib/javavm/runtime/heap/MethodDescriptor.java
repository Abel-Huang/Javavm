package cn.abelib.javavm.runtime.heap;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/5/31 23:40
 */
public class MethodDescriptor {
    private List<String> parameterTypes;
    private String returnType;
    private String raw;
    private int offset;

    public MethodDescriptor() {
        this.parameterTypes = Lists.newArrayList();
    }

    public List<String> getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(List<String> parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    /**
     * parseMethodDescriptor
      */
    public void parseMethodDescriptor(String descriptor) {
        this.raw = descriptor;
        startParams();
        parseParamTypes();
        endParams();
        parseReturnType();
        finish();
    }

    /**
     * start check
     */
    private void startParams() {
        if (this.readChar() != '(') {
            throw new RuntimeException("BAD descriptor: " + raw);
        }
    }

    private void parseParamTypes() {
        while (true) {
            String t = this.parseFieldType();
            if (!t.equals("")) {
                this.addParameterType(t);
            } else {
                break;
            }
        }
    }

    private void addParameterType(String t) {
        this.parameterTypes.add(t);
    }

    private void endParams() {
        if (this.readChar() != ')') {
            throw new RuntimeException("BAD descriptor: " + raw);
        }
    }

    private void parseReturnType() {
        if (this.readChar() == 'V') {
            this.returnType = "V";
            return;
        }

        this.unreadChar();
        String t = this.parseFieldType();
        if (!t.equals("")) {
            this.returnType = t;
            return;
        }

        throw new RuntimeException("BAD descriptor: " + raw);
    }

    public void finish() {
        if (offset != raw.length()) {
            throw new RuntimeException("BAD descriptor: " + raw);
        }
    }

    private char readChar() {
        char c = this.raw.charAt(this.offset);
        this.offset++;
        return c;
    }

    private void unreadChar() {
        this.offset--;
    }

    private String parseFieldType() {
        switch (this.readChar()) {
            case 'B':
                return "B";
            case 'C':
                return "C";
            case 'D':
                return "D";
            case 'F':
                return "F";
            case 'I':
                return "I";
            case 'J':
                return "J";
            case 'S':
                return "S";
            case 'Z':
                return "Z";
            case 'L':
                return this.parseObjectType();
            case '[':
                return this.parseArrayType();
            default:
                this.unreadChar();
                return "";
        }
    }

    private String parseObjectType() {
        String unread = this.raw.substring(this.offset);
        int semicolonIndex = unread.indexOf(';');
        if (semicolonIndex == -1) {
            throw new RuntimeException("BAD descriptor: " + raw);
        } else {
            int objStart = this.offset - 1;
            int objEnd = this.offset + semicolonIndex + 1;
            this.offset = objEnd;
            return this.raw.substring(objStart, objEnd);
        }
    }

    private String parseArrayType() {
        int arrStart = this.offset - 1;
        this.parseFieldType();
        int arrEnd = this.offset;
        return this.raw.substring(arrStart, arrEnd);
    }
}