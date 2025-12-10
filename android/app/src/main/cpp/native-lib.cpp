#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_digitalsamurai_jni_1test_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_digitalsamurai_jni_1test_MainActivity_obama(JNIEnv *env, jobject thiz) {
//    const char *str = (*env).GetStringUTFChars(env,false);
    return env->NewStringUTF("obama");;
}