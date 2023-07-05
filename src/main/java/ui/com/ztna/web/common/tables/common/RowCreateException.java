package ui.com.ztna.web.common.tables.common;

import ui.com.ztna.web.common.tables.Row;

public class RowCreateException extends RuntimeException{

    public <T extends Row> RowCreateException(Class<T> tClass){
        super("Cannot Create Row of type: " + tClass.getName());
    }
}
