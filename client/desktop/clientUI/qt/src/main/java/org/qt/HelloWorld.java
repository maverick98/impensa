/**
  * This file is part of impensa.
  * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
  *
  * 1) Modify it if you can understand.
  * 2) If you distribute a modified version, you must do it at your own risk.
  *
  */
package org.qt;

import com.trolltech.qt.gui.*;
 
public class HelloWorld {
    public static void main(String args[]) {
        QApplication.initialize(args);
 
        QPushButton hello = new QPushButton("Hello World!");
        hello.show();
 
        QApplication.exec();
    }
}