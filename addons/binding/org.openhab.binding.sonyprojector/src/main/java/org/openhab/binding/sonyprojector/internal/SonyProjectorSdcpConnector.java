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

import org.eclipse.smarthome.core.util.HexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for communicating with Sony Projectors through an IP connectiopn
 * using Pj Talk service (SDCP protocol)
 *
 * @author Markus Wehrle - Initial contribution
 * @author Laurent Garnier - Use byte[] rather than String for binary messages
 */
public class SonyProjectorSdcpConnector extends SonyProjectorConnector {

    private final Logger logger = LoggerFactory.getLogger(SonyProjectorSdcpConnector.class);

    private static final int DEFAULT_PORT = 53484;
    private static final String DEFAULT_COMMUNITY = "SONY";

    private static final byte[] HEADER = new byte[] { 0x02, 0x0A };
    private static final byte[] SET = new byte[] { 0x00 };
    private static final byte[] GET = new byte[] { 0x01 };

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
    protected byte[] getSetting(SonyProjectorCommand command) throws SonyProjectorConnectorException {
        logger.debug("Sony Projector SDCP getSetting: {}", command.getName());

        byte[] cmd = command.getCommandCode();
        byte[] dataLength = new byte[] { (byte) 0 };
        byte[] message = new byte[HEADER.length + community.length() + GET.length + cmd.length + dataLength.length];
        int len = 0;
        System.arraycopy(HEADER, 0, message, len, HEADER.length);
        len += HEADER.length;
        System.arraycopy(community.getBytes(), 0, message, len, community.length());
        len += community.length();
        System.arraycopy(GET, 0, message, len, GET.length);
        len += GET.length;
        System.arraycopy(cmd, 0, message, len, cmd.length);
        len += cmd.length;
        System.arraycopy(dataLength, 0, message, len, dataLength.length);

        byte[] result = getResponseData(sendCommand(message));

        logger.debug("Sony Projector SDCP getSetting result data: {}", HexUtils.bytesToHex(result));

        return result;
    }

    @Override
    protected void setSetting(SonyProjectorCommand command, byte[] data) throws SonyProjectorConnectorException {
        logger.debug("Sony Projector SDCP setSetting: {}, data: {}", command.getName(), HexUtils.bytesToHex(data));

        byte[] cmd = command.getCommandCode();
        byte[] dataLength = new byte[] { (byte) data.length };
        byte[] message = new byte[HEADER.length + community.length() + SET.length + cmd.length + dataLength.length
                + data.length];
        int len = 0;
        System.arraycopy(HEADER, 0, message, len, HEADER.length);
        len += HEADER.length;
        System.arraycopy(community.getBytes(), 0, message, len, community.length());
        len += community.length();
        System.arraycopy(SET, 0, message, len, SET.length);
        len += SET.length;
        System.arraycopy(cmd, 0, message, len, cmd.length);
        len += cmd.length;
        System.arraycopy(dataLength, 0, message, len, dataLength.length);
        len += dataLength.length;
        System.arraycopy(data, 0, message, len, data.length);

        sendCommand(message);

        logger.debug("Sony Projector SDCP setSetting successful");
    }

    private byte[] sendCommand(byte[] message) throws SonyProjectorConnectorException {
        logger.debug("Sony Projector SDCP sendCommand: {}", HexUtils.bytesToHex(message));

        // SDCP always reads 12 bytes
        byte[] receivedMessage = new byte[12];

        // connect
        try {
            Socket clientSocket = new Socket(this.address, this.port);

            DataOutputStream dataOut = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream dataIn = new DataInputStream(clientSocket.getInputStream());

            // send request in bytes
            dataOut.write(message);

            int count = dataIn.read(receivedMessage);
            if (count != 12) {
                logger.debug("Sony Projector SDCP - unexpected data length received in response: {]", count);
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
        logger.debug("Sony Projector SDCP validateReceivedMessage: {}", HexUtils.bytesToHex(message));

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
