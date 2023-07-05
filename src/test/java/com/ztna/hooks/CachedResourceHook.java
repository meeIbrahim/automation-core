package com.ztna.hooks;

import io.cucumber.java.After;
import resource.cached.Cache;


public class CachedResourceHook {
    @After
    public void clearResourceCache(){
        Cache.clearAll();
    }
}
