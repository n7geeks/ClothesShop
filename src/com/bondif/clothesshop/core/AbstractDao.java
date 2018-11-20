package com.bondif.clothesshop.core;

import java.sql.Connection;

public abstract class AbstractDao {
    public abstract Connection getConnection();
}
