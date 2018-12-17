package org.openhab.binding.sonyprojector.internal;

import java.util.Arrays;

public class SonyProjectorCalibrationPreset {

    private static final byte[] CinemaFilm1 = new byte[] { 0x00, 0x00 };
    private static final byte[] CinemaFilm2 = new byte[] { 0x00, 0x01 };
    private static final byte[] REF = new byte[] { 0x00, 0x02 };
    private static final byte[] TV = new byte[] { 0x00, 0x03 };
    private static final byte[] Photo = new byte[] { 0x00, 0x04 };
    private static final byte[] Game = new byte[] { 0x00, 0x05 };
    private static final byte[] BRTCINE = new byte[] { 0x00, 0x06 };
    private static final byte[] BRTTV = new byte[] { 0x00, 0x07 };
    private static final byte[] User = new byte[] { 0x00, 0x08 };

    public static byte[] getFromName(String presetName) throws SonyProjectorConnectorException {
        switch (presetName) {
            case "Film1":
                return CinemaFilm1;
            case "Film2":
                return CinemaFilm2;
            case "REF":
                return REF;
            case "TV":
                return TV;
            case "Photo":
                return Photo;
            case "Game":
                return Game;
            case "BRTCINE":
                return BRTCINE;
            case "BRTTV":
                return BRTTV;
            case "User":
                return User;

        }
        throw new SonyProjectorConnectorException("Invalid calibration preset name: " + presetName);
    }

    public static String getFromByteData(byte[] preset) throws SonyProjectorConnectorException {
        if (Arrays.equals(preset, CinemaFilm1)) {
            return "Film1";
        } else if (Arrays.equals(preset, CinemaFilm2)) {
            return "Film2";
        } else if (Arrays.equals(preset, REF)) {
            return "REF";
        } else if (Arrays.equals(preset, TV)) {
            return "TV";
        } else if (Arrays.equals(preset, Photo)) {
            return "Photo";
        } else if (Arrays.equals(preset, Game)) {
            return "Game";
        } else if (Arrays.equals(preset, BRTCINE)) {
            return "BRTCINE";
        } else if (Arrays.equals(preset, BRTTV)) {
            return "BRTTV";
        } else if (Arrays.equals(preset, User)) {
            return "User";
        }

        throw new SonyProjectorConnectorException("Invalid calibration preset data: " + Integer.toHexString(preset[1]));
    }
}
