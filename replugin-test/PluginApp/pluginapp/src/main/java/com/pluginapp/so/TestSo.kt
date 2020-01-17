package com.pluginapp.so

/**
 * Test load so file.
 */
class TestSo {
    external fun testMessage(): String

    companion object {
        init {
            System.loadLibrary("plugin-test")
        }
    }
}