package com.mytest.utils.commons;

import java.lang.reflect.InvocationTargetException;

/**
 * <p>Implementation of <code>DynaBean</code> that wraps a standard JavaBean
 * instance, so that DynaBean APIs can be used to access its properties,
 * though this implementation allows type conversion to occur when properties are set.
 * This means that (say) Strings can be passed in as values in setter methods and
 * this DynaBean will convert them to the correct primitive data types.</p>
 *
 * <p><strong>IMPLEMENTATION NOTE</strong> - This implementation does not
 * support the <code>contains()</code> and <code>remove()</code> methods.</p>
 *
 * @version $Id$
 */

public class ConvertingWrapDynaBean extends WrapDynaBean {



    /**
     * Construct a new <code>DynaBean</code> associated with the specified
     * JavaBean instance.
     *
     * @param instance JavaBean instance to be wrapped
     */
    public ConvertingWrapDynaBean(final Object instance) {

        super(instance);

    }


    /**
     * Set the value of the property with the specified name
     * performing any type conversions if necessary. So this method
     * can accept String values for primitive numeric data types for example.
     *
     * @param name Name of the property whose value is to be set
     * @param value Value to which this property is to be set
     *
     * @throws IllegalArgumentException if there are any problems
     *            copying the property.
     */
    @Override
    public void set(final String name, final Object value) {

        try {
            BeanUtilsBean.getInstance().copyProperty(instance, name, value);
        } catch (final InvocationTargetException ite) {
            final Throwable cause = ite.getTargetException();
            throw new IllegalArgumentException
                    ("Error setting property '" + name +
                              "' nested exception - " + cause);
        } catch (final Throwable t) {
            final IllegalArgumentException iae = new IllegalArgumentException
                    ("Error setting property '" + name +
                              "', exception - " + t);
            BeanUtilsBean.getInstance().initCause(iae, t);
            throw iae;
        }

    }
}
