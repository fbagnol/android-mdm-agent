package org.flyve.mdm.agent.core.supervisor;

import android.content.Context;

/*
 *   Copyright (C) 2017 Teclib. All rights reserved.
 *
 *   This file is part of flyve-mdm-android-agent
 *
 * flyve-mdm-android-agent is a subproject of Flyve MDM. Flyve MDM is a mobile
 * device management software.
 *
 * Flyve MDM is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * Flyve MDM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * ------------------------------------------------------------------------------
 * @author    Rafael Hernandez
 * @date      9/8/17
 * @copyright Copyright (C) 2017 Teclib. All rights reserved.
 * @license   GPLv3 https://www.gnu.org/licenses/gpl-3.0.html
 * @link      https://github.com/flyve-mdm/flyve-mdm-android-agent
 * @link      https://flyve-mdm.com
 * ------------------------------------------------------------------------------
 */

public class SupervisorController {

    private SupervisorStorage cache;

    /**
     * Constructor
     * @param context
     */
    public SupervisorController(Context context) {
        cache = new SupervisorStorage(context);
    }

    /**
     * Get the cache
     * @return the local storage
     */
    public SupervisorModel getCache() {
        return cache.getSupervisor();
    }

    /**
     * Save the local storage
     * @param supervisor
     * @return boolean true if saved, else otherwise
     */
    public boolean save(SupervisorModel supervisor) {
        try {
            cache.setSupervisor(supervisor);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}