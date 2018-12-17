package org.openhab.binding.sonyprojector;

import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.test.java.JavaTest;
import org.junit.Test;
import org.openhab.binding.sonyprojector.internal.SonyProjectorCommand;
import org.openhab.binding.sonyprojector.internal.SonyProjectorConnector;

public class SonyProjectorConnectorTest extends JavaTest {

    private final static String host = "192.168.178.93";
    private final static int port = 53484;
    private final static String community = "SONY";

    private static SonyProjectorConnector spc = new SonyProjectorConnector(host, port, community);

    // @Test
    // public void setPowerOn() throws Exception {
    // spc.setPower(OnOffType.ON);
    // }

    @Test
    public void setPowerOff() throws Exception {
        spc.setPower(OnOffType.OFF);
    }

    @Test
    public void getPower() throws Exception {
        OnOffType power = spc.getPower();
    }

    @Test
    public void getPowerStatus() throws Exception {
        byte[] powerstatus = spc.getSetting(SonyProjectorCommand.StatusPower);
    }

    @Test
    public void setCalibrationPreset() throws Exception {
        spc.setCalibrationPreset(new StringType("Film2"));
    }

    @Test
    public void getCalibrationPreset() throws Exception {
        String preset = spc.getCalibrationPreset();
    }

    @Test
    public void getLampTimer() throws Exception {
        int value = spc.getLampHours();
    }
}
