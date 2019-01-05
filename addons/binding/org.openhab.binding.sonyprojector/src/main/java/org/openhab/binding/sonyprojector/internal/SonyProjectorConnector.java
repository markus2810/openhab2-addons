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

import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.types.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for communicating with Sony Projectors
 *
 * @author Markus Wehrle - Initial contribution
 */
public abstract class SonyProjectorConnector {

    private final Logger logger = LoggerFactory.getLogger(SonyProjectorConnector.class);

    public int getLampHours() throws SonyProjectorConnectorException {
        byte[] bytes = getSetting(SonyProjectorCommand.LampTimer);
        int val = ((bytes[0] & 0xff) << 8) | (bytes[1] & 0xff);
        return val;
    }

    public void setCalibrationPreset(Command cmd) throws SonyProjectorConnectorException {
        String presetData = new String(SonyProjectorCalibrationPreset.getFromName(cmd.toString()));
        setSetting(SonyProjectorCommand.CalibrationPreset, presetData);
    }

    public String getCalibrationPreset() throws SonyProjectorConnectorException {
        byte[] data = getSetting(SonyProjectorCommand.CalibrationPreset);
        return SonyProjectorCalibrationPreset.getFromByteData(data);
    }

    public void setPower(Command cmd) throws SonyProjectorConnectorException {
        logger.debug("setting sony projector power");

        String data;
        if (cmd == OnOffType.ON) {
            data = SonyProjectorData.On;
        } else if (cmd == OnOffType.OFF) {
            data = SonyProjectorData.Off;
        } else {
            logger.debug("setPower invalid cmd");
            return;
        }

        setSetting(SonyProjectorCommand.Power, data);
    }

    public OnOffType getPower() throws SonyProjectorConnectorException {
        byte[] data = getSetting(SonyProjectorCommand.StatusPower);

        if (Arrays.equals(data, SonyProjectorStatusPower.StartUp)
                || Arrays.equals(data, SonyProjectorStatusPower.SartUpLamp)
                || Arrays.equals(data, SonyProjectorStatusPower.PowerOn)) {
            return OnOffType.ON;
        }
        return OnOffType.OFF;
    }

    public String getStatusPower() throws SonyProjectorConnectorException {
        byte[] data = getSetting(SonyProjectorCommand.StatusPower);
        return SonyProjectorStatusPower.getFromByteData(data);
    }

    protected abstract byte[] getSetting(String cmd) throws SonyProjectorConnectorException;

    protected abstract void setSetting(String cmd, String data) throws SonyProjectorConnectorException;

}
