package edu.hitsz.factory;
import edu.hitsz.supply.Fire;
import edu.hitsz.supply.supply;

public class Fire_f implements supply_f{

    @Override
    public supply createSupply(int x, int y, int sx, int sy) {
        return new Fire(x,y,sx,sy);
    }
}
