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
 * for projector models VW315, VW320, VW328, VW365, VW515, VW520, VW528, VW665,
 * HW35ES, HW40ES, HW50ES, HW55ES, HW58ES, HW60, HW65, HW68
 *
 * @author Markus Wehrle - Initial contribution
 * @author Laurent Garnier - Transform into an enum
 */
public enum SonyProjectorCalibrationPreset {

    CINEMA_FILM1("Film1", new byte[] { 0x00, 0x00 }),
    CINEMA_FILM2("Film2", new byte[] { 0x00, 0x01 }),
    REFERENCE("Reference", new byte[] { 0x00, 0x02 }),
    TV("TV", new byte[] { 0x00, 0x03 }),
    PHOTO("Photo", new byte[] { 0x00, 0x04 }),
    GAME("Game", new byte[] { 0x00, 0x05 }),
    BRT_CINE("BRTCINE", new byte[] { 0x00, 0x06 }),
    BRT_TV("BRTTV", new byte[] { 0x00, 0x07 }),
    USER("User", new byte[] { 0x00, 0x08 });

    private String name;
    private byte[] dataCode;

    private SonyProjectorCalibrationPreset(String name, byte[] dataCode) {
        this.name = name;
        this.dataCode = dataCode;
    }

    public byte[] getDataCode() {
        return dataCode;
    }

    public String getName() {
        return name;
    }

    public static SonyProjectorCalibrationPreset getFromName(String presetName) throws SonyProjectorConnectorException {
        for (SonyProjectorCalibrationPreset value : SonyProjectorCalibrationPreset.values()) {
            if (value.getName().equals(presetName)) {
                return value;
            }
        }
        throw new SonyProjectorConnectorException("Invalid calibration preset name: " + presetName);
    }

    public static SonyProjectorCalibrationPreset getFromDataCode(byte[] dataCode)
            throws SonyProjectorConnectorException {
        for (SonyProjectorCalibrationPreset value : SonyProjectorCalibrationPreset.values()) {
            if (Arrays.equals(dataCode, value.getDataCode())) {
                return value;
            }
        }
        throw new SonyProjectorConnectorException("Invalid calibration preset data: " + HexUtils.bytesToHex(dataCode));
    }
}
