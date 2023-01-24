# AndroidExplorer
* This is a file operation library specially designed for Android. You can use it to access files in any directory on Android4.4~Android13, and even the coding method is very similar to java.io.File, which has low learning cost.

## How to
Add it in your root build.gradle at the end of repositories:
Step 1. Add the JitPack repository to your build file
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Step 2. Add the dependency
```gradle
dependencies {
	implementation 'com.github.lumyuan:ly-file:v0.0.1'
}
```

[![](https://jitpack.io/v/lumyuan/ly-file.svg)](https://jitpack.io/#lumyuan/ly-file)
