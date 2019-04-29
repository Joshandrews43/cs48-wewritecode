/**
 * @author Grant Clark
 */

package com.wewritecode;

public class CommandLineParser {
    public String parse(String[] args) {
        String quarter = args[0] + " " + args[1];
        return quarter;
    }
}
