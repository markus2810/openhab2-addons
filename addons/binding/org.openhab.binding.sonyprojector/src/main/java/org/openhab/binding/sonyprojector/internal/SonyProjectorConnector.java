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
 * @author Laurent Garnier - Use ENUMs and use byte[] rather than String for binary messages
 */
public abstract class SonyProjectorConnector {

    private final Logger logger = LoggerFactory.getLogger(SonyProjectorConnector.class);

    private static final byte[] POWER_ON = new byte[] { 0x00, 0x01 };
    private static final byte[] POWER_OFF = new byte[] { 0x00, 0x00 };

    public int getLampHours() throws SonyProjectorConnectorException {
        byte[] bytes = getSetting(SonyProjectorCommand.LAMP_TIMER);
        int val = ((bytes[0] & 0xff) << 8) | (bytes[1] & 0xff);
        return val;
    }

    public void setCalibrationPreset(Command cmd) throws SonyProjectorConnectorException {
        byte[] presetData = SonyProjectorCalibrationPreset.getFromName(cmd.toString()).getDataCode();
        setSetting(SonyProjectorCommand.CALIBRATION_PRESET, presetData);
    }

    public String getCalibrationPreset() throws SonyProjectorConnectorException {
        return SonyProjectorCalibrationPreset.getFromDataCode(getSetting(SonyProjectorCommand.CALIBRATION_PRESET))
                .getName();
    }

    public void setPower(Command cmd) throws SonyProjectorConnectorException {
        logger.debug("setting sony projector power");

        if (cmd == OnOffType.ON) {
            setSetting(SonyProjectorCommand.POWER, POWER_ON);
        } else if (cmd == OnOffType.OFF) {
            setSetting(SonyProjectorCommand.POWER, POWER_OFF);
        } else {
            logger.debug("setPower invalid cmd");
            return;
        }
    }

    public OnOffType getPower() throws SonyProjectorConnectorException {
        byte[] data = getSetting(SonyProjectorCommand.STATUS_POWER);

        if (Arrays.equals(data, SonyProjectorStatusPower.START_UP.getDataCode())
                || Arrays.equals(data, SonyProjectorStatusPower.STARTUP_LAMP.getDataCode())
                || Arrays.equals(data, SonyProjectorStatusPower.POWER_ON.getDataCode())) {
            return OnOffType.ON;
        }
        return OnOffType.OFF;
    }

    public String getStatusPower() throws SonyProjectorConnectorException {
        return SonyProjectorStatusPower.getFromDataCode(getSetting(SonyProjectorCommand.STATUS_POWER)).getName();
    }

    protected abstract byte[] getSetting(SonyProjectorCommand command) throws SonyProjectorConnectorException;

    protected abstract void setSetting(SonyProjectorCommand command, byte[] data)
            throws SonyProjectorConnectorException;

}
