package edu.hitsz.factory;

import edu.hitsz.supply.Add;
import edu.hitsz.supply.supply;

public class Add_f implements supply_f{
    @Override
    public supply createSupply(int x, int y, int sx, int sy) {
        return new Add(x,y,sx,sy);
    }
}
