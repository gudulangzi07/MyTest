package com.mytest.utils.commons;

/**
 * Thrown to indicate that the <em>Bean Access Language</em> cannot execute query
 * against given bean since a nested bean referenced is null.
 *
 * @since 1.7
 * @version $Id$
 */

public class NestedNullException extends BeanAccessLanguageException {

    // --------------------------------------------------------- Constuctors

    /**
     * Constructs a <code>NestedNullException</code> without a detail message.
     */
    public NestedNullException() {
        super();
    }

    /**
     * Constructs a <code>NestedNullException</code> without a detail message.
     *
     * @param message the detail message explaining this exception
     */
    public NestedNullException(final String message) {
        super(message);
    }
}
