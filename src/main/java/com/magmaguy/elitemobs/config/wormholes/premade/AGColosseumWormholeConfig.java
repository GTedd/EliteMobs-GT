package com.magmaguy.elitemobs.config.wormholes.premade;

import com.magmaguy.elitemobs.config.wormholes.WormholeConfigFields;
import com.magmaguy.elitemobs.wormhole.Wormhole;

public class AGColosseumWormholeConfig extends WormholeConfigFields {
    public AGColosseumWormholeConfig() {
        super("ag_colosseum_wormhole",
                true,
                "em_adventurers_guild,296.5,103,308.5,-175,0",
                "the_colosseum_lair.yml",
                Wormhole.WormholeStyle.CRYSTAL);
        setBlindPlayer(true);
        setLocation1Text("&6『Colosseum Minidungeon』 &6Lvl 70");
        setParticleColor(0xFFB000);
    }
}
