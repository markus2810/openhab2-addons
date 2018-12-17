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
 * The {@link SonyProjectorConfiguration} class contains fields mapping thing configuration parameters.
 *
 * @author Markus Wehrle - Initial contribution
 */
public class SonyProjectorConfiguration {

    /**
     * The host name (IP Address) of the SONY projector
     */
    public String host;
    public Integer port;
    public String community;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void settPort(Integer port) {
        this.port = port;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }
}
