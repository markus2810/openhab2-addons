/**
 * Copyright (c) 2014,2018 by the respective copyright holders.
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.sonyprojector;

import static org.junit.Assert.*;

import org.openhab.binding.sonyprojector.internal.SonyProjectorHandler;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.ManagedThingProvider;
import org.eclipse.smarthome.core.thing.ThingProvider;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.binding.builder.BridgeBuilder;
import org.eclipse.smarthome.test.java.JavaOSGiTest;
import org.eclipse.smarthome.test.storage.VolatileStorageService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for {@link SonyProjectorHandler}.
 *
 * @author Markus - Initial contribution
 */
public class SonyProjectorOSGiTest extends JavaOSGiTest {

    private static final ThingTypeUID BRIDGE_THING_TYPE_UID = new ThingTypeUID("sonyprojector", "bridge");

    private ManagedThingProvider managedThingProvider;
    private final VolatileStorageService volatileStorageService = new VolatileStorageService();
    private Bridge bridge;

    @Before
    public void setUp() {
        registerService(volatileStorageService);
        
        managedThingProvider = getService(ThingProvider.class, ManagedThingProvider.class);
        assertNotNull(managedThingProvider);
        
        bridge = BridgeBuilder.create(BRIDGE_THING_TYPE_UID, "1").withLabel("My Bridge").build();
        assertNotNull(bridge);
    }

    @After
    public void tearDown() {
        managedThingProvider.remove(bridge.getUID());
        unregisterService(volatileStorageService);
    }

    @Test
    public void creationOfSonyProjectorHandler() {
        assertNull(bridge.getHandler());
        managedThingProvider.add(bridge);
        waitForAssert(() -> assertNotNull(managedThingProvider.get(bridge.getUID())));
    }
}