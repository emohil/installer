package com.company.api.fw.service.impl.query;

public abstract class AbstractWhere implements Where {

    @Override
    public Where and(Where... others) {
        return join(LogicalOps.AND, this, others);
    }

    @Override
    public Where not() {
        return Wheres.unite(LogicalOps.NOT, this);
    }

    @Override
    public Where or(Where... others) {
        return join(LogicalOps.OR, this, others);
    }

    private Where join(LogicalOp logical, Where one, Where... others) {
        if (others == null || others.length == 0)
            throw new IllegalArgumentException("Others cannot be null or empty.");

        Where[] ws = new Where[others.length + 1];
        ws[0] = one;
        for (int i = 0; i < others.length; i++) {
            ws[i + 1] = others[i];
        }
        return Wheres.unite(logical, ws);
    }

}
