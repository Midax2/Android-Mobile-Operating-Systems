#include <jni.h>
#include <algorithm>
#include <functional>

extern "C" JNIEXPORT void JNICALL
Java_com_pg_cppsorting_MainActivity_sortArray(JNIEnv *env, jobject thiz, jintArray array) {
    jint *elements = env->GetIntArrayElements(array, nullptr);
    jsize length = env->GetArrayLength(array);

    std::sort(elements, elements + length);

    env->ReleaseIntArrayElements(array, elements, 0);
}