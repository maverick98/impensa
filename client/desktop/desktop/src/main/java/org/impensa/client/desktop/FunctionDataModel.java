package org.impensa.client.desktop;

import net.java.html.json.ComputedProperty;
import net.java.html.json.Function;
import net.java.html.json.Model;
import net.java.html.json.Property;

/** Model annotation generates class Data with 
 * one message property, boolean property and read only words property
 */
@Model(className = "FunctionData", properties = {
    @Property(name = "functionName", type = String.class),
    @Property(name = "functionDescription", type = String.class)
})
final class FunctionDataModel {
    /*@ComputedProperty static java.util.List<String> words(String message) {
    String[] arr = new String[6];
    String[] words = message == null ? new String[0] : message.split(" ", 6);
    for (int i = 0; i < 6; i++) {
    arr[i] = words.length > i ? words[i] : "!";
    }
    return java.util.Arrays.asList(arr);
    }*/
    
    @Function static void setName(FunctionData model) {
        
        model.setFunctionName("abc");
    }
    @Function static void setNameAgain(final FunctionData model) {
        confirmByUser("Really turn off?", new Runnable() {
            @Override
            public void run() {
                model.setFunctionName("def");
            }
        });
    }
    
    /** Shows direct interaction with JavaScript */
    @net.java.html.js.JavaScriptBody(
        args = { "msg", "callback" }, 
        javacall = true, 
        body = "alert(msg); callback.@java.lang.Runnable::run()();"
    )
    static native void confirmByUser(String msg, Runnable callback);
}
