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
 * @author Markus Wehrle - Initial contribution
 */
public class SonyProjectorCommand {

    public static final String Power = new String(new byte[] { 0x01, 0x30 });
    public static final String CalibrationPreset = new String(new byte[] { 0x00, 0x02 });
    public static final String Contrast = new String(new byte[] { 0x00, 0x10 });
    public static final String Brightness = new String(new byte[] { 0x00, 0x11 });
    public static final String Color = new String(new byte[] { 0x00, 0x12 });
    public static final String Hue = new String(new byte[] { 0x00, 0x13 });
    public static final String Sharpness = new String(new byte[] { 0x00, 0x14 });
    public static final String ColorTemperature = new String(new byte[] { 0x00, 0x17 });
    public static final String LampControl = new String(new byte[] { 0x00, 0x1A });
    public static final String ContrastEnhancer = new String(new byte[] { 0x00, 0x1C });
    public static final String AdvancedIris = new String(new byte[] { 0x00, 0x1D });
    public static final String FilmMode = new String(new byte[] { 0x00, 0x1F });
    public static final String ColorSpace = new String(new byte[] { 0x00, 0x3B });
    public static final String MotionFlow = new String(new byte[] { 0x00, 0x59 });
    public static final String xvColor = new String(new byte[] { 0x00, 0x5A });
    public static final String RealityCreation = new String(new byte[] { 0x00, 0x67 });
    public static final String HDR = new String(new byte[] { 0x00, 0x7C });
    public static final String InputLagReduction = new String(new byte[] { 0x00, (byte) 0x99 });
    public static final String PicturePosition = new String(new byte[] { 0x00, 0x30 });
    public static final String Aspect = new String(new byte[] { 0x00, 0x20 });
    public static final String Input = new String(new byte[] { 0x00, 0x01 });
    public static final String StatusError = new String(new byte[] { 0x01, 0x01 });
    public static final String StatusPower = new String(new byte[] { 0x01, 0x02 });
    public static final String LampTimer = new String(new byte[] { 0x01, 0x13 });
    public static final String StatusError2 = new String(new byte[] { 0x01, 0x25 });

}
