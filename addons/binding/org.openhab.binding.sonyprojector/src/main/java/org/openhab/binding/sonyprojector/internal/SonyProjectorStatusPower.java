package org.openhab.binding.sonyprojector.internal;

import java.util.Arrays;

public class SonyProjectorStatusPower {

    public static final byte[] Standby = new byte[] { 0x00, 0x00 };
    public static final byte[] StartUp = new byte[] { 0x00, 0x01 };
    public static final byte[] SartUpLamp = new byte[] { 0x00, 0x02 };
    public static final byte[] PowerOn = new byte[] { 0x00, 0x03 };
    public static final byte[] Cooling1 = new byte[] { 0x00, 0x04 };
    public static final byte[] Cooling2 = new byte[] { 0x00, 0x05 };

    public static String getFromByteData(byte[] status) throws SonyProjectorConnectorException {
        if (Arrays.equals(status, Standby)) {
            return "Standby";
        } else if (Arrays.equals(status, StartUp)) {
            return "Start Up";
        } else if (Arrays.equals(status, SartUpLamp)) {
            return "Sartup Lamp";
        } else if (Arrays.equals(status, PowerOn)) {
            return "Power On";
        } else if (Arrays.equals(status, Cooling1)) {
            return "Cooling1";
        } else if (Arrays.equals(status, Cooling2)) {
            return "Cooling2";
        }
        throw new SonyProjectorConnectorException("Invalid calibration preset data: " + Integer.toHexString(status[1]));
    }
}
