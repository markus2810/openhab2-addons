package org.openhab.binding.sonyprojector.internal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
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
public class SonyProjectorConnector {

    private final Logger logger = LoggerFactory.getLogger(SonyProjectorHandler.class);

    private String Address;
    private int Port;
    private String Community = "SONY";

    private final String Header = new String(new byte[] { 0x02, 0x0A });

    public SonyProjectorConnector(String address, Integer port, String community) {
        Address = address;

        // init port
        if (port != null && port == 0) {
            Port = 53484;
        }

        // init community
        if (community != null && !community.isEmpty() && community.length() == 4) {
            Community = community;
        }

    }

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

    public OnOffType getPower() throws SonyProjectorConnectorException {
        byte[] data = getSetting(SonyProjectorCommand.Power);
        if (Arrays.equals(data, new byte[] { 0x00, 0x01 })) {
            return OnOffType.ON;
        }
        return OnOffType.OFF;
    }

    public byte[] getSetting(String cmd) throws SonyProjectorConnectorException {
        logger.debug("Sony Projectore getSetting: " + cmd);

        String dataLength = new String(new byte[] { (byte) 0 });
        String message = SonyProjectorData.Header + Community + SonyProjectorRequestType.Get + cmd + dataLength;
        byte[] result = sendCommand(message);

        logger.debug("Sony Projectore getSetting result: " + result);

        return getResponseData(result);
    }

    private void setSetting(String cmd, String data) throws SonyProjectorConnectorException {
        logger.debug("Sony Projectore setSetting: " + cmd + ", data: ", data);

        String dataLength = new String(new byte[] { (byte) data.length() });
        String message = SonyProjectorData.Header + Community + SonyProjectorRequestType.Set + cmd + dataLength + data;
        sendCommand(message);

        logger.debug("Sony Projectore setSetting successful");
    }

    public void setPower(Command cmd) throws SonyProjectorConnectorException {
        logger.debug("setting sony projector power");

        /*
         * byte[] message = new byte[12];
         * // addHeaderAndCommunity(message);
         *
         * // header
         * message[0] = 0x02;
         * message[1] = 0x0A;
         *
         * String string = new String(message);// , StandardCharsets.US_ASCII);
         *
         * // community
         * message[2] = 0x53; // S
         * message[3] = 0x4F; // O
         * message[4] = 0x4E; // N
         * message[5] = 0x59; // Y
         *
         * // command
         * message[6] = 0x00; // request type SET (0x00), GET (0x01)
         * message[7] = 0x01; // Command
         * message[8] = 0x30; // Command
         * message[9] = 0x02; // data length
         * message[10] = 0x00;
         *
         * // off
         * message[11] = 0x00;
         *
         * // on
         * // message[11] = 0x01;
         *
         * // SET_POWER: '0130'
         *
         */
        String data;
        if (cmd == OnOffType.ON) {
            data = SonyProjectorData.On;
        } else if (cmd == OnOffType.OFF) {
            data = SonyProjectorData.Off;
        } else {
            logger.debug("setPower invalid cmd");
            return;
        }

        String dataLength = new String(new byte[] { (byte) data.length() });
        String message = SonyProjectorData.Header + Community + SonyProjectorRequestType.Set
                + SonyProjectorCommand.Power + dataLength + data;
        byte[] b = message.getBytes();

        sendCommand(message);
        logger.debug("Sony Projectore result received: ");
    }

    private byte[] sendCommand(String message) throws SonyProjectorConnectorException {
        // SDCP always reads 12 bytes
        byte[] receivedMessage = new byte[12];

        // connect
        try {

            Socket clientSocket = new Socket(Address, Port);

            DataOutputStream dataOut = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream dataIn = new DataInputStream(clientSocket.getInputStream());

            // send request in bytes
            dataOut.write(message.getBytes());

            int count = dataIn.read(receivedMessage);
            if (count != 12) {
                logger.debug("sony projector - unexpected data length received in response");
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
