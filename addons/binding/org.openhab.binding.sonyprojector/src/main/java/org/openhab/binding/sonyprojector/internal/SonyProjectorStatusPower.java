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

import org.eclipse.smarthome.core.util.HexUtils;

/**
 * Represents the different kinds of power status
 *
 * @author Markus Wehrle - Initial contribution
 * @author Laurent Garnier - Transform into an enum
 */
public enum SonyProjectorStatusPower {

    STANDBY("Standby", new byte[] { 0x00, 0x00 }),
    START_UP("Start Up", new byte[] { 0x00, 0x01 }),
    STARTUP_LAMP("Sartup Lamp", new byte[] { 0x00, 0x02 }),
    POWER_ON("Power On", new byte[] { 0x00, 0x03 }),
    COOLING1("Cooling1", new byte[] { 0x00, 0x04 }),
    COOLING2("Cooling1", new byte[] { 0x00, 0x05 }),
    SAVING_COOLING1("Saving Cooling1", new byte[] { 0x00, 0x06 }),
    SAVING_COOLING2("Saving Cooling2", new byte[] { 0x00, 0x07 }),
    SAVING_STANDBY("Saving Standby", new byte[] { 0x00, 0x08 });

    private String name;
    private byte[] dataCode;

    private SonyProjectorStatusPower(String name, byte[] dataCode) {
        this.name = name;
        this.dataCode = dataCode;
    }

    public byte[] getDataCode() {
        return dataCode;
    }

    public String getName() {
        return name;
    }

    public static SonyProjectorStatusPower getFromDataCode(byte[] dataCode) throws SonyProjectorConnectorException {
        for (SonyProjectorStatusPower value : SonyProjectorStatusPower.values()) {
            if (Arrays.equals(dataCode, value.getDataCode())) {
                return value;
            }
        }
        throw new SonyProjectorConnectorException("Invalid status power data: " + HexUtils.bytesToHex(dataCode));
    }
}
