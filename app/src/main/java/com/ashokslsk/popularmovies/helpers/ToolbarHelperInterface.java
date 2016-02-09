package com.ashokslsk.popularmovies.helpers;

import android.view.Menu;

/**
 * Created by ashok.kumar on 09/02/16.
 */
public interface ToolbarHelperInterface {
    void toggleToolbarVisibility(boolean value);
    void setToolbarTitle(String toolbarTitle);
    void setToolbarTheme(int toolbarTheme);
    void setHomeUpEnabled(boolean value);
    void setToolbarTitleTextColor(int toolbarTitleTextColor);
    void setHomeUpIndicator(int homeUpIndicator);
    void setToolbarMenu(Menu menu);
    void setToolbarBackgroundColor(int color);
    void showMenuItems(boolean value);
}
