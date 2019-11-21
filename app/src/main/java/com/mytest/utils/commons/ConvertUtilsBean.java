package com.mytest.utils.commons;

import java.lang.reflect.Array;

import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.BigIntegerConverter;
import org.apache.commons.beanutils.converters.BooleanArrayConverter;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.ByteArrayConverter;
import org.apache.commons.beanutils.converters.ByteConverter;
import org.apache.commons.beanutils.converters.CharacterArrayConverter;
import org.apache.commons.beanutils.converters.CharacterConverter;
import org.apache.commons.beanutils.converters.ClassConverter;
import org.apache.commons.beanutils.converters.DoubleArrayConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FileConverter;
import org.apache.commons.beanutils.converters.FloatArrayConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerArrayConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongArrayConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.ShortArrayConverter;
import org.apache.commons.beanutils.converters.ShortConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.beanutils.converters.SqlTimeConverter;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;
import org.apache.commons.beanutils.converters.StringArrayConverter;
import org.apache.commons.beanutils.converters.StringConverter;
import org.apache.commons.beanutils.converters.URLConverter;

public class ConvertUtilsBean {
    private FastHashMap converters = new FastHashMap();
//    private Log log;

    protected static org.apache.commons.beanutils.ConvertUtilsBean getInstance() {
        return BeanUtilsBean.getInstance().getConvertUtils();
    }

    public ConvertUtilsBean() {
//        this.log = LogFactory.getLog(class$org$apache$commons$beanutils$ConvertUtils == null ? (class$org$apache$commons$beanutils$ConvertUtils = class$("org.apache.commons.beanutils.ConvertUtils")) : class$org$apache$commons$beanutils$ConvertUtils);
        this.converters.setFast(false);
        this.deregister();
        this.converters.setFast(true);
    }

    public String convert(Object value) {
        if (value == null) {
            return (String)null;
        } else {
            Converter converter;
            if (value.getClass().isArray()) {
                if (Array.getLength(value) < 1) {
                    return null;
                } else {
                    value = Array.get(value, 0);
                    if (value == null) {
                        return (String)null;
                    } else {
                        converter = this.lookup(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
                        return (String)converter.convert(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String, value);
                    }
                }
            } else {
                converter = this.lookup(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
                return (String)converter.convert(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String, value);
            }
        }
    }

    public Object convert(String value, Class clazz) {
        if (this.log.isDebugEnabled()) {
            this.log.debug("Convert string '" + value + "' to class '" + clazz.getName() + "'");
        }

        Converter converter = this.lookup(clazz);
        if (converter == null) {
            converter = this.lookup(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
        }

//        if (this.log.isTraceEnabled()) {
//            this.log.trace("  Using converter " + converter);
//        }

        return converter.convert(clazz, value);
    }

    public Object convert(String[] values, Class clazz) {
        Class type = clazz;
        if (clazz.isArray()) {
            type = clazz.getComponentType();
        }

//        if (this.log.isDebugEnabled()) {
//            this.log.debug("Convert String[" + values.length + "] to class '" + type.getName() + "[]'");
//        }

        Converter converter = this.lookup(type);
        if (converter == null) {
            converter = this.lookup(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
        }

//        if (this.log.isTraceEnabled()) {
//            this.log.trace("  Using converter " + converter);
//        }

        Object array = Array.newInstance(type, values.length);

        for(int i = 0; i < values.length; ++i) {
            Array.set(array, i, converter.convert(type, values[i]));
        }

        return array;
    }

    public void deregister() {
        boolean[] booleanArray = new boolean[0];
        byte[] byteArray = new byte[0];
        char[] charArray = new char[0];
        double[] doubleArray = new double[0];
        float[] floatArray = new float[0];
        int[] intArray = new int[0];
        long[] longArray = new long[0];
        short[] shortArray = new short[0];
        String[] stringArray = new String[0];
        this.converters.clear();
        this.register((Class)(class$java$math$BigDecimal == null ? (class$java$math$BigDecimal = class$("java.math.BigDecimal")) : class$java$math$BigDecimal), (Converter)(new BigDecimalConverter()));
        this.register((Class)(class$java$math$BigInteger == null ? (class$java$math$BigInteger = class$("java.math.BigInteger")) : class$java$math$BigInteger), (Converter)(new BigIntegerConverter()));
        this.register((Class)Boolean.TYPE, (Converter)(new BooleanConverter(this.defaultBoolean)));
        this.register((Class)(class$java$lang$Boolean == null ? (class$java$lang$Boolean = class$("java.lang.Boolean")) : class$java$lang$Boolean), (Converter)(new BooleanConverter(this.defaultBoolean)));
        this.register((Class)booleanArray.getClass(), (Converter)(new BooleanArrayConverter(booleanArray)));
        this.register((Class)Byte.TYPE, (Converter)(new ByteConverter(this.defaultByte)));
        this.register((Class)(class$java$lang$Byte == null ? (class$java$lang$Byte = class$("java.lang.Byte")) : class$java$lang$Byte), (Converter)(new ByteConverter(this.defaultByte)));
        this.register((Class)byteArray.getClass(), (Converter)(new ByteArrayConverter(byteArray)));
        this.register((Class)Character.TYPE, (Converter)(new CharacterConverter(this.defaultCharacter)));
        this.register((Class)(class$java$lang$Character == null ? (class$java$lang$Character = class$("java.lang.Character")) : class$java$lang$Character), (Converter)(new CharacterConverter(this.defaultCharacter)));
        this.register((Class)charArray.getClass(), (Converter)(new CharacterArrayConverter(charArray)));
        this.register((Class)(class$java$lang$Class == null ? (class$java$lang$Class = class$("java.lang.Class")) : class$java$lang$Class), (Converter)(new ClassConverter()));
        this.register((Class)Double.TYPE, (Converter)(new DoubleConverter(this.defaultDouble)));
        this.register((Class)(class$java$lang$Double == null ? (class$java$lang$Double = class$("java.lang.Double")) : class$java$lang$Double), (Converter)(new DoubleConverter(this.defaultDouble)));
        this.register((Class)doubleArray.getClass(), (Converter)(new DoubleArrayConverter(doubleArray)));
        this.register((Class)Float.TYPE, (Converter)(new FloatConverter(this.defaultFloat)));
        this.register((Class)(class$java$lang$Float == null ? (class$java$lang$Float = class$("java.lang.Float")) : class$java$lang$Float), (Converter)(new FloatConverter(this.defaultFloat)));
        this.register((Class)floatArray.getClass(), (Converter)(new FloatArrayConverter(floatArray)));
        this.register((Class)Integer.TYPE, (Converter)(new IntegerConverter(this.defaultInteger)));
        this.register((Class)(class$java$lang$Integer == null ? (class$java$lang$Integer = class$("java.lang.Integer")) : class$java$lang$Integer), (Converter)(new IntegerConverter(this.defaultInteger)));
        this.register((Class)intArray.getClass(), (Converter)(new IntegerArrayConverter(intArray)));
        this.register((Class)Long.TYPE, (Converter)(new LongConverter(this.defaultLong)));
        this.register((Class)(class$java$lang$Long == null ? (class$java$lang$Long = class$("java.lang.Long")) : class$java$lang$Long), (Converter)(new LongConverter(this.defaultLong)));
        this.register((Class)longArray.getClass(), (Converter)(new LongArrayConverter(longArray)));
        this.register((Class)Short.TYPE, (Converter)(new ShortConverter(defaultShort)));
        this.register((Class)(class$java$lang$Short == null ? (class$java$lang$Short = class$("java.lang.Short")) : class$java$lang$Short), (Converter)(new ShortConverter(defaultShort)));
        this.register((Class)shortArray.getClass(), (Converter)(new ShortArrayConverter(shortArray)));
        this.register((Class)(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String), (Converter)(new StringConverter()));
        this.register((Class)stringArray.getClass(), (Converter)(new StringArrayConverter(stringArray)));
        this.register((Class)(class$java$sql$Date == null ? (class$java$sql$Date = class$("java.sql.Date")) : class$java$sql$Date), (Converter)(new SqlDateConverter()));
        this.register((Class)(class$java$sql$Time == null ? (class$java$sql$Time = class$("java.sql.Time")) : class$java$sql$Time), (Converter)(new SqlTimeConverter()));
        this.register((Class)(class$java$sql$Timestamp == null ? (class$java$sql$Timestamp = class$("java.sql.Timestamp")) : class$java$sql$Timestamp), (Converter)(new SqlTimestampConverter()));
        this.register((Class)(class$java$io$File == null ? (class$java$io$File = class$("java.io.File")) : class$java$io$File), (Converter)(new FileConverter()));
        this.register((Class)(class$java$net$URL == null ? (class$java$net$URL = class$("java.net.URL")) : class$java$net$URL), (Converter)(new URLConverter()));
    }

    private void register(Class clazz, Converter converter) {
        this.register(converter, clazz);
    }

    public void deregister(Class clazz) {
        this.converters.remove(clazz);
    }

    public Converter lookup(Class clazz) {
        return (Converter)this.converters.get(clazz);
    }

    public void register(Converter converter, Class clazz) {
        this.converters.put(clazz, converter);
    }
}

