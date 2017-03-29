package me.gurpreetsk.gopetting.data;

import ckm.simple.sql_provider.UpgradeScript;
import ckm.simple.sql_provider.annotation.ProviderConfig;
import ckm.simple.sql_provider.annotation.SimpleSQLConfig;

/**
 * Created by gurpreet on 29/03/17.
 */

@SimpleSQLConfig(name = "DataProvider",
        authority = "me.gurpreetsk.gopetting",
        database = "data.db",
        version = 1)
public class DataProviderConfig implements ProviderConfig {
    @Override
    public UpgradeScript[] getUpdateScripts() {
        return new UpgradeScript[0];
    }
}