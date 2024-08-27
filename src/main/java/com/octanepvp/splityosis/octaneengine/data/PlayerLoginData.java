package com.octanepvp.splityosis.octaneengine.data;

import com.octanepvp.splityosis.octaneengine.OctaneEngine;
import com.octanepvp.splityosis.octaneengine.utility.TimeUnit;

import java.io.File;

public class PlayerLoginData extends DataFile {

    public PlayerLoginData() {
        super(new File(OctaneEngine.getInstance().getDataFolder(), "player-login-data.yml"), OctaneEngine.getInstance(), TimeUnit.SECONDS.toTicks(20));
    }




}
