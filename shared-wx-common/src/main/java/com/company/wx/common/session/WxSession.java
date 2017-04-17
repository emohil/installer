package com.company.wx.common.session;

import java.util.Enumeration;

/**
 * Provides a way to identify a user across more than one page request weixin
 * and to store information.
 * 
 */
public interface WxSession {

    /**
     * Returns the object bound with the specified name in this session, or
     * <code>null</code> if no object is bound under the name.
     * 
     * @param name
     *            a string specifying the name of the object
     * @return the object with the specified name
     */
    public Object getAttribute(String name);

    /**
     * Returns an <code>Enumeration</code> of <code>String</code> objects
     * containing the names of all the objects bound to this session.
     *
     * @return an <code>Enumeration</code> of <code>String</code> objects
     *         specifying the names of all the objects bound to this session
     */
    public Enumeration<String> getAttributeNames();

    /**
     * 
     * Binds an object to this session, using the name specified. If an object
     * of the same name is already bound to the session, the object is replaced.
     * 
     * @param name
     *            the name to which the object is bound; cannot be null
     * @param value
     *            the object to be bound
     */
    public void setAttribute(String name, Object value);

    /**
     * Removes the object bound with the specified name from this session. If
     * the session does not have an object bound with the specified name, this
     * method does nothing.
     * 
     * @param name
     *            the name of the object to remove from this session
     */
    public void removeAttribute(String name);

    /**
     * Invalidates this session then unbinds any objects bound to it.
     */
    public void invalidate();
}