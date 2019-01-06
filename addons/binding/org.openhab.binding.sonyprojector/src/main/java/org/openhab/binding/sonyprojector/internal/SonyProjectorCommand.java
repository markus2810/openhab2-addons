/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.sonyprojector.internal;

/**
 * Represents the different kinds of commands
 *
 * @author Markus Wehrle - Initial contribution
 * @author Laurent Garnier - Transform into an enum
 */
public enum SonyProjectorCommand {

    // Not available for VW40, VW50, VW60, VW70, VW85, VW95ES, VW100, VW1000ES, VW1100ES,
    // HW15, HW20, HW20A, HW30ES, HW30AES, HW35ES, HW40ES, HW50ES, HW55ES, HW58ES
    POWER("Power", new byte[] { 0x01, 0x30 }),

    INPUT("Input", new byte[] { 0x00, 0x01 }),
    CALIBRATION_PRESET("Picture Mode", new byte[] { 0x00, 0x02 }),
    CONTRAST("Contrast", new byte[] { 0x00, 0x10 }),
    BRIGHTNESS("Brigtness", new byte[] { 0x00, 0x11 }),
    COLOR("Color", new byte[] { 0x00, 0x12 }),
    HUE("Hue", new byte[] { 0x00, 0x13 }),
    SHARPNESS("Sharpness", new byte[] { 0x00, 0x14 }),
    COLOR_TEMP("Color Temperature", new byte[] { 0x00, 0x17 }),
    LAMP_CONTROL("Lamp Control", new byte[] { 0x00, 0x1A }),
    CONTRAST_ENHANCER("Contrast Enhancer", new byte[] { 0x00, 0x1C }),

    // Not available for VW315, VW320, VW328, VW365
    ADVANCED_IRIS("Advanced Iris", new byte[] { 0x00, 0x1D }),

    // Not available for VW315, VW320, VW328, VW365, VW515, VW520, VW528, VW665
    // HW60, HW65, HW68
    REAL_COLOR("Real Color Processing", new byte[] { 0x00, 0x1E }),

    // Not available for VW40, VW50, VW60
    FILM_MODE("Film Mode", new byte[] { 0x00, 0x1F }),

    WIDE_MODE("Wide Mode", new byte[] { 0x00, 0x20 }),
    COLOR_SPACE("Color Space", new byte[] { 0x00, 0x3B }),
    PICTURE_MUTING("Picture Muting", new byte[] { 0x00, 0x30 }),

    // Not available for VW40, VW50, VW60, VW70, VW100
    // HW15, HW20, HW20A
    MOTION_ENHANCER("Motion Enhancer", new byte[] { 0x00, 0x59 }),

    // Not available for VW40, VW50, VW60, VW100
    XVCOLOR("xvColor", new byte[] { 0x00, 0x5A }),

    // Not available for VW40, VW50, VW60, VW70, VW85, VW100
    // HW15, HW20, HW20A, HW30ES, HW30AES, HW35ES, HW40ES, HW50ES, HW55ES, HW58ES, HW60, HW65, HW68
    PICTURE_POSITION("Picture Position", new byte[] { 0x00, 0x66 }),

    // Not available for VW40, VW50, VW60, VW70, VW85, VW95ES, VW100
    // HW15, HW20, HW20A, HW30ES, HW30AES
    REALITY_CREATION("Reality Creation", new byte[] { 0x00, 0x67 }),

    // Not available for VW40, VW50, VW60, VW70, VW85, VW95ES, VW100, VW315, VW320, VW328, VW365, VW1000ES, VW1100ES
    // HW15, HW20, HW20A, HW30ES, HW30AES, HW35ES, HW40ES, HW50ES, HW55ES, HW58ES, HW60, HW65, HW68
    HDR("HDR", new byte[] { 0x00, 0x7C }),

    // Not available for VW40, VW50, VW60, VW70, VW85, VW95ES, VW100, VW1000ES, VW1100ES
    // HW15, HW20, HW20A, HW30ES, HW30AES, HW35ES, HW40ES, HW50ES, HW55ES, HW58ES
    INPUT_LAG_REDUCTION("Input Lag Reduction", new byte[] { 0x00, (byte) 0x99 }),

    STATUS_ERROR("Status Error", new byte[] { 0x01, 0x01 }),
    STATUS_POWER("Status Power", new byte[] { 0x01, 0x02 }),
    LAMP_TIMER("Lamp Timer", new byte[] { 0x01, 0x13 }),

    // Not available for VW40, VW50, VW60, VW70, VW100
    STATUS_ERROR2("Status Error 2", new byte[] { 0x01, 0x25 });

    private String name;
    private byte[] commandCode;

    private SonyProjectorCommand(String name, byte[] commandCode) {
        this.name = name;
        this.commandCode = commandCode;
    }

    public byte[] getCommandCode() {
        return commandCode;
    }

    public String getName() {
        return name;
    }

}
