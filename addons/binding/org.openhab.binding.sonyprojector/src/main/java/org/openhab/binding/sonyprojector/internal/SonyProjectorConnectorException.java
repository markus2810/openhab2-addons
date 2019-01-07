/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.sonyprojector.internal;

/**
 * @author Markus Wehrle - Initial contribution
 */
public class SonyProjectorConnectorException extends Exception {
    // Parameterless Constructor
    public SonyProjectorConnectorException() {
    }

    // Constructor that accepts a message
    public SonyProjectorConnectorException(String message) {
        super(message);
    }
}