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
 * for projector models VW1000ES, VW1100ES
 *
 * @author Laurent Garnier - Initial contribution
 */
public enum SonyProjectorCalibrationPreset5 {

    CINEMA_FILM1("Film1", new byte[] { 0x00, 0x00 }),
    CINEMA_FILM2("Film2", new byte[] { 0x00, 0x01 }),
    CINEMA_DIGITAL("Digital", new byte[] { 0x00, 0x02 }),
    REFERENCE("Reference", new byte[] { 0x00, 0x03 }),
    TV("TV", new byte[] { 0x00, 0x04 }),
    PHOTO("Photo", new byte[] { 0x00, 0x05 }),
    GAME("Game", new byte[] { 0x00, 0x06 }),
    BRT_CINE("BRTCINE", new byte[] { 0x00, 0x07 }),
    BRT_TV("BRTTV", new byte[] { 0x00, 0x08 });

    private String name;
    private byte[] dataCode;

    private SonyProjectorCalibrationPreset5(String name, byte[] dataCode) {
        this.name = name;
        this.dataCode = dataCode;
    }

    public byte[] getDataCode() {
        return dataCode;
    }

    public String getName() {
        return name;
    }

    public static SonyProjectorCalibrationPreset5 getFromName(String presetName)
            throws SonyProjectorConnectorException {
        for (SonyProjectorCalibrationPreset5 value : SonyProjectorCalibrationPreset5.values()) {
            if (value.getName().equals(presetName)) {
                return value;
            }
        }
        throw new SonyProjectorConnectorException("Invalid calibration preset name: " + presetName);
    }

    public static SonyProjectorCalibrationPreset5 getFromDataCode(byte[] dataCode)
            throws SonyProjectorConnectorException {
        for (SonyProjectorCalibrationPreset5 value : SonyProjectorCalibrationPreset5.values()) {
            if (Arrays.equals(dataCode, value.getDataCode())) {
                return value;
            }
        }
        throw new SonyProjectorConnectorException("Invalid calibration preset data: " + HexUtils.bytesToHex(dataCode));
    }
}
