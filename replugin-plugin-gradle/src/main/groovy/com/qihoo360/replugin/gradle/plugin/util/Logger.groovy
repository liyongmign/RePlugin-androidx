package com.qihoo360.replugin.gradle.plugin.util

/**
 * Log printer.
 */
public class Logger {
    public static def i(String tag, String message) {
        System.out.println("[INFO] [${tag}] ${message}")
    }

    public static def e(String tag, String message) {
        System.err.println("[ERROR] [${tag}] ${message}")
    }
}