buildscript {
    extra.apply {
        set("hilt_version", "2.44")
        set("compose_ui_version", "1.4.3")
        set("retrofit_version","2.9.0")
        set("coroutine_version","1.7.1")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("com.android.library") version "7.3.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.22" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
}

