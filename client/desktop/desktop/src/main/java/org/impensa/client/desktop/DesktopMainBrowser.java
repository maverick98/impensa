package org.impensa.client.desktop;

public class DesktopMainBrowser {

    static {
        try {
            DesktopMain.onPageLoad();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }
}
