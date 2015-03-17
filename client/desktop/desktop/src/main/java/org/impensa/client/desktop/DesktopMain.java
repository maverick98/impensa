package org.impensa.client.desktop;


import net.java.html.boot.BrowserBuilder;


/** Bootstrap and initialization. */
public final class DesktopMain {
    private DesktopMain() {
    }
    
    /** Launches the browser */
    public static void main(String... args) throws Exception {
        BrowserBuilder.newBrowser().
                
            loadPage("pages/index.html").
            loadClass(DesktopMain.class).
            invoke("onPageLoad", args).
            showAndWait();
        System.exit(0);
    }
    
    /** Called when page is ready */
    public static void onPageLoad(String... args) throws Exception {
        FunctionData d = new FunctionData();
        
        d.applyBindings();
    }
}
