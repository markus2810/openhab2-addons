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
 * Represents the different supported projector models
 *
 * @author Laurent Garnier - Initial contribution
 */
public enum SonyProjectorModel {

    // HW10("VPL-HW10", false, 2),
    HW15("VPL-HW15", false, 2),
    HW20("VPL-HW20", false, 2),
    HW30ES("VPL-HW30ES", false, 4),
    HW35ES("VPL-HW35ES", false, 1),
    HW40ES("VPL-HW40ES", false, 1),
    // HW45ES("VPL-HW45ES", false, 1),
    HW50ES("VPL-HW50ES", false, 1),
    HW55ES("VPL-HW55ES", false, 1),
    HW58ES("VPL-HW58ES", false, 1),
    HW60("VPL-HW60", true, 1),
    HW65("VPL-HW65", true, 1),
    HW68("VPL-HW68", true, 1),
    // VW10HT("VPL-VW10HT", false, 2),
    // VW11HT("VPL-VW11HT", false, 2),
    // VW12HT("VPL-VW12HT", false, 2),
    VW40("VPL-VW40", false, 2),
    VW50("VPL-VW50", false, 2),
    VW60("VPL-VW60", false, 2),
    VW70("VPL-VW70", false, 2),
    // VW80("VPL-VW80", false, 2),
    VW85("VPL-VW85", false, 3),
    // VW90("VPL-VW90", false, 4),
    VW95("VPL-VW95", false, 4),
    VW100("VPL-VW100", false, 2),
    // VW200("VPL-VW200", false, 2),
    // VW285("VPL-VW285", false, 2),
    // VW295("VPL-VW295", false, 2),
    // VW300("VPL-VW300", false, 2),
    VW315("VPL-VW315", true, 1),
    VW320("VPL-VW320", true, 1),
    VW328("VPL-VW328", true, 1),
    // VW350("VPL-VW350", false, 2),
    VW365("VPL-VW365", true, 1),
    // VW385("VPL-VW385", false, 2),
    // VW500("VPL-VW500", false, 2),
    VW515("VPL-VW515", true, 1),
    VW520("VPL-VW520", true, 1),
    VW528("VPL-VW528", true, 1),
    // VW600("VPL-VW600", false, 2),
    VW665("VPL-VW665", true, 1),
    // VW675("VPL-VW675", false, 2),
    // VW685("VPL-VW685", false, 2),
    // VW885("VPL-VW885", false, 2),
    VW1000ES("VPL-VW1000ES", false, 5),
    VW1100ES("VPL-VW1100ES", false, 5);
    // VW5000ES("VPL-VW5000ES", false, 5);

    private String name;
    private boolean powerCmdAvailable;
    private int idxCalibrPresets;

    private SonyProjectorModel(String name, boolean powerCmdAvailable, int idxCalibrPresets) {
        this.name = name;
        this.powerCmdAvailable = powerCmdAvailable;
        this.idxCalibrPresets = idxCalibrPresets;
    }

    public String getName() {
        return name;
    }

    public boolean isPowerCmdAvailable() {
        return powerCmdAvailable;
    }

    public byte[] getCalibrPresetDataCodeFromName(String presetName) throws SonyProjectorConnectorException {
        if (idxCalibrPresets == 1) {
            return SonyProjectorCalibrationPreset.getFromName(presetName).getDataCode();
        } else if (idxCalibrPresets == 2) {
            return SonyProjectorCalibrationPreset2.getFromName(presetName).getDataCode();
        } else if (idxCalibrPresets == 3) {
            return SonyProjectorCalibrationPreset3.getFromName(presetName).getDataCode();
        } else if (idxCalibrPresets == 4) {
            return SonyProjectorCalibrationPreset4.getFromName(presetName).getDataCode();
        } else if (idxCalibrPresets == 5) {
            return SonyProjectorCalibrationPreset5.getFromName(presetName).getDataCode();
        }
        throw new SonyProjectorConnectorException("Invalid idxCalibrPresets for model " + name);
    }

    public String getCalibrPresetNameFromDataCode(byte[] dataCode) throws SonyProjectorConnectorException {
        if (idxCalibrPresets == 1) {
            return SonyProjectorCalibrationPreset.getFromDataCode(dataCode).getName();
        } else if (idxCalibrPresets == 2) {
            return SonyProjectorCalibrationPreset2.getFromDataCode(dataCode).getName();
        } else if (idxCalibrPresets == 3) {
            return SonyProjectorCalibrationPreset3.getFromDataCode(dataCode).getName();
        } else if (idxCalibrPresets == 4) {
            return SonyProjectorCalibrationPreset4.getFromDataCode(dataCode).getName();
        } else if (idxCalibrPresets == 5) {
            return SonyProjectorCalibrationPreset5.getFromDataCode(dataCode).getName();
        }
        throw new SonyProjectorConnectorException("Invalid idxCalibrPresets for model " + name);
    }
}
