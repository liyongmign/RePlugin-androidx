#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_pluginapp_so_TestSo_testMessage(JNIEnv *env) {
    return env->NewStringUTF("This is a message from so.");
}
