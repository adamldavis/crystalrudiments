/*
 * Created on Jan 27, 2005 by Adam. 
 * TODO File in this comment.
 *
 */
package net.crystalrudiments.common.logging;

import java.util.logging.Logger;


/**
 * DisabledLogger
 */
public class DisabledLogger extends Logger {

    /**
     * @param arg0
     * @param arg1
     */
    public DisabledLogger() {
        super("JMLogger", "jmiedor.logging");
    }
    public void info(String s) {;}
    public void warn(String s) {;}
    public void error(String s) {;}
    public void debug(String s) {;}
    public void fine(String s) {;}
    public void finer(String s) {;}
    public void finest(String s) {;}
}
