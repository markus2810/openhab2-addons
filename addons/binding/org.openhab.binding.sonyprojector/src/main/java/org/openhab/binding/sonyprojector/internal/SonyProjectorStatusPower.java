/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.sonyprojector.internal;

import java.util.Arrays;

/**
 * @author Markus Wehrle - Initial contribution
 */
public class SonyProjectorStatusPower {

    public static final byte[] Standby = new byte[] { 0x00, 0x00 };
    public static final byte[] StartUp = new byte[] { 0x00, 0x01 };
    public static final byte[] SartUpLamp = new byte[] { 0x00, 0x02 };
    public static final byte[] PowerOn = new byte[] { 0x00, 0x03 };
    public static final byte[] Cooling1 = new byte[] { 0x00, 0x04 };
    public static final byte[] Cooling2 = new byte[] { 0x00, 0x05 };
    public static final byte[] SavingCooling1 = new byte[] { 0x00, 0x06 };
    public static final byte[] SavingCooling2 = new byte[] { 0x00, 0x07 };
    public static final byte[] SavingStandby = new byte[] { 0x00, 0x08 };

    public static String getFromByteData(byte[] status) throws SonyProjectorConnectorException {
        if (Arrays.equals(status, Standby)) {
            return "Standby";
        } else if (Arrays.equals(status, StartUp)) {
            return "Start Up";
        } else if (Arrays.equals(status, SartUpLamp)) {
            return "Sartup Lamp";
        } else if (Arrays.equals(status, PowerOn)) {
            return "Power On";
        } else if (Arrays.equals(status, Cooling1)) {
            return "Cooling1";
        } else if (Arrays.equals(status, Cooling2)) {
            return "Cooling2";
        } else if (Arrays.equals(status, SavingCooling1)) {
            return "Saving Cooling1";
        } else if (Arrays.equals(status, SavingCooling2)) {
            return "Saving Cooling2";
        } else if (Arrays.equals(status, SavingStandby)) {
            return "Saving Standby";
        }
        throw new SonyProjectorConnectorException("Invalid calibration preset data: " + Integer.toHexString(status[1]));
    }
}
