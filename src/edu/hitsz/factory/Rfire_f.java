package edu.hitsz.factory;

import edu.hitsz.supply.Rfire;
import edu.hitsz.supply.supply;

public class Rfire_f implements supply_f{
    @Override
    public supply createSupply(int x, int y, int sx, int sy) {
        return new Rfire(x,y,sx,sy);
    }
}
