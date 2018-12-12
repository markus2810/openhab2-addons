package org.openhab.binding.sonyprojector;

import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.test.java.JavaTest;
import org.junit.Test;
import org.openhab.binding.sonyprojector.internal.SonyProjectorConnector;

public class SonyProjectorConnectorTest extends JavaTest {

    @Test
    public void initConnection() {
        SonyProjectorConnector spc = new SonyProjectorConnector("192.168.178.93", 53484);
        spc.setPower(OnOffType.OFF);
    }

}
