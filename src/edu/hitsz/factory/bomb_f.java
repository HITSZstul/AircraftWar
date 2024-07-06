package edu.hitsz.factory;
import edu.hitsz.supply.bomb;
import edu.hitsz.supply.supply;

public class bomb_f implements supply_f{
    @Override
    public supply createSupply(int x, int y, int sx, int sy) {
        return new bomb(x,y,sx,sy);
    }
}
