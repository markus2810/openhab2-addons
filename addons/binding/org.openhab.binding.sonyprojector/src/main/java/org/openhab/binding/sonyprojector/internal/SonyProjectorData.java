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
public class SonyProjectorData {

    public static final String Header = new String(new byte[] { 0x02, 0x0A });
    public static final String On = new String(new byte[] { 0x00, 0x01 });
    public static final String Off = new String(new byte[] { 0x00, 0x00 });

}
