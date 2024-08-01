package com.octanepvp.splityosis.menulib;

import com.octanepvp.splityosis.menulib.MenuItem;

import java.util.Map;
public interface TitleUpdaterExecuter {

    String getTitle(int page, Map<Integer, MenuItem> items);
}
