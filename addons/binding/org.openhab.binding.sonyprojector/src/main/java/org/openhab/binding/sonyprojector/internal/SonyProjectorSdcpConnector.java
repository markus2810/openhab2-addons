/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.sonyprojector.internal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for communicating with Sony Projectors through an IP connectiopn
 * using Pj Talk service (SDCP protocol)
 *
 * @author Markus Wehrle - Initial contribution
 */
public class SonyProjectorSdcpConnector extends SonyProjectorConnector {

    private final Logger logger = LoggerFactory.getLogger(SonyProjectorSdcpConnector.class);

    private static final int DEFAULT_PORT = 53484;
    private static final String DEFAULT_COMMUNITY = "SONY";

    private String address;
    private int port;
    private String community;

    public SonyProjectorSdcpConnector(String address, Integer port, String community) {
        this.address = address;

        // init port
        if (port != null && port > 0) {
            this.port = port;
        } else {
            this.port = DEFAULT_PORT;
        }

        // init community
        if (community != null && !community.isEmpty() && community.length() == 4) {
            this.community = community;
        } else {
            this.community = DEFAULT_COMMUNITY;
        }
    }

    @Override
    protected byte[] getSetting(String cmd) throws SonyProjectorConnectorException {
        logger.debug("Sony Projector SDCP getSetting: {}", cmd);

        String dataLength = new String(new byte[] { (byte) 0 });
        String message = SonyProjectorData.Header + community + SonyProjectorRequestType.Get + cmd + dataLength;
        byte[] result = sendCommand(message);

        logger.debug("Sony Projector SDCP getSetting result: {}", result);

        return getResponseData(result);
    }

    @Override
    protected void setSetting(String cmd, String data) throws SonyProjectorConnectorException {
        logger.debug("Sony Projector SDCP setSetting: {}, data: {}", cmd, data);

        String dataLength = new String(new byte[] { (byte) data.length() });
        String message = SonyProjectorData.Header + community + SonyProjectorRequestType.Set + cmd + dataLength + data;
        sendCommand(message);

        logger.debug("Sony Projector SDCP setSetting successful");
    }

    private byte[] sendCommand(String message) throws SonyProjectorConnectorException {
        // SDCP always reads 12 bytes
        byte[] receivedMessage = new byte[12];

        // connect
        try {
            Socket clientSocket = new Socket(this.address, this.port);

            DataOutputStream dataOut = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream dataIn = new DataInputStream(clientSocket.getInputStream());

            // send request in bytes
            dataOut.write(message.getBytes());

            int count = dataIn.read(receivedMessage);
            if (count != 12) {
                logger.debug("Sony Projector SDCP - unexpected data length received in response");
            }

            // close the socket
            clientSocket.close();
        } catch (Exception e) {
            throw new SonyProjectorConnectorException(e.getMessage());
        }

        validateReceivedMessage(receivedMessage);
        return receivedMessage;
    }

    private void validateReceivedMessage(byte[] message) throws SonyProjectorConnectorException {
        // header should be a sony projector header
        // TODO

        // community should be the same as used for sending
        // TODO

        // byte 7 is expected to be 1, which indicates that the request was succesful
        if (message[6] != 0x1) {
            throw new SonyProjectorConnectorException(
                    "Sending command failed! Received result from projector is not OK");
        }
    }

    private byte[] getResponseData(byte[] message) {
        // get data length (10th byte of message)
        int length = message[9];
        byte[] data = new byte[length];
        for (int i = 0; i < length; ++i) {
            data[i] = message[i + 10];
        }
        return data;
    }

}
