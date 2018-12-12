package org.openhab.binding.sonyprojector.internal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

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
    private final String RequestTypeSet = new String(new byte[] { 0x00 });
    private final String RequestTypeGet = new String(new byte[] { 0x01 });

    private final String CommandPower = new String(new byte[] { 0x01, 0x30 });
    private final String DataOn = new String(new byte[] { 0x00, 0x01 });
    private final String DataOff = new String(new byte[] { 0x00, 0x00 });

    public SonyProjectorConnector(String address, int port) {
        Address = address;
        Port = port;
    }

    public void setPower(Command cmd) {
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
            data = DataOn;
        } else if (cmd == OnOffType.OFF) {
            data = DataOff;
        } else {
            logger.debug("setPower invalid cmd");
            return;
        }

        String dataLength = new String(new byte[] { (byte) data.length() });
        String message = Header + Community + RequestTypeSet + CommandPower + dataLength + data;
        byte[] b = message.getBytes();

        // connect
        try {

            Socket clientSocket = new Socket(Address, Port);

            DataOutputStream dataOut = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream dataIn = new DataInputStream(clientSocket.getInputStream());

            dataOut.write(b);
            int lengthIn = dataIn.readInt(); // read length of incoming message
            if (lengthIn > 0) {
                byte[] receiveMessage = new byte[lengthIn];
                dataIn.readFully(receiveMessage, 0, receiveMessage.length); // read the message
            }

            clientSocket.close();
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }

    }

}
