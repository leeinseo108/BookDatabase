package org.leeinseo108.bookdatabase;

import java.util.ArrayList;

public interface OnDatabaseCallback {
    public void insert(String name, String author, String contents);
    public ArrayList<BookInfo> selectAll();
}
