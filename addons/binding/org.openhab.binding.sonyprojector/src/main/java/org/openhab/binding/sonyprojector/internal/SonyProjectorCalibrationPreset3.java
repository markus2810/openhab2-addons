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
 * Represents the different kinds of calibration preset (picture mode)
 * for projector models VW85
 *
 * @author Laurent Garnier - Initial contribution
 */
public enum SonyProjectorCalibrationPreset3 {

    DYNAMIC("Dynamic", new byte[] { 0x00, 0x00 }),
    STANDARD("Standard", new byte[] { 0x00, 0x01 }),
    CINEMA1("Cinema 1", new byte[] { 0x00, 0x02 }),
    CINEMA2("Cinema 2", new byte[] { 0x00, 0x03 }),
    CINEMA3("Cinema 3", new byte[] { 0x00, 0x04 }),
    USER("User", new byte[] { 0x00, 0x05 });

    private String name;
    private byte[] dataCode;

    private SonyProjectorCalibrationPreset3(String name, byte[] dataCode) {
        this.name = name;
        this.dataCode = dataCode;
    }

    public byte[] getDataCode() {
        return dataCode;
    }

    public String getName() {
        return name;
    }

    public static SonyProjectorCalibrationPreset3 getFromName(String presetName)
            throws SonyProjectorConnectorException {
        for (SonyProjectorCalibrationPreset3 value : SonyProjectorCalibrationPreset3.values()) {
            if (value.getName().equals(presetName)) {
                return value;
            }
        }
        throw new SonyProjectorConnectorException("Invalid calibration preset name: " + presetName);
    }

    public static SonyProjectorCalibrationPreset3 getFromDataCode(byte[] dataCode)
            throws SonyProjectorConnectorException {
        for (SonyProjectorCalibrationPreset3 value : SonyProjectorCalibrationPreset3.values()) {
            if (Arrays.equals(dataCode, value.getDataCode())) {
                return value;
            }
        }
        throw new SonyProjectorConnectorException("Invalid calibration preset data: " + HexUtils.bytesToHex(dataCode));
    }
}
