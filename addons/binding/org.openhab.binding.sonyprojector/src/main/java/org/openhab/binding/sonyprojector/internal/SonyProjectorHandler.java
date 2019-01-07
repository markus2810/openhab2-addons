/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.sonyprojector.internal;

import static org.openhab.binding.sonyprojector.internal.SonyProjectorBindingConstants.*;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link SonyProjectorHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Markus Wehrle - Initial contribution
 */
@NonNullByDefault
public class SonyProjectorHandler extends BaseThingHandler {

    private final Logger logger = LoggerFactory.getLogger(SonyProjectorHandler.class);

    private static final SonyProjectorModel DEFAULT_MODEL = SonyProjectorModel.VW520;

    @Nullable
    private SonyProjectorSdcpConnector connector;

    public SonyProjectorHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        if (connector == null) {
            return;
        }

        try {
            if (CHANNEL_POWER.equals(channelUID.getId())) {
                if (command instanceof RefreshType) {
                    // updateState(channelUID, connector.getPower());
                } else {
                    connector.setPower(command);
                }
            } else if (CHANNEL_POWERSTATE.equals(channelUID.getId())) {
                if (command instanceof RefreshType) {
                    updateState(channelUID, new StringType(connector.getStatusPower()));
                }
            } else if (CHANNEL_CALIBRATIONPRESET.equals(channelUID.getId())) {
                if (command instanceof RefreshType) {
                    updateState(channelUID, new StringType(connector.getCalibrationPreset()));
                } else {
                    connector.setCalibrationPreset(command);
                }
            }

        } catch (Exception e) {
            logger.debug(e.getMessage());
        }

        // if (CHANNEL_1.equals(channelUID.getId())) {

        // TODO: handle command

        // Note: if communication with thing fails for some reason,
        // indicate that by setting the status with detail information:
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
        // "Could not control device at IP address x.x.x.x");
        // }
    }

    @Override
    public void initialize() {
        logger.debug("Start initializing handler for thing {}", getThing().getUID());
        SonyProjectorConfiguration config = getConfigAs(SonyProjectorConfiguration.class);

        // TODO: Initialize the handler.
        // The framework requires you to return from this method quickly. Also, before leaving this method a thing
        // status from one of ONLINE, OFFLINE or UNKNOWN must be set. This might already be the real thing status in
        // case you can decide it directly.
        // In case you can not decide the thing status directly (e.g. for long running connection handshake using WAN
        // access or similar) you should set status UNKNOWN here and then decide the real status asynchronously in the
        // background.
        connector = new SonyProjectorSdcpConnector(config.host, config.port, config.community, DEFAULT_MODEL);

        // set the thing status to UNKNOWN temporarily and let the background task decide for the real status.
        // the framework is then able to reuse the resources from the thing handler initialization.
        // we set this upfront to reliably check status updates in unit tests.
        updateStatus(ThingStatus.UNKNOWN);

        scheduler.execute(() -> {
            boolean thingReachable = true;

            // thing is online, if we are able to read the port state

            try {
                connector.getPower();
            } catch (SonyProjectorConnectorException e) {
                thingReachable = false;
            }

            if (thingReachable) {
                updateStatus(ThingStatus.ONLINE);
            } else {
                updateStatus(ThingStatus.OFFLINE);
            }
        });

        logger.debug("Finished initializing!");

        // Note: When initialization can NOT be done set the status with more details for further
        // analysis. See also class ThingStatusDetail for all available status details.
        // Add a description to give user information to understand why thing does not work as expected. E.g.
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
        // "Can not access device as username and/or password are invalid");
    }
}