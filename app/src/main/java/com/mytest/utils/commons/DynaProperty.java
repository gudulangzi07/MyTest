package com.mytest.utils.commons;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;

public class DynaProperty implements Serializable {
    private static final int BOOLEAN_TYPE = 1;
    private static final int BYTE_TYPE = 2;
    private static final int CHAR_TYPE = 3;
    private static final int DOUBLE_TYPE = 4;
    private static final int FLOAT_TYPE = 5;
    private static final int INT_TYPE = 6;
    private static final int LONG_TYPE = 7;
    private static final int SHORT_TYPE = 8;
    protected String name;
    protected transient Class type;
    protected transient Class contentType;

    public DynaProperty(String name) {
        this(name, java.lang.Object.class);
    }

    public DynaProperty(String name, Class type) {
        this.name = null;
        this.type = null;
        this.name = name;
        this.type = type;
    }

    public DynaProperty(String name, Class type, Class contentType) {
        this.name = null;
        this.type = null;
        this.name = name;
        this.type = type;
        this.contentType = contentType;
    }

    public String getName() {
        return this.name;
    }

    public Class getType() {
        return this.type;
    }

    public Class getContentType() {
        return this.contentType;
    }

    private boolean isIndexed() {
        if (this.type == null) {
            return false;
        } else if (this.type.isArray()) {
            return true;
        } else {
            return (java.util.List.class).isAssignableFrom(this.type);
//            return (class$java$util$List == null ? (class$java$util$List = class$("java.util.List")) : class$java$util$List).isAssignableFrom(this.type);
        }
    }

    private boolean isMapped() {
        return this.type != null && (java.util.Map.class).isAssignableFrom(this.type);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("DynaProperty[name=");
        sb.append(this.name);
        sb.append(",type=");
        sb.append(this.type);
        if (this.isMapped() || this.isIndexed()) {
            sb.append(" <").append(this.contentType).append(">");
        }

        sb.append("]");
        return sb.toString();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        this.writeAnyClass(this.type, out);
        if (this.isMapped() || this.isIndexed()) {
            this.writeAnyClass(this.contentType, out);
        }

        out.defaultWriteObject();
    }

    private void writeAnyClass(Class clazz, ObjectOutputStream out) throws IOException {
        int primitiveType = 0;
        if (Boolean.TYPE.equals(clazz)) {
            primitiveType = 1;
        } else if (Byte.TYPE.equals(clazz)) {
            primitiveType = 2;
        } else if (Character.TYPE.equals(clazz)) {
            primitiveType = 3;
        } else if (Double.TYPE.equals(clazz)) {
            primitiveType = 4;
        } else if (Float.TYPE.equals(clazz)) {
            primitiveType = 5;
        } else if (Integer.TYPE.equals(clazz)) {
            primitiveType = 6;
        } else if (Long.TYPE.equals(clazz)) {
            primitiveType = 7;
        } else if (Short.TYPE.equals(clazz)) {
            primitiveType = 8;
        }

        if (primitiveType == 0) {
            out.writeBoolean(false);
            out.writeObject(clazz);
        } else {
            out.writeBoolean(true);
            out.writeInt(primitiveType);
        }

    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        this.type = this.readAnyClass(in);
        if (this.isMapped() || this.isIndexed()) {
            this.contentType = this.readAnyClass(in);
        }

        in.defaultReadObject();
    }

    private Class readAnyClass(ObjectInputStream in) throws IOException, ClassNotFoundException {
        if (in.readBoolean()) {
            switch(in.readInt()) {
                case 1:
                    return Boolean.TYPE;
                case 2:
                    return Byte.TYPE;
                case 3:
                    return Character.TYPE;
                case 4:
                    return Double.TYPE;
                case 5:
                    return Float.TYPE;
                case 6:
                    return Integer.TYPE;
                case 7:
                    return Long.TYPE;
                case 8:
                    return Short.TYPE;
                default:
                    throw new StreamCorruptedException("Invalid primitive type. Check version of beanutils used to serialize is compatible.");
            }
        } else {
            return (Class)in.readObject();
        }
    }
}
